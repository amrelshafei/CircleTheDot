import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import util.EmptyStackException;
import util.LinkedQueue;
import util.LinkedStack;
import util.Pair;
import util.Point;
import util.Queue;

/**
 * The class <b>GameController</b> is the controller of the game. It implements 
 * the interface ActionListener to be called back when the player makes a move. It computes
 * the next step of the game, and then updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameController implements ActionListener {

    /** The reference to the game's view. */
    private final GameView gameView;

    /** The reference to the game's model. */
    private GameModel gameModel;

    /** The undo stack that holds all the undo-able states of the game. */
	private final LinkedStack<GameModel> undoStack;


    /** The redo stack that holds all the redo-able states of the game. */
    private final LinkedStack<GameModel> redoStack;
	
	
    /**
     * The constructor for the class <b>GameController</b>. It creates both the
     * view and the model instances of the game and automatically checks the
     * running directory to load any saved data for the game.
     *
     * @param size The size of the game's board.
     */
    public GameController(int size) throws ClassNotFoundException, IOException {
		try {
            String runningDir = CircleTheDot.getRunningFile().getParent();
			File save = new File(runningDir + "/last_save.dat");
			gameModel = deserialize(save);
			save.delete();
		} catch (FileNotFoundException | SecurityException e) {
			gameModel = new GameModel(size);
		} finally {
			gameView = new GameView(gameModel, this);
			undoStack = new LinkedStack<GameModel>(); // Initializes the undo stack
			redoStack = new LinkedStack<GameModel>(); // Initializes the redo stack
			gameView.update();
		}
    }

    /**
     * Resets the game.
     *
     * @throws EmptyStackException Thrown if one of the stacks is empty before
     * resetting the the game.
     */
    public void reset() throws EmptyStackException {
        gameModel.reset();
		undoStack.clear(); // Clears the undo stack
		redoStack.clear(); // Clears the redo stack
		gameView.update();
    }

    /**
     * Callback used when the user clicks a button or one of the dots.
     * Implements the logic of the game.
     *
     * @param e
     *            the ActionEvent
     */
    public void actionPerformed(ActionEvent e) throws NullPointerException, EmptyStackException {
        
        if (e.getSource() instanceof DotButton) {
            DotButton clicked = (DotButton)(e.getSource());

        	if (gameModel.getCurrentStatus(clicked.getColumn(),clicked.getRow()) == GameModel.AVAILABLE){

				GameModel undoCloned = (GameModel) gameModel.clone(); // Cloning the game model
				undoStack.push(undoCloned); // Pushes the cloned game model into the undo stack
				
				redoStack.clear(); // clears the redo stack 
				
				gameModel.select(clicked.getColumn(),clicked.getRow());
                oneStep();
            }
        } else if (e.getSource() instanceof JButton) {
            JButton clicked = (JButton)(e.getSource());

            if (clicked.getText().equals("Quit")) {
				gameModel.serialize();
                System.exit(0);
            } else if (clicked.getText().equals("Reset")) {
                reset();
			} else if (clicked.getText().equals("Undo")) { // Undo button is clicked
				undo(); // undo the game
			} else if (clicked.getText().equals("Redo")) { // redo button is clicked
				redo(); // redo the game
			}
        }
    }

	/**
     * Helper method: checks if a point is on the border of the board
     *
     * @param p
     *            the point to check
     *
     * @return true iff p is on the border of the board
     */
    private boolean isOnBorder(Point p) {
        return (p.getX() == 0 || p.getX() == gameModel.getSize() - 1 ||
                p.getY() == 0 || p.getY() == gameModel.getSize() - 1 );
    }

    /**
     * Computes the next step of the game. If the player has lost, it 
     * shows a dialog offering to replay.
     * If the user has won, it shows a dialog showing the number of 
     * steps that had been required in order to win. 
     * Else, it finds one of the shortest path for the blue dot to 
     * exit the board and moves it one step in that direction.
     */
    private void oneStep() {
        Point currentDot = gameModel.getCurrentDot();
        if(isOnBorder(currentDot)) {
            gameModel.setCurrentDot(-1,-1);
            gameView.update();
 
            Object[] options = {"Play Again",
                    "Quit"};
            int n = JOptionPane.showOptionDialog(gameView,
                    "You lost! Would you like to play again?",
                    "Lost",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if(n == 0){
                reset();
            } else{
                System.exit(0);
            }
        }
        else{
            Point direction = findDirection();
            if(direction.getX() == -1){
                gameView.update();
                Object[] options = {"Play Again",
                        "Quit"};
                int n = JOptionPane.showOptionDialog(gameView,
                        "Congratualtions, you won in " + gameModel.getNumberOfSteps() 
                            +" steps!\n Would you like to play again?",
                        "Won",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if(n == 0){
                    reset();
                } else{
                    System.exit(0);
                }
            }
            else{
                gameModel.setCurrentDot(direction.getX(), direction.getY());
                gameView.update();
            }
        }
 
    }

    /**
     * Does a ``breadth-first'' search from the current location of the blue dot to find
     * one of the shortest available path to exit the board. 
     *
     * @return the location (as a Point) of the next step for the blue dot toward the exit.
     * If the blue dot is encircled and cannot exit, returns an instance of the class Point 
     * at location (-1,-1)
     */
    private Point findDirection() {
        boolean[][] blocked = new boolean[gameModel.getSize()][gameModel.getSize()];

        for(int i = 0; i < gameModel.getSize(); i ++){
            for (int j = 0; j < gameModel.getSize(); j ++){
                blocked[i][j] = 
                    !(gameModel.getCurrentStatus(i,j) == GameModel.AVAILABLE);
            }
        }

        Queue<Pair<Point>> myQueue = new LinkedQueue<Pair<Point>>();
        
        LinkedList<Point> possibleNeighbours = new  LinkedList<Point>();

        // start with neighbours of the current dot
        // (note: we know the current dot isn't on the border)
        Point currentDot = gameModel.getCurrentDot();

        possibleNeighbours = findPossibleNeighbours(currentDot, blocked);

        // adding some non determinism into the game !
        java.util.Collections.shuffle(possibleNeighbours);

        for(int i = 0; i < possibleNeighbours.size() ; i++){
            Point p = possibleNeighbours.get(i);
            if(isOnBorder(p)){
                return p;                
            }
            myQueue.enqueue(new Pair<Point>(p,p));
            blocked[p.getX()][p.getY()] = true;
        }


        // start the search
        while(!myQueue.isEmpty()){
            Pair<Point> pointPair = myQueue.dequeue();
            possibleNeighbours = findPossibleNeighbours(pointPair.getFirst(), blocked);
             
            for(int i = 0; i < possibleNeighbours.size() ; i++){
                Point p = possibleNeighbours.get(i);
                if(isOnBorder(p)){
                    return pointPair.getSecond();                
                }
                myQueue.enqueue(new Pair<Point>(p,pointPair.getSecond()));
                blocked[p.getX()][p.getY()]=true;
            }

       }

        // could not find a way out. Return an outside direction
        return new Point(-1,-1);

    }

	/**
     * Helper method: find the list of direct neighbours of a point that are not
     * currenbtly blocked
     *
     * @param point
     *            the point to check
     * @param blocked
     *            a 2 dimentionnal array of booleans specifying the points that 
     *              are currently blocked
     *
     * @return an instance of a LinkedList class, holding a list of instances of 
     *      the class Points representing the neighbours of parameter point that 
     *      are not currently blocked.
     */
    private LinkedList<Point> findPossibleNeighbours(Point point, boolean[][] blocked) {

        LinkedList<Point> list = new LinkedList<Point>();
        int delta = (point.getY() %2 == 0) ? 1 : 0;
        if(!blocked[point.getX()-delta][point.getY()-1]){
            list.add(new Point(point.getX()-delta, point.getY()-1));
        }
        if(!blocked[point.getX()-delta+1][point.getY()-1]){
            list.add(new Point(point.getX()-delta+1, point.getY()-1));
        }
        if(!blocked[point.getX()-1][point.getY()]){
            list.add(new Point(point.getX()-1, point.getY()));
        }
        if(!blocked[point.getX()+1][point.getY()]){
            list.add(new Point(point.getX()+1, point.getY()));
        }
        if(!blocked[point.getX()-delta][point.getY()+1]){
            list.add(new Point(point.getX()-delta, point.getY()+1));
        }
        if(!blocked[point.getX()-delta+1][point.getY()+1]){
            list.add(new Point(point.getX()-delta+1, point.getY()+1));
        }
        return list;
    }
	
	/**
	 * Undoes the game
	 */
	private void undo() throws EmptyStackException {
		GameModel redoCloned = (GameModel) gameModel.clone(); // Cloning the game model
		redoStack.push(redoCloned); // Pushes the cloned game model into the redo stack
		
		GameModel undoed = undoStack.pop();
		
		gameModel.setModel(undoed.getModel());
		gameModel.setCurrentDot(undoed.getCurrentDot().getX(), undoed.getCurrentDot().getY());
		gameModel.decrementNumberOfSteps();
		
		gameView.update();
	}
	
	/**
	 * Redoes the game
	 */
	private void redo() throws EmptyStackException {
		GameModel undoCloned = (GameModel) gameModel.clone(); // Cloning the game model
		undoStack.push(undoCloned); // Pushes the cloned game model into the undo stack
		
		GameModel redoed = redoStack.pop();
		
		gameModel.setModel(redoed.getModel());
		gameModel.setCurrentDot(redoed.getCurrentDot().getX(), redoed.getCurrentDot().getY());
		gameModel.incrementNumberOfSteps();
		
		gameView.update();
	}
	
	/**
	 * Checks if the redo stack is empty
	 */
	public boolean isRedoStackEmpty() {
		return redoStack.isEmpty();
	}
	
	/**
	 * Checks if the undo stack is empty
	 */
	public boolean isUndoStackEmpty() {
		return undoStack.isEmpty();
	}

	/**
	 * Creates a game model object by reading an SER file
	 * @param save
	 *			the file that contains the game model binary data
	 * @return the created game model
	 */
	private GameModel deserialize(File save) throws FileNotFoundException, ClassNotFoundException, IOException {
		FileInputStream saveIS = new FileInputStream(save);
		ObjectInputStream gameModelIS = new ObjectInputStream(saveIS);
		GameModel gameModel = (GameModel) gameModelIS.readObject();
		gameModelIS.close();
		
		return gameModel;
	}
	
}
