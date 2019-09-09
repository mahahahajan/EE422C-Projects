/* MULTITHREADING BookingClient.java
 * EE422C Project 6 submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */
package assignment6;

import java.util.Map;

import assignment6.Theater.Seat;

import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingClient {

	public Map<String, Integer> office;
	public Theater theater;
	public List<String> boxOffices = new ArrayList<>();
	public List<Integer> clientIDs = new ArrayList<Integer>();

	/**
	 * @param office  maps box office id to number of customers in line
	 * @param theater the theater where the show is playing
	 */
	public BookingClient(Map<String, Integer> office, Theater theater) {
		// TODO: Implement this constructor
		this.office = office;
		this.theater = theater;
		for (String key : office.keySet()) {
			boxOffices.add(key); 											//create an arraylist of boxOfficeIds (Keeps track of BX1, BX2, etc)
		}
	}

	/**
	 * Starts the box office simulation by creating (and starting) threads for each
	 * box office to sell tickets for the given theater
	 *
	 * @return list of threads used in the simulation, should have as many threads
	 *         as there are box offices
	 */
	public List<Thread> simulate() {
		// TODO: Implement this method
		List<Thread> threadList = new LinkedList<>();
		for (int i = 0; i < office.size(); i++) {
			String boxOfficeId = boxOffices.get(i);
			Thread currThread = new Thread() {
				@Override
				public void run() {
					int current = office.get(boxOfficeId);          		//get number of clients for the box office
					while (current > 0) { 				   	        		//while I have clients in line
						synchronized (theater.seatsQueue) { 		 		//Only 1 thread at a time can access it
							Seat best = theater.bestAvailableSeat(); 		//Get the next best seat by popping off the Queue
																	 		//Generate a unique id for each customer
							int client = Integer.parseInt(boxOfficeId.substring(boxOfficeId.indexOf("BX") + 2));
							client = client * 10 + current;
							if (!clientIDs.isEmpty()) {
								while(clientIDs.contains(client)) {    		//double checking that every client ID is unique
									client *= 2;
								}
							}
							clientIDs.add(client);					        //add client id to arrayList of clientIds to avoid future duplicates
							theater.printTicket(boxOfficeId, best, client); //print ticket
							office.replace(boxOfficeId, current - 1); 		//change the key value pair to have a new value
							current--;										//decrement counter 
						}
						try {
							Thread.sleep(theater.getPrintDelay());			//put thread to sleep so other threads run -- could use yield
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			};
			threadList.add(currThread);										//add thread to list of Threads
			currThread.start();												//start thread
		}
		return threadList;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO: Initialize test data to description
		Map<String, Integer> offices = new HashMap<String, Integer>() {
			{
				put("BX1", 300);
				put("BX2", 300);
				put("BX3", 300);
				put("BX4", 400);
			}
		};

		Theater t = new Theater(1000, 1, "Avengers: Endgame");
		BookingClient bc = new BookingClient(offices, t);
//		PrintStream fileOut = new PrintStream("out.txt");
//		System.setOut(fileOut);
		List<Thread> threads = bc.simulate();
		
		for (int i = 0; i < t.getTransactionLog().size(); i++) {
			System.out.println(t.getTransactionLog().get(i));
		}

	}
}
