package model;

import java.util.ArrayList;

import armies.ArmiesEnum;
import battle_phases.ArmyBuild;
import battle_phases.Battle;
import gui.MyRectangleUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Application {
	
	//static Stage window;
	//static Scene mainScene;
	static ArmyBuild army_1;
	static ArmyBuild army_2;
	
	private static Stage p;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			army_1 = new ArmyBuild(ArmiesEnum.FIRSTARMY);
			//army_1.chooseArmy("GUI");
			army_2 = new ArmyBuild(ArmiesEnum.SECONDARMY);
			//army_2.chooseArmy("GUI");
			
			Parent root = FXMLLoader.load(getClass().getResource("../gui/MainScreen.fxml"));	
			Scene scene = new Scene(root, 1280, 800);
			primaryStage.setTitle("Ancient Warfare - SIMULATOR");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			p = primaryStage;
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void reset() {
		p.show();
	}
	
	/**
	 * sets all configurable setting of an army to random values
	 * @param army
	 */
	public static void setRandomArmy (ArmyBuild army) { army.randomArmy(); }
	
	public static void createComposition() {
		army_1.chooseArmy();
		army_2.chooseArmy();
	}
	
	public static void battle(ArrayList<MyRectangleUnit> battleOrder1, ArrayList<MyRectangleUnit> battleOrder2, 
			int widthOfBattlefield, GridPane fieldGrid) {
		
		Battle battle = new Battle(battleOrder1, battleOrder2, widthOfBattlefield, fieldGrid);
		battle.fightMoment();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
