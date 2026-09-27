import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    public static int maxRecursionDepth = 0;

    public static double solve(Point[] points) {
        if (points == null || points.length < 2) {
            return 0;
        }

        maxRecursionDepth = 0;

        Point[] px = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));

        Point[] py = points.clone();
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        return closest(px, py, 0);
    }

    private static double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    private static double closest(Point[] px, Point[] py, int depth) {
        if (depth > maxRecursionDepth) {
            maxRecursionDepth = depth;
        }

        if (px.length <= 3) {
            return bruteForce(px);
        }

        int mid = px.length / 2;
        Point midPoint = px[mid];

        Point[] lx = Arrays.copyOfRange(px, 0, mid);
        Point[] rx = Arrays.copyOfRange(px, mid, px.length);

        final double midX = midPoint.x;

        Point[] ly = Arrays.stream(py).filter(p -> p.x <= midX).toArray(Point[]::new);
        Point[] ry = Arrays.stream(py).filter(p -> p.x > midX).toArray(Point[]::new);

        double d1 = closest(lx, ly, depth + 1);
        double d2 = closest(rx, ry, depth + 1);
        final double d = Math.min(d1, d2);

        Point[] strip = Arrays.stream(py)
                .filter(p -> Math.abs(p.x - midX) < d)
                .toArray(Point[]::new);

        double minD = d;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < minD; j++) {
                minD = Math.min(minD, dist(strip[i], strip[j]));
            }
        }
        return minD;
    }

    public static double bruteForce(Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                min = Math.min(min, dist(points[i], points[j]));
            }
        }
        return min;
    }
}