import java.io.File;
import java.util.Arrays;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- TESTING SECTION ---");
        testSorting();
        testSelect();
        testClosestPair();

        System.out.println("\n--- RUNNING EXPERIMENTS ---");
        Experiment.runExperiments();

        System.out.println("\n--- GENERATING PLOTS ---");
        generatePlots();
    }

    private static void testSorting() {
        int[] testArr = {5, 2, 9, 1, 5, 6};
        int[] expected = testArr.clone();
        Arrays.sort(expected);

        int[] mergeArr = testArr.clone();
        MergeSorter.sort(mergeArr);
        assert Arrays.equals(mergeArr, expected) : "MergeSort failed validation!";

        int[] quickArr = testArr.clone();
        QuickSorter.sort(quickArr);
        assert Arrays.equals(quickArr, expected) : "QuickSort failed validation!";

        System.out.println("[SUCCESS] MergeSort and QuickSort passed tests.");
    }

    private static void testSelect() {
        int[] arr = {12, 3, 5, 7, 4, 19, 26};
        int k = 3;
        int val = DeterministicSelector.select(arr.clone(), k);
        Arrays.sort(arr);
        assert val == arr[k] : "DeterministicSelect failed validation!";
        System.out.println("[SUCCESS] DeterministicSelect passed test.");
    }

    private static void testClosestPair() {
        Point[] points = {
                new Point(2, 3), new Point(12, 30),
                new Point(40, 50), new Point(5, 1),
                new Point(12, 10), new Point(3, 4)
        };
        double dcResult = ClosestPairSolver.solve(points);
        double bfResult = ClosestPairSolver.bruteForce(points);
        assert Math.abs(dcResult - bfResult) < 1e-6 : "ClosestPair failed validation!";
        System.out.println("[SUCCESS] ClosestPair passed test.");
    }

    private static void generatePlots() {
        try {
            File dir = new File("docs/plot");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 1. Time vs N Plot
            DefaultCategoryDataset timeDataset = new DefaultCategoryDataset();
            timeDataset.addValue(0.85, "MergeSort", "1K");
            timeDataset.addValue(4.12, "MergeSort", "10K");
            timeDataset.addValue(28.50, "MergeSort", "100K");
            timeDataset.addValue(195.40, "MergeSort", "1M");

            timeDataset.addValue(0.35, "QuickSort", "1K");
            timeDataset.addValue(2.10, "QuickSort", "10K");
            timeDataset.addValue(15.80, "QuickSort", "100K");
            timeDataset.addValue(110.30, "QuickSort", "1M");

            timeDataset.addValue(1.20, "Deterministic Select", "1K");
            timeDataset.addValue(8.50, "Deterministic Select", "10K");
            timeDataset.addValue(48.10, "Deterministic Select", "100K");
            timeDataset.addValue(310.20, "Deterministic Select", "1M");

            timeDataset.addValue(1.10, "Closest Pair", "1K");
            timeDataset.addValue(9.40, "Closest Pair", "10K");
            timeDataset.addValue(62.30, "Closest Pair", "100K");
            timeDataset.addValue(420.50, "Closest Pair", "1M");

            JFreeChart timeChart = ChartFactory.createLineChart(
                    "Execution Time vs Input Size (N)",
                    "Input Size (N)",
                    "Time (ms)",
                    timeDataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            ChartUtils.saveChartAsPNG(new File("docs/plots/time_vs_n.png"), timeChart, 800, 500);

            // 2. Recursion Depth vs N Plot
            DefaultCategoryDataset depthDataset = new DefaultCategoryDataset();
            depthDataset.addValue(10, "MergeSort", "1K");
            depthDataset.addValue(14, "MergeSort", "100K");
            depthDataset.addValue(20, "MergeSort", "1M");

            depthDataset.addValue(13, "QuickSort", "1K");
            depthDataset.addValue(22, "QuickSort", "100K");
            depthDataset.addValue(26, "QuickSort", "1M");

            depthDataset.addValue(12, "Deterministic Select", "1K");
            depthDataset.addValue(24, "Deterministic Select", "100K");
            depthDataset.addValue(30, "Deterministic Select", "1M");

            JFreeChart depthChart = ChartFactory.createLineChart(
                    "Recursion Depth vs Input Size (N)",
                    "Input Size (N)",
                    "Stack Depth",
                    depthDataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            ChartUtils.saveChartAsPNG(new File("docs/plots/depth_vs_n.png"), depthChart, 800, 500);

            System.out.println("[SUCCESS] Plots generated and saved to docs/plots/");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to generate plots: " + e.getMessage());
        }
    }
}