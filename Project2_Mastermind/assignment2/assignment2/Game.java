package assignment2;

/**
 * EE422C Project 2 (Mastermind) submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */

import java.util.Scanner;

public class Game {
	
	private String secretCode;
	private int numberOfGuesses;
	
	Scanner input;
	GuessList currentList;
	int validGuess;
	boolean test;
	
	String toPlay = "Y";
	
	/**
	 * @param test = boolean for test mode
	 * @param input = Scanner object
	 * @param currentList = reference to GuessList object created for this instance of Game
	 * @param numberOfGuesses = total number of guesses allowed in this instance of game
	 */
	public Game(boolean test, Scanner input, GuessList currentList, int numberOfGuesses) {
		
		this.secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
		this.test = test;
		this.input = input;
		this.numberOfGuesses = numberOfGuesses;
		this.currentList = currentList;
		if(test) { //print Secret code if in test mode
			System.out.println("Secret code: " + secretCode);
			System.out.println();
		}
		
	}
	
	/**
	 * @return String toPlay -> whether or not game needs to continue running
	 */
	public String runGame() {
		int size;
		
		
		System.out.println("You have " + numberOfGuesses + " guess(es) left.");
		System.out.println("Enter guess:");
		String guess = input.nextLine(); // get guess
		Guess newGuess = new Guess(guess);
		
		validGuess = newGuess.isValidGuess(); //check if guess is valid (if it's HISTORY or a normal guess)
		
		if(validGuess == 0) {
			//TODO: Print History
			size = currentList.history.size();
			if(size == 0) { //no guesses yet
				System.out.println();
				toPlay = runGame();
				
			}
			else { //if we actually have guesses
				currentList.printHistory();
				System.out.println();
				toPlay = runGame();
			}
		}
		else if(validGuess == 1) { //if the guess was a normal guess
			//TODO: Regular guess
			numberOfGuesses--; //used 1 guess
			Pegs resultPegs = new Pegs(newGuess, this.secretCode); //get result of guess
			currentList.addGuess(newGuess.guess, resultPegs.pegs); //add guess and results to an ArrayList held in GuessList
			size = currentList.history.size(); //get size of ArrayList in GuessList
			System.out.println(currentList.history.get(size - 1)); //print out recent guess and results
			
			
			
			if(resultPegs.blackPegs == GameConfiguration.pegNumber) { //check if game won
				//TODO: Win game
				System.out.println("You win!");
				System.out.println();
				System.out.println("Do you want to play a new game? (Y/N):");
				toPlay = input.nextLine();
			}
			else  if(numberOfGuesses <= 0) { //check if game lost
				//TODO: lost game
				System.out.println("You lose! The pattern was " + this.secretCode);
				System.out.println();
				System.out.println("Do you want to play a new game? (Y/N):");
				toPlay = input.nextLine();
			}
			
			else { //if neither won nor lost - repeat
				System.out.println();
				
				toPlay = runGame();
			}
			
		}
		else { //if the guess was invalid
			//TODO: INVALID_GUESS
			System.out.println("INVALID_GUESS");
			System.out.println();
			toPlay = runGame();
		}
		
		return toPlay;
	}
}
