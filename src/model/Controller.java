package model;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import armies.ArmiesStatusEnum;
import armies.FactionEnum;
import java.util.List;
import battle_phases.ArmyBuild;
import battle_phases.FileControl;
import factors.Location;
import factors.LocationEnum;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import soldier_types.Units;

public class Controller implements Initializable {
	

	// Choice boxes variables
	@FXML private ChoiceBox<String> armyChoice_1,armyChoice_2;
	@FXML private ChoiceBox<String> weatherChoice;
	@FXML private ChoiceBox<String> locationChoice;
	
	// Buttons
	// Add or remove units from composition
	@FXML private Button archer_1, cavalry_1, heavy_1, pike_1, light_1;
	@FXML private Button archer_2, cavalry_2, heavy_2, pike_2, light_2;
	
	@FXML private Button start, randomButton, tick;
	
	@FXML
	private CheckBox run;
	
	// Sliders
	@FXML private Slider commanderSlider_1, commanderSlider_2, numberOfSimulationsSlider;
	
	// Progress bar
	@FXML private ProgressBar armySummaryBar1, armySummaryBar2, armyMoraleBar1,armyMoraleBar2;
	
	private int numberOfSimulations = 10;
	private int currentSimulation = 0;
	
	private Output outputManager;
	
	// Counter for added units
	@FXML private Label archer_number_1, archer_number_2, cavalry_number_1, cavalry_number_2,
	heavy_number_1, heavy_number_2, pike_number_1, pike_number_2, light_number_1, light_number_2;
	
	@FXML private Label labelStatus1, labelStatus2;
	
	@FXML private Label numberOfSimulationsLabel;
	
	private Label[] numberOfRegiments_1 = new Label[5];
	private Label[] numberOfRegiments_2 = new Label[5];
	
	List<Label> numberOfunits;
	
	@FXML private Label label_bar_1, label_bar_2, 
	armyCount1, armyCount2, armyLosses1, armyLosses2, lossesTotal1, lossesTotal2,
	factionDescription1, factionDescription2;
	
	// Images
	@FXML private ImageView weatherImage, locationImage;
	@FXML private MediaView mediaView;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	private File fileMusic;
	private Media music;
	private MediaPlayer musicPlayer;
	
	@FXML private GridPane fieldGrid;
    private final int BATTLEFIELDWIDTH = 20 ;
	
	//static ObservableList<MyRectangleUnit> sourceList = FXCollections.observableArrayList();
	public static MyRectangleUnit currentUnitSelected; 
	
	
    // armies
	private ArmyBuild firstArmy = Game.firstArmy;
	private ArmyBuild secondArmy = Game.secondArmy;
	
	//Thread
	private MyThread autoPilotThread;
	
	
	//TODO armies should be facing each other, they should be centered, this will break battle, another shift will be needed
	//TODO location should change batllewidth 
	//TODO add random coeficient for damage and show it in gui
	//TODO add as much eye candy as possible pictures, music more scenes etc.
	//TODO show end of batlle
	//TODO location should change battle field width
	//TODO adda more control like setting the max value of army (cost)
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
		factionDescription1.setText(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase()).getFactionDescription());
		
		armyChoice_2.setOnAction(this::setFaction_2);
		armyChoice_2.setValue(armyChoice_2.getItems().get(1));
		factionDescription2.setText(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase()).getFactionDescription());
				
		for(WeatherEnum weather : WeatherEnum.values()) { weatherChoice.getItems().add(weather.getTypeOfWeather()); }
		weatherChoice.setOnAction(this::setWeather);
		weatherChoice.setValue(weatherChoice.getItems().get(0));
		weatherImage.setImage(new Image(getClass().getResourceAsStream(WeatherEnum.valueOf(weatherChoice.getValue().toUpperCase()).getWeatherImageURL())));
		
		
		for(LocationEnum location : LocationEnum.values()) { locationChoice.getItems().add(location.getTypeOfLocation()); }
		locationChoice.setOnAction(this::setLocation);
		locationChoice.setValue(locationChoice.getItems().get(0));
		locationImage.setImage(new Image(getClass().getResourceAsStream(LocationEnum.valueOf(locationChoice.getValue().toUpperCase()).getLocationImageURL())));
		
		// Slider initialization
		
		numberOfSimulationsSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				numberOfSimulations = (int)numberOfSimulationsSlider.getValue();
				numberOfSimulationsLabel.setText(String.valueOf(numberOfSimulations));
			}
		});
		
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
		
		fileMusic = new File("media/Rome HQ - Rome Total War Original Soundtrack - Jeff van Dyck.mp4");
		//fileMusic = new File("media/Rome Total War - Rome Total War Original Soundtrack - Jeff van Dyck.mp4");
		
		music = new Media(fileMusic.toURI().toString());
		musicPlayer = new MediaPlayer(music);
		musicPlayer.setVolume(0.05);
		musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		musicPlayer.play();

		
		// output initialization
		outputManager = new Output(this, label_bar_1, label_bar_2, 
				armyCount1, armyCount2, armyLosses1, armyLosses2, 
				armySummaryBar1, armySummaryBar2, armyMoraleBar1, armyMoraleBar2,
				lossesTotal1, lossesTotal2);
		
		autoPilotThread = new MyThread("Automatic", tick);
		run.addEventFilter(ActionEvent.ACTION, new AutoPilotControl());
	}
	
	// Choice box event handlers
	public void setFaction_1(ActionEvent event) {
		firstArmy.setFactionName(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase())); 
		factionDescription1.setText(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase()).getFactionDescription());
	}
	
	public void setFaction_2(ActionEvent event) {
		secondArmy.setFactionName(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase())); 
		factionDescription2.setText(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase()).getFactionDescription());
	}
	
	public void setWeather(ActionEvent event) {
		Weather.setWeather(WeatherEnum.valueOf(weatherChoice.getValue().toUpperCase()));
		weatherImage.setImage(new Image(getClass().getResourceAsStream(WeatherEnum.valueOf(weatherChoice.getValue().toUpperCase()).getWeatherImageURL())));
	}
	
	public void setLocation(ActionEvent event) {
		Location.setLocation(LocationEnum.valueOf(locationChoice.getValue().toUpperCase()));
		locationImage.setImage(new Image(getClass().getResourceAsStream(LocationEnum.valueOf(locationChoice.getValue().toUpperCase()).getLocationImageURL())));
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
					firstArmy.setArmyStatus(ArmiesStatusEnum.FIGHTING);
					secondArmy.setArmyStatus(ArmiesStatusEnum.FIGHTING);
					refreshStatus();
					drawArmy(firstArmy);
					drawArmy(secondArmy);
					refreshDrawing();
					automaticFight(true);
					run.setSelected(true);
					tick.setDisable(false);
					run.setDisable(false);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("SOMETHING FAILED ATER START BUTTON WAS CLICKED!");
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
		checkPhase();
		
	}
	
	private void checkPhase() {
		if(firstArmy.getFirstLine().isEmpty())  { 
			firstArmy.setArmyStatus(ArmiesStatusEnum.LOST);
			secondArmy.setArmyStatus(ArmiesStatusEnum.WON);
			automaticFight(false);
			run.setSelected(false);
		} else if(secondArmy.getFirstLine().isEmpty()) {
			firstArmy.setArmyStatus(ArmiesStatusEnum.WON);
			secondArmy.setArmyStatus(ArmiesStatusEnum.LOST);
			automaticFight(false);
			run.setSelected(false);
		}
		
		refreshStatus();
	}
	
	private void refreshStatus() {
		labelStatus1.setText(firstArmy.getArmyStatus().toString());
		labelStatus2.setText(secondArmy.getArmyStatus().toString());
	}
	
	private void refreshDrawing() {
		for(int i = 0; i < firstArmy.getFirstLine().size(); i++) { fieldGrid.add(firstArmy.getFirstLine().get(i), i, 1, 1, 1); }
		for(int i = 0; i < firstArmy.getSecondLine().size(); i++) { fieldGrid.add(firstArmy.getSecondLine().get(i), i + 
				((firstArmy.getFirstLine().size()-firstArmy.getSecondLine().size())/2), 0, 1, 1); }
		
		for(int i = 0; i < secondArmy.getFirstLine().size(); i++) { fieldGrid.add(secondArmy.getFirstLine().get(i), i, 3, 1, 1); }
		for(int i = 0; i < secondArmy.getSecondLine().size(); i++) { fieldGrid.add(secondArmy.getSecondLine().get(i), i +
				((secondArmy.getFirstLine().size()-secondArmy.getSecondLine().size())/2), 4, 1, 1); }
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
			int  archersToFirstLine = units[0].length - cavalryPlusInfantry;
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
	
	public void simulation(ActionEvent event) {
		
		FileControl fileControl = new FileControl();
		
		int[] victories = new int[4];
		int civilWar = 0;
		
		for(currentSimulation = 1 ; currentSimulation < numberOfSimulations; currentSimulation++) {
			reset(); // clear previous  
			Game.setRandomArmy(firstArmy);
			Game.setRandomArmy(secondArmy);
			civilWar = firstArmy.getFactionName().equals(secondArmy.getFactionName()) ? civilWar++ : civilWar;
			//System.out.println(civilWar);
			Game.createComposition();
			drawArmy(firstArmy);
			drawArmy(secondArmy);
			
			do {
				Game.battle(firstArmy, secondArmy, BATTLEFIELDWIDTH);
			} while(!(firstArmy.getFirstLine().size() == 0 || secondArmy.getFirstLine().size() == 0));
			if(firstArmy.getFirstLine().size() == 0) { victories[firstArmy.getFaction().getFactionIndex()]++; }
			else { victories[secondArmy.getFaction().getFactionIndex()]++; }
			
		}
		fileControl.writeTheResult(civilWar, victories);
	}

	
	public void setRandom() {
		
		//TODO add options for individual randomizing of armies, location ...
		Game.setRandomArmy(firstArmy);
		Game.setRandomArmy(secondArmy);
		updateFactions();
		setBars();
		setUnitsLabels();
		Game.createComposition();
		refreshStatus();
		setArmyCounters();
		
	}
	
	private void updateFactions() {
		armyChoice_1.setValue(firstArmy.getFactionName().getNameOfFaction());
		factionDescription1.setText(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase()).getFactionDescription());
		armyChoice_2.setValue(secondArmy.getFactionName().getNameOfFaction());
		factionDescription2.setText(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase()).getFactionDescription());
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
		outputManager.setLossesTotal1(Integer.toString(Integer.parseInt(outputManager.getLossesTotal1())+
				Integer.parseInt(outputManager.getArmyLosses1Text())));
		outputManager.setLossesTotal2(Integer.toString(Integer.parseInt(outputManager.getLossesTotal2())+
				Integer.parseInt(outputManager.getArmyLosses2Text())));
	}
	
	
	private void reset() {
		//remove units from previous armies
		firstArmy.resetLines();
		secondArmy.resetLines();
		// clear rectangles from battle field grid
		fieldGrid.getChildren().clear();
		
		outputManager.setLossesTotal1("0");
		outputManager.setLossesTotal2("0");
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
	private void automaticFight(boolean run) {
		if (run) {
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
