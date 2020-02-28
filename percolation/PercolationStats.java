import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int size;
    private final int numOfTrials;
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    
    // perform independent trials on a n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        numOfTrials = trials;
        final double[] pArray = new double[trials];
        for (int i = 0; i < trials; i += 1) {
            Percolation perc = new Percolation(n);
            pArray[i] = oneTrial(perc);
        }
        mean = StdStats.mean(pArray);
        stddev = StdStats.stddev(pArray);
    }
    
    private double oneTrial(Percolation perc) {
        while (!perc.percolates()) {
            int i = StdRandom.uniform(1, size + 1);
            int j = StdRandom.uniform(1, size + 1);
            perc.open(i, j);
        }
        return perc.numberOfOpenSites() / (double) (size * size);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    
    // low endpoint of 95% confident interval;
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * stddev / Math.sqrt(numOfTrials);
    }
    
    // high endpoint of 95% confident interval;
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * stddev / Math.sqrt(numOfTrials);
    }
    
    // test client
    
    /**
     * takes two command-line arguments n and T, performs T independent computational experiments
     * on an n-by-n grid, and prints the sample mean,
     * sample standard deviation, and the 95% confidence interval for the percolation threshold.
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println(String.format("sample mean: %f \nsample standard deviation: %f \n" +
                        "95%% confidence interval: [%f, %f]",
                ps.mean(),
                ps.stddev(),
                ps.confidenceLo(),
                ps.confidenceHi()));
    }
}
