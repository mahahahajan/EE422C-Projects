package assignment5;

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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import assignment5.Critter.TestCritter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ControlsUI {

	/**
	 * @param btn      button that opens this UI (ex. Critters button for Controls)
	 * @param editPane GridPane to be edited
	 * @param world    GridPane containing the world
	 */
	public ControlsUI(Button btn, GridPane editPane, GridPane world) {
		editPane.getChildren().clear();
		editPane.setOnMouseEntered(value -> {
			btn.setStyle(Painter.LIGHTER_BLACK);
		});
		editPane.setOnMouseExited(value -> {
			btn.setStyle(Painter.DARKER_BLACK);
		});
		GridPane timeStepPane = new GridPane(); // Holds all elements related to making a time step
		GridPane setSeedPane = new GridPane(); // Holds all elements related to setting seed

		// Space Panes are created in order to create spacing
		Pane spacePane1 = new Pane();
		spacePane1.setMinSize(Main.SCREEN_WIDTH / 16, Main.SCREEN_HEIGHT / 2);

		Pane spacePane2 = new Pane();
		spacePane2.setMinSize(Main.SCREEN_WIDTH / 16, Main.SCREEN_HEIGHT / 2);

		// Creating nodes needed for UI
		Label timeStepLabel = new Label("Do Time Step");
		timeStepLabel.setMinSize(((Main.SCREEN_WIDTH * (4 / 5)) / 2), (Main.SCREEN_HEIGHT / 10));
		timeStepLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");

		Label setSeedLabel = new Label("Set Seed");
		setSeedLabel.setMinSize(((Main.SCREEN_WIDTH * (4 / 5)) / 2), (Main.SCREEN_HEIGHT / 10));
		setSeedLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");

		TextField timeStepInput = new TextField();
		timeStepInput.setMinSize((Main.SCREEN_WIDTH / 5) * 1.5, (Main.SCREEN_HEIGHT / 15));
		timeStepInput.setPromptText("Insert number of time steps");

		TextField setSeedInput = new TextField();
		setSeedInput.setMinSize((Main.SCREEN_WIDTH / 5) * 1.5, (Main.SCREEN_HEIGHT / 15));
		setSeedInput.setPromptText("Insert Seed");

		// Button to set seed
		Button executeSeed = new Button("Set seed");
		executeSeed.setMinHeight(Main.SCREEN_HEIGHT / 22);
		executeSeed.setStyle(Painter.LIGHTER_GREEN);
		// change color on hover
		executeSeed.setOnMouseEntered(value -> {
			executeSeed.setStyle(Painter.DARKER_GREEN);
		});
		executeSeed.setOnMouseExited(value -> {
			executeSeed.setStyle(Painter.LIGHTER_GREEN);
		});
		// behavior on click
		executeSeed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int num = Integer.parseInt(setSeedInput.getText());
				Critter.setSeed(num);
				setSeedInput.clear();
			}
		});

		// Button to do time step
		Button executeTime = new Button("Do time step(s)");
		executeTime.setMinHeight(Main.SCREEN_HEIGHT / 22);
		executeTime.setStyle(Painter.LIGHTER_GREEN);
		// change color on hover
		executeTime.setOnMouseEntered(value -> {
			executeTime.setStyle(Painter.DARKER_GREEN);
		});
		executeTime.setOnMouseExited(value -> {
			executeTime.setStyle(Painter.LIGHTER_GREEN);
		});
		// behavior on click
		executeTime.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int num = Integer.parseInt(timeStepInput.getText());
				for (int i = 0; i < num; i++) {
					Critter.worldTimeStep();
				}
				timeStepInput.clear();
				Painter.paint(world); // update world
			}
		});

		// Add all nodes to time step pane - creates the area we see for time step
		timeStepPane.add(timeStepLabel, 0, 0);
		timeStepPane.add(timeStepInput, 0, 1);
		timeStepPane.add(executeTime, 0, 2);

		// Add all nodes to set seed pane - creates the area we see for setting seeds
		setSeedPane.add(setSeedLabel, 0, 0);
		setSeedPane.add(setSeedInput, 0, 1);
		setSeedPane.add(executeSeed, 0, 2);

		// set alignments so that everything looks nice in time step
		GridPane.setHalignment(timeStepPane.getChildren().get(0), HPos.CENTER);
		GridPane.setHalignment(timeStepPane.getChildren().get(2), HPos.CENTER);
		GridPane.setValignment(timeStepPane.getChildren().get(2), VPos.BASELINE);

		// set alignments so that everything looks nice in time step
		GridPane.setHalignment(setSeedPane.getChildren().get(0), HPos.CENTER);
		GridPane.setHalignment(setSeedPane.getChildren().get(2), HPos.CENTER);
		GridPane.setValignment(setSeedPane.getChildren().get(2), VPos.BASELINE);

		timeStepPane.setVgap(Main.SCREEN_HEIGHT / 60);
		setSeedPane.setVgap(Main.SCREEN_HEIGHT / 60);

		timeStepPane.setAlignment(Pos.CENTER_RIGHT);
		setSeedPane.setAlignment(Pos.CENTER_RIGHT);

		// create the HBox and fill it with space pane and necessary panes
		HBox topHalf = new HBox(spacePane1, timeStepPane, spacePane2, setSeedPane);
		topHalf.setMinSize(Main.SCREEN_WIDTH * (4 / 5), Main.SCREEN_HEIGHT / 2);

		// creating the Make Critter area (follows similar processes as mentioned above)
		GridPane makeCritterPane = new GridPane();
		GridPane makeCritterPaneInner = new GridPane();

		makeCritterPaneInner.setHgap(5);
		Pane spacePane3 = new Pane();
		spacePane3.setMinSize(Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT / 2);
		Pane spacePane4 = new Pane();
		spacePane4.setMinSize(Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT / 2);

		Label makeCritterLabel = new Label("Make Critter");
		makeCritterLabel.setMinSize(((Main.SCREEN_WIDTH * (4 / 5)) / 2), (Main.SCREEN_HEIGHT / 10));
		makeCritterLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");

		// creates a drop down menu that has all the Critters in the package
		ComboBox<String> critterDropDown = new ComboBox<String>();
		List<Class<?>> classes = getallclasses();
		for (Class<?> c : classes) {
			if (!c.getSimpleName().equals("TestCritter") && c.getSuperclass().getName().equals(Critter.class.getName())
					|| c.getSuperclass().getName().equals(TestCritter.class.getName())) {
				critterDropDown.getItems().add(c.getSimpleName()); // add names of critters to drop down menu
			}
		}
		// Default option as well as correct sizes
		critterDropDown.setEditable(false);
		critterDropDown.setValue(critterDropDown.getItems().get(0));
		critterDropDown.setMinWidth((Main.SCREEN_HEIGHT / 8));
		critterDropDown.setMinHeight((Main.SCREEN_HEIGHT / 16));

		TextField critterInput = new TextField();
		critterInput.setMinSize((Main.SCREEN_WIDTH / 5) * 1.5, (Main.SCREEN_HEIGHT / 15));
		critterInput.setPromptText("Insert number of critters");

		Button executeCritter = new Button("Make Critter(s)");
		executeCritter.setMinHeight(Main.SCREEN_HEIGHT / 22);
		executeCritter.setStyle(Painter.LIGHTER_GREEN);
		executeCritter.setOnMouseEntered(value -> {
			executeCritter.setStyle(Painter.DARKER_GREEN);
		});
		executeCritter.setOnMouseExited(value -> {
			executeCritter.setStyle(Painter.LIGHTER_GREEN);
		});
		// sets behavior on Button click
		executeCritter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int num = Integer.parseInt(critterInput.getText());
				for (int i = 0; i < num; i++) {
					try {
						Critter.createCritter(critterDropDown.getValue());
					} catch (InvalidCritterException e) {

					}
				}
				critterInput.clear();
				Painter.paint(world);
			}
		});
		// Add Make critter stuff to bottom half HBox
		HBox bottomHalf = new HBox(makeCritterPane);
		bottomHalf.setMinWidth(Main.SCREEN_WIDTH * 4 / 5);
		bottomHalf.setMinHeight(Main.SCREEN_HEIGHT / 2.25);
		bottomHalf.setAlignment(Pos.CENTER);
		makeCritterPane.add(makeCritterLabel, 0, 0);
		makeCritterPaneInner.add(critterInput, 0, 0);
		makeCritterPaneInner.add(critterDropDown, 1, 0);
		makeCritterPane.add(makeCritterPaneInner, 0, 1);
		makeCritterPane.add(executeCritter, 0, 2);

		// Align nodes to make everything look nice
		GridPane.setHalignment(makeCritterPane.getChildren().get(0), HPos.CENTER);
		GridPane.setHalignment(makeCritterPane.getChildren().get(2), HPos.CENTER);

		makeCritterPane.setVgap(Main.SCREEN_HEIGHT / 60);
		makeCritterPane.setHgap(Main.SCREEN_WIDTH / 60);

		editPane.add(topHalf, 0, 0);
		editPane.add(bottomHalf, 0, 1);
	}

	// Method to get classes of critters
	static List<Class<?>> getallclasses() {
		String file_path = "assignment5";
		List<Class<?>> classes = new ArrayList<>();
		String[] classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
		String name;
		for (String classpathEntry : classPathEntries) {
			if (classpathEntry.endsWith(".jar")) {
				File jar = new File(classpathEntry);
				try {
					JarInputStream is = new JarInputStream(new FileInputStream(jar));
					JarEntry entry;
					while ((entry = is.getNextJarEntry()) != null) {
						name = entry.getName();
						if (name.endsWith(".class")) {
							if (name.contains(file_path) && name.endsWith(".class")) {
								String classPath = name.substring(0, entry.getName().length() - 6);
								classPath = classPath.replaceAll("[\\|/]", ".");
								classes.add(Class.forName(classPath));
							}
						}
					}
					is.close();
				} catch (Exception e) {

				}
			} else {
				try {
					File base = new File(classpathEntry + File.separatorChar + file_path);
					for (File file : base.listFiles()) {
						name = file.getName();
						if (name.endsWith(".class")) {
							name = name.substring(0, name.length() - 6);
							classes.add(Class.forName(file_path + "." + name));
						}
					}
				} catch (Exception e) {

				}
			}
		}
		return classes;
	}

}
