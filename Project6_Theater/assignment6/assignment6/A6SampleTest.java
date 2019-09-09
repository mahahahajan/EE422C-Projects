package assignment6;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class A6SampleTest {

    private static String show = "A6 Movie";
    private static List<Theater.Ticket> concurrencyTestLog;

    private static void joinAllThreads(List<Thread> threads)
            throws InterruptedException {
        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * Initialize tests for concurrency by simulating BookingClient with
     * 1) BX1: 100 clients
     * 2) BX2: 100 clients
     * 3) Theater: 100 rows, 2 seats per row
     * <p>
     * Stores the transactions into concurrencyTestLog
     *
     * @throws InterruptedException
     */
    @BeforeClass
    public static void setupBeforeClass() throws InterruptedException {
        Map<String, Integer> offices = new HashMap<String, Integer>() {{
            put("BX1", 100);
            put("BX2", 100);
        }};

        Theater t = new Theater(100, 2, show);
        BookingClient bc = new BookingClient(offices, t);
        joinAllThreads(bc.simulate());

        concurrencyTestLog = t.getTransactionLog();
    }

    /**
     * Tests that bestAvailableSeat() can calculate seats with two letters (ex: AA)
     * <p>
     * Precondition: 30 seats sold
     * Expected: AE1
     *
     * @throws InterruptedException
     */
    @Test(timeout = 120000)
    public void testBestSeatDouble() throws InterruptedException {
        Map<String, Integer> offices = new HashMap<String, Integer>() {{
            put("BX1", 15);
            put("BX2", 15);
        }};

        Theater t = new Theater(50, 1, show);
        BookingClient bc = new BookingClient(offices, t);
        joinAllThreads(bc.simulate());

        Theater.Seat best = t.bestAvailableSeat();
        assertNotNull(best);
        assertEquals("AE1", best.toString());
    }

    /**
     * Tests that bestAvailableSeat() can handle an empty theater
     * <p>
     * Precondition: Theater has not sold any seats yet
     * Expected: A1
     */
    @Test(timeout = 120000)
    public void testBestSeatEmpty() {
        Theater t = new Theater(1, 1, show);
        Theater.Seat best = t.bestAvailableSeat();
        assertNotNull(best);
        assertTrue(best.toString().equalsIgnoreCase("A1"));
    }
}
