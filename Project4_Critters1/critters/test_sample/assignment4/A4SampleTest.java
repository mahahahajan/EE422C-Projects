package assignment4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import assignment4.Critter.TestCritter;

public class A4SampleTest {

    private static String TESTSRCDIR = "test_sample/";
    private static ByteArrayOutputStream outContent;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        TestCritter.clearWorld();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws Exception {
    }


    /*
     * 1. KillCritters
     * Test: killCritters
     * Test for make critter and stats, and step
     * Creates large number of make critters and compare stats after 500 steps
     * Expects all Critters to be dead
     */
    @Test(timeout = 1000)
    public void KillCritters() {


        // Uncomment Following Codeblock to test
        // Remove final keyword from Params.java

        Params.WORLD_WIDTH = 20;
        Params.WORLD_HEIGHT = 20;
        Params.WALK_ENERGY_COST = 2;
        Params.RUN_ENERGY_COST = 5;
        Params.REST_ENERGY_COST = 1;
        Params.MIN_REPRODUCE_ENERGY = 20;
        Params.REFRESH_CLOVER_COUNT =
                (int) Math.max(1, Params.WORLD_WIDTH * Params.WORLD_HEIGHT / 1000);
        Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = 1;
        Params.START_ENERGY = 5;

        String fileFolder = "kill_all_critter";
        String[] inputs = {TESTSRCDIR + fileFolder + "/input.txt", "test"};

        Main.main(inputs);
        outContent = Main.testOutputString;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(TESTSRCDIR
                    + fileFolder
                    + "/expected_output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String text = scanner.useDelimiter("\\A").next().trim();
        String output = cleanString(outContent.toString());
        scanner.close();

        assertEquals(text, output);
    }


    /*
     * 2. ParseErrors
     *
     * Test: ParseErrors
     * Test for errors within valid inputs
     * Expects errors to be printed
     */
   @Test //(timeout = 1000)
    public void ParseErrors() {


        // Uncomment following codeblock to test with parameters
        // Remove final keyword in Params.java

        Params.WORLD_WIDTH = 20;
        Params.WORLD_HEIGHT = 20;
        Params.WALK_ENERGY_COST = 2;
        Params.RUN_ENERGY_COST = 5;
        Params.REST_ENERGY_COST = 1;
        Params.MIN_REPRODUCE_ENERGY = 20;
        Params.REFRESH_CLOVER_COUNT =
                (int) Math.max(1, Params.WORLD_WIDTH * Params.WORLD_HEIGHT / 1000);
        Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = 1;
        Params.START_ENERGY = 100;


        String fileFolder = "error_processing";
        String[] inputs = {TESTSRCDIR + fileFolder + "/input.txt", "test"};

        Main.main(inputs);
        outContent = Main.testOutputString;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(TESTSRCDIR
                    + fileFolder + "/expected_output.txt"));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        String text = scanner.useDelimiter("\\A").next().trim();
        String output = cleanString(outContent.toString());
        System.out.println(text);
        System.out.println(output);
        assertTrue(text.contains(output));

    }

    /*
     * 3. ShowEmptyWorld
     */
    @Test(timeout = 1000)
    public void showEmptyWorld() {

        /*
         * Test: InvalidCritter
         * Expects error in creating critters
         */
        Params.WORLD_WIDTH = 20;
        Params.WORLD_HEIGHT = 20;
        Params.WALK_ENERGY_COST = 2;
        Params.RUN_ENERGY_COST = 5;
        Params.REST_ENERGY_COST = 1;
        Params.MIN_REPRODUCE_ENERGY = 20;
        Params.REFRESH_CLOVER_COUNT =
                (int) Math.max(1, Params.WORLD_WIDTH * Params.WORLD_HEIGHT / 1000);
        Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = 1;
        Params.START_ENERGY = 100;

        String fileFolder = "empty_world";
        String[] inputs = {TESTSRCDIR + fileFolder + "/input.txt", "test"};


        Main.main(inputs);
        outContent = Main.testOutputString;


        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(TESTSRCDIR
                    + fileFolder + "/expected_output.txt"));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        String text = scanner.useDelimiter("\\A").next().trim();
        String output = cleanString(outContent.toString());
        scanner.close();
        assertEquals(text, output);


    }


    /*
     * 4. ParseCreateLargeCritter
     */
    @Test(timeout = 1000)
    public void ParseMakeLargeCritter() {
        // Test for Create and show command
        // Expects entire board to be filled with critters


        Params.WORLD_WIDTH = 20;
        Params.WORLD_HEIGHT = 20;
        Params.WALK_ENERGY_COST = 2;
        Params.RUN_ENERGY_COST = 5;
        Params.REST_ENERGY_COST = 1;
        Params.MIN_REPRODUCE_ENERGY = 20;
        Params.REFRESH_CLOVER_COUNT =
                (int) Math.max(1, Params.WORLD_WIDTH * Params.WORLD_HEIGHT / 1000);
        Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = 1;
        Params.START_ENERGY = 100;

        String fileFolder = "create_large_critter";
        String[] inputs = {TESTSRCDIR + fileFolder + "/input.txt", "test"};

        Main.main(inputs);
        outContent = Main.testOutputString;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(TESTSRCDIR
                    + fileFolder + "/expected_output.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String text = scanner.useDelimiter("\\A").next().trim();
        String output = cleanString(outContent.toString());
        scanner.close();
        assertEquals(text, output);
    }

    String cleanString(String input) {
        String lineSep = System.getProperty("line.separator");
        input = input.replaceAll("critter>", "")
                .replaceAll("\r\n", "\n")
                .replaceAll("critters>", "")
                .replaceAll(lineSep + "\\s+", "\n")
                .replaceAll(lineSep, "\n")
                .replaceAll("(?m)^[ \t]*\r?\n", "")
                .trim();
        return input;
    }
}
