import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Random;
import util.Util;
import util.Point;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. It 
 * stores the following information:
 * - The current location of the blue dot.
 * - The state of all the dots on the board (available, selected or occupied by 
 *   the blue dot.
 * - The size of the board.
 * - The number of steps since the last reset.
 *
 * The model provides all of this informations to the other classes trough 
 * appropriate Getters. The controller can also update the model through 
 * Setters. Finally, the model is also in charge of initializing the game.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class GameModel implements Cloneable, Serializable {

    /**
	 * The version number of the serializable class associated by the 
     * serializable runtime.
	 */
    private static final long serialVersionUID = -5655252147418456207L;

    /** 
     * The predefined value to capture the state of a dot when it is
     * available. 
     */
    public static final int AVAILABLE = 0;

    /** 
     * The predefined value to capture the state of a dot when it is
     * selected. 
     */
    public static final int SELECTED = 1;

    /** 
     * The predefined value to capture the state of a dot when it is
     * the blue dot. 
     */
    public static final int BLUE_DOT = 2;

    /**
     * The probability that an available cell will be initially selected
     */
    private static final int INITIAL_PROBA = 10;
    
    /**
     * The size of the game.
     */
    private int sizeOfGame;
 
    /**
     * A 2 dimentionnal array of sizeOfGame*sizeOfGame recording the state of each dot
     */
    private int[][] model;

	/**
     * The current position of the blue dot
     */
    private Point currentDot;

	/**
     * The number of steps played since the last reset
     */
    private int numberOfSteps;
	
	/**
	 * Reference to a random generator
	 */
    private Random generator;
	
	
    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param size
     *            the size of the board
     */
    public GameModel(int size) {
        numberOfSteps = 0;
        generator = new Random();
        sizeOfGame = size;

        reset();
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . The blue dot is positioned as per instructions, and each 
     * dot of the board is either AVAILABLE, or SELECTED (with
     * a probability 1/INITIAL_PROBA). The number of steps is reset.
     */
    public void reset() {

        model = new int[sizeOfGame][sizeOfGame];

        for(int i = 0; i < sizeOfGame; i++){
            for(int j = 0; j < sizeOfGame; j++){
                model[i][j] = AVAILABLE;
            }
        }

        // on a odd board, put the current dot randomly on a centered square of
        // 2 by 2, on an even board, put the current dot randomly on a centered 
        // square of 3 by 3

        if(sizeOfGame%2 == 0){
            currentDot = new Point(sizeOfGame/2 - generator.nextInt(2),
                sizeOfGame/2 - generator.nextInt(2));
        } else{
            currentDot = new Point(sizeOfGame/2 + 1 - generator.nextInt(3),
                sizeOfGame/2 + 1 - generator.nextInt(3));
        }

        model[currentDot.getX()][currentDot.getY()] = BLUE_DOT;

        for(int i = 0; i < sizeOfGame; i++){
            for(int j = 0; j < sizeOfGame; j++){
                if(!( i == currentDot.getX() && j == currentDot.getY())){
                    if(generator.nextInt(INITIAL_PROBA) == 0){
                        model[i][j] = SELECTED;
                    }
                }
            }
        }

        numberOfSteps = 0;
    }

    /**
     * Getter method for the size of the game
     * 
     * @return the value of the attribute sizeOfGame
     */   
    public int getSize() {
        return sizeOfGame;
    }

	/**
     * Getter method for the ``model'' array
     * 
     * @return the model array
     */   
    public int[][] getModel() {
        return model;
    }

    /**
     * returns the current status (AVAILABLE, SELECTED or BLUE_DOT) of a given dot in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public int getCurrentStatus(int i, int j) {
        return model[i][j];
    }

	/**
     * Getter method for the current blue dot
     * 
     * @return the location of the curent blue dot
     */   
    public Point getCurrentDot() {
        return currentDot;
    }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps() {
        return numberOfSteps;
    }
	
	/**
	 *
	 */
	public void setModel(int[][] model) {
    	for(int i = 0; i < sizeOfGame; i++){
		   	for(int j = 0; j < sizeOfGame; j++){
		   		this.model[i][j] = model[i][j];
		   	}
		}
	}
	
    /**
     * Sets the status of the dot at coordinate (i,j) to SELECTED, and 
     * increases the number of steps by one
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void select(int i, int j) {
        model[i][j] = SELECTED;
        numberOfSteps++;
    }

    /**
     * Puts the blue dot at coordinate (i,j). Clears the previous location 
     * of the blue dot. If the i coordinate is "-1", it means that the blue 
     * dot exits the board (the player lost)
     *
     * @param i
     *            the new x coordinate of the blue dot
     * @param j
     *            the new y coordinate of the blue dot
     */   
    public void setCurrentDot(int i, int j) {
        model[currentDot.getX()][currentDot.getY()] = AVAILABLE;
        // pass on "-1" to remove the current dot at the end of the game
        if(i != -1) {
            model[i][j] = BLUE_DOT;
            currentDot.reset(i,j);
        }
    }

	/**
	 * Decrements the number of steps of the game
	 */
	public void decrementNumberOfSteps() {
		--numberOfSteps;
	}
	
	/**
	 * Increments the number of steps of the game
	 */
	public void incrementNumberOfSteps() {
		numberOfSteps++;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		str.append("GameModel");
		str.append(Util.matrixToString(model));
		str.append("\n");
		str.append(currentDot.toString());
		str.append("\n");
		str.append(numberOfSteps);
		
		return str.toString();
	}
	
	@Override
	public GameModel clone() {
		GameModel cloned;
		try {
			cloned = (GameModel) super.clone();
			cloned.currentDot = new Point(currentDot.getX(), currentDot.getY());
			cloned.model = new int[sizeOfGame][sizeOfGame];
			cloned.setModel(model);
		} catch (CloneNotSupportedException c) {
			throw new RuntimeException("Object cannot be cloned");
		}
		return cloned;
	}

	/**
	 * Creates an DAT file the contains the binary data of the calling game model
	 * @returns the DAT file
	 */
	public File serialize() {
		File save;
		try {
			String runningDir = CircleTheDot.getRunningFile().getParent();
			save = new File(runningDir + "/last_save.dat");
			FileOutputStream saveOS = new FileOutputStream(save);
			ObjectOutputStream gameModelOS = new ObjectOutputStream(saveOS);
			gameModelOS.writeObject(this);
			gameModelOS.close();
		} catch (FileNotFoundException f) {
			throw new RuntimeException("SER File not found");
		} catch (IOException i) {
			throw new RuntimeException("I/O operation failed");
		}
		return save;
	}

	/**
	 * The main method reflects a simple test on the GameModel cloning
	 */
	/**
	public static void main(String[] args) {
		GameModel original = new GameModel(8);
		original.serialize();
		LinkedStack<Object> stack = new LinkedStack<Object>();
		
		GameModel cloned0 = (GameModel) original.clone();
		stack.push(cloned0);
		System.out.println("\n Cloned0");
		System.out.println(cloned0);
		
		original.setCurrentDot(5, 7);
		original.numberOfSteps++;
		GameModel cloned1 = (GameModel) original.clone();
		stack.push(cloned1);
		System.out.println("\n Cloned1");
		System.out.println(cloned1);
		
		original.setCurrentDot(3, 2);
		original.numberOfSteps++;
		GameModel cloned2 = (GameModel) original.clone();
		stack.push(cloned2);
		System.out.println("\n Cloned2");
		System.out.println(cloned2);
		
		original.setCurrentDot(4, 3);
		original.numberOfSteps++;
		GameModel cloned3 = (GameModel) original.clone();
		stack.push(cloned3);
		System.out.println("\n Cloned3");
		System.out.println(cloned3);
		
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}
	 */
	
}
