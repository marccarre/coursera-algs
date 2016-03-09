package part01.week01;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PercolationTest {
    private final Percolation percolation = new Percolation(3);
    
    @Test
    public void open() {
        final int i = 2;
        final int j = 2;
        assertThat(percolation.isOpen(i, j), is(false));
        assertThat(percolation.isFull(i, j), is(false));
        percolation.open(i, j);
        assertThat(percolation.isOpen(i, j), is(true));
        assertThat(percolation.isFull(i, j), is(false));
        assertThat(percolation.percolates(), is(false));
    }

    @Test
    public void openAndFill() {
        final int i = 1;
        final int j = 2;
        assertThat(percolation.isOpen(i, j), is(false));
        assertThat(percolation.isFull(i, j), is(false));
        percolation.open(i, j);
        assertThat(percolation.isOpen(i, j), is(true));
        assertThat(percolation.isFull(i, j), is(true));
        assertThat(percolation.percolates(), is(false));
    }

    @Test
    public void percolatesVertically() {
        final int j = 2;
        for (int i = 1; i <= 2; ++i) {
            assertThat(percolation.isOpen(i, j), is(false));
            assertThat(percolation.isFull(i, j), is(false));
            percolation.open(i, j);
            assertThat(percolation.isOpen(i, j), is(true));
            assertThat(percolation.isFull(i, j), is(true));
            assertThat(percolation.percolates(), is(false));
        }
        percolation.open(3, j);
        assertThat(percolation.percolates(), is(true));
    }

    @Test
    public void doesNotPercolateVertically() {
        final int j = 2;
        for (int i = 1; i <= 2; ++i) {
            assertThat(percolation.isOpen(i, j), is(false));
            assertThat(percolation.isFull(i, j), is(false));
            percolation.open(i, j);
            assertThat(percolation.isOpen(i, j), is(true));
            assertThat(percolation.isFull(i, j), is(true));
            assertThat(percolation.percolates(), is(false));
        }
        assertThat(percolation.percolates(), is(false));
    }

    @Test
    public void percolatesDiagonally() {
        int j = 1;
        for (int i = 1; i <= 2; ++i) {
            assertThat(percolation.isOpen(i, j), is(false));
            assertThat(percolation.isFull(i, j), is(false));
            percolation.open(i, j);
            assertThat(percolation.isOpen(i, j), is(true));
            assertThat(percolation.isFull(i, j), is(true));
            assertThat(percolation.percolates(), is(false));
            if (i != 2) {
                assertThat(percolation.isOpen(i + 1, j), is(false));
                assertThat(percolation.isFull(i + 1, j), is(false));
                percolation.open(i + 1, j);
                assertThat(percolation.isOpen(i + 1, j), is(true));
                assertThat(percolation.isFull(i + 1, j), is(true));
                assertThat(percolation.percolates(), is(false));
                ++j;
            }
        }
        percolation.open(3, j);
        assertThat(percolation.percolates(), is(true));
    }

    @Test
    public void doesNotPercolateDiagonally() {
        int j = 1;
        for (int i = 1; i <= 2; ++i) {
            assertThat(percolation.isOpen(i, j), is(false));
            assertThat(percolation.isFull(i, j), is(false));
            percolation.open(i, j);
            assertThat(percolation.isOpen(i, j), is(true));
            assertThat(percolation.isFull(i, j), is(true));
            assertThat(percolation.percolates(), is(false));
            if (i != 2) {
                assertThat(percolation.isOpen(i + 1, j), is(false));
                assertThat(percolation.isFull(i + 1, j), is(false));
                percolation.open(i + 1, j);
                assertThat(percolation.isOpen(i + 1, j), is(true));
                assertThat(percolation.isFull(i + 1, j), is(true));
                assertThat(percolation.percolates(), is(false));
                ++j;
            }
        }
        assertThat(percolation.percolates(), is(false));
    }

    @Test
    public void doesNotBackWash() {
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 3);
        percolation.open(3, 1);
        assertThat(percolation.percolates(), is(true));
        assertThat(percolation.isOpen(3, 3), is(true));
        assertThat(percolation.isFull(3, 3), is(false));
    }
}
