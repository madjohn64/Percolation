import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.InputMismatchException;

public class PercolationNoUF {
    /**
     * {@code boolean} array representing the indexes of all sites:
     * {@code true} - site is already opened
     * {@code false} - site is closed yet
     */
    private boolean[][] opened;
    /**
     * {@code boolean} array representing whether each site is full:
     * {@code true} - site is already full
     * {@code false} - site is not yet full, even if open
     */
    private boolean[][] full;
    /**
     * {@code int} variable representing the grid's side length
     */
    private final int size;
    /**
     * {@code int} counter variable returning number of sites open
     */
    private int openSites;
    /**
     * {@code boolean} updates whether model percolates
     */
    private boolean percolates;

    /**
     * Takes {@code int} variable and creates N*N grid
     * for solving Percolation problem on it
     *
     * @param size grid's side length
     * @param
     */
    public PercolationNoUF(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        opened = new boolean[n][n];
        full = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                opened[i][j] = false;
                full[i][j] = false;
            }
        }
        percolates = false;
        openSites = 0;
        size = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        opened[row - 1][col - 1] = true;
        openSites++;

        //Now checks for whether an upper or adjacent (left, right) site is full.
        if ( (col - 1 > 0 && isFull( row, col - 1 )) ||
                (row - 1 > 0 && isFull( row - 1, col )) ||
                (row + 1 <= size && isFull( row + 1, col )) ) {
            full[row - 1][col - 1] = true;
        }

                /*The following if-else statements check for an adjacent full
                cell and change the cell to full if an adjacent cell is full.
                 */
        if (row == 1) {
            full[row - 1][col - 1] = 1;
        } else {
            if (row < size) {
                if (col > 1 && col < size) {
                    if (isFull(row - 1, col) ||
                            isFull(row, col - 1) ||
                            isFull(row, col + 1)) {
                        full[row - 1][col - 1] = 1;
                    }
                }
                if (col == size) {
                    if (isFull(row - 1, size) ||
                            isFull(row, size - 1)) {
                        full[row - 1][col - 1] = 1;
                    }
                }
                if (col == 1) {
                    if (isFull(row, 2) ||
                            isFull(row - 1, 1)) {
                        full[row - 1][col - 1] = 1;
                    }
                }
            }
            if ((row == size) && (isFull(row - 1, col))) {
                full[row - 1][col - 1] = 1;
                percolates = true;
            }
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return full[row - 1][col - 1];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    /**
     * Validates row and column indices of the site
     *
     * @param x column index
     * @param y row index
     * @throws IndexOutOfBoundsException if one of the indexes less
     *                                   or equal to zero or more than grid's side size
     */
    private void validate(int x, int y) {
        if (x <= 0 || x > size || y <= 0 || y > size) {
            throw new IndexOutOfBoundsException("one of the indexes is out of bounds");
        }
    }

    private void trickle(int row, int col) {
        validate(row, col);

        //Checks if left neighbor is full. If not, fill it. Trickle.
        if ( row - 1 > 0 && !isFull( row - 1 , col  )) {
            full[row - 2][col - 1] = true;
            trickle(row - 1, col);
        }
        //Checks if right neighbor is full. If not, fill it. Trickle.
        if ( row + 1 <= size && !isFull( row + 1 , col  )) {
            full[row][col - 1] = true;
            trickle(row + 1, col);
        }
        //Checks if bottom neighbor is full. If not, fill it. Trickle.
        if ( col + 1 <= size && !isFull( row , col + 1 )) {
            full[row - 1][col] = true;
            trickle(row , col + 1);
        }

    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        int count = 0;
        while (!perc.percolates && count < 25) {
            int row = StdRandom.uniformInt(perc.size) + 1;
            int col = StdRandom.uniformInt(perc.size) + 1;
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
                count++;
            }
        }
        for (int i = 0; i < perc.size; i++) {
            for (int j = 0; j < perc.size; j++) {
                StdOut.print(perc.grid[i][j] + " ");
            }
            StdOut.print("   ");
            for (int j = 0; j < perc.size; j++) {
                StdOut.print(perc.full[i][j] + " ");
            }
            StdOut.println();
        }
        if (perc.percolates()) {
            StdOut.println("Model percolated. Iterations: " + count);
            StdOut.println("Total number of open sites: " + perc.numberOfOpenSites());
        } else {
            StdOut.println("Model did NOT percolate. Iterations: " + count);
            StdOut.println("Total number of open sites: " + perc.numberOfOpenSites());
        }
    }
}

