/* MULTITHREADING Theater.java
 * EE422C Project 6 submission by
 * Pulkit Mahajan
 * pm28838
 * Slip days used: <0>
 * Spring 2019
 */
package assignment6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Theater {

	/**
	 * the delay time you will use when print tickets
	 */
	private int printDelay = 50; // 50 ms. Use it in your Thread.sleep()

	public void setPrintDelay(int printDelay) {
		this.printDelay = printDelay;
	}

	public int getPrintDelay() {
		return printDelay;
	}

	/**
	 * Represents a seat in the theater A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		@Override
		public String toString() {
			String result = "";
			int tempRowNumber = rowNum + 1;
			do {
				tempRowNumber--;
				result = ((char) ('A' + tempRowNumber % 26)) + result;
				tempRowNumber = tempRowNumber / 26;
			} while (tempRowNumber > 0);
			result += seatNum;
			return result;
		}
	}

	/**
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
		private int client;
		public static final int ticketStringRowLength = 31;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			String result, dashLine, showLine, boxLine, seatLine, clientLine, eol;

			eol = System.getProperty("line.separator");

			dashLine = new String(new char[ticketStringRowLength]).replace('\0', '-');

			showLine = "| Show: " + show;
			for (int i = showLine.length(); i < ticketStringRowLength - 1; ++i) {
				showLine += " ";
			}
			showLine += "|";

			boxLine = "| Box Office ID: " + boxOfficeId;
			for (int i = boxLine.length(); i < ticketStringRowLength - 1; ++i) {
				boxLine += " ";
			}
			boxLine += "|";

			seatLine = "| Seat: " + seat.toString();
			for (int i = seatLine.length(); i < ticketStringRowLength - 1; ++i) {
				seatLine += " ";
			}
			seatLine += "|";

			clientLine = "| Client: " + client;
			for (int i = clientLine.length(); i < ticketStringRowLength - 1; ++i) {
				clientLine += " ";
			}
			clientLine += "|";

			result = dashLine + eol + showLine + eol + boxLine + eol + seatLine + eol + clientLine + eol + dashLine;

			return result;
		}
	}

	public Queue<Seat> seatsQueue = new LinkedList<>(); // Queue that holds all the seats - the first seat is the best
	public List<Ticket> soldTickets = new ArrayList<Ticket>(); // Arraylist to hold tickets
	public String show; // show name - just need to pass to toString method
	public boolean soldOut = false; // boolean to see if there's anymore seats

	public Theater(int numRows, int seatsPerRow, String show) {
		// TODO: Implement this constructor
		this.show = show;
		for (int row = 0; row < numRows + 1; row++) {
			for (int seatNum = 0; seatNum < seatsPerRow; seatNum++) {
				Seat currentSeat = new Seat(row, seatNum + 1); // create a new seat with the appropriate row and column
				seatsQueue.add(currentSeat); // add seat to queue of seats
			}
		}

	}

	/**
	 * Calculates the best seat not yet reserved
	 *
	 * @return the best seat or null if theater is full
	 */
	public Seat bestAvailableSeat() {
		// TODO: Implement this method
		if (seatsQueue.isEmpty()) { // check if there are no more seats
			return null;
		}
		return seatsQueue.poll(); // pop from Queue
	}

	/**
	 * Prints a ticket for the client after they reserve a seat Also prints the
	 * ticket to the console
	 *
	 * @param seat a particular seat in the theater
	 * @return a ticket or null if a box office failed to reserve the seat
	 */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		// TODO: Implement this method

		if (!soldOut) { 									// If we have more seats
			if (seat != null) { 							// and the Seat object passed to this method by
															// BookingClient is not null
				Ticket currTicket = new Ticket(show, boxOfficeId, seat, client);

				System.out.println(currTicket.toString());  //output Ticket
				soldTickets.add(currTicket);				//add ticket to transaction log
				return currTicket;							//return ticket
			}
			if (this.seatsQueue.peek() == null) {			//check if there is another seat
				System.out.println("Sorry, we are sold out!");
				soldOut = true;								//set value of SoldOut to true so no other thread can process
			}
		}
		return null;

	}

	/**
	 * Lists all tickets sold for this theater in order of purchase
	 *
	 * @return list of tickets sold
	 */
	public List<Ticket> getTransactionLog() {
		// TODO: Implement this method
		return soldTickets;
	}
}
