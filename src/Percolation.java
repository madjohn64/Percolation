import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.InputMismatchException;

public class Percolation {
    private int[][] grid;
    private int[][] full;
    private int size;
    private int open;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        try {
            if (n <= 0) {
                throw new IllegalArgumentException();
            }
            grid = new int[n][n];
            full = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    grid[i][j] = 0;
                    full[i][j] = 0;
                }
            }
            percolates = false;
            size = n;
            open = 0;
        }
        catch(IllegalArgumentException iae) {
            System.out.println("The size of the grid must be greater than 0.");
            System.out.println("Please run the program again");
        }
        catch(InputMismatchException ime) {
            System.out.println("The size of the grid must be an integer.");
            System.out.println("Please run the program again");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        try {
            if (row <= 0 || col <= 0 || row > size || col > size) {
                throw new IllegalArgumentException();
            }
                grid[row - 1][col - 1] = 1;
                open++;

                /*The following if-else statements check for an adjacent full
                cell and change the cell to full if an adjacent cell is full.
                 */
                if (row == 1) {
                    full[row - 1][col - 1] = 1;
                } else {
                    if ( row < size) {
                        if ( col > 1 && col < size) {
                            if (isFull(row - 1, col ) ||
                                    isFull(row , col - 1) ||
                                    isFull(row , col + 1) ) {
                                full[row - 1][col - 1] = 1;
                            }
                        }
                        if ( col == size) {
                            if (isFull(row - 1, size ) ||
                                    isFull(row , size - 1) ) {
                                full[row - 1][col - 1] = 1;
                            }
                        }
                        if ( col == 1 ) {
                            if (isFull(row , 2) ||
                                    isFull(row - 1 , 1) ) {
                                full[row - 1][col - 1] = 1;
                            }
                        }
                    }
                    if ((row == size) && (isFull(row - 1, col ) ) ) {
                        full[row - 1][col - 1] = 1;
                        percolates = true;
                    }
                }
        }
        catch(IllegalArgumentException iae) {
            System.out.println("The size of the grid must be greater than 0.");
            System.out.println("Please run the program again");
        }
        catch(InputMismatchException ime) {
            System.out.println("The size of the grid must be an integer.");
            System.out.println("Please run the program again");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            if (row <= 0 || col <= 0 || row > size || col > size) {
                throw new IllegalArgumentException();
            }
            return grid[row - 1][col - 1] == 1;
        }
        catch(IllegalArgumentException iae) {
            System.out.println("The size of the grid must be greater than 0.");
            System.out.println("Please run the program again");
        }
        catch(InputMismatchException ime) {
            System.out.println("The size of the grid must be an integer.");
            System.out.println("Please run the program again");
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        try {
            if (row <= 0 || col <= 0 || row > size || col > size) {
                throw new IllegalArgumentException();
            }
            return full[row - 1][col - 1] == 1;
        }
        catch(IllegalArgumentException iae) {
            StdOut.println("The size of the grid must be greater than 0.");
            StdOut.println("Please run the program again");
        }
        catch(InputMismatchException ime) {
            StdOut.println("The size of the grid must be an integer.");
            StdOut.println("Please run the program again");
        } return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
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
                StdOut.print(perc.grid[i][j] + " " );
            }
            StdOut.print("   ");
            for (int j = 0; j < perc.size; j++) {
                StdOut.print(perc.full[i][j] + " " );
            }
            StdOut.println();
        }
        if(perc.percolates()) {
            StdOut.println("Model percolated. Iterations: " + count);
            StdOut.println("Total number of open sites: " + perc.numberOfOpenSites());
        }
        else {
            StdOut.println("Model did NOT percolate. Iterations: " + count);
            StdOut.println("Total number of open sites: " + perc.numberOfOpenSites());
        }
    }
}
