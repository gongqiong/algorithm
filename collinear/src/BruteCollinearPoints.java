import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private final int size;
    
    // find all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.sort(points);
        checkDuplicate(points);
        final int num = points.length;
        List<LineSegment> listOfLineSegment = new LinkedList<>();
        for (int i = 0; i < num - 3; i += 1) {
            for (int j = i + 1; j < num - 2; j += 1) {
                for (int k = j + 1; k < num - 1; k += 1) {
                    if (points[i].slopeOrder().compare(points[j], points[k]) == 0) {
                        for (int l = k + 1; l < num; l += 1) {
                            if (points[i].slopeOrder().compare(points[j], points[l]) == 0) {
                                listOfLineSegment.add(new LineSegment(points[i], points[l]));
                                break;
                            }
                        }
                    }
                }
            }
        }
        size = listOfLineSegment.size();
        lineSegments = listOfLineSegment.toArray(new LineSegment[size]);
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
    
    // the line segments
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
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment lineSegment : bcp.lineSegments) {
            StdOut.println(lineSegment);
            lineSegment.draw();
        }
        StdDraw.show();
    }
}
