import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * methods for finding and removing duplicates in a list of integers usingthe Iterable and Iterator interfaces. 
*/
public class H5A {

    /**
     * Returns the integer that appears twice in the sequence.
     * Uses an enhanced for loop to scan the Iterable.
     * @param xs An Iterable of Integers containing exactly one duplicate.
     * @return The duplicated integer value.
     */

    public static int findDuplicate(Iterable<Integer> xs) {
        int n = 0;
        // Find the max number to calculate size of the array
        for (int x : xs) {
            if (x > n) n = x;
        }
        boolean[] seen = new boolean[n + 1]; 
        for (int x : xs) {
            if (seen[x]) return x; // found the duplicate
            seen[x] = true;
        }
        throw new IllegalArgumentException("No duplicate found");
    }

    /**
     * Implements ArrayList
     * Reads integer input into a list, prints the duplicate, removes
     * the first occurrence via iterator, and prints deduplicated result.
     * Input is terminated by any non-integer.
     * @param args ignored
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Integer> data = new ArrayList<>(); // Implements ArrayList

        // Read integers from input
        while (in.hasNextInt()) {
            data.add(in.nextInt());
        }

         // Find and print duplicate
        int dup = findDuplicate(data);
        System.out.println("The duplicate is " + dup + ".");

        Iterator<Integer> it = data.iterator();
        while (it.hasNext()) {
            if (it.next() == dup) {
                it.remove(); // removes the first occurence of the repeat
                break;
            }
        }

        System.out.println("Deduplicated: " + data);
    }
}