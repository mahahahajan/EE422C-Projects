package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Pulkit Mahajan 
 * pm28838	
 * Santosh Balachandra 
 * sb55774
 * Slip days used: <0>
 * Spring 2019
 */

import javafx.scene.paint.Color;

/**
 * Centaur critter. This critter is wise and majestic, and only fights when it's
 * confident it has enough energy to do so. During normal time, due to it's
 * unique build, it only moves vertically and horizontally on the map. However,
 * during battle, if it needs to flee it can escape diagonally, in an attempt to
 * out smart it's opponent.
 * 
 * 
 * TL;DR Flees diagonally, otherwise moves vertically and horizontally
 *
 */
public class Critter1 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		walk(Critter.getVertAndHoriz());
		if (Critter.getRandomInt(10) > 8) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		if (opponent.equals("@")) {
			return true;
		} else {
			if (getEnergy() > Params.START_ENERGY / 2) {
				return true;
			}

			boolean found = false;
			while (!found) {
				int dir = Critter.getDiagonal();
				if (look(dir, false) == null) {
					found = true;
					walk(dir);
				} else if (look(dir, true) == null) {
					found = true;
					run(dir);
				}
			}
			return false;
		}

	}

	@Override
	public String toString() {
		return "1";
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.GREEN;
	}

	@Override
	public javafx.scene.paint.Color viewFillColor() {
		Color color = new Color(0, 0, 0, 1);
		color = Color.rgb(255, 109, 0, 1);
		return color;
	}

}
