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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


/*
 *  public static int WORLD_WIDTH = 10;
    public static int WORLD_HEIGHT = 10;
    public static int WALK_ENERGY_COST = 2;
    public static int RUN_ENERGY_COST = 5;
    public static int REST_ENERGY_COST = 1;
    public static int MIN_REPRODUCE_ENERGY = 20;
    public static int REFRESH_CLOVER_COUNT = 10;
    public static int PHOTOSYNTHESIS_ENERGY_AMOUNT = 1;
    public static int START_ENERGY = 100;

    public static int LOOK_ENERGY_COST = 1;
 */

public class ParamsUI {
	
	//Constructor of ParamsUI - creates the Parameter Pane
	public ParamsUI(Button btn, GridPane editPane, GridPane world) {
		String costLabelStyle = "-fx-text-fill: #ecf0f1; -fx-font-size: 115%; -fx-font-family: Roboto; ";
		
		editPane.getChildren().clear();
		editPane.setOnMouseEntered(value -> {
			btn.setStyle(Painter.LIGHTER_BLACK);
		});
		editPane.setOnMouseExited(value -> {
			btn.setStyle(Painter.DARKER_BLACK);
		});
		//space panes
		Pane spacePane1 = new Pane();
		spacePane1.setMinSize(Main.SCREEN_WIDTH/20, Main.SCREEN_HEIGHT/2);
		Pane spacePane2 = new Pane();
		spacePane2.setMinSize(Main.SCREEN_WIDTH/60, Main.SCREEN_HEIGHT/2);
		
		GridPane paramsPane = new GridPane();
		paramsPane.setHgap(10);
		
		Label paramsLabel = new Label("World Params");
		paramsLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 185%; -fx-font-family: Roboto; ");
		
		/*
		 * This pattern is repeated for each parameter
		 * Label is created
		 * TextField is created
		 * Button is created
		 * set PromptText to old parameter value
		 * On button click -> change parameter value, clear TextField, update PromptText
		 */
		Label walkCostLabel = new Label("Walk Energy Cost:");
		walkCostLabel.setStyle(costLabelStyle);
		walkCostLabel.setWrapText(true);
		
		TextField walkCostInput = new TextField();
		walkCostInput.setPromptText(Integer.toString(Params.WALK_ENERGY_COST));
		walkCostInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button changeWalkCost = new Button("Change Value");
		changeWalkCost.setMinHeight(Main.SCREEN_HEIGHT/22);
		changeWalkCost.setMinWidth(Main.SCREEN_WIDTH/8);
		changeWalkCost.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Params.WALK_ENERGY_COST = Integer.parseInt(walkCostInput.getText());
				walkCostInput.clear();
				walkCostInput.setPromptText(Integer.toString(Params.WALK_ENERGY_COST));
			}

		});
		changeWalkCost.setStyle(Painter.LIGHTER_GREEN);
		changeWalkCost.setOnMouseEntered(value->{
			changeWalkCost.setStyle(Painter.DARKER_GREEN);
		});
		changeWalkCost.setOnMouseExited(value->{
			changeWalkCost.setStyle(Painter.LIGHTER_GREEN);
		});
		Label runCostLabel = new Label("Run Energy Cost:");
		runCostLabel.setStyle(costLabelStyle);
		runCostLabel.setWrapText(true);
		
		TextField runCostInput = new TextField();
		runCostInput.setPromptText(Integer.toString(Params.RUN_ENERGY_COST));
		runCostInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button changeRunCost = new Button("Change Value");
		changeRunCost.setMinHeight(Main.SCREEN_HEIGHT/22);
		changeRunCost.setStyle(Painter.LIGHTER_GREEN);
		changeRunCost.setOnMouseEntered(value->{
			changeRunCost.setStyle(Painter.DARKER_GREEN);
		});
		changeRunCost.setOnMouseExited(value->{
			changeRunCost.setStyle(Painter.LIGHTER_GREEN);
		});
		changeRunCost.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Params.RUN_ENERGY_COST = Integer.parseInt(runCostInput.getText());
				runCostInput.clear();
				runCostInput.setPromptText(Integer.toString(Params.RUN_ENERGY_COST));
			}

		});
		Label restCostLabel = new Label("Rest Energy Cost:");
		restCostLabel.setStyle(costLabelStyle);
		restCostLabel.setWrapText(true);
		
		TextField restCostInput = new TextField();
		restCostInput.setPromptText(Integer.toString(Params.REST_ENERGY_COST));
		restCostInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button changeRestCost = new Button("Change Value");
		changeRestCost.setMinHeight(Main.SCREEN_HEIGHT/22);
		changeRestCost.setStyle(Painter.LIGHTER_GREEN);
		changeRestCost.setOnMouseEntered(value->{
			changeRestCost.setStyle(Painter.DARKER_GREEN);
		});
		changeRestCost.setOnMouseExited(value->{
			changeRestCost.setStyle(Painter.LIGHTER_GREEN);
		});
		changeRestCost.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Params.REST_ENERGY_COST = Integer.parseInt(restCostInput.getText());
				restCostInput.clear();
				restCostInput.setPromptText(Integer.toString(Params.REST_ENERGY_COST));
			}
		});
		
		Label reproduceLabel = new Label("Minimum neccessary reproduction energy: ");
		reproduceLabel.setWrapText(true);
		reproduceLabel.setStyle(costLabelStyle);
		
		TextField reproduceInput = new TextField();
		reproduceInput.setPromptText(Integer.toString(Params.MIN_REPRODUCE_ENERGY));
		reproduceInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button changeReproduce = new Button("Change Value");
		changeReproduce.setMinHeight(Main.SCREEN_HEIGHT/22);
		changeReproduce.setStyle(Painter.LIGHTER_GREEN);
		changeReproduce.setOnMouseEntered(value->{
			changeReproduce.setStyle(Painter.DARKER_GREEN);
		});
		changeReproduce.setOnMouseExited(value->{
			changeReproduce.setStyle(Painter.LIGHTER_GREEN);
		});
		changeReproduce.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Params.MIN_REPRODUCE_ENERGY = Integer.parseInt(reproduceInput.getText());
				reproduceInput.clear();
				reproduceInput.setPromptText(Integer.toString(Params.MIN_REPRODUCE_ENERGY));
			}
		});
		Label cloverLabel = new Label("Clovers created per timestep: ");
		cloverLabel.setStyle(costLabelStyle);
		cloverLabel.setWrapText(true);
		
		TextField cloverInput = new TextField();
		cloverInput.setPromptText(Integer.toString(Params.REFRESH_CLOVER_COUNT));
		cloverInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button cloverButton = new Button("Change Value");
		cloverButton.setMinHeight(Main.SCREEN_HEIGHT/22);
		cloverButton.setStyle(Painter.LIGHTER_GREEN);
		cloverButton.setOnMouseEntered(value->{
			cloverButton.setStyle(Painter.DARKER_GREEN);
		});
		cloverButton.setOnMouseExited(value->{
			cloverButton.setStyle(Painter.LIGHTER_GREEN);
		});
		cloverButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Params.REFRESH_CLOVER_COUNT = Integer.parseInt(cloverInput.getText());
				cloverInput.clear();
				cloverInput.setPromptText(Integer.toString(Params.REFRESH_CLOVER_COUNT));
			}
		});
		Label photoSynthLabel = new Label("Amount of Energy gained from Photosynthesis: ");
		photoSynthLabel.setStyle(costLabelStyle);
		photoSynthLabel.setWrapText(true);
		
		TextField photoSynthInput = new TextField();
		photoSynthInput.setPromptText(Integer.toString(Params.PHOTOSYNTHESIS_ENERGY_AMOUNT));
		photoSynthInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button photoSynthButton = new Button("Change Value");
		photoSynthButton.setMinHeight(Main.SCREEN_HEIGHT/22);
		photoSynthButton.setStyle(Painter.LIGHTER_GREEN);
		photoSynthButton.setOnMouseEntered(value->{
			photoSynthButton.setStyle(Painter.DARKER_GREEN);
		});
		photoSynthButton.setOnMouseExited(value->{
			photoSynthButton.setStyle(Painter.LIGHTER_GREEN);
		});
		photoSynthButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = Integer.parseInt(photoSynthInput.getText());
				photoSynthInput.clear();
				photoSynthInput.setPromptText(Integer.toString(Params.PHOTOSYNTHESIS_ENERGY_AMOUNT));
			}
		});
		
		Label startLabel = new Label("Start Energy: ");
		startLabel.setStyle(costLabelStyle);
		
		TextField startInput = new TextField();
		startInput.setPromptText(Integer.toString(Params.START_ENERGY));
		startInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button startCostButton = new Button("Change Value");
		startCostButton.setMinHeight(Main.SCREEN_HEIGHT/22);
		startCostButton.setStyle(Painter.LIGHTER_GREEN);
		startCostButton.setOnMouseEntered(value->{
			startCostButton.setStyle(Painter.DARKER_GREEN);
		});
		startCostButton.setOnMouseExited(value->{
			startCostButton.setStyle(Painter.LIGHTER_GREEN);
		});
		
		startCostButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Params.START_ENERGY = Integer.parseInt(startInput.getText());
				startInput.clear();
				startInput.setPromptText(Integer.toString(Params.START_ENERGY));
			}
		});
		
		Label lookLabel = new Label("Look energy: ");
		lookLabel.setStyle(costLabelStyle);
		
		TextField lookInput = new TextField();
		lookInput.setPromptText(Integer.toString(Params.LOOK_ENERGY_COST));
		lookInput.setAlignment(Pos.CENTER_RIGHT);
		
		Button lookButton = new Button("Change Value");
		lookButton.setMinHeight(Main.SCREEN_HEIGHT/22);
		lookButton.setStyle(Painter.LIGHTER_GREEN);
		lookButton.setOnMouseEntered(value->{
			lookButton.setStyle(Painter.DARKER_GREEN);
		});
		lookButton.setOnMouseExited(value->{
			lookButton.setStyle(Painter.LIGHTER_GREEN);
		});
		lookButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Params.LOOK_ENERGY_COST = Integer.parseInt(lookInput.getText());
				lookInput.clear();
				lookInput.setPromptText(Integer.toString(Params.LOOK_ENERGY_COST));
			}
		});
		//Add everything to the params Pane
		paramsPane.setVgap(15);
		paramsPane.add(paramsLabel, 0, 0);
		
		paramsPane.add(walkCostLabel, 0, 1);
		paramsPane.add(walkCostInput, 1, 1);
		paramsPane.add(changeWalkCost, 2, 1);
		
		paramsPane.add(runCostLabel, 0, 2);
		paramsPane.add(runCostInput, 1, 2);
		paramsPane.add(changeRunCost, 2, 2);
		
		paramsPane.add(restCostLabel, 0, 3);
		paramsPane.add(restCostInput, 1, 3);
		paramsPane.add(changeRestCost, 2, 3);
		
		paramsPane.add(reproduceLabel, 0, 4);
		paramsPane.add(reproduceInput, 1, 4);
		paramsPane.add(changeReproduce, 2, 4);
		
		paramsPane.add(cloverLabel, 0, 5);
		paramsPane.add(cloverInput, 1, 5);
		paramsPane.add(cloverButton, 2, 5);
		
		paramsPane.add(photoSynthLabel, 0, 6);
		paramsPane.add(photoSynthInput, 1, 6);
		paramsPane.add(photoSynthButton, 2, 6);
		
		paramsPane.add(startLabel, 0, 7);
		paramsPane.add(startInput, 1, 7);
		paramsPane.add(startCostButton, 2, 7);
		
		paramsPane.add(lookLabel, 0, 8);
		paramsPane.add(lookInput, 1, 8);
		paramsPane.add(lookButton, 2, 8);
		
		HBox topHalf = new HBox(spacePane1, paramsPane, spacePane2); 
		
		editPane.add(topHalf, 0, 0);
		//We were too lazy to change the label size ourselves (b/c we wrote everything above first
		//For loop that changes the sizes of buttons and labels for us
		for(int i = 0; i < paramsPane.getChildren().size(); i++) {
			if(GridPane.getColumnIndex(paramsPane.getChildren().get(i)) == 2) {
				((Button) paramsPane.getChildren().get(i)).setMinWidth(Main.SCREEN_WIDTH* 3/20);
			}
			if(GridPane.getColumnIndex(paramsPane.getChildren().get(i)) == 1) {
				((TextField) paramsPane.getChildren().get(i)).setMinWidth(Main.SCREEN_WIDTH* 3/10);
			}
		}
		
	}
}
