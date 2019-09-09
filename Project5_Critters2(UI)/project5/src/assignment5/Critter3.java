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
 * Hippogriff Critter. The hipogriff critter is proud and fierce. With it's
 * sharp eagle eyes and broad wings, this creature is not able to simply walk or
 * run. Instead it leaps, moving a direction of 3 instead of the normal walk(1)
 * or run(2). Built for distances, this graceful creature covers the map much
 * faster than any other creature, roaming the country at it's luxury.
 * Additionally, depending on it's personality (dice roll), it could either flee
 * or fight depending on it's start energy.
 * 
 * TL;DR moves 3 spaces using custom leap method. Divide start energy by random
 * amount to see if Hipogriff wants to fight.
 *
 */
public class Critter3 extends Critter.TestCritter {
	@Override
	public void doTimeStep() {
		int rand = Critter.getRandomInt(7);
		leap(rand);

	}

	@Override
	public boolean fight(String opponent) {
		if (opponent.equals("@")) {
			return true;
		} else {
			if (getEnergy() > Params.START_ENERGY / (Critter.getRandomInt(4) + 1)) {
				return true;
			} else {
				int rand = Critter.getRandomInt(7);
				leap(rand);
				return false;
			}
		}
	}

	@Override
	public String toString() {
		return "3";
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.GRAY;
	}

	@Override
	public javafx.scene.paint.Color viewFillColor() { // rgba(3,169,244 ,1)
		Color color = new Color(0, 0, 0, 1);
		color = Color.rgb(93, 64, 55);
		return color;
	}
}
