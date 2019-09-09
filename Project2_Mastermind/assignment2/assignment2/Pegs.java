package assignment2;

import java.util.*;

/**
 * EE422C Project 2 (Mastermind) submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */

public class Pegs {
	
	int blackPegs;
	int whitePegs;
	boolean alreadyUsedThisGuess;
	String[] userCode;
	String[] secretCode;
	
	
	String pegs; 
	
	
	/**
	 * @param newGuess = the new User Guess
	 * @param secretCode = the Secret Code generated at the beginning of the game
	 */
	public Pegs(Guess newGuess, String secretCode) {
		userCode = newGuess.guess.split("");
		this.secretCode = secretCode.split("");
		compareToOriginal();
		pegs = blackPegs + "b_" + whitePegs + "w";
	}
	
	//Not checking duplicates yet
	/**
	 * Method to compare user's guess to the secret code
	 */
	public void compareToOriginal() {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<Integer> whiteIndexes = new ArrayList<Integer>();
		/**
		 * First loop through the user code and match it to the secret code. If at
		 * a certain location the color and position is correct, the position is
		 * recorded and the number of black pegs is increased by 1
		 */
		for(int i = 0; i < userCode.length; i++ ) {
			if(userCode[i].equals(secretCode[i])) { 
				blackPegs++;
				indexes.add(i);
			}
		}
		/**
		 * Run another loop that compares the user code to the secret code. The first
		 * loop grabs a color from the user's code. The second loop compares that color
		 * to every color in the secret guess. If at any point the user color appears in
		 * the secret code, check to see if that location has already been used for a 
		 * black peg, a white peg, or whether or not that user's color has already been
		 * used (used this boolean instead of doing a System.exit()). If the location of
		 * the match has not been used for a black peg or white peg, and the color has not
		 * already been used for a peg before, then the amount of white pegs is incremented
		 */
		for(int i = 0; i < userCode.length; i++) {
			alreadyUsedThisGuess = false;
			if(indexes.contains(i) == false) {
				for(int j = 0; j < userCode.length; j++) {
					if(userCode[i].equals(secretCode[j])) {
						if(indexes.contains(j) == false && alreadyUsedThisGuess == false && whiteIndexes.contains(j) == false) {
							whitePegs++;
							whiteIndexes.add(j);
							alreadyUsedThisGuess = true;
						}
						
					}
				}
			}
			
		}
	}
}
