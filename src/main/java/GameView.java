import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It
 * extends the swing component <b>JFrame</b> and lays out an instance of the
 * class <b>BoardView</b> and two instances of JButton for redo and undo
 * functionalities. The action listener for the buttons is the controller.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class GameView extends JFrame {

    private static final long serialVersionUID = 5992657131118857705L;

    /** The reference to the view of the board. */
    private BoardView board;

    /** The reference to the game's model. */
    private GameModel gameModel;

    /** The reference to the game's controller. */
    private GameController gameController;

    /** The redo button of the game. */
    private JButton buttonRedo;

    /** The undo button of the game. */
    private JButton buttonUndo;

    /**
     * The constructor for the custom swing component <b>GameView</b>.
     *
     *  @param gameModel The model of the game.
     * @param gameController the controller of the game.
     */
    public GameView(GameModel gameModel, GameController gameController) {
        super("Circle the Dot");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        this.gameModel = gameModel;
        this.gameController = gameController;
        board = new BoardView(gameModel, gameController);
        add(board, BorderLayout.CENTER);

        buttonRedo = new JButton("Redo");
        buttonRedo.setFocusPainted(false);
        buttonRedo.addActionListener(gameController);

        JButton buttonReset = new JButton("Reset");
        buttonReset.setFocusPainted(false);
        buttonReset.addActionListener(gameController);

        JButton buttonExit = new JButton("Quit");
        buttonExit.setFocusPainted(false);
        buttonExit.addActionListener(gameController);

        buttonUndo = new JButton("Undo");
        buttonUndo.setFocusPainted(false);
        buttonUndo.addActionListener(gameController);

        JPanel control = new JPanel();
        control.setBackground(Color.WHITE);
        control.add(buttonRedo);
        control.add(buttonReset);
        control.add(buttonExit);
        control.add(buttonUndo);
        add(control, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setVisible(true);

        setButtonRedoVisible(false);
        setButtonUndoVisible(false);
    }

    /**
     * An instance method for getting the reference to the game's board.
     *
     * @return The board view of the game.
     */
    public BoardView getBoard() {
        return board;
    }

    /**
     * An instance method for getting the reference to the game's model.
     *
     * @return The reference to the game's model.
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Controls the visibility of redo button.
     *
     * @param flag True if the redo button is desired to be visible.
     */
    public void setButtonRedoVisible(boolean flag) {
        buttonRedo.setVisible(flag);
    }

    /**
     * Controls the visibility of undo button.
     *
     * @param flag True if the undo button is desired to be visible.
     */
    public void setButtonUndoVisible(boolean flag) {
        buttonUndo.setVisible(flag);
    }

    /**
     * Updates the game's view, i.e., board, and undo and redo buttons.
     */
    public void update() {
        setButtonRedoVisible(!gameController.isRedoStackEmpty());
        setButtonUndoVisible(!gameController.isUndoStackEmpty());
        board.update();
    }

}
