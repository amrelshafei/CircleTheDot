package util;

/**
 * The class <b>LinkedQueue</b> that implements the <b>Queue</b> interface by 
 * storing elements in a set of sequentially linked nodes. 
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class LinkedQueue<E> implements Queue<E> {

    /**
     * The nested static class that cantains a data field and a reference to 
     * the next node.
     * 
     * @param <E> The type of the data field of the node.
     */
    private static class Node<E> {
        
        /** The data field of the node. */
        private final E value;

        /** The reference to the next node. */
        private Node<E> next;
        
        /**
         * The constructor of the nested static class <b>Node</b>.
         * 
         * @param value The data field of the node.
         * @param next  The reference to the next node.
         */
        private Node(final E value, final Node<E> next) {
            this.value = value;
            this.next = next;
        }

    }

    private Node<E> front;
    private Node<E> rear;
    private int size;

    public void enqueue(final E element) throws IllegalArgumentException {
        final Node<E> newElem = new Node<E>(element, null);

        if (element == null) {
            throw new IllegalArgumentException("Cannot enqueue a null element");
        }

        // special case.
        else if (rear == null) { // if queue is empty
            front = rear = newElem; // enqueuing the element
        }
        // general case.
        else {
            rear.next = newElem; // enqueuing the element
            rear = newElem; // moving rear position
        }
        size++;
    }

    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("No element to dequeue");
        }

        final E result = front.value;
        // Special case. If the queue has only one element.
        if (front != null & front.next == null) {
            front = rear = null; // Dequeue element.
        }
        // general case.
        else {
            front = front.next; // moving the front position.
        }
        // Decrement the queue size.
        --size;
        return result;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("No element at peek");
        }
        return front.value;
    }

    public int size() {
        return size;
    }

    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    @Override
    public String toString() {
        final StringBuffer str = new StringBuffer();

        Node<E> p = front;

        while (p != null) {
            str.append(p.value);
            p = p.next;
        }
        return str.toString();
    }

    /**
     * The main method reflects a simple test with linked queues.
     */
    public static void main(final String[] args) {
        final LinkedQueue<Object> queue = new LinkedQueue<Object>();

        final String str1 = "value1";
        final String str2 = "value2";
        final String str3 = "value3";
        System.out.println(queue.isEmpty());
        
        queue.enqueue(str1);
        queue.enqueue(str2);
        queue.enqueue(str3);
        System.out.println(queue.size());
        
        System.out.println(queue.isEmpty());
        
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.size());
        
        System.out.println(queue.isEmpty());
        
        queue.dequeue();
    }

}
