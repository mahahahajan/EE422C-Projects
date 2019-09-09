package assignment2;

/**
 * EE422C Project 2 (Mastermind) submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */

public class Guess {
	
	int codeLength = GameConfiguration.pegNumber;
	String[] colors = GameConfiguration.colors;
	int numberOfGuesses;
	String guess;
	
	/**
	 * @param guess = the String the user entered
	 */
	public Guess(String guess) {
		this.guess = guess;
	}
	
	

	/**
	 * @return int that determines if guess was valid
	 */
	public int isValidGuess() {
		char guessChar;
		String guessCharString;
		if(guess.equals("HISTORY")) { //compare string to HISTORY
			return 0;
		}
		else if(guess.length() != codeLength ) { //check length
			return -1;
		}
		for(int i = 0; i < guess.length(); i++) { //check that all colors are acceptable
			guessChar = guess.charAt(i);
			guessCharString = Character.toString(guessChar);
			if(Character.isLowerCase(guessChar)) { // check if guess is all in Upper Case
				return -1;
			}
			guessCharString = Character.toString(guessChar);
			if(checkValidColor(guessCharString) == -1) { //check whether or not guess color is in Array of acceptable colors
				return -1;
			}
		}
		
		return 1;
	}
	
	/**
	 * @param guessColor = the single char of the color (Ex. "O") 
	 * @return whether or not guessColor exists in the acceptable colors Array
	 */
	public int checkValidColor(String guessColor) {
		for(int i = 0; i < colors.length; i++) {
			if(guessColor.equals(colors[i])) {
				return 1;
			}
		}
		return -1;
	}
	
}
