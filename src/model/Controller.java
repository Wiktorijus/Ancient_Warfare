package model;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import armies.FactionEnum;
import java.util.List;
import battle_phases.ArmyBuild;
import factors.Location;
import factors.LocationEnum;
import factors.TimeOfDayEnum;
import factors.Weather;
import factors.WeatherEnum;
import gui.MyRectangleUnit;
import gui.Output;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import soldier_types.Units;
import soldier_types.UnitsStatusEnum;

public class Controller implements Initializable {
	

	// Choice boxes variables
	@FXML private ChoiceBox<String> armyChoice_1,armyChoice_2;
	@FXML private ChoiceBox<String> weatherChoice;
	@FXML private ChoiceBox<String> locationChoice;
	@FXML private ChoiceBox<String> timeOfDay;
	
	// Buttons
	// Add or remove units from composition
	@FXML private Button archer_1, cavalry_1, heavy_1, pike_1, light_1;
	@FXML private Button archer_2, cavalry_2, heavy_2, pike_2, light_2;
	
	@FXML Button start, randomButton;

	@FXML
	private Button tick;
	
	@FXML
	private CheckBox run;
	
	// Sliders
	@FXML private Slider commanderSlider_1, commanderSlider_2;
	
	// Progress bar
	@FXML private ProgressBar armySummaryBar1, armySummaryBar2, armyMoraleBar1,armyMoraleBar2;
	
	// Text area
	@FXML private TextArea mainTextArea;
	
	private Output outputManager;
	
	// Counter for added units
	@FXML private Label archer_number_1, archer_number_2, cavalry_number_1, cavalry_number_2,
	heavy_number_1, heavy_number_2, pike_number_1, pike_number_2, light_number_1, light_number_2; 
	
	private Label[] numberOfRegiments_1 = new Label[5];
	private Label[] numberOfRegiments_2 = new Label[5];
	
	List<Label> numberOfunits;
	
	@FXML private Label label_bar_1, label_bar_2, armyCount1, armyCount2, armyLosses1, armyLosses2;
	
	@FXML private MediaView mediaView;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	@FXML private GridPane fieldGrid;
	private final int NUMBEROFCOLUMNS = 5;
    private final int NUMBEROFROWS = 12 ;
	
	//static ObservableList<MyRectangleUnit> sourceList = FXCollections.observableArrayList();
	public static MyRectangleUnit currentUnitSelected; 
	private ArrayList<MyRectangleUnit> regimentsRectanglesArmy1 = new ArrayList<>();
	private ArrayList<MyRectangleUnit> regimentsRectanglesArmy2 = new ArrayList<>();
	
	//Thread
	MyThread autoPilotThread;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Choice box initialization
		for(FactionEnum faction : FactionEnum.values()) {
			armyChoice_1.getItems().add(faction.getNameOfFaction());
			armyChoice_2.getItems().add(faction.getNameOfFaction());
		}
		armyChoice_1.setOnAction(this::setFaction_1);
		armyChoice_1.setValue(armyChoice_1.getItems().get(0));
		armyChoice_2.setOnAction(this::setFaction_2);
		armyChoice_2.setValue(armyChoice_2.getItems().get(1));
				
		for(WeatherEnum weather : WeatherEnum.values()) { weatherChoice.getItems().add(weather.getTypeOfWeather()); }
		weatherChoice.setOnAction(this::setWeather);
		weatherChoice.setValue(weatherChoice.getItems().get(0));
		
		for(LocationEnum location : LocationEnum.values()) { locationChoice.getItems().add(location.getTypeOfLocation()); }
		locationChoice.setOnAction(this::setLocation);
		locationChoice.setValue(locationChoice.getItems().get(0));
		
		for(TimeOfDayEnum time : TimeOfDayEnum.values()) { timeOfDay.getItems().add(time.getTimeOfDay()); }
		timeOfDay.setOnAction(this::setTimeOfDay);
		timeOfDay.setValue(timeOfDay.getItems().get(0));
		
		// Slider initialization
//		Game.army_1.getLeader().setSkill((int)commanderSlider_1.getValue());
//		commanderSlider_1.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
//				// TODO Auto-generated method stub
//				Game.army_1.getLeader().setSkill((int)commanderSlider_1.getValue());
//				System.out.println(Game.army_1.getLeader().getSkill());
//			}
//		});
		
//		Game.army_2.getLeader().setSkill((int)commanderSlider_2.getValue());
//		commanderSlider_2.valueProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
//				// TODO Auto-generated method stub
//				Game.army_2.getLeader().setSkill((int)commanderSlider_2.getValue());
//			}
//		});
		
		// initialize array of labels for easier looping
		//TODO do it through this 
		//numberOfunits = new ArrayList<>();
		//numberOfunits.add(archer_number_1);
		
		numberOfRegiments_1[0] = archer_number_1;
		numberOfRegiments_1[1] = cavalry_number_1;
		numberOfRegiments_1[2] = heavy_number_1;
		numberOfRegiments_1[3] = pike_number_1;
		numberOfRegiments_1[4] = light_number_1;
		
		numberOfRegiments_2[0] = archer_number_2;
		numberOfRegiments_2[1] = cavalry_number_2;
		numberOfRegiments_2[2] = heavy_number_2;
		numberOfRegiments_2[3] = pike_number_2;
		numberOfRegiments_2[4] = light_number_2;
		
		
		
		// Media initialization
		//file = new File("media/Main Menu Background in 4K with Music.mp4");
		//file = new File("media/background_short_dark_blue.mp4");
		file = new File("media/Rome Total War background.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.setVolume(0);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();

		
		// output initialization
		outputManager = new Output(this, mainTextArea, label_bar_1, label_bar_2, armyCount1, armyCount2, armyLosses1, armyLosses2, armySummaryBar1, armySummaryBar2, armyMoraleBar1, armyMoraleBar2);
//        for (int i = 0 ; i < NUMBEROFCOLUMNS ; i++) {
//            for (int j = 0; j < NUMBEROFROWS; j++) {
//                addPane(i, j);
//            }
//        }
		autoPilotThread = new MyThread("Automatic", tick);
		run.addEventFilter(ActionEvent.ACTION, new AutoPilotControl());
	}
	
	// Choice box event handlers
	public void setFaction_1(ActionEvent event) {
		// converts faction selected string to enum of factions
		Game.army_1.setFactionName(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase()));  	
	}
	
	public void setFaction_2(ActionEvent event) {
		// converts faction selected string to enum of factions
		Game.army_2.setFactionName(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase())); 
	}
	
	public void setWeather(ActionEvent event) {
		ArmyBuild.chooseWeather(weatherChoice.getValue());
	}
	
	public void setLocation(ActionEvent event) {
		ArmyBuild.chooseLocation(locationChoice.getValue());
	}
	
	public void setTimeOfDay(ActionEvent event) {
		//TODO
		System.out.println("TODO night and day effect!");
	}
	
	// Button handlers
	
	public void addUnit(MouseEvent event) {
		
		// Get id of button
		final Node source = (Node) event.getSource();
		// holds information about button that was pressed
		String id = source.getId();
		
		int changeValue = 0; 
		
		if (event.getButton() == MouseButton.PRIMARY)
        {
			changeValue = 1;
        } else if (event.getButton() == MouseButton.SECONDARY)
        {
            changeValue = -1;
        }
				
		// Decide army gets new regiment was clicked and add regiment to army
		switch(id.substring(id.length()-1)) {
			case("1"):
				if (armySummaryBar1.getProgress() >= 1 && changeValue == 1) { break; }
				Game.army_1.changeComposition(id, changeValue);
				
				if (Game.army_1.getComp().summary() > 1 && changeValue == 1) { 
					Game.army_1.changeComposition(id, -changeValue);
					break; }
				break;
				
			case("2"):
				Game.army_2.changeComposition(id, changeValue);
				
				if (Game.army_2.getComp().summary() > 1 && changeValue == 1) { 
					Game.army_2.changeComposition(id, -changeValue);
					break; }
				break;
			
			default:
				System.out.println(((Button)event.getSource()).getText());
		}
		setUnitsLabels();
		Game.createComposition();
		setBars();
		setArmyCounters();
	}
	
	@SuppressWarnings("static-access")
	public void start(ActionEvent event) {	
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {	
					Game.createComposition();
					drawArmy();
					if(regimentsRectanglesArmy1.isEmpty() || regimentsRectanglesArmy2.isEmpty()) { 
						//TODO redo this
						boolean end = regimentsRectanglesArmy1.isEmpty() ? outputManager.setTextmainTextArea(Game.army_2) : outputManager.setTextmainTextArea(Game.army_1);
						//fieldGrid.getChildren().clear();
						//run.fire();
					}
					battleTick();
				} catch (Exception e) {
					e.printStackTrace();
					mainTextArea.setText("SOMETHING FAILED ATER START BUTTON WAS CLICKED!");
				}
			}
		});	
		
	}
	
	public void battleTick() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Game.battle(regimentsRectanglesArmy1, regimentsRectanglesArmy2, NUMBEROFROWS, fieldGrid);
				refreshBattlefield(); // update tooltips clear gird and redraw it
				setArmyLosses(); // count losses
				setArmyCounters(); // update army numbers
			}
		});	
	}
	private void refreshBattlefield() {
		
		//refresh tooltips
		for(MyRectangleUnit unit : regimentsRectanglesArmy1) { unit.setTooltip(); }
		for(MyRectangleUnit unit : regimentsRectanglesArmy2) { unit.setTooltip(); }
		//redraw battlefield
		fieldGrid.getChildren().clear();
		setBars();
		refreshDrawing();
		
		
	}
	
	private void refreshDrawing() {
		for(int i = 0; i < regimentsRectanglesArmy1.size(); i++) { fieldGrid.add(regimentsRectanglesArmy1.get(i), 1, i, 1, 1); }
		for(int i = 0; i < regimentsRectanglesArmy2.size(); i++) { fieldGrid.add(regimentsRectanglesArmy2.get(i), 3, i, 1, 1); }
	}

	private void drawArmy() {
		
		Units[][] units1 = Game.army_1.getComp().getArmy().getUnits();
		Units[][] units2 = Game.army_2.getComp().getArmy().getUnits();
		
			
		for(int type = 0; type < units1.length; type++) { // Add rectangles to units
			System.out.println(units1[type].length);
			if(units1[type] != null) {
				for (int regiment = 0; regiment < units1[type].length; regiment++) {
					if(units1[type][regiment].getStatus() != UnitsStatusEnum.RETREATED) { // dont add units that already retreated
						regimentsRectanglesArmy1.add(new MyRectangleUnit(units1[type][regiment]));
					}
				}
			}
		}	
		for(int type = 0; type < units2.length; type++) { // Add rectangles to units
			System.out.println(units2[type].length);
			if(units2[type] != null) {
				for (int regiment = 0; regiment < units2[type].length; regiment++) {
					if(units2[type][regiment].getStatus() != UnitsStatusEnum.RETREATED) {
						regimentsRectanglesArmy2.add(new MyRectangleUnit(units2[type][regiment]));	
					}
					
				}
				
			}
		}
		refreshDrawing(); 
	}
	
	public void setRandom(ActionEvent event) {
		
		//TODO add options for individual randomizing of armies, location ...
		Game.setRandomArmy(Game.army_1);
		Game.setRandomArmy(Game.army_2);
		setBars();
		setUnitsLabels();
		Game.createComposition();
		setArmyCounters();
		
	}
	 /**
	  * sets bars and bar labels to updated values
	  * */
	private void setBars() {
		armySummaryBar1.setProgress(Game.army_1.getComp().summary());
		outputManager.setTextLabel_bar_1(Integer.toString((int)Math.round(armySummaryBar1.getProgress() * 100)) + " %");
		armySummaryBar2.setProgress(Game.army_2.getComp().summary()); 
		outputManager.setTextLabel_bar_2(Integer.toString((int)Math.round(armySummaryBar2.getProgress() * 100)) + " %");
		
		armyMoraleBar1.setProgress(Game.army_1.getComp().getArmy().getMorale());
		armyMoraleBar2.setProgress(Game.army_2.getComp().getArmy().getMorale());
		
	}
	
	private void setUnitsLabels() {
		//TODO maybe try doing it 1 array
		Integer[] composition1 = new Integer[5]; // for updating labels
		Integer[] composition2 = new Integer[5];
		composition1 = Game.army_1.getComp().getComposition();
		composition2 = Game.army_2.getComp().getComposition();
		
		for(int i = 0; i < composition1.length; i++) {
			numberOfRegiments_1[i].setText(String.valueOf(composition1[i]));
			numberOfRegiments_2[i].setText(String.valueOf(composition2[i]));
		}
	}
	
	private void setArmyCounters() {
		outputManager.setTextArmyCount1(Integer.toString(Game.army_1.getComp().getFactionCount()));
		outputManager.setTextArmyCount2(Integer.toString(Game.army_2.getComp().getFactionCount()));
	}
	
	private void setArmyLosses() {
		outputManager.setTextArmyLosses1(
				Integer.toString(Integer.parseInt(outputManager.getTextArmyCount1())
						-
						Game.army_1.getComp().getFactionCount()));
		outputManager.setTextArmyLosses2(
				Integer.toString(Integer.parseInt(outputManager.getTextArmyCount2())
						-
						Game.army_2.getComp().getFactionCount()));
	}
	
	private class AutoPilotControl implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (run.isSelected()) {
				if (autoPilotThread.isAlive()) {
					autoPilotThread.running = true;
					synchronized (autoPilotThread) {
						autoPilotThread.notify();
					}
				} else {
					autoPilotThread.start();
				}

			} else {
				autoPilotThread.running = false;
			}
		}
	}
}
