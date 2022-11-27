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
	
	private static PrintWriter pw = new PrintWriter(System.out, true);

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
	private CheckBox random_army_2, run;
	
	// Sliders
	@FXML private Slider commanderSlider_1, commanderSlider_2;
	
	// Progress bar
	@FXML private ProgressBar bar_1, bar_2;
	
	// Text area
	@FXML private TextArea mainTextArea;
	
	private Output outputManager;
	
	// Counter for added units
	@FXML private Label archer_number_1, archer_number_2, cavalry_number_1, cavalry_number_2,
	heavy_number_1, heavy_number_2, pike_number_1, pike_number_2, light_number_1, light_number_2; 
	
	@FXML private Label[] numberOfRegiments_1 = new Label[5];
	private Label[] numberOfRegiments_2 = new Label[5];
	
	List<Label> numberOfunits;
	
	@FXML private Label label_bar_1, label_bar_2;
	
	@FXML private MediaView mediaView;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	@FXML private Canvas canvas;
	
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
		Game.army_1.getLeader().setSkill((int)commanderSlider_1.getValue());
		commanderSlider_1.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				Game.army_1.getLeader().setSkill((int)commanderSlider_1.getValue());
				System.out.println(Game.army_1.getLeader().getSkill());
			}
		});
		
		Game.army_2.getLeader().setSkill((int)commanderSlider_2.getValue());
		commanderSlider_2.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				Game.army_2.getLeader().setSkill((int)commanderSlider_2.getValue());
			}
		});
		
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
		outputManager = new Output(mainTextArea);
//        for (int i = 0 ; i < NUMBEROFCOLUMNS ; i++) {
//            for (int j = 0; j < NUMBEROFROWS; j++) {
//                addPane(i, j);
//            }
//        }
		autoPilotThread = new MyThread("Automatic", tick);
		run.addEventFilter(ActionEvent.ACTION, new AutoPilotControl());
	}
	//TODO this might not be used
//	private void addPane(int colIndex, int rowIndex) {
//        Pane pane = new Pane();
//        pane.setOnMouseClicked(e -> {
//            System.out.printf("Mouse clicked cell [%d, %d]%n", colIndex, rowIndex);
//            moveUnit(colIndex, rowIndex);
//        });
//        fieldGrid.add(pane, colIndex, rowIndex);
//        //TODO change color of rectangle to default
//    }
	
//	private void moveUnit(int colIndex, int rowIndex) {
//		// TODO Auto-generated method stub
//		if(currentUnitSelected != null ) {
//			currentUnitSelected.setColor(Color.BLACK);
//			fieldGrid.getChildren().remove(currentUnitSelected);
//			fieldGrid.add(currentUnitSelected, colIndex, rowIndex);
//			currentUnitSelected = null;
//		} else {
//			System.out.println("nothing is selected!");
//		}
//			
//	}

	
	// Choice box event handlers
	
	public void setFaction_1(ActionEvent event) {
		Game.army_1.setFactionName(FactionEnum.valueOf(armyChoice_1.getValue().toUpperCase())); // converts faction selected string to enum of factions 
		System.out.println(Game.army_1.getFactionName());
		
	}
	
	public void setFaction_2(ActionEvent event) {
		Game.army_2.setFactionName(FactionEnum.valueOf(armyChoice_2.getValue().toUpperCase())); // converts faction selected string to enum of factions
		System.out.println(Game.army_2.getFactionName());
	}
	
	public void setWeather(ActionEvent event) {
		ArmyBuild.chooseWeather(weatherChoice.getValue());
		System.out.println(Weather.getWeather());
	}
	
	public void setLocation(ActionEvent event) {
		ArmyBuild.chooseLocation(locationChoice.getValue());
		System.out.println(Location.getLocation());
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
				if (bar_1.getProgress() >= 1 && changeValue == 1) { break; }
				Game.army_1.changeComposition(id, changeValue);
				
				if (Game.army_1.getComp().summary() > 1 && changeValue == 1) { 
					Game.army_1.changeComposition(id, -changeValue);
					break; }
				
				setUnitsLabels();
				setBars();	
				
				break;
				
			case("2"):
				Game.army_2.changeComposition(id, changeValue);
				
				if (Game.army_2.getComp().summary() > 1 && changeValue == 1) { 
					Game.army_2.changeComposition(id, -changeValue);
					break; }
				
				setUnitsLabels();
				setBars();
				
				break;
			
			default:
				System.out.println(((Button)event.getSource()).getText());
		}
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
						boolean end = regimentsRectanglesArmy1.isEmpty() ? outputManager.setTextmainTextArea(Game.army_2) : outputManager.setTextmainTextArea(Game.army_1);
						//fieldGrid.getChildren().clear();
						//run.fire();
					}
					battleTick();
					System.out.println("HEUREKAA");
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
		refreshBattlefield();
			}
		});	
	}
	private void refreshBattlefield() {
		
		//refresh tooltips
		for(MyRectangleUnit unit : regimentsRectanglesArmy1) { unit.setTooltip(); }
		for(MyRectangleUnit unit : regimentsRectanglesArmy2) { unit.setTooltip(); }
		//redraw battlefield
		fieldGrid.getChildren().clear();
		refreshDrawing();
		
		
	}
	
	private void refreshDrawing() {
		for(int i = 0; i < regimentsRectanglesArmy1.size(); i++) { fieldGrid.add(regimentsRectanglesArmy1.get(i), 1, i, 1, 1); }
		for(int i = 0; i < regimentsRectanglesArmy2.size(); i++) { fieldGrid.add(regimentsRectanglesArmy2.get(i), 3, i, 1, 1); }
	}

	private void drawArmy() {
		
		Units[][] units1 = Game.army_1.getComp().getArmy().getUnits();
		Units[][] units2 = Game.army_2.getComp().getArmy().getUnits();
		//------------ battlefield
		// Add rectangles to units
		
		for(int type = 0; type < units1.length; type++) {
			System.out.println(units1[type].length);
			if(units1[type] != null) {
				for (int regiment = 0; regiment < units1[type].length; regiment++) {
					if(units1[type][regiment].getStatus() != UnitsStatusEnum.RETREATED) { // dont add units that already retreated
						regimentsRectanglesArmy1.add(new MyRectangleUnit(units1[type][regiment]));
					}
				}
			}
		}	
		for(int type = 0; type < units2.length; type++) {
			System.out.println(units2[type].length);
			if(units2[type] != null) {
				for (int regiment = 0; regiment < units2[type].length; regiment++) {
					if(units2[type][regiment].getStatus() != UnitsStatusEnum.RETREATED) {
						regimentsRectanglesArmy2.add(new MyRectangleUnit(units2[type][regiment]));	
					}
					
				}
				
			}
		}
		fieldGrid.setAlignment(Pos.CENTER);
		//ColumnConstraints constraints = new ColumnConstraints();
		//constraints.setHgrow(Priority.ALWAYS);

		//fieldGrid.getColumnConstraints().add(constraints);
		
		for(int i = 0; i < regimentsRectanglesArmy1.size(); i++) { fieldGrid.add(regimentsRectanglesArmy1.get(i), 1, i, 1, 1); }
		for(int i = 0; i < regimentsRectanglesArmy2.size(); i++) { fieldGrid.add(regimentsRectanglesArmy2.get(i), 3, i, 1, 1); }
				//------------- end 
	}
	
	// TODO delete?
	public void draw() {
		GraphicsContext g= this.canvas.getGraphicsContext2D();
		//GraphicsContext graphicCOntext = canvas.getGraphicsContext2D();
		pw.println("hello");
		final int SCREEN_HEIGHT = 320;
		final int SCREEN_WIDTH = 320;
		final int UNIT_SIZE = 10;
		
		//g.setFill(Color.BLACK);
		//g.fillRect(0, 0, 320, 320);
		Image image = new Image("file:images/map.png");
		
		g.drawImage(image, 0, 0, 320, 320);
		//graphicCOntext.drawImage(image, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		/*for(int i=1; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
			g.setFill(Color.BLUE);
			g.fillRect(SCREEN_WIDTH/4, 12*i, 10, 10);
		}*/
		
		// shows "size" of first army
		g.setFill(Color.BLUE);
		g.fillRect(SCREEN_WIDTH/8+100-100*bar_1.getProgress(), 10, 100*bar_1.getProgress(), SCREEN_HEIGHT-20);
		// shows "size" of second army
		g.setFill(Color.RED);
		g.fillRect(2*SCREEN_WIDTH/8 + 100, 10, 100*bar_2.getProgress(), SCREEN_HEIGHT-20);
		
	}
	
	public void setRandom(ActionEvent event) {
		
		//TODO add options for individual randomizing of armies, location ...
		Game.setRandomArmy(Game.army_1);
		Game.setRandomArmy(Game.army_2);
		setBars();
		setUnitsLabels();
		Game.createComposition();
		
	}
	 /**
	  * sets bars and bar labels to updated values
	  * */
	private void setBars() {
		bar_1.setProgress(Game.army_1.getComp().summary()); 
		label_bar_1.setText(Integer.toString((int)Math.round(bar_1.getProgress() * 100)) + " %");
		bar_2.setProgress(Game.army_2.getComp().summary()); 
		label_bar_2.setText(Integer.toString((int)Math.round(bar_2.getProgress() * 100)) + " %");
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
