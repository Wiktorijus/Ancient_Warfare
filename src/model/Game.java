package model;

import java.util.ArrayList;

import armies.ArmiesEnum;
import battle_phases.ArmyBuild;
import battle_phases.Battle;
import factors.Weather;
import gui.MyRectangleUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application {
	
	//static Stage window;
	//static Scene mainScene;
	static ArmyBuild firstArmy;
	static ArmyBuild secondArmy;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			firstArmy = new ArmyBuild(ArmiesEnum.FIRSTARMY);
			//army_1.chooseArmy("GUI");
			secondArmy = new ArmyBuild(ArmiesEnum.SECONDARMY);
			//army_2.chooseArmy("GUI");
			
			Parent root = FXMLLoader.load(getClass().getResource("../gui/MainScreen.fxml"));	
			Scene scene = new Scene(root);
			primaryStage.setTitle("Ancient Warfare");
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../gui/logo.png")));
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sets all configurable setting of an army to random values
	 * @param army
	 */
	public static void setRandomArmy (ArmyBuild army) { army.randomArmy(); }
	
	public static void createComposition() {
		firstArmy.chooseArmy();
		secondArmy.chooseArmy();
	}
	
	public static void battle(ArmyBuild firstArmy, ArmyBuild secondArmy, 
			int widthOfBattlefield, GridPane fieldGrid) {
		
		Battle battle = new Battle(firstArmy, secondArmy, widthOfBattlefield, fieldGrid);
		battle.fightMoment();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
