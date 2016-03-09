package part01.week01;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final double[] experiments;

    /**
     * Performs T independent experiments on an N-by-N grid.
     */
    public PercolationStats(final int N, final int T) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be strictly positive but was " + N + ".");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T must be strictly positive but was " + T + ".");
        }

        experiments = new double[T];
        for (int i = 0; i < T; ++i) {
            experiments[i] = monteCarloSimulation(N);
        }
    }

    private double monteCarloSimulation(final int N) {
        final Percolation percolation = new Percolation(N);
        
        int numOpenSites = 0;

        while (!percolation.percolates()) {
            final int i = StdRandom.uniform(1, N + 1);
            final int j = StdRandom.uniform(1, N + 1);
            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
                ++numOpenSites;
            }
        }

        final double numSites = N * N;
        return numOpenSites / numSites;
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(experiments);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(experiments);
    }

    /**
     * Low endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(experiments.length);
    }

    /**
     * High endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(experiments.length);
    }

    public static void main(final String[] args) {
        final int N = StdIn.readInt();
        final int T = StdIn.readInt();
        final PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean                    = %d\n", stats.mean());
        StdOut.printf("stddev                  = %d\n", stats.stddev());
        StdOut.printf("95% confidence interval = %d, %d\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
