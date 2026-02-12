public class LinkedList<E> {
    /**
    * A generic linked list that can store any type.
    * @param <E> the type of elements stored in this list
    */
    private Node<E> head; // the first node
    private Node<E> tail;
    private int size;
    
    private static class Node<T> {
        private final T data; // the data stored here
        private Node<T> rest; // next node in the list
        
    /**
    * Construct a new node
    * @param data the element that it holds
    * @param rest the next node (or null)
    */
        public Node(T data, Node<T> rest) {
            this.data = data;
            this.rest = rest;
        }
        
        /**
         * Get the data
         * @return the data in this node
         */
        public T getData() {
            return data;
        }
        
        /**
         * Get the next node
         * @return next node
         */
        public Node<T> getRest() {
            return rest;
        }

        public void setRest(Node<T> newRest) {
            this.rest = newRest;
        }


    }
    
    /**
     * Constructs an empty linked list.
     */
    public LinkedList() {
        head = null; // start with nothing
        tail = null;
        size = 0;
    }
    
    /**
    * Checks if the list is empty.
    * Time Complexity: &Theta;(1)
    * @return true if the list has no elements, false otherwise
    */
    public boolean isEmpty() {
        return head == null; // realized the if statement was redundant so removed it
    }

    /*
    T(n) = c  (for all n ≥ 0)
    T(n) = c = Θ(1)
    it just clears the list
     */

    /**
     * Removes all elements from the list, making it empty.
     * Time Complexity: &Theta;(1)
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }


    /*
    TIME COMPLEXITY ANALYSIS FOR size()
     
     T(0) = c (base case)
     T(n) = T(n-1) + c (recursive case)
     
     T(n) = T(n-1) + c
          = T(n-2) + 2c
          = T(0) + nc
          = c + nc = c(n+1)
     Therefore T(n) = Θ(n)
     
     Proof by Induction:
     Claim: T(n) = c(n+1) for all n ≥ 0
     Base case (n=0): T(0) = c(0+1) = c 
     Inductive Hypothesis: Assume T(k) = c(k+1) for some k ≥ 0
     Inductive Step: T(k+1) = T(k) + c = c(k+1) + c = c(k+2) 
    */

    /*
    Updated

    - Added private int size field
    - Initialize size = 0 in constructor
    - Increment in add() methods
    - Decrement in remove() method
    - size() now returns field in constant time: T(n) = c = Θ(1)
    */


    /**
     * Get size of list
     * @return how many elements
     * Time Complexity: &Theta;(1)
     */
    public int size() {
        return size;
    }
    
    /* 
     * Get size of list
     * @return how many elements
     * Time Complexity: &Theta;(n)

    NO USE ANYMORE 

    // helper method for size - counts nodes recursively
    private int helperSize(Node currentNode) {
        if (currentNode == null) {
            return 0; // no more nodes
        }
        // count this node plus all the rest
        return 1 + helperSize(currentNode.getRest());
    }
    */



    /*
    T(0) = c (base case: index is 0, found immediately)
    T(i) = T(i-1) + c (recursive case: check current node, recurse with i-1)

    T(i) = T(i-1) + c
    = T(i-2) + 2c
    = T(0) + ic
    = c + ic
    = c(i+1)

    T(n-1) = c(n) = Θ(n)

    Claim: T(i) = c(i+1) for all i ≥ 0

    Base case (i=0): T(0) = c(0+1) = c 

    Inductive Hypothesis: Assume T(k) = c(k+1) for some k ≥ 0

    Inductive Step: T(k+1) = T(k) + c = c(k+1) + c = c(k+2) 

     */


    /**
     * Get element at index
     * @param index where to look
     * @return the data at that spot
     * @throws IndexOutOfBoundsException if index is 
     * Time Complexity: &Theta;(n) 
     */
    public E get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index!");
        }
        return helperGet(head, index);
    }
    
    // recursive helper for get method
    private E helperGet(Node<E> currentNode, int index) {
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

    /*
    Same as get()

    T(0) = c (base case: index is 0, found immediately)
    T(i) = T(i-1) + c (recursive case: check current node, recurse with i-1)

    T(i) = T(i-1) + c
    = T(i-2) + 2c
    = T(0) + ic
    = c + ic
    = c(i+1)

    T(n-1) = c(n) = Θ(n)

    Claim: T(i) = c(i+1) for all i ≥ 0

    Base case (i=0): T(0) = c(0+1) = c 

    Inductive Hypothesis: Assume T(k) = c(k+1) for some k ≥ 0

    Inductive Step: T(k+1) = T(k) + c = c(k+1) + c = c(k+2) 

     */

    /**
     * Add element at specific index
     * @param index where to add
     * @param x what to add
     * @throws IndexOutOfBoundsException if index is out of range
     * Time Complexity: &Theta;(n) 
     */
    public void add(int index, E x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index");
        }

        if (index > size) {
            throw new IndexOutOfBoundsException("index too big");
        }

        if (index == size) {
            // utilzing the O(1) add()
            add(x);
            return; // end it
        }

        head = helperAdd(head, index, x);

        // if list empty or only 1 element then update tail
        if (size == 0) {
            tail = head;
        }
        else if (size == 1){
            tail = head.getRest();
        }

        size++;
    }
    
    // Not sure if this is right way 
    private Node<E> helperAdd(Node<E> currentNode, int index, E x) {
        if (index == 0) {
            // insert here by making new node that points to current
            return new Node<>(x, currentNode); // Using the <> operator because I got yelled by java for calling the generate type redundantly
        }
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        // rebuild the node with the rest modified
        return new Node<>(currentNode.getData(), helperAdd(currentNode.getRest(), index - 1, x));
    }

    /*
    TIME COMPLEXITY ANALYSIS FOR addtoEnd()
    same as size()
     
     T(0) = c (base case)
     T(n) = T(n-1) + c (recursive case)
     
     T(n) = T(n-1) + c
          = T(n-2) + 2c
          = T(0) + nc
          = c + nc = c(n+1)
     Therefore T(n) = Θ(n)
     
     Proof by Induction:
     Claim: T(n) = c(n+1) for all n ≥ 0
     Base case (n=0): T(0) = c(0+1) = c 
     Inductive Hypothesis: Assume T(k) = c(k+1) for some k ≥ 0
     Inductive Step: T(k+1) = T(k) + c = c(k+1) + c = c(k+2) 
    */

    /*

     Updated:

    - Removed 'final' from Node fields (ephemeral nodes)
    - Added private Node tail field
    - Added setRest() method to Node
    - add(E x) now appends in constant time: T(n) = c = Θ(1)
    */

    
    /**
     * Add to end of list
     * @param x the thing to add
     * @return true always
     * Time Complexity: updated from &Theta;(n) to &Theta;(1)
     */
    public boolean add(E x) {
        Node<E>newNode = new Node<>(x, null);

        if (head==null) {
            // for empty list
            head = newNode;
            tail = newNode;
        }
        else{
            // attach to the current tail and update it
            tail.setRest(newNode);
            tail = newNode;
        }

        size++;
        return true;

    }
    /*
    DONT NEED IT ANYMORE
    // add to the end recursively
    private Node addToEnd(Node currentNode, E x) {
        if (currentNode == null) {
            // reached the end, make new node
            return new Node(x, null);
        }
        // rebuild this node with modified rest
        return new Node(currentNode.getData(), addToEnd(currentNode.getRest(), x));

    }
    
    */

    /*
    1.) TIME COMPLEXITY ANALYSIS FOR get()
    I already computed - Θ(n)

     
    2.) TIME COMPLEXITY ANALYSIS FOR helperRemove(n)
     
     T(0) = c (base case)
     T(n) = T(n-1) + c (recursive case)
     
     T(n) = T(n-1) + c
          = T(n-2) + 2c
          = T(0) + nc
          = c + nc = c(n+1)
     Therefore T(n) = Θ(n)
     
     Proof by Induction:
     Claim: T(n) = c(n+1) for all n ≥ 0
     Base case (n=0): T(0) = c(0+1) = c 
     Inductive Hypothesis: Assume T(k) = c(k+1) for some k ≥ 0
     Inductive Step: T(k+1) = T(k) + c = c(k+1) + c = c(k+2) 

     T_remove(n) = T_get(n) + T_helperRemove(n)
            = Θ(n) + Θ(n)
            = Θ(n)
    */


    
    /**
     * Remove element at index
     * @param index which one to remove
     * @return the thing that got removed
     * @throws IndexOutOfBoundsException if index is bad
     * Time Complexity: &Theta;(n)
     */
    public E remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("can't remove negative index");
        }
        if (head == null) {
            throw new IndexOutOfBoundsException("can't remove from empty list");
        }
        E valueToRemove = get(index);
        head = helperRemove(head,index);
        size--;

        if (size == 0) {
            tail = null; //Empty list
        } else if (index == size) {
            // last element removed find a new tail through traversal
            tail = findLastNode(head);

        }

        return valueToRemove;
    }
    // helper method removes node at index recursively
    private Node<E> helperRemove(Node<E> currentNode, int index) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            // skip this node by returning the rest
            return currentNode.getRest();
        }
        // rebuild with modified rest
        return new Node<>(currentNode.getData(), helperRemove(currentNode.getRest(), index - 1));
    }

    private Node<E> findLastNode(Node<E> currentNode){
        if (currentNode == null || currentNode.getRest()==null) {
            return currentNode;
        
        }
        return findLastNode(currentNode.getRest());
    }
    
    /**
     * Set value at index
     * @param index where to set
     * @param x new value
     * @return old value
     * @throws IndexOutOfBoundsException if index is out of range
     * Time Complexity: &Theta;(n) 
     */
    public E set(int index, E x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index");
        }
        E oldValue = get(index); 
        head = helperSet(head, index, x);
        return oldValue;
    }
    
    // helper methods sets value at index recursively
    private Node<E> helperSet(Node<E> currentNode, int index, E x) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            return new Node<>(x, currentNode.getRest());
        }
        return new Node<>(currentNode.getData(), helperSet(currentNode.getRest(), index - 1, x));
    }
    
}

/*

ALGORITHM A ANALYSIS:

Algorithm A uses: list.add(0, x) - adds at index 0 (beginning)

Time complexity of add(0, x):
From helperAdd(currentNode, 0, x):
  - When index == 0, immediately returns new Node(x, currentNode)
  - No recursion, no traversal
  - Time: Θ(1)

For n elements total:
T(n) = n × c (each insertion costs constant time c)
T(n) = cn = Θ(n)


ALGORITHM B ANALYSIS:

Algorithm B uses: list.add(x) - adds at end

Time complexity of add(x) when list has k elements: Θ(k)
(From previous analysis: must traverse and rebuild all k nodes)

For n elements total:
- 1st insertion: list size = 0, cost = c(0+1) = c
- 2nd insertion: list size = 1, cost = c(1+1) = 2c
- 3rd insertion: list size = 2, cost = c(2+1) = 3c
- ...
- nth insertion: list size = n-1, cost = c(n-1+1) = cn

Total time:
T(n) = c + 2c + 3c + ... + nc
     = c(1 + 2 + 3 + ... + n)
     = c × [n(n+1)/2]
     = c(n² + n)/2
     = Θ(n²)

Proof that 1 + 2 + 3 + ... + n = n(n+1)/2:
Base case (n=1): 1 = 1(1+1)/2 = 1 
Inductive Hypothesis: Assume 1 + 2 + ... + k = k(k+1)/2
Inductive Step: 
  1 + 2 + ... + k + (k+1) = k(k+1)/2 + (k+1)
                          = [k(k+1) + 2(k+1)]/2
                          = [(k+1)(k+2)]/2 



Algorithm A is Better.
- Algorithm A: Θ(n) - linear time
- Algorithm B: Θ(n²) - quadratic time
                         
    
 */


/*
After adding the ephermal nodes and tail pointers

Algorithm A: Still Θ(n)
- Uses add(0, x) which remains Θ(1) per call
- For n elements: n × Θ(1) = Θ(n)

Algorithm B: Now Better Θ(n)
- Uses add(x) which is NOW Θ(1) per call (was Θ(n) before)
- For n elements: n × Θ(1) = Θ(n)

*/
