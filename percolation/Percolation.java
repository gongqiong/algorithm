import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The constructor should take time proportional to n2;
 * all methods should take constant time plus a constant number
 * of calls to the union–find methods union(), find(), connected(), and count().
 */
public class Percolation {
    private boolean[][] grid;
    private final int size;
    private int numOfOpenSites;
    private final WeightedQuickUnionUF uf;
    
    // creates n-by-n grid, with all sites initially block (set value to be 0)
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("no such grid");
        }
        size = n;
        grid = new boolean[n][n];
        numOfOpenSites = 0;
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                grid[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
    }
    
    /**
     * map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
     */
    private int xyToID(int row, int col) {
        return (row - 1) * size + col;
    }
    
    private void indicesValidation(int row, int col) {
        if (row < 1 || row > size) {
            throw new IllegalArgumentException(String.format("row index %d out of bounds", row));
        }
        if (col < 1 || col > size) {
            throw new IllegalArgumentException(String.format("column index %d out of bounds", col));
        }
    }
    
    /**
     * opens the site (row, col) if is not open already
     * First, it should validate the indices of the site that it receives.
     * Second, it should somehow mark the site as open.
     * Third, it should perform some sequence of WeightedQuickUnionuf operations
     * that links the site in question to its open neighbors.
     */
    public void open(int row, int col) {
        indicesValidation(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grid[row - 1][col - 1] = true;
        numOfOpenSites += 1;
        connect(row, col);
        if (!percolates()) {
            if (row == size) {
                uf.union(size * size + 1, xyToID(row, col));
            }
        }
    }
    
    private void connect(int row, int col) {
        int ID = xyToID(row, col);
        if (row == 1) {
            uf.union(0, ID);
        }
        // up
        if (row > 1 && grid[row - 2][col - 1]) {
            uf.union(ID, ID - size);
        }
        // down
        if (row < size && grid[row][col - 1]) {
            uf.union(ID, ID + size);
        }
        // left
        if (col > 1 && grid[row - 1][col - 2]) {
            uf.union(ID, ID - 1);
        }
        // right
        if (col < size && grid[row - 1][col]) {
            uf.union(ID, ID + 1);
        }
    }
    
    // is the site open?
    public boolean isOpen(int row, int col) {
        indicesValidation(row, col);
        return grid[row - 1][col - 1];
    }
    
    // is the site full?
    public boolean isFull(int row, int col) {
        indicesValidation(row, col);
        return uf.connected(0, xyToID(row, col));
    }
    
    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, size * size + 1);
    }
    
    // test client
    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        p.open(1, 1);
        p.open(1, 2);
        System.out.println(p.uf.connected(p.xyToID(1, 1), p.xyToID(1, 2)));
    }
}
