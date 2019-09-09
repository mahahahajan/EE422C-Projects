package assignment2;

import java.util.*;
/**
 * EE422C Project 2 (Mastermind) submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */


public class GuessList {
	
	ArrayList<String> history;
	
	
	
	/**
	 * new GuessList object creates new ArrayList
	 */
	public GuessList(){
		history = new ArrayList<String>();
	}
	
	/**
	 * @param guess is the user's guess
	 * @param pegs is the result of the user's guess
	 */
	public void addGuess(String guess, String pegs) {
		String guessAndPegs = guess + " -> " + pegs;
		history.add(guessAndPegs);
	}
	
	/**
	 * if called, print out the ArrayList holding all previous guesses and results
	 */
	public void printHistory() {
		for(int i = 0; i < history.size(); i++) {
			System.out.println(history.get(i));
		}
	}
	
}
