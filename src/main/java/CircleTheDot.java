import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The class <b>CircleTheDot</b> housing the main function that launches the
 * game.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class CircleTheDot {

    // The predefined value for the size of the game's board.
    private static final int DEFAULT_SIZE = 9;

    /**
     * Return the location associated with the code source, i.e., the running
     * main program location.
     *
     * @return The file to the code source.
     * @throws SecurityException Thrown if this method is unable to request the
     * protection domain.
     */
    public static File getRunningFile() throws SecurityException {
        java.net.URL location = CircleTheDot.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();
        try {
            return new File(location.toURI());
        } catch (URISyntaxException e) {
            return new File(location.toString());
        }
    }

    /**
     * The <b>main</b> function of the game. It creates the instance of
     * <b>GameController</b> and starts the game. If a game size is greater
     * than 4 the size is passed to the game controller and used as the board
     * size. Otherwise, a default size is passed.
     * 
     * @param args Command line program arguments. $ java CircleTheDot _SIZE_
     */
     public static void main(String[] args) throws ClassNotFoundException, IOException {
        int size = DEFAULT_SIZE;
        if (args.length == 1) {
            try {
                size = Integer.parseInt(args[0]);
                if(size < 4) {
                    System.out.println("Invalid argument, using default...");
                    size = 9;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid argument, using default...");
            }
        }
        
        new GameController(size);
    }

}
