package util;

public class EmptyQueueException extends RuntimeException {
    
    /**
     * The version number of the serializable class associated by the 
     * serializable runtime.
     */
    private static final long serialVersionUID = -3137043748681837412L;

    /**
     * Constructs a new empty-queue exception with null as its detail message.
     */
    public EmptyQueueException() {
        super();
    }
    
    /**
     * Constructs a new empty-queue exception with the specified detail 
     * message. 
     * @param msg The detail message that is saved for later retrieval by the 
     *            getMessage() method.
     */
    public EmptyQueueException(String msg) {
        super(msg);
    }
    
}