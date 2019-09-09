/*
 * Example critter
 * Do not change or submit this file.
 */

package assignment5;

import java.util.List;

public class Goblin extends Critter {

	@Override
	public String toString() {
		return "G";
	}

	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;

	public Goblin() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}

	@Override
	public boolean fight(String not_used) {
		return true;
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);

		if (getEnergy() > 150) {
			Goblin child = new Goblin();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}

		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert (turn < 8);

		dir = (dir + turn) % 8;
	}

	public static String runStats(List<Critter> goblins) {
		String str = "";
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : goblins) {
			Goblin c = (Goblin) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		str += ("" + goblins.size() + " total Craigs    \n");
		str += ("" + total_straight / (GENE_TOTAL * 0.01 * goblins.size()) + "% straight   \n");
		str += ("" + total_back / (GENE_TOTAL * 0.01 * goblins.size()) + "% back   \n");
		str += ("" + total_right / (GENE_TOTAL * 0.01 * goblins.size()) + "% right   \n");
		str += ("" + total_left / (GENE_TOTAL * 0.01 * goblins.size()) + "% left   \n");
		return str;
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.BLUE;
	}

	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.BLACK;
	}

}
