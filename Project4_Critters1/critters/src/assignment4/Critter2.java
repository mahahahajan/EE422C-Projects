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
 * Dragon critter. This critter is strong and powerful. It doesn't ever
 * back down from it's fight because it believes that it is the strongest
 * of all the creatures. During normal time, it wanders around looking for
 * prey / opponents to fight with. 
 * 
 * TL;DR Randomly moving critter that enjoys a fight
 *
 */
public class Critter2 extends Critter.TestCritter {
	
	@Override
	public void doTimeStep() {
		int rand = Critter.getRandomInt(7);
		walk(rand);
	}

	@Override
	public boolean fight(String opponent) {
		return true;
	}

	@Override 
	public String toString() {
		return "2";
	}
}
