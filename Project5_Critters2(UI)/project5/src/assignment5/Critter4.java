package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Pulkit Mahajan 
 * pm28838	
 * Santosh Balachandra 
 * sb55774
 * Slip days used: <0>
 * Spring 2019
 */

import javafx.scene.paint.Color;

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
		boolean found = false;
		while(!found) {
			int dir = Critter.getDiagonal();
			if(look(dir, false) == null){
				found = true;
				walk(dir);
			}
			else if(look(dir, true) == null) {
				found = true;
				run(dir);
			}	
		}
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

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	}
	
    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return javafx.scene.paint.Color.BLACK;
    }
	
    @Override
    public javafx.scene.paint.Color viewFillColor() {
    	Color color = new Color(0, 0, 0, 1);
    	color = Color.rgb(33,33,33);
     	return color;
    }
    
}
