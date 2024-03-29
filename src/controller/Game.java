package controller;

import java.util.ArrayList;

import armies.ArmiesEnum;
import armies.ArmiesStatusEnum;
import battle_phases.ArmyBuild;
import battle_phases.Battle;
import factors.Weather;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.MyRectangleUnit;

public class Game extends Application {
	
	//static Stage window;
	//static Scene mainScene;
	static ArmyBuild firstArmy;
	static ArmyBuild secondArmy;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			Image image = new Image(getClass().getResource("../view/cursor_sword.png").toExternalForm());

			
			firstArmy = new ArmyBuild(ArmiesEnum.FIRSTARMY, true);
			//army_1.chooseArmy("GUI");
			secondArmy = new ArmyBuild(ArmiesEnum.SECONDARMY, false);
			//army_2.chooseArmy("GUI");
			
			Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));	
			
			root.setCursor(new ImageCursor(image,
                    image.getWidth() / 32,
                    image.getHeight() /32));
			Scene scene = new Scene(root);
			
			primaryStage.setResizable(false);
			//primaryStage.setFullScreen(true);
			
			scene.getStylesheets().add(getClass().getResource("../view/mainScene.css").toExternalForm());
			primaryStage.setTitle("Ancient Warfare");
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../view/logo.png")));
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
		firstArmy.setArmyStatus(ArmiesStatusEnum.PREPARING);
		secondArmy.setArmyStatus(ArmiesStatusEnum.PREPARING);
	}
	
	public static void battle(ArmyBuild firstArmy, ArmyBuild secondArmy, 
			int widthOfBattlefield, GridPane fieldGrid) {
		
		Battle battle = new Battle(firstArmy, secondArmy, widthOfBattlefield, fieldGrid);
		battle.fightMoment();
	}
	
	public static void battle(ArmyBuild firstArmy2, ArmyBuild secondArmy2, int widthOfBattlefield) {
		Battle battle = new Battle(firstArmy, secondArmy, widthOfBattlefield);
		battle.simulation();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	

	
}
