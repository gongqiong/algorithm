import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] lineSegments;
    private final int size;
    
    // find all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.sort(points);
        checkDuplicate(points);
        int num = points.length;
        List<LineSegment> listOfLineSegments = new LinkedList<>();
        for (int i = 0; i < num - 3; i += 1) {
            Point pi = points[i];
            int numOfNext = num - i - 1;
            Point[] pointsBySlop = new Point[numOfNext];
            System.arraycopy(points, i + 1, pointsBySlop, 0, numOfNext);
            Arrays.sort(pointsBySlop, pi.slopeOrder());
            int x = 0;
            while (numOfNext >= 3 && x < numOfNext - 2) {
                int n = 1;
                double slopi = pi.slopeTo(pointsBySlop[x]);
                while (x < numOfNext - 1 && pi.slopeTo(pointsBySlop[x + 1]) == slopi) {
                    n += 1;
                    x += 1;
                }
                if (n >= 3) {
                    listOfLineSegments.add(new LineSegment(pi, pointsBySlop[x]));
                }
                x += 1;
            }
        }
        size = listOfLineSegments.size();
        lineSegments = listOfLineSegments.toArray(new LineSegment[size]);
    }
    
    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("the array \"points\" is null.");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("the array contains null item.");
            }
        }
    }
    
    private void checkDuplicate(Point[] points) {
        for (int i = 1; i < points.length; i += 1) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("the array contains repeated points.");
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return size;
    }
    
    // the line segment
    public LineSegment[] segments() {
        return lineSegments;
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int numberOfPoints = in.readInt();
        Point[] points = new Point[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i += 1) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-1000, 32768);
        StdDraw.setYscale(-1000, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        for (LineSegment lineSegment : fcp.lineSegments) {
            StdOut.println(lineSegment);
            lineSegment.draw();
        }
        StdDraw.show();
    }
}
