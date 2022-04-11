package hw2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF union;
    private boolean[] openStatus;
    private int openSize;
    private int rowSize;

    public Percolation(int N) {
        union = new WeightedQuickUnionUF(N*N + 2);
        openStatus = new boolean[N*N+2];
        openSize = 0;
        openStatus[0] = true;
        openStatus[N*N + 1] = true;
        rowSize = N;
    }               // create N-by-N grid, with all sites initially blocked

    private int translate(int row, int col) {
        return row*rowSize + col + 1;
    }

    public void open(int row, int col) {
        int index = translate(row, col);
        openStatus[index] = true;
        openSize++;
        if (row == 0) {
            union.union(0, index);
        }
        if (row == rowSize - 1) {
            union.union(rowSize*rowSize + 1, index);
        }
        if (col > 0 && isOpen(row, col - 1)) {
            union.union(index, index - 1);
        }
        if (col < rowSize - 1 && isOpen(row, col + 1)) {
            union.union(index, index + 1);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            union.union(index, index - rowSize);
        }
        if (row < rowSize - 1 && isOpen(row + 1, col)) {
            union.union(index, index + rowSize);
        }
    }       // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        return openStatus[translate(row, col)];
    } // is the site (row, col) open?
    public boolean isFull(int row, int col) {
        return union.connected(translate(row, col), 0);
    } // is the site (row, col) full?
    public int numberOfOpenSites() {
        return openSize;
    }          // number of open sites
    public boolean percolates() {
        return union.connected(0, rowSize*rowSize + 1);
    }             // does the system percolate?
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(0, 0);
        assertFalse(p.isFull(1, 1));
        assertTrue(p.isFull(0, 0));
        assertFalse(p.isOpen(0, 1));
        assertTrue(p.isOpen(0, 0));
        p.open(2, 2);
        p.open(2, 1);
        assertFalse(p.percolates());
        p.open(1,0);
        assertTrue(p.percolates());

    }  // use for unit testing (not required)
}
