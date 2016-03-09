package part01.week01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private int bottom;

    private final int N;
    private final WeightedQuickUnionUF sites;
    private final WeightedQuickUnionUF sitesNoBackwash;
    private final boolean[][] openSites;

    /**
     * Create N-by-N grid, with all sites blocked.
     */
    public Percolation(final int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be strictly positive but was " + N + ".");
        }
        this.N = N;
        openSites = new boolean[N][N];

        // TOP, site #0, is a virtual site, the top site where the percolation starts, it is initially open and full by convention.

        // bottom, site #N*N+1, is a virtual site, the bottom site where the percolation ends, it is initially open and empty by convention.
        bottom = N * N + 1;

        // Other sites are in the grid, identified with their coordinates (i, j) each being in [1, N].
        sites = new WeightedQuickUnionUF(N * N + 2);

        // No bottom site. Useful to check whether a site is full or not,
        // as otherwise you would have "backwash" from the bottom site.
        sitesNoBackwash = new WeightedQuickUnionUF(N * N + 1);
    }

    /**
      * Open site (row i, column j) if it is not open already.
      */
    public void open(final int i, final int j) {
        validateCoordinate("i", i);
        validateCoordinate("j", j);

        openSites[i - 1][j - 1] = true;

        final int currentSite = convertFrom2DOneBasedTo1DZeroBased(i, j);
        if ((i > 1) && isOpen(i - 1, j)) { // Top neighbour.
            sites.union(currentSite, currentSite - N);
            sitesNoBackwash.union(currentSite, currentSite - N);
        }
        if ((j > 1) && isOpen(i, j - 1)) { // Left neighbour.
            sites.union(currentSite, currentSite - 1);
            sitesNoBackwash.union(currentSite, currentSite - 1);
        }
        if ((i < N) && isOpen(i + 1, j)) { // Bottom neighbour.
            sites.union(currentSite, currentSite + N);
            sitesNoBackwash.union(currentSite, currentSite + N);
        }
        if ((j < N) && isOpen(i, j + 1)) { // Right neighbour.
            sites.union(currentSite, currentSite + 1);
            sitesNoBackwash.union(currentSite, currentSite + 1);
        }

        // Current site is directly connected to top site, which is full (and open) by convention.
        if (i == 1) {
            sites.union(currentSite, TOP);
            sitesNoBackwash.union(currentSite, TOP);
        }
        // Current site is directly connected to bottom site.
        if (i == N) {
            sites.union(currentSite, bottom);
        }
    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(final int i, final int j) {
        validateCoordinate("i", i);
        validateCoordinate("j", j);

        return openSites[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(final int i, final int j) {
        validateCoordinate("i", i);
        validateCoordinate("j", j);

        // Current site is transitively connected to top site, which is full (and open) by convention.
        return sitesNoBackwash.connected(convertFrom2DOneBasedTo1DZeroBased(i, j), TOP);
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return sites.connected(bottom, TOP);
    }

    private void validateCoordinate(final String name, final int i) {
        if (i < 1 || i > N) {
            throw new IndexOutOfBoundsException(name + " must be in [1, " + N + "] but was " + i + ".");
        }
    }

    private int convertFrom2DOneBasedTo1DZeroBased(final int i, final int j) {
        return (i - 1) * N + j;
    }
}
