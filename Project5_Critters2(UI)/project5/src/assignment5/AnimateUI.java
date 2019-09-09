package assignment5;

import javafx.animation.AnimationTimer;

/*
 * CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Pulkit Mahajan
 * pm28838
 * Santosh Balachandra
 * sb55774
 * Slip days used: <0>
 * Spring 2019
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AnimateUI {
	public static int started;
	Button stopAnimate;
	public static int num = 0;
	public static int num1 = 0;
	GridPane world;

	// Constructor if there is no animation
	public AnimateUI(Button btn, GridPane editPane, GridPane world, Button critters, Button animate, Button stats,
			Button params) {

		this.world = world; // pass reference to world grid pane so that we can update it
		editPane.getChildren().clear();
		editPane.setOnMouseEntered(value -> {
			btn.setStyle(Painter.LIGHTER_BLACK);
		});
		editPane.setOnMouseExited(value -> {
			btn.setStyle(Painter.DARKER_BLACK);
		});
		Pane spacePane1 = new Pane();
		spacePane1.setMinSize(Main.SCREEN_WIDTH / 16, Main.SCREEN_HEIGHT / 2);

		GridPane animatePane = new GridPane();
		GridPane buttonPane = new GridPane();

		Label animateLabel = new Label("Animation");
		animateLabel.setMinSize(((Main.SCREEN_WIDTH * (4 / 5)) / 2), (Main.SCREEN_HEIGHT / 10));
		animateLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");

		TextField animateInput = new TextField();
		animateInput.setMinSize((Main.SCREEN_WIDTH / 5) * 2.5, (Main.SCREEN_HEIGHT / 15));
		animateInput.setPromptText("Insert number of time steps ");
		animateInput.setAlignment(Pos.CENTER_RIGHT);

		Label perSecond = new Label("time steps per second");
		perSecond.setStyle("-fx-text-fill: #ecf0f1; -fx-font-family: Roboto; ");

		Button startAnimate = new Button("Start");
		startAnimate.setMinWidth(Main.SCREEN_WIDTH / 10);
		startAnimate.setMinHeight(Main.SCREEN_HEIGHT / 22);
		startAnimate.setStyle(Painter.LIGHTER_GREEN);
		startAnimate.setOnMouseEntered(value -> {
			startAnimate.setStyle(Painter.DARKER_GREEN);
		});
		startAnimate.setOnMouseExited(value -> {
			startAnimate.setStyle(Painter.LIGHTER_GREEN);
		});
		startAnimate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				num = Integer.parseInt(animateInput.getText());
				Painter.rate = num;
				started = 1;
				AnimationTimer timer = new MyTimer(num);
				startAnimate.setDisable(true);
				stopAnimate.setDisable(false);
				timer.start();

				critters.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
					@Override
					public void handle(ActionEvent event) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ANIMATION ERROR");
						alert.setHeaderText("Error: Animation still running");
						alert.setContentText("You cannot access this pane without stopping the animation first. "
								+ "Please stop the animation, then continue.");

						alert.show();
					}
				});
				params.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
					@Override
					public void handle(ActionEvent event) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ANIMATION ERROR");
						alert.setHeaderText("Error: Animation still running");
						alert.setContentText("You cannot access this pane without stopping the animation first. "
								+ "Please stop the animation, then continue.");

						alert.show();
					}
				});

			}
		});
		stopAnimate = new Button("Stop");
		stopAnimate.setDisable(true);
		stopAnimate.setMinWidth(Main.SCREEN_WIDTH / 10);
		stopAnimate.setMinHeight(Main.SCREEN_HEIGHT / 22);
		stopAnimate.setStyle(Painter.LIGHTER_GREEN);
		stopAnimate.setOnMouseEntered(value -> {
			stopAnimate.setStyle(Painter.DARKER_GREEN);
		});
		stopAnimate.setOnMouseExited(value -> {
			stopAnimate.setStyle(Painter.LIGHTER_GREEN);
		});
		stopAnimate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				started = 0;
				startAnimate.setDisable(false);
				stopAnimate.setDisable(true);
				critters.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) { // set normal functionality
						// TODO Auto-generated method stub
						Painter.critterUI(critters, editPane, world);
					}

				});
				params.setOnAction(new EventHandler<ActionEvent>() { // set normal functionality

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub

						Painter.paramsUI(params, editPane, world);
					}

				});
			}
		});
		animatePane.add(animateLabel, 0, 0);
		animatePane.add(animateInput, 0, 1);
		animatePane.add(perSecond, 1, 1);
		buttonPane.add(startAnimate, 0, 0);
		buttonPane.setHgap(5);
		buttonPane.add(stopAnimate, 1, 0);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);

		animatePane.add(buttonPane, 0, 2);
		animatePane.setHgap(25);
		animatePane.setVgap(10);

		HBox topHalf = new HBox(spacePane1, animatePane);

		editPane.add(topHalf, 0, 0);

		// If there is an animation already running
		if (started == 1) {
			System.out.println(num);
			startAnimate.setDisable(true);
			stopAnimate.setDisable(false);
		}
	}

	// Constructor if there is a running animation
	public AnimateUI(Button btn, GridPane editPane, GridPane world, Button critters, Button animate, Button stats,
			Button params, int num) {
		this.world = world; // pass reference to world grid pane so that we can update it
		editPane.getChildren().clear();
		editPane.setOnMouseEntered(value -> {
			btn.setStyle(Painter.LIGHTER_BLACK);
		});
		editPane.setOnMouseExited(value -> {
			btn.setStyle(Painter.DARKER_BLACK);
		});
		Pane spacePane1 = new Pane();
		spacePane1.setMinSize(Main.SCREEN_WIDTH / 16, Main.SCREEN_HEIGHT / 2);

		GridPane animatePane = new GridPane();
		GridPane buttonPane = new GridPane();

		Label animateLabel = new Label("Animation");
		animateLabel.setMinSize(((Main.SCREEN_WIDTH * (4 / 5)) / 2), (Main.SCREEN_HEIGHT / 10));
		animateLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");

		TextField animateInput = new TextField();
		animateInput.setMinSize((Main.SCREEN_WIDTH / 5) * 2.5, (Main.SCREEN_HEIGHT / 15));
		animateInput.setText(Integer.toString(num));
		animateInput.setAlignment(Pos.CENTER_RIGHT);

		Label perSecond = new Label("time steps per second");
		perSecond.setStyle("-fx-text-fill: #ecf0f1; -fx-font-family: Roboto; ");

		Button startAnimate = new Button("Start");
		startAnimate.setMinWidth(Main.SCREEN_WIDTH / 10);
		startAnimate.setMinHeight(Main.SCREEN_HEIGHT / 22);
		startAnimate.setStyle(Painter.LIGHTER_GREEN);
		startAnimate.setOnMouseEntered(value -> {
			startAnimate.setStyle(Painter.DARKER_GREEN);
		});
		startAnimate.setOnMouseExited(value -> {
			startAnimate.setStyle(Painter.LIGHTER_GREEN);
		});

		startAnimate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				num1 = Integer.parseInt(animateInput.getText());
				Painter.rate = num1;
				started = 1;
				AnimationTimer timer = new MyTimer(num1);
				startAnimate.setDisable(true);
				stopAnimate.setDisable(false);
				timer.start();

				critters.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
					@Override
					public void handle(ActionEvent event) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ANIMATION ERROR");
						alert.setHeaderText("Error: Animation still running");
						alert.setContentText("You cannot access this pane without stopping the animation first. "
								+ "Please stop the animation, then continue.");

						alert.show();
					}
				});
				params.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
					@Override
					public void handle(ActionEvent event) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ANIMATION ERROR");
						alert.setHeaderText("Error: Animation still running");
						alert.setContentText("You cannot access this pane without stopping the animation first. "
								+ "Please stop the animation, then continue.");

						alert.show();
					}
				});
			}
		});

		stopAnimate = new Button("Stop");
		stopAnimate.setDisable(true);
		stopAnimate.setMinWidth(Main.SCREEN_WIDTH / 10);
		stopAnimate.setMinHeight(Main.SCREEN_HEIGHT / 22);
		stopAnimate.setStyle(Painter.LIGHTER_GREEN);
		stopAnimate.setOnMouseEntered(value -> {
			stopAnimate.setStyle(Painter.DARKER_GREEN);
		});
		stopAnimate.setOnMouseExited(value -> {
			stopAnimate.setStyle(Painter.LIGHTER_GREEN);
		});
		stopAnimate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				started = 0;
				startAnimate.setDisable(false);
				stopAnimate.setDisable(true);
				critters.setOnAction(new EventHandler<ActionEvent>() { // set normal functionality

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						Painter.critterUI(critters, editPane, world);
					}

				});
				params.setOnAction(new EventHandler<ActionEvent>() { // set normal functionality

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub

						Painter.paramsUI(params, editPane, world);
					}

				});
			}
		});
		animatePane.add(animateLabel, 0, 0);
		animatePane.add(animateInput, 0, 1);

		animatePane.add(perSecond, 1, 1);

		buttonPane.add(startAnimate, 0, 0);
		buttonPane.setHgap(5);
		buttonPane.add(stopAnimate, 1, 0);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		animatePane.add(buttonPane, 0, 2);
		animatePane.setHgap(25);
		HBox topHalf = new HBox(spacePane1, animatePane);

		editPane.add(topHalf, 0, 0);

		// If there's an animation already running
		if (started == 1) {
			System.out.println(num);
			startAnimate.setDisable(true);
			stopAnimate.setDisable(false);
		}
	}

	// Custom class to handle animation and animation timer
	private class MyTimer extends AnimationTimer {

		int num;
		int currentTimer;

		public MyTimer(int n) {
			num = n;
			currentTimer = 0;
		}

		@Override
		public void handle(long now) {
			if (started == 0) {
				stop();
			}
			currentTimer++;
			if (currentTimer > 15) {
				doHandle();
				currentTimer = 0;
			}
		}

		private void doHandle() {
			Painter.paint(world);
			for (int i = 0; i < num; i++)
				Critter.worldTimeStep();
			StatsUI.updateStats();
		}
	}

}
