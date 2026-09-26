import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Experiment {
    private static final Random random = new Random();

    public static void runExperiments() {
        String csvFile = "results/results.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            writer.println("Algorithm,InputType,Size,ExecutionTimeNs,MaxRecursionDepth,Comparisons");

            int[] sizes = {100, 1000, 10000, 50000};
            String[] types = {"Random", "Sorted", "Reverse-Sorted", "Duplicates"};

            for (int size : sizes) {
                for (String type : types) {
                    int[] arr1 = generateArray(size, type);
                    int[] arr2 = arr1.clone();
                    int[] arr3 = arr1.clone();

                    long start = System.nanoTime();
                    MergeSorter.sort(arr1);
                    long time = System.nanoTime() - start;
                    writer.printf("MergeSort,%s,%d,%d,%d,%d\n", type, size, time, MergeSorter.maxRecursionDepth, MergeSorter.comparisons);

                    start = System.nanoTime();
                    QuickSorter.sort(arr2);
                    time = System.nanoTime() - start;
                    writer.printf("QuickSort,%s,%d,%d,%d,%d\n", type, size, time, QuickSorter.maxRecursionDepth, QuickSorter.comparisons);

                    int k = size / 2;
                    start = System.nanoTime();
                    DeterministicSelector.select(arr3, k);
                    time = System.nanoTime() - start;
                    writer.printf("DeterministicSelect,%s,%d,%d,%d,%d\n", type, size, time, DeterministicSelector.maxRecursionDepth, DeterministicSelector.comparisons);
                }

                Point[] points = generatePoints(size);
                long start = System.nanoTime();
                ClosestPairSolver.solve(points);
                long time = System.nanoTime() - start;
                writer.printf("ClosestPair,Random,%d,%d,%d,0\n", size, time, ClosestPairSolver.maxRecursionDepth);
            }
            System.out.println("Experiments finished. Results saved to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        switch (type) {
            case "Random":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(size * 10);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "Reverse-Sorted":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Duplicates":
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(10);
                break;
        }
        return arr;
    }

    private static Point[] generatePoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }
}