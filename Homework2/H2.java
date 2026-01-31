import java.util.Scanner;

public class H2 {
    /**
     * Main method that reads integers and finds the duplicate.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList list = new LinkedList();
        
        // Read integers and add them to the list
        readInputHelper(scanner, list);
        
        // Find and print the duplicate
        Integer duplicate = findDuplicate(list);
        System.out.println(duplicate);
    }
    
    private static void readInputHelper(Scanner scanner, LinkedList list) {
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            list.add(number);
            readInputHelper(scanner, list);
        }
    }
    
    /**
     * Finds the duplicate number in the given linked list.
     * @param list a LinkedList containing n+1 integers from 1 to n, with exactly one duplicate
     * @return the Integer that repeats
     * Sumlist avoids getting an index more than once
     */
    public static Integer findDuplicate(LinkedList list) {
        int size = list.size();
        int n = size - 1;
        long sum = sumList(list, 0, size);
        long expected = n * (n + 1) / 2; // sum of numbers from 1 to n
        return (int) (sum - expected); // exposes the duplicate
    }

    private static long sumList(LinkedList list, int index, int size) {
        if (index == size) {
            return 0L;
        }
        return list.get(index) + sumList(list, index + 1, size);
    }
}