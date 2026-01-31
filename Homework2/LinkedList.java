public class LinkedList {
    /**
     * Linkedlist for Integers.
     */
    private Node head; // the first node
    
    private static class Node {
        private final Integer data; // the number stored here
        private final Node rest; // next node in the list
        
        /**
         * Construct a new node
         * @param data the number that it holds
         * @param rest the next node (or null)
         */
        public Node(int data, Node rest) {
            this.data = data;
            this.rest = rest;
        }
        
        /**
         * Get the data
         * @return the number in this node
         */
        public Integer getData() {
            return data;
        }
        
        /**
         * Get the next node
         * @return next node
         */
        public Node getRest() {
            return rest;
        }
    }
    
    /**
     * Constructs an empty linked list.
     */
    public LinkedList() {
        head = null; // start with nothing
    }
    
    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes all elements from the list, making it empty.
     */
    public void clear() {
        head = null;
    }
    
    /**
     * Get size of list
     * @return how many elements
     */
    public int size() {
        return helperSize(head);
    }
    
    // helper method for size - counts nodes recursively
    private int helperSize(Node currentNode) {
        if (currentNode == null) {
            return 0; // no more nodes
        }
        // count this node plus all the rest
        return 1 + helperSize(currentNode.getRest());
    }
    
    /**
     * Get element at index
     * @param index where to look
     * @return the Integer at that spot
     * @throws IndexOutOfBoundsException if index is wronf
     */
    public Integer get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index!");
        }
        return helperGet(head, index);
    }
    
    // recursive helper for get method
    private Integer helperGet(Node currentNode, int index) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            // found it!
            return currentNode.getData();
        }
        // keep looking
        return helperGet(currentNode.getRest(), index - 1);
    }
    
    /**
     * Add element at specific index
     * @param index where to add
     * @param x what to add
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void add(int index, Integer x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index");
        }
        head = helperAdd(head, index, x);
    }
    
    // Not sure if this is right way 
    private Node helperAdd(Node currentNode, int index, Integer x) {
        if (index == 0) {
            // insert here by making new node that points to current
            return new Node(x, currentNode);
        }
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        // rebuild the node with the rest modified
        return new Node(currentNode.getData(), helperAdd(currentNode.getRest(), index - 1, x));
    }
    
    /**
     * Add to end of list
     * @param x the thing to add
     * @return true always
     */
    public boolean add(Integer x) {
        head = addToEnd(head, x);
        return true; // assignment says to return true
    }
    
    // add to the end recursively
    private Node addToEnd(Node currentNode, Integer x) {
        if (currentNode == null) {
            // reached the end, make new node
            return new Node(x, null);
        }
        // rebuild this node with modified rest
        return new Node(currentNode.getData(), addToEnd(currentNode.getRest(), x));
    }
    
    /**
     * Remove element at index
     * @param index which one to remove
     * @return the thing that got removed
     * @throws IndexOutOfBoundsException if index is bad
     */
    public Integer remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("can't remove negative index");
        }
        if (head == null) {
            throw new IndexOutOfBoundsException("can't remove from empty list");
        }
        Integer valueToRemove = get(index);
        head = helperRemove(head, index);
        return valueToRemove;
    }
    
    // helper method removes node at index recursively
    private Node helperRemove(Node currentNode, int index) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            // skip this node by returning the rest
            return currentNode.getRest();
        }
        // rebuild with modified rest
        return new Node(currentNode.getData(), helperRemove(currentNode.getRest(), index - 1));
    }
    
    /**
     * Set value at index
     * @param index where to set
     * @param x new value
     * @return old value
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Integer set(int index, Integer x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index");
        }
        Integer oldValue = get(index); 
        head = helperSet(head, index, x);
        return oldValue;
    }
    
    // helper methods sets value at index recursively
    private Node helperSet(Node currentNode, int index, Integer x) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            return new Node(x, currentNode.getRest());
        }
        return new Node(currentNode.getData(), helperSet(currentNode.getRest(), index - 1, x));
    }
    
}