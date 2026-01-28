package Homework2;

/**
 * A singly linked list with immutable Nodes.
 */
public class LinkedList {

    // The inner Node class as specified
    private static class Node {
        final Integer data; // final means we cannot change it later
        final Node rest;    // final means we cannot change the link later

        /**
         * Two-argument constructor.
         * @param data The number to store
         * @param rest The next node in the chain
         */
        Node(int data, Node rest) {
            this.data = data;
            this.rest = rest;
        }

        Integer getData() {
            return data;
        }

        Node getRest() {
            return rest;
        }
    }

    // The head of the list
    private Node head;

    /**
     * Zero-argument constructor initializing an empty list.
     */
    public LinkedList() {
        this.head = null;
    }

    /**
     * Returns true if the list has no elements.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        head = null;
    }

    /**
     * Returns the number of elements in the list.
     * @return the size
     */
    public int size() {
        return size(head);
    }

    // Recursive helper for size
    private static int size(Node n) {
        if (n == null) {
            return 0;
        }
        // 1 for this node + size of the rest
        return 1 + size(n.rest); 
    }

    /**
     * Gets the value at a specific index.
     * @param index the position to look at
     * @return the Integer value
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Integer get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }
        return get(head, index);
    }

    // Recursive helper for get
    private static Integer get(Node n, int index) {
        if (n == null) {
            throw new IndexOutOfBoundsException("Index too large");
        }
        if (index == 0) {
            return n.data;
        }
        // Go to the next node, decrease index by 1
        return get(n.rest, index - 1);
    }

    /**
     * Adds an element at a specific index.
     * @param index position to insert at
     * @param x value to insert
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void add(int index, Integer x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }
        // We update head to point to the new structure returned by the helper
        head = add(head, index, x);
    }

    // Recursive helper for add
    private static Node add(Node n, int index, Integer x) {
        if (index == 0) {
            // Found the spot! Create new node pointing to the current n
            return new Node(x, n);
        }
        if (n == null) {
            // We reached the end but index is still > 0
            throw new IndexOutOfBoundsException("Index too large");
        }
        // Copy current node, and recurse for the rest
        return new Node(n.data, add(n.rest, index - 1, x));
    }

    /**
     * Adds an element to the end of the list.
     * @param x value to add
     * @return true
     */
    public boolean add(Integer x) {
        head = addLast(head, x);
        return true;
    }

    // Recursive helper for addLast
    private static Node addLast(Node n, Integer x) {
        if (n == null) {
            // We found the end, create the new node here
            return new Node(x, null);
        }
        // Rebuild the current node pointing to the result of the recursion
        return new Node(n.data, addLast(n.rest, x));
    }

    /**
     * Removes element at specific index.
     * @param index position to remove
     * @return the value that was removed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Integer remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }
        // Get the value first so we can return it later
        Integer valueToRemove = get(index);
        
        // Update head with the list after removal
        head = remove(head, index);
        return valueToRemove;
    }

    // Recursive helper for remove
    private static Node remove(Node n, int index) {
        if (n == null) {
            throw new IndexOutOfBoundsException("Index too large");
        }
        if (index == 0) {
            // Skip this node by returning the next one
            return n.rest;
        }
        // Copy current node, recurse on the rest
        return new Node(n.data, remove(n.rest, index - 1));
    }

    /**
     * Updates the value at a specific index.
     * @param index position to set
     * @param x new value
     * @return the old value
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Integer set(int index, Integer x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }
        Integer oldValue = get(index);
        head = set(head, index, x);
        return oldValue;
    }

    // Recursive helper for set
    private static Node set(Node n, int index, Integer x) {
        if (n == null) {
            throw new IndexOutOfBoundsException("Index too large");
        }
        if (index == 0) {
            // Create replacement node pointing to the rest
            return new Node(x, n.rest);
        }
        // Copy current node, recurse on the rest
        return new Node(n.data, set(n.rest, index - 1, x));
    }

    /**
     * Returns a string representation of the list.
     * @return the string
     */
    public String toString() {
        return "[" + toStringHelper(head) + "]";
    }

    private String toStringHelper(Node n) {
        if (n == null) {
            return "";
        }
        if (n.rest == null) {
            return String.valueOf(n.data);
        }
        return n.data + ", " + toStringHelper(n.rest);
    }
}