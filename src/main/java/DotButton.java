import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * The class <b>DotButton</b> represents a dot in the game's <b>Board</b>
 * instance. It extends the swing component <b>JButton</b> and uses different
 * icons to visually reflect the state of the dot. A blue icon is displayed if
 * the location of the dot is the current location of the blue dot. An orange
 * icon is displayed if the dot has been selected. Otherwise, a grey icon is
 * displayed.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class DotButton extends JButton {

    /**
     * The version number of the serializable class associated by the
     * serializable runtime.
     */
    private static final long serialVersionUID = -2393278252444838901L;

    /** The Number of colours representing the different states of a dot. */
    private static final int NUM_STATES = 3;

    /**
     * An array is used to cache all the icons. Since the icons are not
     * modified, all the cells that display the same image reuse the same
     * <b>ImageIcon</b> object.
     */
    private static final ImageIcon[] icons = new ImageIcon[NUM_STATES];

    /** The row number of the dot on a <b>Board</b> instance. */
    private final int row;

    /** The column number of the dot on a <b>Board</b> instance. */
    private final int column;

    /**
     * The captured state of the dot. Valid predefined values in the class
     * <b>GameModel</b> are <b>GameModel.AVAILABLE</b>,
     * <b>GameModel.SELECTED</b>, and <b>GameModel.BLUE_DOT</b>.
     */
    private int type;

    /**
     * The constructor for the custom swing component <b>DotButton</b>. It
     * initializes a dot of a specified location and state.
     * 
     * @param row The row number of the dot.
     * @param column The column number of the dot.
     * @param type The captured state of the dot.
     */
    public DotButton(int row, int column, int type) {
        // Initializing the dot with a specified location and state.
    	this.row = row;
    	this.column = column;
    	this.type = type;

        // Setting up the inherited JButton properties.
    	setBackground(Color.WHITE);
    	setIcon(getImageIcon());
    	Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    	setBorder(emptyBorder);
    	setBorderPainted(false);
    }

    /**
     * Determines the icon to use based on the dot's state. This method is
     * implemented on the caching mechanism.
     * 
     * @return the icon to be displayed by the <b>DotButton</b>.
     */
    private ImageIcon getImageIcon() {
	
    	if (icons[type] == null) {
    	    String strId = Integer.toString(type);
    	    icons[type] = new ImageIcon(getClass().getResource("/dot_" + strId + "_48px.png"));
    	}
    	return icons[type];
    }

    /**
     * Changes the state of the dot while changing the icon accordingly.
     * 
     * @param type The state of the dot.
     */
    public void setType(int type) {
    	this.type = type;
    	setIcon(getImageIcon());
    }
 
    /**
     * An instance method for getting the row number of the dot.
     *
     * @return The row number of the dot.
     */
    public int getRow() {
	   return row;
    }

    /**
     * An instance method for getting the column number of the dot.
     *
     * @return The column number of the dot.
     */
    public int getColumn() {
	   return column;
    }

}
