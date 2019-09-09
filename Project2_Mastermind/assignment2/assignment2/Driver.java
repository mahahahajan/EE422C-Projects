package assignment2;

import java.util.Scanner;

/**
 * EE422C Project 2 (Mastermind) submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */

/**
 * @author pulkitmahajan
 *
 */
public class Driver {
	
	
	/**
	 * @param args
	 * boolean test = variable to determine if in test mode
	 * Scanner input = Scanner object to handle input 
	 * numberOfGuesses = number of guesses allowed in game
	 */
	public static void main(String[] args) {
		
		boolean test = false;
		int numberOfGuesses = GameConfiguration.guessNumber;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Welcome to Mastermind.");
		System.out.println("Do you want to play a new game? (Y/N):");
		String toPlay = input.nextLine();
		while(toPlay.equals("Y")) { //TODO: CHANGE THIS TO WHILE AT THE END
			if(args.length != 0) {
				if(args[0].equals("1")) {
					test = true;
				}
			}
			GuessList guessList = new GuessList();
			
			Game newGame = new Game(test, input, guessList, numberOfGuesses);
			
			toPlay = newGame.runGame();
		}
	}
	
	
	
}
