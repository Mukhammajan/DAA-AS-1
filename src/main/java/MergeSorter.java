public class MergeSorter {
    public static long comparisons = 0;
    public static int maxRecursionDepth = 0;

    public static void sort(int[] a) {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (a == null || a.length <= 1) return;
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1, 1);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, int depth) {
        if (depth > maxRecursionDepth) maxRecursionDepth = depth;
        if (hi - lo <= 15) {
            insertionSort(a, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, depth + 1);
        sort(a, aux, mid + 1, hi, depth + 1);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            comparisons++;
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }
}