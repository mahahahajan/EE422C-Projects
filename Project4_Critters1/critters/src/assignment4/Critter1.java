package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Pulkit Mahajan 
 * pm28838	
 * Santosh Balachandra 
 * sb55774
 * Slip days used: <0>
 * Spring 2019
 */

import java.util.*;

/**
 * Centaur critter. This critter is wise and majestic, and only fights when it's
 * confident it has enough energy to do so. During normal time, due to it's unique 
 * build, it only moves vertically and horizontally on the map. However, during 
 * battle, if it needs to flee it can escape diagonally, in an attempt to out smart 
 * it's opponent. 
 * 
 * 
 * TL;DR Flees diagonally, otherwise moves vertically and horizontally
 *
 */
public class Critter1 extends Critter.TestCritter {
	
	@Override
	public void doTimeStep() {
		walk(Critter.getVertAndHoriz());
	}

	@Override
	public boolean fight(String opponent) {
		if(opponent.equals("@")) {
			return true;
		}
		else {
			if (getEnergy() > Params.START_ENERGY/2) {
				return true;
			}
			walk(Critter.getDiagonal());
			return false;
		}
		
	}

	@Override 
	public String toString() {
		return "1";
	}
}
