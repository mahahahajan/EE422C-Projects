/*
 * CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Pulkit Mahajan
 * pm28838
 * Santosh Balachandra
 * sb55774
 * Slip days used: <0>
 * Spring 2019
 */

package assignment5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

	/* START --- NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE, SQUARE, TRIANGLE, DIAMOND, STAR
	}

	/*
	 * the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the
	 * default color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a
	 * non-filled shape, at least, that's the intent. You can edit these default
	 * methods however you need to, but please preserve that intent as you implement
	 * them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.GREEN;
	}

	public javafx.scene.paint.Color viewOutlineColor() {
		return viewColor();
	}

	public javafx.scene.paint.Color viewFillColor() {
		return viewColor();
	}

	public abstract CritterShape viewShape();

	/**
	 * @param direction
	 * @param steps
	 * @return null if space is empty, or critter
	 */
	protected final String look(int direction, boolean steps) {
		energy -= Params.LOOK_ENERGY_COST;
		int dist = 1;
		int look_x = x_coord;
		int look_y = y_coord;
		if (steps) {
			dist = 2;
		}
		switch (direction) {
		case 0:
			look_x += dist;
			break;
		case 1:
			look_x += dist;
			look_y -= dist;
			break;
		case 2:
			look_y -= dist;
			break;
		case 3:
			look_x -= dist;
			look_y -= dist;
			break;
		case 4:
			look_x -= dist;
			break;
		case 5:
			look_x -= dist;
			look_y += dist;
			break;
		case 6:
			look_y += dist;
			break;
		case 7:
			look_x += dist;
			look_y += dist;
			break;
		}
		for (Critter c : population) {
			if (c.x_coord == look_x && c.y_coord == look_y) {
				return c.toString();
			}
		}
		return null;
	}

	/**
	 * @param critters
	 * @return String that has the stats of each critter
	 */
	public static String runStats(List<Critter> critters) {
		String output = "";
		// System.out.print("" + critters.size() + " critters as follows -- ");
		output += "" + critters.size() + " critters as follows -- ";
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			critter_count.put(crit_string, critter_count.getOrDefault(crit_string, 0) + 1);
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			output += prefix + s + ":" + critter_count.get(s);
			prefix = ", ";
		}
		// System.out.println();
		return output;
	}

	/**
	 * Using single pane - split it into 2 Left pane is for controls, right pane is
	 * the "world"
	 * 
	 * @param pane
	 */
	public static void displayWorld(GridPane pane) {
		Scene scene = new Scene(pane, Main.SCREEN_WIDTH * 2, Main.SCREEN_HEIGHT);
		Main.stage.setScene(scene);
		Main.stage.show();
		GridPane ui = new GridPane();
		GridPane world = new GridPane();
		pane.add(world, Main.SCREEN_WIDTH, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		pane.add(ui, 0, 0);
		Painter.paint(world);
		Painter.paintUI(ui, world);
	}

	/*
	 * END --- NEW FOR PROJECT 5 rest is unchanged from Project 4
	 */

	private int energy = 0;

	private int x_coord;
	private int y_coord;

	private boolean can_move = true;

	public static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();

	private static boolean fight = false;

	/*
	 * Gets the package name. This assumes that Critter and its subclasses are all
	 * in the same package.
	 */
	private static String myPackage;

	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static Random rand = new Random();

	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new Random(new_seed);
	}

	/**
	 * create and initialize a Critter subclass. critter_class_name must be the
	 * qualified name of a concrete subclass of Critter, if not, an
	 * InvalidCritterException must be thrown.
	 *
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void createCritter(String critter_class_name) throws InvalidCritterException {
		Critter new_critter = getCritter(critter_class_name);
		new_critter.energy = Params.START_ENERGY;
		new_critter.x_coord = getRandomInt(Params.WORLD_WIDTH);
		new_critter.y_coord = getRandomInt(Params.WORLD_HEIGHT);
		population.add(new_critter);
	}

	/**
	 * Gets a list of critters of a specific type.
	 *
	 * @param critter_class_name What kind of Critter is to be listed. Unqualified
	 *                           class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		java.util.ArrayList<Critter> output = new java.util.ArrayList<Critter>();
		if (critter_class_name == null) {
			return population;
		}
		Critter my_critter = getCritter(critter_class_name);
		for (Critter c : population) {
			if (my_critter.getClass().isInstance(c)) {
				output.add(c);
			}
		}
		return output;
	}

	/**
	 * Return a Critter from a String
	 *
	 * @param critter_class_name What kind of Critter is to be created. Unqualified
	 *                           class name.
	 * @Returns a new critter with that name
	 * @throws InvalidCritterException
	 */
	public static Critter getCritter(String critter_class_name) throws InvalidCritterException {
		try {
			return (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}

	public int getX_coord() {
		return x_coord;
	}

	public int getY_coord() {
		return y_coord;
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() { // TODO: need to throw exception if population = 0
		// TODO: Complete this method
		for (int i = 0; i < population.size(); i++) {
			population.remove(i);
			i--;
		}
	}

	private static boolean contains(int x, int y) {
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).getX_coord() == x && population.get(i).getY_coord() == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Reusable function that uses getRandom method to generate random number, then
	 * has a direction assigned to each possible value
	 * 
	 * 
	 * @return int representing direction
	 */
	public static int getDiagonal() {
		int rand = Critter.getRandomInt(4);
		switch (rand) {
		case 1:
			return 1;
		case 2:
			return 3;
		case 3:
			return 5;
		case 4:
			return 7;
		default:
			return 1;
		}
	}

	/**
	 * Reusable function that uses getRandom method to generate random number, then
	 * has a direction assigned to each possible value.Only returns values that
	 * correspond with Horizontal or Vertical on the map
	 * 
	 * 
	 * @return int representing direction
	 */
	public static int getVertAndHoriz() {
		int rand = Critter.getRandomInt(4);
		switch (rand) {
		case 1:
			return 0;
		case 2:
			return 2;
		case 3:
			return 4;
		case 4:
			return 6;
		default:
			return 2;
		}
	}

	/**
	 * Does time step for all critters 1. increment timestep; timestep++; 2.
	 * doTimeSteps(); 3. Do the fights. doEncounters(); 4. updateRestEnergy(); 5.
	 * Generate clover genClover(); 6. Move babies to general population.
	 * 
	 */
	public static void worldTimeStep() {
		for (int i = 0; i < population.size(); i++) {
			population.get(i).doTimeStep();
			population.get(i).energy -= Params.REST_ENERGY_COST;
			if (population.get(i).energy <= 0) {
				population.remove(i);
				i--;
			}
		}
		doEncounters();
		createClover();
		population.addAll(babies);
		babies.clear();
	}

	/**
	 * Creates clovers based on refresh rate set in Params
	 */
	public static void createClover() {
		for (int i = 0; i < Params.REFRESH_CLOVER_COUNT; i++) {
			try {
				createCritter("Clover");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * handles all encounters - if 2 critters occupy same space calls function
	 * fightHandler and Critter.fight()
	 */
	public static void doEncounters() {
		boolean crit1FightResponse, crit2FightResponse;
		boolean result;
		fight = true;
		for (int i = 0; i < population.size() - 1; i++) {
			for (int j = i + 1; j < population.size(); j++) {
				Critter critter1 = population.get(i);
				Critter critter2 = population.get(j);
				// Check if 2 critters share the same position
				if (critter1.x_coord == critter2.x_coord && critter1.y_coord == critter2.y_coord) {
					// Trigger the fight or flight response of both critters
					crit1FightResponse = critter1.fight(critter2.toString());
					crit2FightResponse = critter2.fight(critter1.toString());
					if (critter1.energy > 0 && critter2.energy > 0 && critter1.x_coord == critter2.x_coord
							&& critter1.y_coord == critter2.y_coord) {
						// If both critters are alive and still share the same position, begin a fight
						// true means critter 1 wins, false means critter 2 wins
						result = fightHandler(critter1, critter2, crit1FightResponse, crit2FightResponse);
						if (result) {
							// kill critter 2
							critter1.energy += critter2.energy / 2;
							population.remove(j);
							j--;
						} else {
							// kill critter 1
							critter2.energy += critter1.energy / 2;
							population.remove(i);
							i--;
							j = population.size();
						}
					}
					// if a fight didn't happen, check to see if it's because one of the critters
					// died
					if (critter1.energy <= 0) {
						// kill critter 1
						population.remove(i);
						i--;
						j = population.size();
					}
					if (critter2.energy <= 0) {
						// kill crit2
						population.remove(j);
						j--;
					}
				}
			}
		}
		fight = false;
		for (Critter c : population) {
			c.can_move = true;
		}
	}

	/**
	 * Decide which Critter wins a fight based on return value from Critter.fight()
	 * 
	 * @param crit1    critter 1
	 * @param crit2    critter 2
	 * @param c1_fight Whether or not Critter 1 wants to fight
	 * @param c2_fight Whether or not Critter 2 wants to fight
	 * 
	 * @return winner of the fight
	 */
	public static boolean fightHandler(Critter crit1, Critter crit2, boolean c1_fight, boolean c2_fight) {
		int crit1_power, crit2_power;
		if (!c1_fight) {
			crit1_power = 0;
		} else {
			crit1_power = getRandomInt(crit1.energy);
		}
		if (!c2_fight) {
			crit2_power = 0;
		} else {
			crit2_power = getRandomInt(crit2.energy);
		}
		// returns whether or not critter 1 was the winner of encounter
		boolean critter1Wins = crit1_power > crit2_power;
		return critter1Wins;
	}

	public abstract void doTimeStep();

	public abstract boolean fight(String oponent);

	/*
	 * a one-character long string that visually depicts your critter in the ASCII
	 * interface
	 */
	@Override
	public String toString() {
		return "";
	}

	/**
	 * Returns energy of critter
	 * 
	 * @return energy
	 */
	protected int getEnergy() {
		return energy;
	}

	/**
	 * Moves critter in given direction represented by int Moves 1 space
	 * 
	 * @param direction
	 */
	protected final void walk(int direction) {
		energy -= Params.WALK_ENERGY_COST;
		move(direction, 1);
	}

	/**
	 * Moves critter in given direction represented by int Moves 2 spaces
	 * 
	 * @param direction
	 */
	protected final void run(int direction) {
		energy -= Params.RUN_ENERGY_COST;
		move(direction, 2);
	}

	/**
	 * Moves Critter in given direction represented by int Moves 3 spaces Custom
	 * function for Hipogriff class
	 * 
	 * @param direction
	 */
	protected final void leap(int direction) {
		energy -= (Params.RUN_ENERGY_COST + Params.WALK_ENERGY_COST) / 2;
		move(direction, 3);
	}

	/**
	 * Move n spaces in a certain direction
	 *
	 * @param direction to move in
	 * @param dist      distance to move for
	 */
	protected final void move(int direction, int dist) {
		if (can_move) {
			can_move = false;
			int temp_x_coord = x_coord;
			int temp_y_coord = y_coord;
			switch (direction) {
			case 0:
				temp_x_coord += dist;
				break;
			case 1:
				temp_x_coord += dist;
				temp_y_coord -= dist;
				break;
			case 2:
				temp_y_coord -= dist;
				break;
			case 3:
				temp_x_coord -= dist;
				temp_y_coord -= dist;
				break;
			case 4:
				temp_x_coord -= dist;
				break;
			case 5:
				temp_x_coord -= dist;
				temp_y_coord += dist;
				break;
			case 6:
				temp_y_coord += dist;
				break;
			case 7:
				temp_x_coord += dist;
				temp_y_coord += dist;
				break;
			}
			if (temp_x_coord > Params.WORLD_WIDTH - 1) {
				temp_x_coord = temp_x_coord - Params.WORLD_WIDTH - 1;
			}
			if (temp_x_coord < 0) {
				temp_x_coord = temp_x_coord + Params.WORLD_WIDTH - 1;
			}
			if (temp_y_coord > Params.WORLD_HEIGHT - 1) {
				temp_y_coord = temp_y_coord - Params.WORLD_HEIGHT - 1;
			}
			if (temp_y_coord < 0) {
				temp_y_coord = temp_y_coord + Params.WORLD_HEIGHT - 1;
			}
			if (!contains(temp_x_coord, temp_y_coord) && fight || !fight) {
				x_coord = temp_x_coord;
				y_coord = temp_y_coord;
			}
		}
	}

	/**
	 * Creates new "child" of parent Critter.
	 * 
	 * 
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (energy < Params.MIN_REPRODUCE_ENERGY) {
			return;
		}
		offspring.energy = (int) Math.ceil(energy / 2); // not sure
		energy = (int) Math.ceil(energy / 2);
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		offspring.move(direction, 1);
		babies.add(offspring);
	}

	/**
	 * The TestCritter class allows some critters to "cheat". If you want to create
	 * tests of your Critter model, you can create subclasses of this class and then
	 * use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your
	 * implementation of Critter. That means, if you're recording the positions of
	 * your critters using some sort of external grid or some other data structure
	 * in addition to the x_coord and y_coord functions, then you MUST update these
	 * setter functions so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {

		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		/**
		 * This method getPopulation has to be modified by you if you are not using the
		 * population ArrayList that has been provided in the starter code. In any case,
		 * it has to be implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/**
		 * This method getBabies has to be modified by you if you are not using the
		 * babies ArrayList that has been provided in the starter code. In any case, it
		 * has to be implemented for grading tests to work. Babies should be added to
		 * the general population at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}

	}

	public Color viewFill() {
		// TODO Auto-generated method stub
		return null;
	}

}
