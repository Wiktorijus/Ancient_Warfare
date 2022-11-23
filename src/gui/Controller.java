package gui;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.InaccessibleObjectException;
import java.net.URL;
import java.rmi.ServerRuntimeException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.util.List;
import battle_phases.ArmyBuild;
import battle_phases.Result;
import factors.Composition;
import factors.Location;
import factors.Weather;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
	
	private static PrintWriter pw = new PrintWriter(System.out, true);

	// Choice boxes variables
	@FXML private ChoiceBox<String> armyChoice_1,armyChoice_2;
	@FXML private ChoiceBox<String> weatherChoice;
	@FXML private ChoiceBox<String> locationChoice;
	@FXML private ChoiceBox<String> timeOfDay;
	
	// Lists of options for choice boxes
	// TODO try true ENUM so we mitigate mistakes
	private String[] factions = {"Carthaginian", "Celtic", "Greek", "Roman"};
	private String[] weatherList = {"Clear", "Rainy", "Foggy"};
	private String[] locationList = {"Plains", "Forest", "Hill", "Mountain"};
	private String[] timeOfDayList = {"Day", "Night"};
	
	// Buttons
	// Add or remove units from composition
	@FXML private Button archer_1, cavalry_1, heavy_1, pike_1, light_1;
	@FXML private Button archer_2, cavalry_2, heavy_2, pike_2, light_2;
	
	@FXML Button start;
	
	@FXML
	private CheckBox random_army_2;
	
	// Sliders
	@FXML private Slider commanderSlider_1, commanderSlider_2;
	
	// Progress bar
	@FXML private ProgressBar bar_1, bar_2;
	
	// Text area
	@FXML private TextArea output;
	
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		// Choice box initialization
		armyChoice_1.getItems().addAll(factions);
		armyChoice_1.setOnAction(this::setFaction_1);
		armyChoice_1.setValue("Roman");
				
		armyChoice_2.getItems().addAll(factions);
		armyChoice_2.setOnAction(this::setFaction_2);
		armyChoice_2.setValue("Carthaginian");
				
		weatherChoice.getItems().addAll(weatherList);
		weatherChoice.setOnAction(this::setWeather);
		weatherChoice.setValue("Clear");
		
		locationChoice.getItems().addAll(locationList);
		locationChoice.setOnAction(this::setLocation);
		locationChoice.setValue("Plains");
		
		timeOfDay.getItems().addAll(timeOfDayList);
		timeOfDay.setOnAction(this::setTimeOfDay);
		
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
	}
	
	
	// Choice box event handlers
	
	public void setFaction_1(ActionEvent event) {
		Game.army_1.setFactionName(armyChoice_1.getValue());
		System.out.println(Game.army_1.getFactionName());
		
	}
	
	public void setFaction_2(ActionEvent event) {
		Game.army_2.setFactionName(armyChoice_2.getValue()); 
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
	
	public void start(ActionEvent event) {
		
		draw();
		output.setText(Result.finalResult(Game.army_1, Game.army_2));
		//output.setText("HELLO");
	}
	
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
}