package util;

/**
 * The class <b>LinkedStack</b> that implements the <b>Stack</b> interface by 
 * storing elements in a set of sequentially linked nodes. 
 *
 * @author  Amr ElShafei
 * @version 2.0
 * @since   March 28th, 2016
 */
public class LinkedStack<E> implements Stack<E> {

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

    private Node<E> top;
    private int size;

    public void push(final E element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("Cannot push a null element");
        }
        top = new Node<E>(element, top);
        size++;
    }

    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("No element to pop");
        }

        final E result = top.value;
        top = top.next; // moving the top position
        --size;

        return result;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("No element at peek");
        }
        return top.value;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
    }

    /**
     * The main method reflects a simple test with linked stacks.
     */
    public static void main(final String[] args) {
        final LinkedStack<Object> stack = new LinkedStack<Object>();

        final String str1 = "value1";
        final String str2 = "value2";
        final String str3 = "value3";
        System.out.println(stack.isEmpty());
        
        stack.push(str1);
        stack.push(str2);
        stack.push(str3);
        System.out.println(stack.size());
        
        System.out.println(stack.isEmpty());
        
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.size());
        
        System.out.println(stack.isEmpty());
        
        stack.pop();
    }
    
}
