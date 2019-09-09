package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Pulkit Mahajan 
 * pm28838	
 * Santosh Balachandra 
 * sb55774
 * Slip days used: <0>
 * Spring 2019
 */

import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

	static Scanner kb; // scanner connected to keyboard input, or input file
	private static String inputFile; // input file, used instead of keyboard input if specified
	static ByteArrayOutputStream testOutputString; // if test specified, holds all console output
	private static boolean DEBUG = false; // Use it or not, as you wish!
	static PrintStream old = System.out; // if you want to restore output to console

	// Gets the package name. The usage assumes that Critter and its subclasses are
	// all in the same package.
	private static String myPackage; // package of Critter file. Critter cannot be in default pkg.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	/**
	 * Main method.
	 * 
	 * @param args args can be empty. If not empty, provide two parameters -- the
	 *             first is a file name, and the second is test (for test output,
	 *             where all output to be directed to a String), or nothing.
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			try {
				inputFile = args[0];
				kb = new Scanner(new File(inputFile));
			} catch (FileNotFoundException e) {
				System.out.println("USAGE: java Main OR java Main <input file> <test output>");
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
			}
			if (args.length >= 2) {
				if (args[1].equals("test")) { // if the word "test" is the second argument to java
					// Create a stream to hold the output
					testOutputString = new ByteArrayOutputStream();
					PrintStream ps = new PrintStream(testOutputString);
					// Save the old System.out.
					old = System.out;
					// Tell Java to use the special stream; all console output will be redirected
					// here from now
					System.setOut(ps);
				}
			}
		} else { // if no arguments to main
			kb = new Scanner(System.in); // use keyboard and console
		}
		commandInterpreter(kb);

		System.out.flush();

	}
	/* Do not alter the code above for your submission. */

	/**
	 * Handles all commands to the simulation should throw error if args[] has size
	 * greater than 3
	 * 
	 * @param kb
	 */
	private static void commandInterpreter(Scanner kb) {
		String input;
		while (true) {
			System.out.print("critters> ");
			input = kb.nextLine();
			String[] args = input.split(" ");
			if (args[0].equals("quit")) {
				if(args.length > 1 ) {
					error(args);
				}
				else{
					return;
				}
			} 
			else if (args[0].equals("show")) {
				if (args.length > 1) {
					error(args);
				}
				else{
					Critter.displayWorld();	
				}
			} 
			else if (args[0].equals("step")) {
				if (args.length > 3) {
					error(args);
				} 
				else {
					try {
						doStep(args);
					} catch (NumberFormatException e) {
						error(args);
					}
				}
			} 
			else if (args[0].equals("seed")) {
				if (args.length > 3) {
					error(args);
				} 
				else {
					try {
						Critter.setSeed(Integer.parseInt(args[1]));
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						error(args);
					}
				}
			} 
			else if (args[0].equals("create")) {
				if (args.length > 3) {
					error(args);
				}
				else {
					try {
						doCreate(args);
					} catch (NumberFormatException | InvalidCritterException | NoClassDefFoundError e) {
						error(args);
					}
				}
			} 
			else if (args[0].equals("stats")) {
				
				try {
					doStats(args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException | ClassNotFoundException
						| InvalidCritterException | NoClassDefFoundError e) {
					error(args);
				}
			} else if (args[0].equals("clear")) {
				if(args.length != 1) {
					error(args);
				}
				else {
					Critter.clearWorld();
				}
			} else {
				System.out.println("invalid command: " + String.join(" ", args));
			}

		}
	}

	/**
	 * Handles stats command as well as takes care of possible exceptions
	 * 
	 * @param args 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InvalidCritterException
	 */
	public static void doStats(String[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, ClassNotFoundException, InvalidCritterException {
		List<Critter> crits = Critter.getInstances(args[1]);
		Class<?>[] paramTypes = { List.class };
		Method runStats = Class.forName(myPackage + "." + args[1]).getMethod("runStats", paramTypes);
		runStats.invoke(null, crits);
	}

	/**
	 * Handles create command and any possible errors 
	 * 
	 * @param args
	 * @throws InvalidCritterException
	 * @throws NumberFormatException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static void doCreate(String[] args)
			throws InvalidCritterException, NumberFormatException, ArrayIndexOutOfBoundsException {
		String my_class = args[1];
		int count = 0;
		if (args.length == 2) {
			count = 1;
		} else {
			count = Integer.parseInt(args[2]);
		}
		for (int i = 0; i < count; i++) {
			Critter.createCritter(my_class);
		}
	}

	/**
	 * Handles step method and number formatting error 
	 * 
	 * @param args
	 * @throws NumberFormatException
	 */
	public static void doStep(String[] args) throws NumberFormatException {
		int count = 0;
		if (args.length == 1) {
			count = 1;
		} else {
			count = Integer.parseInt(args[1]);
		}
		for (int i = 0; i < count; i++) {
			Critter.worldTimeStep();
		}
	}

	/**
	 * @param args
	 */
	public static void error(String[] args) {
		System.out.println("error processing: " + String.join(" ", args));
	}

}
