
import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.util.ArrayList;

import java.util.Arrays;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatClient extends Application {

	AnchorPane root;
	public static Stage primaryStage;
	@FXML
	PasswordField passWordField;
	@FXML
	TextField ipAddressField, portNumber, usernameField, usersToAddField, newChatName;
	@FXML
	Button myIP, connect, createAccount, loginBtn, addUsersBtn, createNewChatroomBtn;

	@FXML
	GridPane userList;
	@FXML
	TextArea outgoingMessage;
	@FXML
	ScrollPane messages, usersPane;

	static String ipAddress;
	public static int port;
	static Socket sock = null;
	public static String username, password;
	public static ArrayList<String> users = new ArrayList<String>();
	public static ArrayList<Label> chats = new ArrayList<Label>();
	public static ArrayList<String> newUsersToAdd = new ArrayList<String>();
	static BufferedReader reader;
	static PrintWriter writer;

	public DataOutputStream send;
	public DataInputStream receive;

	public String EVEN_USER = "-fx-background-color: #ecf0f1; -fx-font-family: Futura; -fx-font-size: 15;";
	public String ODD_USER = "-fx-background-color: #bdc3c7; -fx-font-family: Futura; -fx-font-size: 15;";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ChatClient.primaryStage = primaryStage;
		primaryStage.setOnCloseRequest(e ->{
			System.exit(0);
		});
		initView();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}).start();
	}

	public void autofillIP(ActionEvent event) {
		System.out.println("hi");
		String ipAddress = null;
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress().trim();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ipAddressField.setText(ipAddress);
		this.ipAddress = ipAddressField.getText();
	}

	public void connectToServer() {
		ChatClient.port = Integer.parseInt(portNumber.getText());
		System.out.println(port);
		ipAddress = ipAddressField.getText();
		System.out.println(ipAddress);
		try {
			showLogin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAccountBtnClicked() {
		try {
			root = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("createUser.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene createUserScene = new Scene(root, 800, 450);
		primaryStage.setScene(createUserScene);
	}

	public void login() throws IOException {
		// TODO: Firebase authentication, maybe oath
		ChatClient.username = usernameField.getText();
		ChatClient.password = passWordField.getText();
		// some authentication
		// create chat window

		enterChatroom();
	}

	public void enterChatroom() throws IOException {
		
		try {
			sock = new Socket(ipAddress, port);
			root = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("ChatRoom.fxml"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR: NO SERVER STARTED");
			alert.setHeaderText("Error: No server");
			alert.setContentText("There is no server running. "
					+ "Please exit this application and start a server before relaunching");

			alert.showAndWait();
			System.exit(1);
		}

		send = new DataOutputStream(sock.getOutputStream());
		receive = new DataInputStream(sock.getInputStream());

		send.writeUTF("Name:" + username);

		Scene chatroomMain = new Scene(root, 800, 600);
		primaryStage.setScene(chatroomMain);

		System.out.println(root.getChildrenUnmodifiable().toString());
		userList = (GridPane) root.getChildren().get(0);
		System.out.println(userList.toString());

		String raw = receive.readUTF();
		String arr = raw.substring(1, raw.indexOf("]"));

		users = new ArrayList<String>(Arrays.asList(arr.split(",")));
		users.add(0, username);
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).trim().length() == 0) {
				users.remove(i);
				i--;
			}
			users.set(i, users.get(i).trim());
		}

		for (int i = 0; i < users.size(); i++) {

			Button currButton = new Button();

			if (users.get(i).equals(username)) {
				currButton.setText(users.get(i) + " (Me)");
			} else {
				currButton.setText(users.get(i));
				
				// currButton.setMinHeight(300/users.size());
			}
			currButton.setMinWidth(200);
			currButton.setOnMouseClicked(e->{
				createNewChatPopUp();
			});
			VBox currUserSize = new VBox(currButton);
			VBox.setVgrow(currButton, Priority.ALWAYS);
		
			if (i % 2 == 0) {
				currButton.setStyle(EVEN_USER);
			} else {
				currButton.setStyle(ODD_USER);
			}
			userList.add(currButton, 0, i);
			GridPane.setValignment(currUserSize, VPos.TOP);
			
			
		}
		System.out.println(userList.getChildren().toString());

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				}
			}

		});

		t.start();
		try {
			setUpNetworking(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Thread updateUsers = new Thread() { public void run() {
		 * while(primaryStage.getScene() == chatroomMain) {
		 * if(userGrid.getChildren().size() < users.size()) { for(int i =
		 * userGrid.getChildren().size(); i < users.size(); i++) { Button currButton =
		 * new Button();
		 * 
		 * currButton.setText(users.get(i)); HBox currUserSize = new HBox(currButton);
		 * 
		 * 
		 * System.out.println(users.get(i)); userGrid.add(currUserSize, 0, i); } } } }
		 * };
		 * 
		 * updateUsers.start(); try { Thread.sleep(10000); } catch (InterruptedException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

	}

	public void createNewChatPopUp() {
		// TODO Auto-generated method stub
		Stage newStage = new Stage();
		try {
			root = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("customChatRoom.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene popUpScene = new Scene(root, 400, 300);
		newStage.setScene(popUpScene);
		newStage.show();
		
	}
	public void createNewChatRoom() throws IOException{
		Stage newStage = new Stage();
		try {
			sock = new Socket(ipAddress, port);
			root = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("ChatRoom.fxml"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR: NO SERVER STARTED");
			alert.setHeaderText("Error: No server");
			alert.setContentText("There is no server running. "
					+ "Please exit this application and start a server before relaunching");

			alert.showAndWait();
			System.exit(1);
		}

		send = new DataOutputStream(sock.getOutputStream());
		receive = new DataInputStream(sock.getInputStream());

		send.writeUTF("Name:" + username);

		Scene chatroomMain = new Scene(root, 800, 600);
		newStage.setScene(chatroomMain);
		newStage.show();

		System.out.println(root.getChildrenUnmodifiable().toString());
		userList = (GridPane) root.getChildren().get(0);
		System.out.println(userList.toString());

		String raw = receive.readUTF();
		String arr = raw.substring(1, raw.indexOf("]"));

		this.users = newUsersToAdd;
		

		for (int i = 0; i < users.size(); i++) {

			Button currButton = new Button();

			if (users.get(i).equals(username)) {
				currButton.setText(users.get(i) + " (Me)");
			} else {
				currButton.setText(users.get(i));
				
				// currButton.setMinHeight(300/users.size());
			}
			currButton.setMinWidth(200);
			currButton.setOnMouseClicked(e->{
				createNewChatPopUp();
			});
			VBox currUserSize = new VBox(currButton);
			VBox.setVgrow(currButton, Priority.ALWAYS);
		
			if (i % 2 == 0) {
				currButton.setStyle(EVEN_USER);
			} else {
				currButton.setStyle(ODD_USER);
			}
			userList.add(currButton, 0, i);
			GridPane.setValignment(currUserSize, VPos.TOP);
			
			
		}
		System.out.println(userList.getChildren().toString());

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				}
			}

		});

		t.start();
		try {
			setUpNetworking(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void validateUser() {
		newUsersToAdd = new ArrayList<String>();
		System.out.println("CHECK");
		String user = usersToAddField.getText();
		for(String u : users) {
			System.out.println(u);
		}
		if(users.indexOf(user) != -1) {
			System.out.println("user exits");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successfully added.");
			alert.setHeaderText("Added " + user + " successfully");
			alert.setContentText("The user " + user + " has been successfully addded");
			alert.showAndWait();
			newUsersToAdd.add(user);
			
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR: User Not Found");
			alert.setHeaderText("User " + user + " not found");
			alert.setContentText("The user " + user + " was unable to be added."
					+ " This user was not found within the currently active users");
			alert.showAndWait();
		}
	}
	
	
	public void periodicUpdate() throws InterruptedException {
		System.out.println(userList);
		userList.getChildren().clear();
		for (int i = 0; i < users.size(); i++) {

			Button currButton = new Button();

			if (users.get(i).equals(username)) {
				currButton.setText(users.get(i) + " (Me)");
			} else {
				currButton.setText(users.get(i));
				
				// currButton.setMinHeight(300/users.size());
			}
			currButton.setMinWidth(200);
			currButton.setOnMouseClicked(e->{
				createNewChatPopUp();
			});
			VBox currUserSize = new VBox(currButton);
			VBox.setVgrow(currButton, Priority.ALWAYS);
		
			if (i % 2 == 0) {
				currButton.setStyle(EVEN_USER);
			} else {
				currButton.setStyle(ODD_USER);
			}
			userList.add(currButton, 0, i);
			GridPane.setValignment(currUserSize, VPos.TOP);
			
		
		}
	}

	private void initView() {
		try {
			root = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("EnterIP.fxml"));
			Scene ipScene = new Scene(root, 800, 450);
			primaryStage.setResizable(false);
			primaryStage.setScene(ipScene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showLogin() {
		// TODO: UNCOMMENT SOCKET
		
		
		try {
//			this.sock = ;
//			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			root = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("login.fxml"));
			System.out.println("yo");
			Scene loginScene = new Scene(root, 800, 450);
			primaryStage.setScene(loginScene);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		reader = new BufferedReader(streamReader);
//		writer = new PrintWriter(sock.getOutputStream());
//		System.out.println("networking established");
//		Thread readerThread = new Thread(new IncomingReader());
//		readerThread.start();
	}

//	class SendButtonListener implements ActionListener {
//		public void actionPerformed(ActionEvent ev) {
	// writer.println(outgoing.getText());
	// writer.flush();
	// outgoing.setText("");
	// outgoing.requestFocus();
//		}
//	}

	public void sendMessage() {
		System.out.println(outgoingMessage.toString());
		String message = outgoingMessage.textProperty().getValueSafe();
		System.out.println(message);

		writer.println(username+": "+message);
		writer.flush();
		outgoingMessage.setText("");
		outgoingMessage.requestFocus();
	}

	private void setUpNetworking(AnchorPane root) throws Exception {
		@SuppressWarnings("resource")
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader(root));
		readerThread.start();
	}

	class IncomingReader implements Runnable {
		
		ScrollPane messagesPane = null;
		GridPane displayedMessages = null;
		
		public IncomingReader(AnchorPane root) {
			System.out.println(root.toString());
			messagesPane = (ScrollPane)root.getChildren().get(2);
			displayedMessages = (GridPane) messagesPane.getContent();
		}
		
		public void run() {
			String raw;
			try {
				while ((raw = reader.readLine().trim()) != null && raw.indexOf(":") != -1) {
					System.out.println(raw);
					while (raw.contains("NewUser:")) {
						String thisUsername = raw.substring(raw.indexOf(":") + 1, raw.indexOf("]"));
						raw = raw.substring(raw.indexOf("]")+1);
						if (thisUsername.trim().length() > 0) {
							users.add(thisUsername);
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									periodicUpdate();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						});
					}

					while (raw.contains("LeaveUser:")) {
						String thisUsername = raw.substring(raw.indexOf(":") + 1, raw.indexOf("]"));
						System.out.println(thisUsername);
						for (int x = 0; x < users.size(); x++) {
							System.out.println(users.get(x) + " == " + thisUsername);
							if (users.get(x).equals(thisUsername)) {
								users.remove(x);
								x--;
							}
						}
						raw = raw.substring(raw.indexOf("]")+1);

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									periodicUpdate();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						});
					} 
					
					{
						chats.add(new Label(raw + "\n"));
					}
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							displayedMessages.add(chats.get(chats.size()-1), 0, chats.size() );
							GridPane.setHgrow(chats.get(chats.size()-1), Priority.ALWAYS);
						}
						
					});
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			new ChatClient();
			Application.launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
