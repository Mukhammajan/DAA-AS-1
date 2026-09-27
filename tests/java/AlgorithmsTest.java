import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmsTest {

    private final Random random = new Random(123);

    private int[] randomArray(int n, int bound) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = random.nextInt(bound);
        return a;
    }

    //  MergeSort

    @Test
    void mergeSortMatchesArraysSortOnRandomInput() {
        int[] a = randomArray(500, 1000);
        int[] expected = a.clone();
        Arrays.sort(expected);

        MergeSorter sorter = new MergeSorter();
        sorter.sort(a);
        assertArrayEquals(expected, a);
    }

    @Test
    void mergeSortHandlesEmptyAndSingleElement() {
        MergeSorter sorter = new MergeSorter();

        int[] empty = {};
        sorter.sort(empty);
        assertEquals(0, empty.length);

        int[] single = {42};
        sorter.sort(single);
        assertArrayEquals(new int[]{42}, single);
    }

    @Test
    void mergeSortHandlesSortedReverseAndDuplicates() {
        MergeSorter sorter = new MergeSorter();

        int[] sorted = {1, 2, 3, 4, 5};
        sorter.sort(sorted);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);

        int[] reverse = {5, 4, 3, 2, 1};
        sorter.sort(reverse);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, reverse);

        int[] dup = {3, 3, 1, 3, 2, 1};
        int[] expectedDup = dup.clone();
        Arrays.sort(expectedDup);
        sorter.sort(dup);
        assertArrayEquals(expectedDup, dup);
    }

    //  QuickSort

    @Test
    void quickSortMatchesArraysSortOnRandomInput() {
        int[] a = randomArray(500, 1000);
        int[] expected = a.clone();
        Arrays.sort(expected);

        QuickSorter.sort(a);
        assertArrayEquals(expected, a);
    }

    @Test
    void quickSortHandlesEdgeCases() {
        int[] empty = {};
        QuickSorter.sort(empty);
        assertEquals(0, empty.length);

        int[] single = {7};
        QuickSorter.sort(single);
        assertArrayEquals(new int[]{7}, single);

        int[] dup = {2, 2, 2, 2, 1};
        int[] expectedDup = dup.clone();
        Arrays.sort(expectedDup);
        QuickSorter.sort(dup);
        assertArrayEquals(expectedDup, dup);
    }

    //  Deterministic Select

    @Test
    void deterministicSelectMatchesSortedArrayOver100RandomTrials() {
        DeterministicSelector ds = new DeterministicSelector();
        for (int trial = 0; trial < 100; trial++) {
            int n = 1 + random.nextInt(300);
            int[] a = randomArray(n, 1000);
            int k = random.nextInt(n);

            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            int actual = ds.select(a, k);
            assertEquals(expected, actual, "Mismatch for n=" + n + ", k=" + k);
        }
    }
}