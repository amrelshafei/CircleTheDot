package util;

/**
 * The interface <b>Queue</b> for defining the abstraction of a queue data 
 * structure.
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public interface Queue<E> {

    /**
     * Puts an element onto the front of the queue.
     * @param element The element be put onto the front of this queue.
     */
    public abstract void enqueue(E element);
    
    /**
     * Removes and returns the element at the rear of the queue.
     * @return The rear element of the queue.
     */
    public abstract E dequeue();
    
    /**
     * Checks if the queue is empty.
     * @return true if this queue is empty; and false otherwise.
     */
    public abstract boolean isEmpty();
    
    /**
     * Returns a reference to the front element.
     * @return The front element of the queue without removing it.
     */
    public abstract E peek();
    
    /**
     * Returns the size of the elements in the queue.
     * @return queue's size.
     */
    public abstract int size();
    
    /**
     * Clears the queue from all elements.
     */
    public abstract void clear();
}
