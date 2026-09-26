import java.util.Random;

public class QuickSorter {
    public static long comparisons = 0;
    public static int maxRecursionDepth = 0;
    private static final Random random = new Random();

    public static void sort(int[] a) {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (a == null || a.length <= 1) return;
        sort(a, 0, a.length - 1, 1);
    }

    private static void sort(int[] a, int lo, int hi, int depth) {
        while (lo < hi) {
            if (depth > maxRecursionDepth) maxRecursionDepth = depth;
            int pivotIndex = lo + random.nextInt(hi - lo + 1);
            int p = partition(a, lo, hi, pivotIndex);

            if (p - lo < hi - p) {
                sort(a, lo, p - 1, depth + 1);
                lo = p + 1;
            } else {
                sort(a, p + 1, hi, depth + 1);
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, int pivotIndex) {
        int pivotValue = a[pivotIndex];
        swap(a, pivotIndex, hi);
        int storeIndex = lo;
        for (int i = lo; i < hi; i++) {
            comparisons++;
            if (a[i] < pivotValue) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, storeIndex, hi);
        return storeIndex;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}