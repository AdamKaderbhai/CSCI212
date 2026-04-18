import java.util.Arrays;
import java.util.Random;

/**
 * Sorting experiment class for Paper P2.
 *
 * Step 1 to Step 4 include selection sort, heapsort, merge sort,
 * and tree sort (Red-Black tree).
 * Step 5 adds introsort.
 */
public class Sorting {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * Node used by the Red-Black Tree for tree sort.
     */
    private static class RBNode {
        int key;
        int count;
        boolean color;
        RBNode left;
        RBNode right;
        RBNode parent;

        RBNode(int key, boolean color) {
            this.key = key;
            this.count = 1;
            this.color = color;
        }
    }

    /**
     * Minimal Red-Black Tree used for tree sort.
     */
    private static class RBTree {
        private RBNode root;

        public void insert(int key) {
            if (root == null) {
                root = new RBNode(key, BLACK);
                return;
            }

            RBNode parent = null;
            RBNode cur = root;
            int cmp = 0;

            while (cur != null) {
                parent = cur;
                if (key < cur.key) {
                    cmp = -1;
                    cur = cur.left;
                } else if (key > cur.key) {
                    cmp = 1;
                    cur = cur.right;
                } else {
                    cur.count++;
                    return;
                }
            }

            RBNode z = new RBNode(key, RED);
            z.parent = parent;
            if (cmp < 0) {
                parent.left = z;
            } else {
                parent.right = z;
            }

            insertFixup(z);
        }

        public int writeInOrder(int[] arr) {
            int[] index = {0};
            writeInOrder(root, arr, index);
            return index[0];
        }

        private void writeInOrder(RBNode node, int[] arr, int[] index) {
            if (node == null) {
                return;
            }

            writeInOrder(node.left, arr, index);
            for (int i = 0; i < node.count; i++) {
                arr[index[0]] = node.key;
                index[0]++;
            }
            writeInOrder(node.right, arr, index);
        }

        private void insertFixup(RBNode z) {
            while (z.parent != null && z.parent.color == RED) {
                RBNode gp = z.parent.parent;
                if (gp == null) {
                    break;
                }

                if (z.parent == gp.left) {
                    RBNode uncle = gp.right;
                    if (uncle != null && uncle.color == RED) {
                        z.parent.color = BLACK;
                        uncle.color = BLACK;
                        gp.color = RED;
                        z = gp;
                    } else {
                        if (z == z.parent.right) {
                            z = z.parent;
                            rotateLeft(z);
                        }
                        z.parent.color = BLACK;
                        gp.color = RED;
                        rotateRight(gp);
                    }
                } else {
                    RBNode uncle = gp.left;
                    if (uncle != null && uncle.color == RED) {
                        z.parent.color = BLACK;
                        uncle.color = BLACK;
                        gp.color = RED;
                        z = gp;
                    } else {
                        if (z == z.parent.left) {
                            z = z.parent;
                            rotateRight(z);
                        }
                        z.parent.color = BLACK;
                        gp.color = RED;
                        rotateLeft(gp);
                    }
                }
            }

            root.color = BLACK;
        }

        private void rotateLeft(RBNode x) {
            RBNode y = x.right;
            x.right = y.left;
            if (y.left != null) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        private void rotateRight(RBNode y) {
            RBNode x = y.left;
            y.left = x.right;
            if (x.right != null) {
                x.right.parent = y;
            }
            x.parent = y.parent;
            if (y.parent == null) {
                root = x;
            } else if (y == y.parent.left) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
            x.right = y;
            y.parent = x;
        }
    }

    /**
     * Sorts an array in ascending order using selection sort.
     *
     * @param arr array to sort
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    /**
     * Sorts an array in ascending order using heapsort.
     *
     * @param arr array to sort
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build max-heap.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Repeatedly move max to end, then fix heap.
        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            heapify(arr, end, 0);
        }
    }

    /**
     * Sorts an array in ascending order using merge sort.
     *
     * @param arr array to sort
     */
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    /**
     * Sorts an array in ascending order using tree sort with a Red-Black Tree.
     *
     * @param arr array to sort
     */
    public static void treeSort(int[] arr) {
        RBTree tree = new RBTree();
        for (int value : arr) {
            tree.insert(value);
        }
        tree.writeInOrder(arr);
    }

    /**
     * Sorts an array in ascending order using introsort.
     * Introsort starts like quicksort, but switches to heapsort
     * when recursion gets too deep.
     *
     * @param arr array to sort
     */
    public static void introSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }

        int depthLimit = 2 * floorLog2(arr.length);
        introSort(arr, 0, arr.length - 1, depthLimit);
        insertionSortRange(arr, 0, arr.length - 1);
    }

    private static void introSort(int[] arr, int low, int high, int depthLimit) {
        while (high - low > 16) {
            if (depthLimit == 0) {
                heapSortRange(arr, low, high);
                return;
            }

            depthLimit--;
            int pivotIndex = partition(arr, low, high);

            // Recurse on smaller side first to keep stack shallow.
            if (pivotIndex - low < high - pivotIndex) {
                introSort(arr, low, pivotIndex - 1, depthLimit);
                low = pivotIndex + 1;
            } else {
                introSort(arr, pivotIndex + 1, high, depthLimit);
                high = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        // Median-of-three pivot selection.
        if (arr[mid] < arr[low]) {
            swap(arr, low, mid);
        }
        if (arr[high] < arr[low]) {
            swap(arr, low, high);
        }
        if (arr[high] < arr[mid]) {
            swap(arr, mid, high);
        }

        int pivot = arr[mid];
        swap(arr, mid, high);

        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }

        swap(arr, i, high);
        return i;
    }

    private static void insertionSortRange(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    private static int floorLog2(int n) {
        int result = 0;
        while (n > 1) {
            n /= 2;
            result++;
        }
        return result;
    }

    private static void heapSortRange(int[] arr, int low, int high) {
        int size = high - low + 1;

        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDownRange(arr, low, size, i);
        }

        for (int end = size - 1; end > 0; end--) {
            swap(arr, low, low + end);
            siftDownRange(arr, low, end, 0);
        }
    }

    private static void siftDownRange(int[] arr, int base, int heapSize, int root) {
        int current = root;

        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int largest = current;

            if (left < heapSize && arr[base + left] > arr[base + largest]) {
                largest = left;
            }

            if (right < heapSize && arr[base + right] > arr[base + largest]) {
                largest = right;
            }

            if (largest == current) {
                break;
            }

            swap(arr, base + current, base + largest);
            current = largest;
        }
    }

    /**
     * Recursive merge sort over arr[left..right].
     */
    private static void mergeSort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    /**
     * Merges two sorted halves: left..mid and mid+1..right.
     */
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int idx = left; idx <= right; idx++) {
            arr[idx] = temp[idx];
        }
    }

    /**
     * Restores max-heap property for subtree rooted at index i.
     */
    private static void heapify(int[] arr, int heapSize, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, heapSize, largest);
        }
    }

    /**
     * Swaps two positions in an array.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Checks if an array is sorted in non-decreasing order.
     *
     * @param arr array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Temporary main used for step-by-step development.
     */
    public static void main(String[] args) {
        runSmallDemo();

        System.out.println();
        System.out.println("================ Experiment Results ================");

        int n = 5000;
        int trials = 3;

        runExperimentForType("Random", n, trials);
        runExperimentForType("Sorted", n, trials);
        runExperimentForType("Reverse", n, trials);
    }

    private static void runSmallDemo() {
        int[] sample = {8, 6, 7, 5, 3, 0, 9};
        int[] sample2 = Arrays.copyOf(sample, sample.length);
        int[] sample3 = Arrays.copyOf(sample, sample.length);
        int[] sample4 = Arrays.copyOf(sample, sample.length);
        int[] sample5 = Arrays.copyOf(sample, sample.length);

        System.out.println("Before selection sort: " + Arrays.toString(sample));
        selectionSort(sample);
        System.out.println("After selection sort:  " + Arrays.toString(sample));
        System.out.println("Sorted? " + isSorted(sample));

        System.out.println();

        System.out.println("Before heap sort:      " + Arrays.toString(sample2));
        heapSort(sample2);
        System.out.println("After heap sort:       " + Arrays.toString(sample2));
        System.out.println("Sorted? " + isSorted(sample2));

        System.out.println();

        System.out.println("Before merge sort:     " + Arrays.toString(sample3));
        mergeSort(sample3);
        System.out.println("After merge sort:      " + Arrays.toString(sample3));
        System.out.println("Sorted? " + isSorted(sample3));

        System.out.println();

        System.out.println("Before tree sort:      " + Arrays.toString(sample4));
        treeSort(sample4);
        System.out.println("After tree sort:       " + Arrays.toString(sample4));
        System.out.println("Sorted? " + isSorted(sample4));

        System.out.println();

        System.out.println("Before intro sort:     " + Arrays.toString(sample5));
        introSort(sample5);
        System.out.println("After intro sort:      " + Arrays.toString(sample5));
        System.out.println("Sorted? " + isSorted(sample5));
    }

    private static void runExperimentForType(String inputType, int n, int trials) {
        long totalSelectionNs = 0;
        long totalHeapNs = 0;
        long totalMergeNs = 0;
        long totalTreeNs = 0;
        long totalIntroNs = 0;

        Random random = new Random(42);

        for (int t = 0; t < trials; t++) {
            int[] base = generateBaseArray(n, random);
            int[] input = buildInputType(base, inputType);

            totalSelectionNs += timedSort("selection", input);
            totalHeapNs += timedSort("heap", input);
            totalMergeNs += timedSort("merge", input);
            totalTreeNs += timedSort("tree", input);
            totalIntroNs += timedSort("intro", input);
        }

        System.out.println();
        System.out.println("Input type: " + inputType + " | n=" + n + " | trials=" + trials);
        System.out.printf("Selection sort avg ms: %.3f%n", totalSelectionNs / 1_000_000.0 / trials);
        System.out.printf("Heap sort avg ms:      %.3f%n", totalHeapNs / 1_000_000.0 / trials);
        System.out.printf("Merge sort avg ms:     %.3f%n", totalMergeNs / 1_000_000.0 / trials);
        System.out.printf("Tree sort avg ms:      %.3f%n", totalTreeNs / 1_000_000.0 / trials);
        System.out.printf("Intro sort avg ms:     %.3f%n", totalIntroNs / 1_000_000.0 / trials);
    }

    private static int[] generateBaseArray(int n, Random random) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100000);
        }
        return arr;
    }

    private static int[] buildInputType(int[] base, String inputType) {
        int[] result = Arrays.copyOf(base, base.length);

        if ("Sorted".equals(inputType)) {
            Arrays.sort(result);
        } else if ("Reverse".equals(inputType)) {
            Arrays.sort(result);
            reverseArray(result);
        }

        return result;
    }

    private static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }

    private static long timedSort(String name, int[] input) {
        int[] working = Arrays.copyOf(input, input.length);

        long start = System.nanoTime();

        if ("selection".equals(name)) {
            selectionSort(working);
        } else if ("heap".equals(name)) {
            heapSort(working);
        } else if ("merge".equals(name)) {
            mergeSort(working);
        } else if ("tree".equals(name)) {
            treeSort(working);
        } else if ("intro".equals(name)) {
            introSort(working);
        }

        long end = System.nanoTime();

        if (!isSorted(working)) {
            throw new RuntimeException(name + " sort failed");
        }

        return end - start;
    }
}
