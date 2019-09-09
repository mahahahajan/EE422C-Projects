/*
 * Do not change or submit this file.
 */

package assignment5;

import assignment5.Critter.TestCritter;

public class Clover extends TestCritter {

    @Override
	public String toString() {
        return "@";
    }

    @Override
	public boolean fight(String opponent) {
        // same species as me!
        if (toString().equals(opponent)) {
            /* try to move away */
            walk(Critter.getRandomInt(8));
        }
        return false;
    }

    @Override
	public void doTimeStep() {
        setEnergy(getEnergy() + Params.PHOTOSYNTHESIS_ENERGY_AMOUNT);
    }

    @Override
	public CritterShape viewShape() {
        return CritterShape.CIRCLE;
    }

    @Override
	public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.GREEN;
    }
}
