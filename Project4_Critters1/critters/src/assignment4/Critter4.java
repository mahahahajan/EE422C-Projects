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
 * Tank critter. This modern critter is tough and unique. Due to it's 
 * mechanical design, it's unable to move horizontally and vertically, 
 * instead only moving diagonally across the map. It fights with it's
 * cannon, shooting down any opponents, yet it's slow reload rate makes
 * it suspect to defeat. The tank is likely to engage in fights unless
 * it's nearing low energy, in which case it typically flees. 
 * 
 * TL;DR Moves diagonally only. 
 *
 */
public class Critter4 extends Critter.TestCritter{
	
	@Override
	public void doTimeStep() {
		walk(Critter.getDiagonal());
	}

	@Override
	public boolean fight(String opponent) {
		if(opponent.equals("@")) {
			return true;
		}
		else {
			if (getEnergy() > Params.START_ENERGY / 3) {
				return true;
			}
			run(Critter.getDiagonal());
			return false;
		}
	}

	@Override 
	public String toString() {
		return "4";
	}
}
