package battle_phases;

import java.util.ArrayList;

import gui.MyRectangleUnit;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import model.Game;
import soldier_types.Archers;
import soldier_types.UnitsStatusEnum;

public class Battle {

	private ArmyBuild firstArmy;
	private ArmyBuild secondArmy;
	
	private int widthOfBattlefield = 10;
	private final double TICKMORALELOSS = 0.1;
	private final double BREAKINGPOINT = 0.5; // percentage of unit after which it runs away 
	//columns of grid pane, these must be change if design of gridpane in view changes
	private final int firstArmyFirstLine = 1;
	private final int firstArmySecondLine = 0;
	private final int secondArmyFirstLine = 3;
	private final int secondArmySecondLine = 4;
	
	private GridPane fieldGrid;
	private Node[][] gridPaneArray = null;
	
	public Battle (ArmyBuild firstArmy, ArmyBuild secondArmy, int widthOfBattlefield) {
		this.firstArmy = firstArmy;
		this.secondArmy = secondArmy;
		this.widthOfBattlefield = widthOfBattlefield;
	}
	
	public Battle (ArmyBuild firstArmy, ArmyBuild secondArmy, 
			int widthOfBattlefield, GridPane fieldGrid) {
		this.firstArmy = firstArmy;
		this.secondArmy = secondArmy;
		this.widthOfBattlefield = widthOfBattlefield;
		this.fieldGrid = fieldGrid;
		
		initializeGridPaneArray();	
	}
	
	private void initializeGridPaneArray() {  
		
		this.gridPaneArray = new Node[fieldGrid.getRowCount()][fieldGrid.getColumnCount()];
		
		for (Node child : fieldGrid.getChildren()) {
		    Integer column = GridPane.getColumnIndex(child);
		    Integer row = GridPane.getRowIndex(child);
		    if (column != null && row != null) {
		    	gridPaneArray[row][column] = child ;
		    } 
		}
    }
	
	public void simulation() { // simulation without gridpane, and aligment of armies but still legitimate as long one unit attack one unit
		int smallerArmy = (firstArmy.getFirstLine().size() < secondArmy.getFirstLine().size()) ? firstArmy.getFirstLine().size() : secondArmy.getFirstLine().size();
		
		for(int position = 0; position < smallerArmy; position++) {
				
			// archer damage of first army HALFED because they are in second line
			if(firstArmy.getSecondLine().size() > position && secondArmy.getFirstLine().size() > position) {// second first army
				int secondLineFirstArmy = firstArmy.getSecondLine().get(position).getUnit().getDamage() / 2; // archers
				secondArmy.getFirstLine().get(position).getUnit().decreaseNumber(secondLineFirstArmy);
			}
			// archer damage of second army HALFED because they are in second line
			if(secondArmy.getSecondLine().size() > position && firstArmy.getFirstLine().size() > position) { // second first army
				int secondLineSecondArmy = secondArmy.getSecondLine().get(position).getUnit().getDamage() / 2; // archers
				firstArmy.getFirstLine().get(position).getUnit().decreaseNumber(secondLineSecondArmy);
			}
			
			//first line
			if(firstArmy.getFirstLine().size() > position && secondArmy.getFirstLine().size() > position) {
				int firstLineFirstArmy = firstArmy.getFirstLine().get(position).getUnit().getDamage();
				int firstLineSecondArmy = secondArmy.getFirstLine().get(position).getUnit().getDamage();
				
				if(firstArmy.getFirstLine().get(position).getUnit() instanceof Archers) { // damage to archers  doubled if in first line
					firstArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineSecondArmy * 2);
				} else {
					firstArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineSecondArmy);
				}
				
				if(secondArmy.getFirstLine().get(position).getUnit() instanceof Archers) { // damage to archers  doubled if in first line
					secondArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineFirstArmy * 2);
				} else {
					secondArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineFirstArmy);
				}	
			}
					
			decreaseMoralTick(firstArmy);
			decreaseMoralTick(secondArmy);
			unitStatus(firstArmy);
			unitStatus(secondArmy);
		}
	}
	
	public void fightMoment () {
		
		// second line is centered, but list of units in second rows isn't, shifting
		int  shiftSecondLinePosition1 = (firstArmy.getFirstLine().size() - firstArmy.getSecondLine().size()) / 2;
		int  shiftSecondLinePosition2 = (secondArmy.getFirstLine().size() - secondArmy.getSecondLine().size()) / 2;
		
		for(int position = 0; position < fieldGrid.getColumnCount(); position++) {
			
			// archer damage of first army HALFED because they are in second line
			if(gridPaneArray[firstArmySecondLine][position] != null && gridPaneArray[secondArmyFirstLine][position] != null) { // second first army
				int secondLineFirstArmy = firstArmy.getSecondLine().get(position - shiftSecondLinePosition1).getUnit().getDamage() / 2; // archers
				secondArmy.getFirstLine().get(position).getUnit().decreaseNumber(secondLineFirstArmy);
			}
			// archer damage of second army HALFED because they are in second line
			if(gridPaneArray[secondArmySecondLine][position] != null && gridPaneArray[firstArmyFirstLine][position] != null) { // second first army
				int secondLineSecondArmy = secondArmy.getSecondLine().get(position - shiftSecondLinePosition2).getUnit().getDamage() / 2; // archers
				firstArmy.getFirstLine().get(position).getUnit().decreaseNumber(secondLineSecondArmy);
			}
			
			if(gridPaneArray[firstArmyFirstLine][position] != null && gridPaneArray[secondArmyFirstLine][position] != null) { //first line
				
				int firstLineFirstArmy = firstArmy.getFirstLine().get(position).getUnit().getDamage();
				int firstLineSecondArmy = secondArmy.getFirstLine().get(position).getUnit().getDamage();
				
				if(firstArmy.getFirstLine().get(position).getUnit() instanceof Archers) { // damage to archers  doubled if in first line
					firstArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineSecondArmy * 2);
				} else {
					firstArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineSecondArmy);
				}
				
				if(secondArmy.getFirstLine().get(position).getUnit() instanceof Archers) { // damage to archers  doubled if in first line
					secondArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineFirstArmy * 2);
				} else {
					secondArmy.getFirstLine().get(position).getUnit().decreaseNumber(firstLineFirstArmy);
				}			
			}
		}
		decreaseMoralTick(firstArmy);
		decreaseMoralTick(secondArmy);
		unitStatus(firstArmy);
		unitStatus(secondArmy);
	}
	
	private void decreaseMoralTick(ArmyBuild army) {
		decreaseMoralTickOfLine(army.getFirstLine());
		decreaseMoralTickOfLine(army.getSecondLine());
		decreaseMoralTickOfLine(army.getReserveLine());	
	}
	private void decreaseMoralTickOfLine(ArrayList<MyRectangleUnit> line) {
		for(MyRectangleUnit unit : line) {
			unit.getUnit().decreaseMorale(TICKMORALELOSS);
		}
	}
	private void unitStatus(ArmyBuild army) {
		
		ArrayList<MyRectangleUnit> tempFirstline = (ArrayList<MyRectangleUnit>) army.getFirstLine().clone();
		
		//if unit is destroyed in one battle order or it's morale is 0 remove it and use reserve or second line in it's place
		for (MyRectangleUnit unit: tempFirstline) {
			
			if (unit.getUnit().getNumber() <= BREAKINGPOINT * unit.getUnit().getMaxNumber()
					|| unit.getUnit().getMorale() <= 0) {
				army.getFirstLine().remove(unit);
				unit.getUnit().setStatus(UnitsStatusEnum.RETREATED);
				
				//use reserve unit fill the gap
				if(army.getReserveLine().size() > 0) {
					army.getFirstLine().add(0, army.getReserveLine().get(army.getReserveLine().size()-1));
					army.getReserveLine().remove(army.getReserveLine().size()-1);
				} else if (army.getSecondLine().size() > 0) {
					army.getFirstLine().add(0, army.getSecondLine().get(0));
					army.getSecondLine().remove(0);
				}
			}
		}

	
}
	
}
