/* CRITTERS Critter.java
 *
 */
package assignment5;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Painter {
	static int sizeX = Main.SCREEN_WIDTH / Params.WORLD_WIDTH;
	static int sizeY = Main.SCREEN_HEIGHT / Params.WORLD_HEIGHT;
	public static String LIGHTER_BLACK = "-fx-background-color: #34495e; -fx-text-fill:white; -fx-font-size: 200%;";
	public static String DARKER_BLACK = "-fx-background-color: #2c3e50 ; -fx-text-fill:white; -fx-font-size: 200%;";
	public static String LIGHTER_GREEN = "-fx-background-color: #29b564; -fx-text-fill: #ecf0f1; ";
	public static String DARKER_GREEN = "-fx-background-color: #27ae60; -fx-text-fill:#ecf0f1;";
	public static String LIGHTER_RED = "-fx-background-color: #e74c3c; -fx-text-fill: #ecf0f1; ";
	public static String DARKER_RED = "-fx-background-color: #c0392b; -fx-text-fill:#ecf0f1;";
	public static int rate = 0;
	public static ArrayList<Label> labelList = new ArrayList<Label>();
	public static ArrayList<String> critterNameList = new ArrayList<String>();
	/*
	 * Paint the grid lines in orange. The purpose is two-fold -- to indicate
	 * boundaries of icons, and as place-holders for empty cells. Without
	 * placeholders, grid may not display properly.
	 */
	private static void paintGridLines(GridPane grid) {
		for (int i = 0; i < Params.WORLD_WIDTH; i++) {
			for (int j = 0; j < Params.WORLD_HEIGHT; j++) {
				Shape s = new Rectangle(sizeX, sizeY);
				s.autosize();
				s.setFill(Color.rgb(39, 174, 96));
				s.setStroke(Color.rgb(44, 62, 80));
				grid.add(s, i, j);
			}
		}
	}

	/*
	 * Paints the icon shapes on a grid.
	 */
	public static void paint(GridPane grid) {
		grid.getChildren().clear(); // clear the grid
		paintGridLines(grid); // paint the borders
		ArrayList<Critter> critters = null;
		try {
			critters = (ArrayList<Critter>) Critter.getInstances(null);
		} catch (InvalidCritterException e) {
			e.printStackTrace();
		}
		for (Critter c : critters) {
			Shape s = null;
			switch (c.viewShape()) {
			case CIRCLE:
				s = getIcon(0);
				break;
			case SQUARE:
				s = getIcon(1);
				break;
			case TRIANGLE:
				s = getIcon(2);
				break;
			case DIAMOND:
				s = getIcon(3);
				break;
			case STAR:
				s = getIcon(4);
				break;
			}
			s.setFill(c.viewFillColor());
			s.setStroke(c.viewOutlineColor());
			grid.add(s, c.getX_coord(), c.getY_coord());
		}

	}

	/*
	 * Returns a square or a circle depending on the shapeIndex parameter
	 *
	 */
	static Shape getIcon(int shapeIndex) {
		// bounds our shape to the smallest size, width or height
		int smallsize;
		if (sizeX < sizeY) {
			smallsize = sizeX;
		} else
			smallsize = sizeY;

		Shape s = null;
		switch (shapeIndex) {
		case 0:
			s = new Circle(smallsize / 2);
			break;
		case 1:
			s = new Rectangle(smallsize, smallsize);
			break;
		case 2:
			s = new Path(new MoveTo(0,0), new LineTo(-smallsize+2,0), new LineTo(-smallsize/2,-smallsize+2), new LineTo(0,0));
			break;
		case 3:
			s = new Path(new MoveTo(0,0), new LineTo(smallsize/2,smallsize/2-2), new LineTo(smallsize-2,0), new LineTo(smallsize/2,-smallsize/2+2), new LineTo(0,0));
			break;
		case 4:
			s = new Path(new MoveTo(0,0), new LineTo(smallsize/3+smallsize/25, smallsize/3-smallsize/6), new LineTo(smallsize/2,smallsize/2-2), new LineTo(3*smallsize/4.7, smallsize/3-smallsize/6), new LineTo(smallsize-3,0), new LineTo(3*smallsize/4.7, -smallsize/3+smallsize/6),new LineTo(smallsize/2,-smallsize/2+2), new LineTo(smallsize/3+smallsize/25, -smallsize/3+smallsize/6), new LineTo(0,0));
			break;
		}
		return s;
	}

	/*
	 * Paints the icon shapes on a grid.
	 */
	public static void paintRandom(GridPane grid) {
		Random rand = new Random();
		grid.getChildren().clear(); // clear the grid
		paintGridLines(grid); // paint the borders
		for (int i = 0; i <= 2; i++) {
			Shape s = getIcon(i);
			grid.add(s, rand.nextInt(3), rand.nextInt(3));
		}
	}

	public static void paintUI(GridPane ui, GridPane world) {
		ui.setStyle("-fx-font-family: futura;");
		GridPane editPane = new GridPane();
		editPane.setMinSize((Main.SCREEN_WIDTH / 5) * 4, Main.SCREEN_HEIGHT);
		
		VBox side = new VBox();
		side.setPrefSize(Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT);
		side.setStyle("-fx-background-color: #34495e");
		
		VBox edit = new VBox(editPane);
		edit.setPrefSize((Main.SCREEN_WIDTH / 5) * 4, Main.SCREEN_HEIGHT);
		edit.setStyle("-fx-background-color: #34495e");
		
		Button critters = new Button();
		critters.setText("Critters");
		critters.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				critterUI(critters, editPane, world);
			}

		});

		HBox crittersHbox = hoverBorderBox(critters);

		Button animation = new Button();
		animation.setText("Animation");
		

		HBox animationHbox = hoverBorderBox(animation);

		Button stats = new Button();
		stats.setText("Stats");
		stats.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				statsUI(stats, editPane, world);
			}

		});

		HBox statsHbox = hoverBorderBox(stats);

		Button params = new Button();
		params.setText("Params");
		params.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				paramsUI(params, editPane, world);
			}

		});

		HBox paramsHbox = hoverBorderBox(params);
		
		Button quit = new Button ("Quit");
		quit.setMinSize(Main.SCREEN_WIDTH/5, Main.SCREEN_HEIGHT/20);
		quit.setStyle(Painter.LIGHTER_RED);
		quit.setOnMouseEntered(value->{
			quit.setStyle(Painter.DARKER_RED);
		});
		quit.setOnMouseExited(value->{
			quit.setStyle(Painter.LIGHTER_RED);
		});
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}	
		});

		HBox quitHbox = new HBox(quit);

		side.getChildren().add(crittersHbox);
		side.getChildren().add(animationHbox);
		side.getChildren().add(statsHbox);
		side.getChildren().add(paramsHbox);
		side.getChildren().add(quitHbox);
		
		ui.add(side, 0, 0);
		ui.add(edit, 1, 0, (Main.SCREEN_WIDTH/5) * 4, Main.SCREEN_HEIGHT );
		
		animation.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				animateUI(animation, editPane, world, critters, animation, stats, params);
			}

		});
		
	}

	public static HBox hoverBorderBox(Button btn) {
		Pane sidePane = new Pane();
		sidePane.setMinSize(Main.SCREEN_WIDTH/60, Main.SCREEN_HEIGHT/4);

		HBox horizontal = new HBox( btn);

		horizontal.setMinSize(Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT / 4.5);
		btn.setStyle(DARKER_BLACK);
		btn.setMinSize((Main.SCREEN_WIDTH / 5), Main.SCREEN_HEIGHT / 4.2);
		btn.setOnMouseClicked(value -> {
			btn.setStyle(LIGHTER_BLACK);
		});
		btn.setOnMouseEntered(value -> {
			btn.setStyle(LIGHTER_BLACK);
		});
		btn.setOnMouseExited(value -> {
			btn.setStyle(DARKER_BLACK);
		});
		
		return horizontal;
	}

	public static void critterUI(Button btn, GridPane editPane, GridPane world) {
		
		ControlsUI critter = new ControlsUI(btn, editPane, world);
		
	}
	public static void animateUI(Button btn, GridPane editPane, GridPane world, 
			Button critters, Button animation, Button stats, Button params) {
		AnimateUI animate = null;
		
		if(AnimateUI.started != 1) {
			animate = new AnimateUI(btn, editPane, world, critters, 
					animation, stats, params );
		}
		else {
			animate = new AnimateUI(btn, editPane, world, critters, 
					animation, stats, params, rate);
		}
		
	}
	public static void statsUI(Button btn, GridPane editPane, GridPane world) {
		StatsUI stats = new StatsUI(btn, editPane, world);
		
	}
	public static void paramsUI(Button btn, GridPane editPane, GridPane world) {
		ParamsUI params = new ParamsUI(btn, editPane, world);
		
	}

}
