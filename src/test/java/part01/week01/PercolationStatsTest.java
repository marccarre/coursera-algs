package part01.week01;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class PercolationStatsTest {
    private static final double TOLERANCE = 0.01;

    @Test
    public void percolationStats_200_100() {
        final PercolationStats stats = new PercolationStats(200, 100);
        assertThat(stats.mean(), is(closeTo(0.59, TOLERANCE)));
        assertThat(stats.stddev(), is(closeTo(0.01, TOLERANCE)));
        assertThat(stats.confidenceLo(), is(closeTo(0.59, TOLERANCE)));
        assertThat(stats.confidenceHi(), is(closeTo(0.59, TOLERANCE)));
    }
}
