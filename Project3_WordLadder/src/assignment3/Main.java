/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Drew Bernard
 * dhb653
 * 16225
 * Pulkit Mahajan
 * pm28838
 * 16225
 * Slip days used: <0>
 * Git URL: https://github.com/EE422C/project-3-wordladder-pair-72
 * Spring 2019
 */

package assignment3;

import java.util.*;
import java.io.*;

public class Main {

	// static variables and constants only here.

	static Set<String> dictionary;
	static char[] alphabet;
	static Map<String, Node> wordMap; // Data structure for DFS optimization
	static boolean firstCallDFS;

	public static void main(String[] args) throws Exception {
		
		Scanner kb; // input Scanner for commands
		PrintStream ps; // output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps); // redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out; // default output to Stdout
		}
		initialize();
		
		ArrayList<String> startAndEnd = parse(kb);
		ArrayList<String> wordLadderBFS = getWordLadderBFS(startAndEnd.get(0), startAndEnd.get(1));
		
		startAndEnd = parse(kb);
		ArrayList<String> wordLadderDFS = getWordLadderDFS(startAndEnd.get(0), startAndEnd.get(1));
		printLadder(wordLadderDFS);
		
	}

	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests. So call it
		// only once at the start of main.
		dictionary = makeDictionary();
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		firstCallDFS = false; // need some way to check this somewhere else (maybe a method that's called from
								// within DFS?)
		wordMap = new HashMap<String, Node>();
	}

	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. If command
	 *         is /quit, return empty ArrayList.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		// TO DO
		ArrayList<String> startAndEnd = new ArrayList<String>();
		String firstInput = keyboard.next();
		String start;
		String end;
		if (firstInput.equals("/quit")) {
			start = firstInput;
			end = "test";
			startAndEnd.add(start);
			startAndEnd.add(1, end);
			return startAndEnd;
		} else {
			start = firstInput;
			end = keyboard.next();
			startAndEnd.add(start);
			startAndEnd.add(1, end);
		}

		return startAndEnd;
	}

	/**
	 * @param start String to be used as start of the word ladder
	 * @param end	String to be used as end of the word ladder
	 * @return ArrayList containing either all the words in between start and end, or just start and end if there is no word ladder
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		//to handle /quit input
		if(start.equals("/quit")){
			return null;
		}
		//Ran into issues with JUNIT because the words being passed in were lower case
		//This way we manually upper case the letters - should solve all issues
		
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayList<String> wordLadder = new ArrayList<String>();
		Queue<Node> wordsQueue = new LinkedList<>();
		Node currentNode = new Node();

		String newWord = null;
		if (!firstCallDFS) { // check if this is the first time calling the recursive function
			currentNode = new Node(start, null, 0);
			wordMap.put(start, currentNode);
			firstCallDFS = true;
		}

		if (wordMap.containsKey(end)) { // check if end word has been found
			Node endNode = wordMap.get(end);
			while (endNode.length != 0) {
				String endWord = endNode.word;
				wordLadder.add(endNode.word);
				endNode = endNode.parent;
				
				//method is over so we need to set this back up so that we can use the method again without initialize being called
				//firstCallDFS = false; 
			}
			wordLadder.add(endNode.word);
			return wordLadder;
		}
		getClosestNeighbors(wordsQueue, start, end);

		while (!wordsQueue.isEmpty()) {
			Node newCurrent = wordsQueue.remove();
			ArrayList<String> resultList = getWordLadderDFS(newCurrent.word, end);
			if (resultList.size() != 0 && resultList.get(0).equals(end)) {
				return resultList;
			}
		}
		if (wordsQueue.isEmpty()) {
			ArrayList<String> resultList = new ArrayList<>();
			wordLadder.add(start);
			wordLadder.add(end);
			//firstCallDFS = false;  //if it's empty, wanna reset the method without needing initialize
		}
		return wordLadder;
	}

	public static void getClosestNeighbors(Queue<Node> wordsQueue, String start, String endWord) {
		Queue<Node> zeroCommon = new LinkedList<>();
		Queue<Node> oneCommon = new LinkedList<>();
		Queue<Node> twoCommon = new LinkedList<>();
		Queue<Node> threeCommon = new LinkedList<>();
		Queue<Node> fourCommon = new LinkedList<>();
		Queue<Node> fiveCommon = new LinkedList<>();

		char[] currentWordChars = start.toCharArray();

		for (int i = 0; i < 5; i++) {
			char oldChar = currentWordChars[i];
			for (int j = 0; j < alphabet.length; j++) {
				currentWordChars[i] = alphabet[j];
				String newWord = new String(currentWordChars).toUpperCase();
				if (dictionary.contains(newWord) && !wordMap.containsKey(newWord)) { // if the word is in the dictionary and hasn't been visited yet
					
					Node newNode = new Node(newWord, wordMap.get(start), wordMap.get(start).length + 1);
					wordMap.put(newWord, newNode);
					int lettersInCommon = 0;
					for (int k = 0; k < 5; k++) {
						if (newWord.charAt(k) == endWord.charAt(k)) {
							lettersInCommon++; //see how close it is to end word
						}
					}
					if (lettersInCommon == 5) {
						fiveCommon.add(newNode);
					} else if (lettersInCommon == 4) {
						fourCommon.add(newNode);
					} else if (lettersInCommon == 3) {
						threeCommon.add(newNode);
					} else if (lettersInCommon == 2) {
						twoCommon.add(newNode);
					} else if (lettersInCommon == 1) {
						oneCommon.add(newNode);
					} else {
						zeroCommon.add(newNode);
					}
				}
			}
			currentWordChars[i] = oldChar;
		}
		//check words in order of most letters in common to least letters in common
		wordsQueue.addAll(fiveCommon); 
		wordsQueue.addAll(fourCommon);
		wordsQueue.addAll(threeCommon);
		wordsQueue.addAll(twoCommon);
		wordsQueue.addAll(oneCommon);
		wordsQueue.addAll(zeroCommon);
	}

	/**
	 * @param start String to be used as start of the word ladder
	 * @param end	String to be used as end of the word ladder
	 * @return ArrayList containing either all the words in between start and end, or just start and end if there is no word ladder
	 */
	public static ArrayList<String> getWordLadderBFS(String start, String end) {

		//to handle /quit input
		if(start.equals("/quit")){
			return null;
		}
		
		//Ran into issues with JUNIT because the words being passed in were lower case
		//This way we manually upper case the letters - should solve all issues
		start = start.toUpperCase();
		end = end.toUpperCase();
		String newWord = null;
		ArrayList<String> visitedWords = new ArrayList<String>();
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		ArrayList<String> wordLadder = new ArrayList<String>();
		Queue<String> wordsQueue = new LinkedList<>();
		Queue<Node> nodeQueue = new LinkedList<>();

		int check = 0;

		Node startNode = new Node(start);
		Node currentNode = startNode;
		Node newNode = new Node(null, null);

		wordsQueue.add(start); // add start word to the queue

		while (wordsQueue.size() > 0) {
			String currentWord = wordsQueue.remove();
			if (check != 0) { // if it's not the first call
				currentNode = nodeQueue.remove();
				currentWord = currentNode.word;
			}
			check++;
			char[] currentWordChars = currentWord.toCharArray();
			for (int i = 0; i < 5; i++) {
				char oldChar = currentWordChars[i];

				for (int j = 0; j < alphabet.length; j++) {

					currentWordChars[i] = alphabet[j];
					newWord = new String(currentWordChars).toUpperCase();

					if (dictionary.contains(newWord) && !visitedWords.contains(newWord)) {
						wordsQueue.add(newWord);
						visitedWords.add(newWord);
						newNode = new Node(newWord, currentNode);
						nodeQueue.add(newNode);
						visitedNodes.add(newNode);

					}
				}
				currentWordChars[i] = oldChar;
			}
			if (visitedWords.contains(end)) { // if we have found the end word

				int index = visitedWords.indexOf(end);
				newNode = visitedNodes.get(index);

				while (newNode.parent != null) { // trace parents
					wordLadder.add(newNode.word.toLowerCase());
					newNode = newNode.parent;
				}
				wordLadder.add(newNode.word.toLowerCase());
				break;
			}
		}
		if (wordLadder.size() == 0) { //check if word ladder doesn't exist
			wordLadder.add(start);
			wordLadder.add(end);
		}
		return wordLadder;
	}

	/**
	 * @param ladder ArrayList containing the words to be used in the word ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		
		if(ladder == null) {
			return;
		}
		
		if (ladder.size() == 2) {
			System.out.println("no word ladder can be found between " + ladder.get(0).toLowerCase()
					+ " and " + ladder.get(ladder.size() - 1).toLowerCase() + ".");
		} else {
			System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between "
					+ ladder.get(ladder.size() - 1).toLowerCase() + " and " + ladder.get(0).toLowerCase() + ".");
			for (int i = 0; i < ladder.size(); i++) {
				System.out.println(ladder.get((ladder.size() - 1) - i).toLowerCase());
			}
		}
	}
	// TODO
	// Other private static methods here

	/* Do not modify makeDictionary */
	public static Set<String> makeDictionary() {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner(new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
