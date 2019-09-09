import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class viewController extends Application {

	@FXML
	static Label portNumber;
	
	public static void server(int port) throws Exception {
		// TODO Auto-generated method stub
		try {
			GridPane root = null;
			String ipAddress = null;
			try {
				root = (GridPane) FXMLLoader.load(viewController.class.getResource("serverLayout.fxml"));
				ipAddress = InetAddress.getLocalHost().getHostAddress().trim();
				System.out.println(ipAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root, 800, 450);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			HBox second = (HBox) root.getChildren().get(1);
			System.out.println(second.getChildren().get(0));
			((Text) second.getChildren().get(0)).setText(ipAddress);
			//portNumber.setText("Port: " + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		int port = ChatServer.getPort();
		server(port);
		new Thread( () ->  {
			try {
				new ChatServer().setUpNetworking();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		primaryStage.setOnCloseRequest(e ->{
			System.exit(0);
		});
	}

}
