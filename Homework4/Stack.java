/**
 * A data structure with efficient last-in first-out insertion and removal.
 * 
 * @param <E>  the type of objects contained by this stack.
 * @author Dakotah Lambert
 * @version 2025.09
 */
public interface Stack<E> {
	/**
	 * Insert the specified element into this stack.
	 * Implementations should provide &Theta;(1) insertion.
	 *
	 * @param x  the element to insert.
	 */
	void push(E x);

	/**
	 * Removes and returns the most recently pushed element in this stack.
	 * No guarantees are provided when modifications
	 * other than {@link push} or {@link pop} are used.
	 * Implementations should provide &Theta;(1) removal.
	 *
	 * @return  the next element in this stack.
	 * @throws java.util.NoSuchElementException if this stack is empty.
	 */
	E pop();

	/**
	 * Retrieves but does not remove the top of this stack.
	 * No guarantees are provided when operations
	 * other than {@link push} or {@link pop} are used.
	 * Implementations should provide &Theta;(1) access.
	 * This may be implemented by first popping
	 * and then pushing back the content.
	 *
	 * @return  the next element in this stack.
	 * @throws java.util.NoSuchElementException if this stack is empty.
	 */
	E top();

	/**
	 * Returns whether this stack is empty.
	 *
	 * @return whether a {@link pop} or {@link top} operation is safe to perform.
	 */
	boolean isEmpty();
}
