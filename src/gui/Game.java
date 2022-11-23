package gui;

import battle_phases.ArmyBuild;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {
	
	//static Stage window;
	//static Scene mainScene;
	static ArmyBuild army_1;
	static ArmyBuild army_2;
	
	@FXML Button bestButton = new Button();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			army_1 = new ArmyBuild();
			//army_1.chooseArmy("GUI");
			army_2 = new ArmyBuild();
			//army_2.chooseArmy("GUI");
			
			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));	
			Scene scene = new Scene(root, 1280, 800);
			primaryStage.setTitle("Ancient Warfare - SIMULATOR");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/**window = stage;
		
		
		VBox mainRoot = new VBox();
		HBox root = new HBox();
		root.setAlignment(Pos.CENTER);
		//root.setPadding(new Insets(20));
		root.setId("mainscene");
		//root.setVisible(true);
		
		VBox leftSide = new VBox();
		VBox middle = new VBox();
		VBox rightSide = new VBox();
		
		root.getChildren().addAll(leftSide, middle, rightSide);
		
		// left part of main screen
		ObservableList<String> factionNames = FXCollections.observableArrayList("Carthagian", "Celtic", "Greek", "Roman");
		ChoiceBox<String> firstFaction = new ChoiceBox<String>(factionNames);
		
		leftSide.getChildren().add(firstFaction);
		
		// middle part of main screen
		Button startButton = new Button();
		
		middle.getChildren().add(startButton);	
		// right part of main screen
		ChoiceBox<String> secondFaction = new ChoiceBox<String>(factionNames);
		rightSide.getChildren().add(secondFaction);
		
		mainScene = new Scene(root);
		
		window.setTitle("Ancient Military Simulator");
		window.setScene(mainScene);
		
		window.show();
		*/
	}
	
	/**
	 * sets all configurable setting of an army to random values
	 * @param army
	 */
	public static void setRandomArmy (ArmyBuild army) { army.randomArmy(); }
	
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
