package Homework2;

import java.util.Scanner;

/**
 * Main program class for Homework 2.
 * Reads integers into a LinkedList and finds a duplicate.
 */
public class H2 {

    /**
     * Main entry point. Reads integers from standard input until invalid,
     * populates a list, and prints the duplicate found.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList list = new LinkedList();
        
        // Populate list recursively to avoid while-loops
        readAndAdd(scanner, list);
        
        System.out.println(findDuplicate(list));
    }

    /**
     * Recursively reads integers from the scanner and adds them to the list.
     * Stops when scanner.hasNextInt() returns false.
     *
     * @param scanner The scanner to read from.
     * @param list    The list to populate.
     */
    private static void readAndAdd(Scanner scanner, LinkedList list) {
        if (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
            readAndAdd(scanner, list);
        }
    }

    /**
     * Finds the duplicate number in a list containing numbers 1..n where one is repeated.
     * <p>
     * Precondition: list size is n+1, contains 1..n exactly once, plus one duplicate.
     * Uses the mathematical property that Sum(List) - Sum(1..n) = Duplicate.
     * This approach accesses every index exactly once via recursion.
     *
     * @param list The linked list to search.
     * @return The integer that appears twice in the list.
     */
    public static Integer findDuplicate(LinkedList list) {
        // The list has size n + 1. The values are 1..n with one duplicate.
        int size = list.size();
        int n = size - 1;

        // Calculate expected sum of 1..n: n(n+1)/2
        long expectedSum = (long) n * (n + 1) / 2;

        // Calculate actual sum of elements in the list
        long actualSum = sumListRecursive(list, 0);

        return (int) (actualSum - expectedSum);
    }

    /**
     * Recursively sums the elements of the list.
     *
     * @param list  The list to sum.
     * @param index The current index being processed.
     * @return The sum of elements from index to the end.
     */
    private static long sumListRecursive(LinkedList list, int index) {
        if (index >= list.size()) {
            return 0;
        }
        return list.get(index) + sumListRecursive(list, index + 1);
    }
}
