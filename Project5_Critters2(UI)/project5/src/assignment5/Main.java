package assignment5;

import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static Stage stage; //primary stage
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 800;
	public static MediaPlayer player = null;
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage my_stage) throws Exception {
		stage = my_stage;
		stage.setResizable(false);
		stage.setTitle("Critters 5");
		GridPane screen = new GridPane();	
		
		Critter.createClover();
		Critter.displayWorld(screen);
	
		Media media = null;
		try {
		  media = new Media(getClass().getResource("music.mp3").toURI().toString());
		  MediaPlayer mp = new MediaPlayer(media);
	      mp.stop();
	      mp.setStartTime(new Duration(0));
		  mp.play();
		} catch (URISyntaxException e) {
		  e.printStackTrace();
		}
		
	}
	
	 public static void playMusic() {
		 /*URL resource = Critter.class.getResource("music.mp3");
	    	String media = resource.toString();
	    	System.out.println(media);
	    	
	    	String uriString = new File(media).toURI().toString();
	    	System.out.println(uriString);
	    	 AudioClip player= new AudioClip(media);
	    	
	    	player.play(); */
		 
		 

	 }
}
