package util;

public class EmptyStackException extends RuntimeException {
    
    /**
     * The version number of the serializable class associated by the 
     * serializable runtime.
     */
    private static final long serialVersionUID = -6334573415871836171L;

    /**
     * Constructs a new empty-stack exception with null as its detail message.
     */
    public EmptyStackException() {
        super();
    }
    
    /**
     * Constructs a new empty-stack exception with the specified detail 
     * message. 
     * @param msg The detail message that is saved for later retrieval by the 
     *            getMessage() method.
     */
    public EmptyStackException(String msg) {
        super(msg);
    }
    
}