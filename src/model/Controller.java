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
    private final int BATTLEFIELDWIDTH = 10 ;
	
	//static ObservableList<MyRectangleUnit> sourceList = FXCollections.observableArrayList();
	public static MyRectangleUnit currentUnitSelected; 
	
	
    // armies
	private ArmyBuild firstArmy = Game.firstArmy;
	private ArmyBuild secondArmy = Game.secondArmy;
	//Thread
	MyThread autoPilotThread;
	
	//TODO add random coeficient for damage and show it in gui
	//TODO add as much eye candy as possible music more scenes etc.
	//TODO make lines array into array in Armybuild class and used foreach like below
//	ArrayList<ArrayList<Integer>> gigachad = new ArrayList<ArrayList<Integer>>();
//	
//	
//	gigachad.add(new ArrayList<Integer>());
//	gigachad.add(new ArrayList<Integer>());
//	gigachad.add(new ArrayList<Integer>());
//	gigachad.get(0).add((10));
//	gigachad.get(1).add(20);
//	gigachad.get(2).add(30);
//	gigachad.forEach((n) -> n.forEach((k) -> System.out.println(k)));
//	gigachad.forEach((n) -> n.forEach((k) -> System.out.println(k-10)));
	
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
		firstArmy.setFactionName(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase()));  	
	}
	
	public void setFaction_2(ActionEvent event) {
		// converts faction selected string to enum of factions
		secondArmy.setFactionName(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase())); 
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
				firstArmy.changeComposition(id, changeValue);
				
				if (firstArmy.getComp().summary() > 1 && changeValue == 1) { 
					firstArmy.changeComposition(id, -changeValue);
					break; }
				break;
				
			case("2"):
				secondArmy.changeComposition(id, changeValue);
				
				if (secondArmy.getComp().summary() > 1 && changeValue == 1) { 
					secondArmy.changeComposition(id, -changeValue);
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
					reset(); // cleanup before next simulation
					Game.createComposition();
					drawArmy(firstArmy);
					drawArmy(secondArmy);
					refreshDrawing();
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
				Game.battle(firstArmy, secondArmy, BATTLEFIELDWIDTH, fieldGrid);
				refreshBattlefield(); // update tooltips clear gird and redraw it
				setArmyLosses(); // count losses
				setArmyCounters(); // update army numbers
			}
		});	
	}
	private void refreshBattlefield() {
		
		//refresh tooltips
		firstArmy.getFirstLine().forEach((unit) -> unit.setTooltip());
		firstArmy.getSecondLine().forEach((unit) -> unit.setTooltip());
		secondArmy.getFirstLine().forEach((unit) -> unit.setTooltip());
		secondArmy.getSecondLine().forEach((unit) -> unit.setTooltip());
		//redraw battlefield
		fieldGrid.getChildren().clear();
		setBars();
		refreshDrawing();
		if(firstArmy.getFirstLine().isEmpty() || secondArmy.getFirstLine().isEmpty()) { 
			//TODO redo this
			boolean end = firstArmy.getFirstLine().isEmpty() ? outputManager.setTextmainTextArea(secondArmy) : outputManager.setTextmainTextArea(firstArmy);
			//fieldGrid.getChildren().clear();
			//run.fire();
		}
	}
	
	private void refreshDrawing() {
		for(int i = 0; i < firstArmy.getFirstLine().size(); i++) { fieldGrid.add(firstArmy.getFirstLine().get(i), 1, i, 1, 1); }
		for(int i = 0; i < firstArmy.getSecondLine().size(); i++) { fieldGrid.add(firstArmy.getSecondLine().get(i), 0, i, 1, 1); }
		
		for(int i = 0; i < secondArmy.getFirstLine().size(); i++) { fieldGrid.add(secondArmy.getFirstLine().get(i), 3, i, 1, 1); }
		for(int i = 0; i < secondArmy.getSecondLine().size(); i++) { fieldGrid.add(secondArmy.getSecondLine().get(i), 4, i, 1, 1); }
	}

	private void drawArmy(ArmyBuild army) {
		
		Units[][] units = army.getComp().getArmy().getUnits();
		
		int cavalryPlusInfantry = army.getComp().getArmy().getNumberOfUnits() - units[0].length;
		 
		// if there are more units than battlefield allows
		if(cavalryPlusInfantry > BATTLEFIELDWIDTH ) {
			
			for(int type = 0; type < units.length; type++) { // Add rectangles to units
				for (int regiment = 0; regiment < units[type].length; regiment++) {
					if(type == 0 && army.getSecondLine().size() < BATTLEFIELDWIDTH) {
						army.getSecondLine().add(new MyRectangleUnit(units[type][regiment]));
					}
					else if(type != 0 && army.getFirstLine().size() < BATTLEFIELDWIDTH) { // add to first line everyone who is not archer until first line is full
						army.getFirstLine().add(new MyRectangleUnit(units[type][regiment]));	
					} else { //add rest of cavalry/infantry/archers to reserve
						army.getReserveLine().add(new MyRectangleUnit(units[type][regiment]));
					}
				}
			}
		} else if (units[0].length > cavalryPlusInfantry) {
			int  archersToFirstLine = BATTLEFIELDWIDTH - cavalryPlusInfantry;
			for(int type = 0 ; type < units.length; type++) { // Add rectangles to units
				for (int regiment = 0; regiment < units[type].length; regiment++, archersToFirstLine--) {
					if(army.getFirstLine().size() < BATTLEFIELDWIDTH && (type != 0 || archersToFirstLine > 0)) { // add to first line everyone even archers until first line is full
						army.getFirstLine().add(new MyRectangleUnit(units[type][regiment]));
					} else if(type == 0 && army.getSecondLine().size() < BATTLEFIELDWIDTH) {
						army.getSecondLine().add(new MyRectangleUnit(units[type][regiment]));	
					} else { //add rest of cavalry/infantry/archers to reserve
						army.getReserveLine().add(new MyRectangleUnit(units[type][regiment]));
					}
				}
			}	
		} else { // first and second line is smaller than battle field width and there is less/equal archers than cav + int
			for(int type = 0; type < units.length; type++) { // Add rectangles to units
				for (int regiment = 0; regiment < units[type].length; regiment++) {
					if(type == 0) {
						army.getSecondLine().add(new MyRectangleUnit(units[type][regiment]));
					}
					else { // add to first line everyone who is not archer until first line is full
						army.getFirstLine().add(new MyRectangleUnit(units[type][regiment]));	
					}
				}
			}
			
		}
	}	
	
	public void setRandom(ActionEvent event) {
		
		//TODO add options for individual randomizing of armies, location ...
		Game.setRandomArmy(firstArmy);
		Game.setRandomArmy(secondArmy);
		setBars();
		setUnitsLabels();
		Game.createComposition();
		setArmyCounters();
		
	}
	 /**
	  * sets bars and bar labels to updated values
	  * */
	private void setBars() {
		armySummaryBar1.setProgress(firstArmy.getComp().summary());
		outputManager.setTextLabel_bar_1(Integer.toString((int)Math.round(armySummaryBar1.getProgress() * 100)) + " %");
		armySummaryBar2.setProgress(secondArmy.getComp().summary()); 
		outputManager.setTextLabel_bar_2(Integer.toString((int)Math.round(armySummaryBar2.getProgress() * 100)) + " %");
		
		armyMoraleBar1.setProgress(firstArmy.getComp().getArmy().getMorale());
		armyMoraleBar2.setProgress(secondArmy.getComp().getArmy().getMorale());
		
	}
	
	private void setUnitsLabels() {
		//TODO maybe try doing it 1 array
		Integer[] composition1 = new Integer[5]; // for updating labels
		Integer[] composition2 = new Integer[5];
		composition1 = firstArmy.getComp().getComposition();
		composition2 = secondArmy.getComp().getComposition();
		
		for(int i = 0; i < composition1.length; i++) {
			numberOfRegiments_1[i].setText(String.valueOf(composition1[i]));
			numberOfRegiments_2[i].setText(String.valueOf(composition2[i]));
		}
	}
	
	private void setArmyCounters() {
		outputManager.setTextArmyCount1(Integer.toString(firstArmy.getComp().getFactionCount()));
		outputManager.setTextArmyCount2(Integer.toString(secondArmy.getComp().getFactionCount()));
	}
	
	private void setArmyLosses() {
		outputManager.setTextArmyLosses1(
				Integer.toString(Integer.parseInt(outputManager.getTextArmyCount1())
						-
						firstArmy.getComp().getFactionCount()));
		outputManager.setTextArmyLosses2(
				Integer.toString(Integer.parseInt(outputManager.getTextArmyCount2())
						-
						secondArmy.getComp().getFactionCount()));
	}
	
	
	private void reset() {
		//remove units from previous armies
		firstArmy.resetLines();
		secondArmy.resetLines();
		// clear rectangles from battle field grid
		fieldGrid.getChildren().clear();
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
