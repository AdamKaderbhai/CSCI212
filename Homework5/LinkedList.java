import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic singly-linked list that implements Stack, Queue, and Iterable interfaces.
 * All public methods have Θ(1) time for stack, queue and iterator.
 * includes a fully functioning iterator with remove.
 * @param <E> the type of elements stored in this list
 */
public class LinkedList<E> implements Stack<E>, Queue<E>, Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * Node in singly-linked list.
     */
    private static class Node<T> {
        private T data;
        private Node<T> rest;

        public Node(T data, Node<T> rest) {
            this.data = data;
            this.rest = rest;
        }
        public T getData() {
            return data;
        }
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
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Checks if the list is empty.
     * Time Complexity: Θ(1)
     * @return true if the list has no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Removes all elements from the list, making it empty.
     * Time Complexity: Θ(1)
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Get size of list.
     * @return how many elements
     * Time Complexity: Θ(1)
     */
    public int size() {
        return size;
    }

    /**
     * Get element at index.
     * @param index where to look
     * @return the data at that spot
     * @throws IndexOutOfBoundsException if index is out of range
     * Time Complexity: Θ(n)
     */
    public E get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index!");
        }
        return helperGet(head, index);
    }

    // Recursive helper for get method
    private E helperGet(Node<E> currentNode, int index) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            return currentNode.getData();
        }
        return helperGet(currentNode.getRest(), index - 1);
    }

    /**
     * Adds element at specific index.
     * @param index where to add
     * @param x what to add
     * @throws IndexOutOfBoundsException if index is out of range
     * Time Complexity: Θ(n)
     */
    public void add(int index, E x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == size) {
            add(x);
            return;
        }
        head = helperAdd(head, index, x);
        if (size == 0) {
            tail = head;
        } else if (size == 1) {
            tail = head.getRest();
        }
        size++;
    }

    private Node<E> helperAdd(Node<E> currentNode, int index, E x) {
        if (index == 0) {
            return new Node<>(x, currentNode);
        }
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        return new Node<>(currentNode.getData(), helperAdd(currentNode.getRest(), index - 1, x));
    }

    /**
     * Add to end of list.
     * @param x the thing to add
     * @return true always
     * Time Complexity: Θ(1)
     */
    public boolean add(E x) {
        Node<E> newNode = new Node<>(x, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setRest(newNode);
            tail = newNode;
        }
        size++;
        return true;
    }

    /**
     * Remove element at index.
     * @param index which one to remove
     * @return the thing that got removed
     * @throws IndexOutOfBoundsException if index is bad
     * Time Complexity: Θ(n)
     */
    public E remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("can't remove negative index");
        }
        if (head == null) {
            throw new IndexOutOfBoundsException("can't remove from empty list");
        }
        E valueToRemove = get(index);
        head = helperRemove(head, index);
        size--;
        if (size == 0) {
            tail = null;
        } else if (index == size) {
            tail = findLastNode(head);
        }
        return valueToRemove;
    }
    private Node<E> helperRemove(Node<E> currentNode, int index) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            return currentNode.getRest();
        }
        return new Node<>(currentNode.getData(), helperRemove(currentNode.getRest(), index - 1));
    }
    private Node<E> findLastNode(Node<E> currentNode){
        if (currentNode == null || currentNode.getRest()==null) {
            return currentNode;
        }
        return findLastNode(currentNode.getRest());
    }

    /**
     * Set value at index.
     * @param index where to set
     * @param x new value
     * @return old value
     * @throws IndexOutOfBoundsException if index is out of range
     * Time Complexity: Θ(n)
     */
    public E set(int index, E x) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("negative index");
        }
        E oldValue = get(index);
        head = helperSet(head, index, x);
        return oldValue;
    }

    private Node<E> helperSet(Node<E> currentNode, int index, E x) {
        if (currentNode == null) {
            throw new IndexOutOfBoundsException("index too big");
        }
        if (index == 0) {
            return new Node<>(x, currentNode.getRest());
        }
        return new Node<>(currentNode.getData(), helperSet(currentNode.getRest(), index - 1, x));
    }

    /** 
     * Stack: Push an element onto the stack (front). Θ(1)
     */
    @Override
    public void push(E x) {
        add(0, x);
    }

    /** 
     * Stack: Remove and return the most recently pushed element. Θ(1)
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty Stack!");
        }
        return remove(0);
    }

    /** 
     * Stack: Retrieve the top element. Θ(1)
     */
    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is Empty");
        }
        return get(0);
    }

    /** 
     * Queue: Enqueue an element at the tail. Θ(1)
     */
    @Override
    public void enqueue(E x) {
        add(x);
    }

    /** 
     * Queue: Remove and return element at the head. Θ(1)
     */
    @Override
    public E dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty Queue");
        return remove(0);
    }

    /**
     * Returns an iterator over elements of type E.
     * Required for Iterable<E>.
     * @return an Iterator that traverses this list from head to tail
     */
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Iterator for LinkedList(inner class)
     * All methods run in Θ(1) time.
     * Supports next(), hasNext(), and remove()
     */
    private class LinkedListIterator implements Iterator<E> {
        private Node<E> prev = null;
        private Node<E> prevOfPrev = null; // node before prev
        private Node<E> curr = head;
        private boolean canRemove = false;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E val = curr.getData();
            prevOfPrev = prev;
            prev = curr;
            curr = curr.getRest();
            canRemove = true;
            return val;
        }

        @Override
        public void remove() {
            if (!canRemove) throw new IllegalStateException("next() not called or remove() already called");
            if (prevOfPrev == null) {
                // removes head
                head = curr;
                if (head == null) tail = null; // removes last element
            } else {
                prevOfPrev.rest = curr;
                if (curr == null) tail = prevOfPrev; // removes tail
            }
            size--;
            prev = prevOfPrev; // allow only one remove per next()
            canRemove = false;
        }
    }

    /**
     * Returns a string representation using {}.
     * @return string form of list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Node<E> node = head;
        while (node != null) {
            sb.append(node.getData());
            node = node.getRest();
            if (node != null) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}


