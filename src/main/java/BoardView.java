import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The class <b>BoardView</b> provides the current view of the board. It
 * extends the swing component <b>JPanel</b> and holds a two dimensional array
 * of <b>DotButton</b> instances.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class BoardView extends JPanel {

	/**
	 * The version number of the serializable class associated by the
	 * serializable runtime.
	 */
    private static final long serialVersionUID = -3040915218223550338L;

  	/** The two dimensional array of <b>DotButton</b> instances. */
	private DotButton[][] board;

 	/** The reference to the game model. */
    private GameModel gameModel;

	/**
     * The constructor for the custom swing component <b>BoardView</b>. It
	 * initializes the board, stores a reference to the game model, and uses
	 * the game controller as the action listener for the <b>DotButton</b>
	 * instances.
     * 
     * @param gameModel The model of the game.
     * @param gameController the controller of the game.
     */
    public BoardView(GameModel gameModel, GameController gameController) {
    	// Storing a reference to the game model.
    	this.gameModel = gameModel;

    	// Setting up the inherited JPanel properties.
		setBackground(Color.WHITE);
		setLayout(new GridLayout(gameModel.getSize(), 1));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

		// Setting up the board of DotButton instances.
		board = new DotButton[gameModel.getSize()][gameModel.getSize()];
		for (int row = 0; row < gameModel.getSize(); row++) {
			JPanel panel = new JPanel();
			if(row%2==0) {
				panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 11));
				panel.setLayout(new FlowLayout(FlowLayout.LEADING,2,2));
			} else {
				panel.setBorder(BorderFactory.createEmptyBorder(0, 11, 0, 0));
				panel.setLayout(new FlowLayout(FlowLayout.TRAILING,2,2));		
			}
			panel.setBackground(Color.WHITE);
		    for (int column = 0; column < gameModel.getSize(); column++) {
				board[column][row] = new DotButton(row, column, GameModel.AVAILABLE);
				board[column][row].addActionListener(gameController);
				panel.add(board[column][row]);
		    }
		    add(panel);
		}

    }

 	/**
	 * updates the status of the board's <b>DotButton</b> instances based on
     * the current game model.
	 */
    public void update() {
    	for(int i = 0; i < gameModel.getSize(); i++){
		   	for(int j = 0; j < gameModel.getSize(); j++){
		   		board[i][j].setType(gameModel.getCurrentStatus(i,j));
		   	}
		}
		repaint();
    }

}
