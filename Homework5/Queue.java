/**
 * A data structure with efficient first-in first-out insertion and removal.
 * 
 * @param <E>  the type of objects contained by this queue.
 * @author Dakotah Lambert
 * @version 2025.09
 */
public interface Queue<E> {
	/**
	 * Inserts the specified element into this queue.
	 * Implementations should provide &Theta;(1) insertion.
	 *
	 * @param x  the element to insert.
	 */
	void enqueue(E x);

	/**
	 * Removes and returns the least recently enqueued element in this queue.
	 * No guarantees are provided when modifications
	 * other than {@link enqueue} or {@link dequeue} are used.
	 * Implementations should provide &Theta;(1) removal.
	 *
	 * @return  the next element in this queue.
	 * @throws java.util.NoSuchElementException if this queue is empty.
	 */
	E dequeue();

	/**
	 * Returns whether this queue is empty.
	 *
	 * @return whether a {@link dequeue} operation is safe to perform.
	 */
	boolean isEmpty();
}
