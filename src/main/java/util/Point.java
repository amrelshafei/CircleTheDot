package util;

import java.io.Serializable;

/**
 * The class <b>Point</b> that holds a two-dimensional point coordinate on a
 * two-dimensional XY-grid.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class Point implements Serializable {

    /**
     * The version number of the serializable class associated by the 
     * serializable runtime.
     */
    private static final long serialVersionUID = 2007580554294062114L;

    /** The x-coordinate of the point. */
    private int x;

    /** The y-coordinate of the point. */
    private int y;

    /**
     * The constructor of the class <b>Point</b>. 
     * 
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        reset(x, y);
    }

    /**
     * The constructor of the class <b>Point</b>. 
     * 
     * @param point The two-dimensional point coordinate.
     */
    public Point(Point point) {
        reset(point.getX(), point.getY());
    }
    
    /**
     * An instance method for getting the x-coordinate of the point.
     * 
     * @return The x-coordinate of the point.
     */
    public int getX() {
        return x;
    }
    
    /**
     * An instance method for getting the y-coordinate of the point.
     * 
     * @return The y-coordinate of the point.
     */
    public int getY() {
        return y;
    }
    
    /**
     * An instance method for resetting the x and y values of the point.
     * 
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }

 }
