package assignment4;

import assignment4.Critter.TestCritter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class A4SampleTest2 {

    private static final boolean DEBUG = false;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        Params.WORLD_WIDTH = 20;
        Params.WORLD_HEIGHT = 20;
        Params.WALK_ENERGY_COST = 2;
        Params.RUN_ENERGY_COST = 5;
        Params.REST_ENERGY_COST = 1;
        Params.MIN_REPRODUCE_ENERGY = 20;
        Params.REFRESH_CLOVER_COUNT = 0;
        Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = 1;
        Params.START_ENERGY = 100;

        TestCritter.clearWorld();
    }

    @After
    public void tearDown() throws Exception {
    }

    // Utility method.

    /**
     * Move n steps in the specified direction.
     *
     * @param x_coord current y, direction to move, steps to move
     * @return new co-ordinates.
     */
    public static int[] moveStep(int x_coord, int y_coord,
                                 int direction, int n) {
        int w = Params.WORLD_WIDTH;
        int h = Params.WORLD_HEIGHT;
        int newX = x_coord + w;
        int newY = y_coord + h;

        switch (direction) {
            case 0:
                newX += n;
                break;
            case 1:
                newX += n;
                newY -= n;
                break;
            case 2:
                newY -= n;
                break;
            case 3:
                newX -= n;
                newY -= n;
                break;
            case 4:
                newX -= n;
                break;
            case 5:
                newX -= n;
                newY += n;
                break;
            case 6:
                newY += n;
                break;
            case 7:
                newX += n;
                newY += n;
                break;
        }
        return new int[]{newX % w, newY % h};
    }

    @Test(timeout = 30000)
    /**
     * 1.
     * Use MakeCritter to create a Critter, and makes sure walk works
     * for 1 step.
     *
     * @throws InvalidCritterException
     */
    public void testWalk() throws InvalidCritterException {
        Critter.createCritter("MyCritter1");
        MyCritter1 m1 = (MyCritter1) TestCritter.getPopulation().get(0);
        int x1a = m1.getX_coord();
        int y1a = m1.getY_coord();
        m1.doTimeStep();
        int x1b = m1.getX_coord();
        int y1b = m1.getY_coord();
        assertTrue((x1b - x1a == 1)
                || (x1b + Params.WORLD_WIDTH - x1a) == 1);
        assertTrue(Math.abs(y1b - y1a) == 0);
    }

    @Test(timeout = 30000)
    /**
     * 7.
     * Walks 1 step each turn.  Check energy drop at each turn.
     */
    public void WalkEnergyTest() throws InvalidCritterException {
        Critter.createCritter("MyCritter1");
        MyCritter1 c = (MyCritter1) TestCritter.getPopulation().get(0);
        int step = 0;
        int energyUsePerStep = Params.REST_ENERGY_COST
                + Params.WALK_ENERGY_COST;
        while (c.getEnergy() > 0) {
            assertEquals(Params.START_ENERGY
                    - step * energyUsePerStep, c.getEnergy());
            Critter.worldTimeStep();
            step++;
        }
    }

    /**
     * 11.
     * Creates two Critters in the same spot, one being a
     * runner. Checks to see if runner moved, lost energy, and lived
     *
     * @throws InvalidCritterException
     */
    @Test(timeout = 30000)
    public void RunDuringFightTest()
            throws InvalidCritterException {
        int x = 0;
        int y = 0;
        int num = 2;
        Critter.createCritter("MyCritter6");
        MyCritter6 runner =
                (MyCritter6) Critter
                        .getInstances("MyCritter6").get(0);
        Critter.createCritter("MyCritter7");
        MyCritter7 fighter =
                (MyCritter7) Critter
                        .getInstances("MyCritter7").get(0);
        runner.setX_coord(x);
        runner.setY_coord(y);
        fighter.setX_coord(x);
        fighter.setY_coord(y);

        assertEquals(num, TestCritter.getPopulation().size());
        Critter.worldTimeStep();
        if (DEBUG) {
            Critter.displayWorld();
        }
        assertFalse(runner.getEnergy() <= 0);
        assertEquals(Params.START_ENERGY
                        - Params.REST_ENERGY_COST
                        - Params.RUN_ENERGY_COST,
                runner.getEnergy());
        assertTrue(runner.getX_coord() != x
                || runner.getY_coord() != y);
        assertTrue(fighter.getX_coord() == x
                && fighter.getY_coord() == y);
    }

}
