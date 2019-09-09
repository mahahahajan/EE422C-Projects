import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Chatroom extends Observable {
	
	public static AnchorPane root = null; 
	
	//the users in this chatroom. you can refer to all the users and their usernames in a chatroom via this list.
	public ArrayList<User> users = new ArrayList<User>();
	

	public void add(User s) throws IOException {
		users.add(s);
		ClientObserver writer = new ClientObserver(s.sock.getOutputStream());
		this.addObserver(writer);
		Thread t = new Thread(new ClientHandler(s.sock));
		t.start();
		updateRoom();
	}
	
	public void updateRoom() {
		System.out.println(users.size());
		for(int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).username);
		}
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for (Thread t : threadSet) {
		    String name = t.getName();
		    Thread.State state = t.getState();
		    int priority = t.getPriority();
		    String type = t.isDaemon() ? "Daemon" : "Normal";
		    System.out.printf("%-20s \t %s \t %d \t %s\n", name, state, priority, type);
		}
	}
	
	public Chatroom() { // for the server to create a new chatroom
		users = new ArrayList<User>();
	}

	class ClientHandler implements Runnable {
		private BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket) {
			sock = clientSocket;
			try {
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("deprecation")
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("server read "+message);
					setChanged();
					notifyObservers(message);
				}
			} catch (IOException e) {
				User leftUser = null;
				for(int u = 0; u < users.size(); u++) {
					if(users.get(u).sock.equals(this.sock)) {
						leftUser = users.get(u);
						users.remove(u);
					}
				}
				setChanged();
				notifyObservers("[LeaveUser:"+leftUser.username+"]");
			}
		}
	}
	
}
