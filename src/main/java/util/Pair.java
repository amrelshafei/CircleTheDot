package util;

/**
 * The class <b>Pair</b> that holds a pair of values of the same type.
 *
 * @author Amr ElShafei
 * @version 2.0
 * @since March 28th, 2016
 */
public class Pair<T> {
    
    /** The first value in the pair. */
    private final T first;

    /** The second value in the pair. */
    private final T second;
    
    /**
     * The constructor of the class <b>Pair</b>.
     * 
     * @param first  The first value in the pair.
     * @param second The second value in the pair.
     */
    public Pair(final T first, final T second) {
        this.first = first;
        this.second = second;
    }
    
    /**
     * An instance method for getting the first value in the pair.
     * 
     * @return The first value in the pair.
     */
    public T getFirst() {
        return first;
    }
    
    /**
     * An instance method for getting the second value in the pair.
     * 
     * @return The second value in the pair.
     */
    public T getSecond() {
        return second;
    }
    
}
