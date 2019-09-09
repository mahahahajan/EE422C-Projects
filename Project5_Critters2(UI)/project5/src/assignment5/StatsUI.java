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

import java.util.ArrayList;
import java.util.List;

import assignment5.Critter.TestCritter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class StatsUI {

	public static int counter = 1;
	public static GridPane statsPane;
	public static ArrayList<Label> labelList = new ArrayList<Label>();
	public static ArrayList<String> critterNameList = new ArrayList<String>();
	public String statsLabelStyle = "-fx-text-fill: white; -fx-font-size: 145%; ";
	public String statsCheckStyle = "-fx-text-fill: white; -fx-font-size: 145%; -fx-body-color: #2ecc71;"
			+ " -fx-focus-color: white; -fx-inner-border: #27ae60; -fx-body-fill: white ";

	// Constructor for Stats UI
	public StatsUI(Button btn, GridPane editPane, GridPane world) {
		counter = 1; // solves issue of stats slowly moving down the page
		editPane.getChildren().clear();
		editPane.setOnMouseEntered(value -> {
			btn.setStyle(Painter.LIGHTER_BLACK);
		});
		editPane.setOnMouseExited(value -> {
			btn.setStyle(Painter.DARKER_BLACK);
		});
		// Spacing panes
		Pane spacePane1 = new Pane();
		spacePane1.setMinSize(Main.SCREEN_WIDTH / 20, Main.SCREEN_HEIGHT / 2);

		// Pane where information will be displayed
		GridPane statsPane = new GridPane();
		StatsUI.statsPane = statsPane;
		statsPane.setHgap(10);

		Label statsLabel = new Label("Run Stats");
		statsLabel.setMinSize(((Main.SCREEN_WIDTH * (4 / 5)) / 2), (Main.SCREEN_HEIGHT / 10));
		statsLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");

		statsPane.add(statsLabel, 0, 0);
		statsPane.setVgap(Main.SCREEN_WIDTH / 80);
		List<Class<?>> classes = ControlsUI.getallclasses(); // find all critters
		List<Critter> critterList = null;
		for (Class<?> c : classes) { // for each critter
			if (!c.getName().equals("assignment5.Critter$TestCritter")
					&& c.getSuperclass().getName().equals(Critter.class.getName())
					|| c.getSuperclass().getName().equals(TestCritter.class.getName())) {
				CheckBox critter = new CheckBox();
				critterNameList.add(c.getSimpleName()); // add critter name to list
				critter.setText(c.getSimpleName()); // set Checkbox text
				critter.setStyle(statsLabelStyle);
				try {
					critterList = Critter.getInstances(c.getSimpleName());
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Label critterStats = new Label(); // create Label to hold String stats
				if (c.getSimpleName().equals("Goblin")) {
					critterStats.setText(Goblin.runStats(critterList));
					System.out.println(Goblin.runStats(critterList));
				} else {
					critterStats.setText(Critter.runStats(critterList));
				}
				critterStats.setVisible(false);
				critterStats.setStyle(statsLabelStyle);
				labelList.add(critterStats);
				statsPane.add(critter, 0, counter);
				statsPane.add(critterStats, 1, counter);
				counter++; // increment row index so the next nodes are one row lower than the previous
				critter.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// check if checkmark is clicked or not
						if (critter.isSelected()) { // if clicked - show label
							critter.setStyle(statsCheckStyle);
							critterStats.setVisible(true);
						} else { // don't show label
							critterStats.setVisible(false);
							critter.setStyle(statsLabelStyle);
						}
					}
				});
			}
		}
		// add everything to the view
		HBox topHalf = new HBox(spacePane1, statsPane);
		editPane.add(topHalf, 0, 0);
		updateStats();
	}

	// Method that keeps updating the stats by changing the text of labels
	public static void updateStats() {
		List<Critter> critterList = null;
		for (int i = 0; i < labelList.size(); i++) {
			try {
				critterList = Critter.getInstances(critterNameList.get(i));
			} catch (InvalidCritterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (critterList.size() > 0 && critterList.get(0).getClass().getSimpleName().equals("Goblin")) {
				labelList.get(i).setText(Goblin.runStats(critterList));
			} else
				labelList.get(i).setText(Critter.runStats(critterList));
		}
	}

}
