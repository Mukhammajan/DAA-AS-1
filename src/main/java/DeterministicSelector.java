import java.util.Arrays;

public class DeterministicSelector {
    public static long comparisons = 0;
    public static int maxRecursionDepth = 0;

    public static int select(int[] a, int k) {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (a == null || k < 0 || k >= a.length) throw new IllegalArgumentException();
        return select(a, 0, a.length - 1, k, 1);
    }

    private static int select(int[] a, int lo, int hi, int k, int depth) {
        if (depth > maxRecursionDepth) maxRecursionDepth = depth;
        if (lo == hi) return a[lo];

        int pivotIndex = pivot(a, lo, hi, depth);
        pivotIndex = partition(a, lo, hi, pivotIndex);

        if (k == pivotIndex) return a[k];
        else if (k < pivotIndex) return select(a, lo, pivotIndex - 1, k, depth + 1);
        else return select(a, pivotIndex + 1, hi, k, depth + 1);
    }

    private static int pivot(int[] a, int lo, int hi, int depth) {
        if (hi - lo < 5) return medianOfFive(a, lo, hi);

        for (int i = lo; i <= hi; i += 5) {
            int subRight = Math.min(i + 4, hi);
            int median = medianOfFive(a, i, subRight);
            swap(a, median, lo + (i - lo) / 5);
        }

        int mid = lo + (hi - lo) / 10;
        return selectIndex(a, lo, lo + (hi - lo) / 5, mid, depth + 1);
    }

    private static int selectIndex(int[] a, int lo, int hi, int k, int depth) {
        if (depth > maxRecursionDepth) maxRecursionDepth = depth;
        if (lo == hi) return lo;

        int pivotIndex = pivot(a, lo, hi, depth);
        pivotIndex = partition(a, lo, hi, pivotIndex);

        if (k == pivotIndex) return k;
        else if (k < pivotIndex) return selectIndex(a, lo, pivotIndex - 1, k, depth + 1);
        else return selectIndex(a, pivotIndex + 1, hi, k, depth + 1);
    }

    private static int medianOfFive(int[] a, int lo, int hi) {
        Arrays.sort(a, lo, hi + 1);
        return lo + (hi - lo) / 2;
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