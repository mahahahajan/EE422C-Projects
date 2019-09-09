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
		if(Critter.getRandomInt(100) > 95) {
			Critter2 child = new Critter2();
			reproduce(child, Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		return true;
	}

	@Override 
	public String toString() {
		return "2";
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
	
    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.RED;
    }
    
    @Override
    public javafx.scene.paint.Color viewFillColor() { 
    	Color color = new Color(0, 0, 0, 1);
    	color = Color.rgb(33,33,33 ,1);
     	return color;
    }
	
}
