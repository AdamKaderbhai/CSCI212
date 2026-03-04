import java.util.Iterator;
import java.util.Scanner;

/**
 * methods for finding and removing duplicates in a list of integers usingthe Iterable and Iterator interfaces. 
*/


public class H5L {
    /**
     * Returns the integer that appears twice in the sequence.
     * Uses an enhanced for-loop to scan the Iterable.
     * @param xs An Iterable of Integers containing exactly one duplicate
     * @return The duplicated integer value.
     */
    public static int findDuplicate(Iterable<Integer> xs) {
        int n = 0;
        // Finds the largest number to figure out n 
        for (int x : xs) {
            if (x > n) n = x;
        }
        // Uses a boolean array to track counts, since values are from 1 to n inclusive.
        boolean[] seen = new boolean[n + 1]; 
        for (int x : xs) {
            if (seen[x]) return x; // found the duplicate
            seen[x] = true;
        }
        // if no duplicate found
        throw new IllegalArgumentException("No duplicate found");
    }

    /**
     * Implements LinkedList
     * Reads integer input into a list, prints the duplicate, removes
     * the first occurrence via iterator, and prints deduplicated result.
     * Input is terminated by any non-integer.
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LinkedList<Integer> data = new LinkedList<>(); // implements LinkedLost
      
        
        // Read integers from input
        while (in.hasNextInt()) {
            data.add(in.nextInt());
        }

        // Find and print duplicate
        int dup = findDuplicate(data);
        System.out.println("The duplicate is " + dup + ".");

        // Remove first occurrence of duplicate using iterator's remove()
        Iterator<Integer> it = data.iterator();
        while (it.hasNext()) {
            if (it.next() == dup) {
                it.remove(); // removes the first occurence of the repeat
                break;
            }
        }

        // Print deduplicated list
        System.out.println("Deduplicated: " + data);
    }
}