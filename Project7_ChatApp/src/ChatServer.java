import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Observable;

import javafx.application.Application;

public class ChatServer extends Observable  {
	
	public static int port = 4243; //change this to change the port for right now
	public Chatroom global;
	
	public static void main(String[] args) {
		try {
			Application.launch(viewController.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	

	void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSock = new ServerSocket(port);
		global = new Chatroom();
		
		while (true) {
			
			Socket clientSocket = serverSock.accept();
			
			//Create a new user u with an arbritrary username and socket, add it to the global chatroom
			User u = new User(""+clientSocket.hashCode(), clientSocket);
			String raw = u.receive.readUTF();
			u.send.writeUTF(global.users.toString());
			String thisUsername = raw.substring(raw.indexOf(":")+1);
			u.username  = thisUsername;
			for(User z : global.users) {
				z.send.writeUTF("[NewUser:"+thisUsername+"]");
			}
			global.add(u);
			
			System.out.println("got a connection from " + thisUsername);
		
			
		}
	}
	
	public static int getPort() {
		return port;
	}

}
