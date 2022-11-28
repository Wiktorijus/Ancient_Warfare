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
	
	public void fightMoment () {
		
		for(int battleOrderPosition = 0, position = 0; position < fieldGrid.getRowCount(); position++) {
			
			// archer damage of first army HALFED because they are in second line
			if(gridPaneArray[position][firstArmySecondLine] != null && gridPaneArray[position][secondArmyFirstLine] != null) { // second first army
				int secondLineFirstArmy = firstArmy.getSecondLine().get(battleOrderPosition).getUnit().getDamage() / 2; // archers
				secondArmy.getFirstLine().get(battleOrderPosition).getUnit().decreaseNumber(secondLineFirstArmy);
			}
			// archer damage of second army HALFED because they are in second line
			if(gridPaneArray[position][secondArmySecondLine] != null && gridPaneArray[position][firstArmyFirstLine] != null) { // second first army
				int secondLineSecondArmy = secondArmy.getSecondLine().get(battleOrderPosition).getUnit().getDamage() / 2; // archers
				firstArmy.getFirstLine().get(battleOrderPosition).getUnit().decreaseNumber(secondLineSecondArmy);
			}
			
			if(gridPaneArray[position][firstArmyFirstLine] != null && gridPaneArray[position][secondArmyFirstLine] != null) { //first line
				
				int firstLineFirstArmy = firstArmy.getFirstLine().get(battleOrderPosition).getUnit().getDamage();
				int firstLineSecondArmy = secondArmy.getFirstLine().get(battleOrderPosition).getUnit().getDamage();
				
				if(firstArmy.getFirstLine().get(battleOrderPosition).getUnit() instanceof Archers) { // damage to archers  doubled if in first line
					firstArmy.getFirstLine().get(battleOrderPosition).getUnit().decreaseNumber(firstLineSecondArmy * 2);
				} else {
					firstArmy.getFirstLine().get(battleOrderPosition).getUnit().decreaseNumber(firstLineSecondArmy);
				}
				
				if(secondArmy.getFirstLine().get(battleOrderPosition).getUnit() instanceof Archers) { // damage to archers  doubled if in first line
					secondArmy.getFirstLine().get(battleOrderPosition).getUnit().decreaseNumber(firstLineFirstArmy * 2);
				} else {
					secondArmy.getFirstLine().get(battleOrderPosition).getUnit().decreaseNumber(firstLineFirstArmy);
				}
				
				battleOrderPosition++;			
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

	
//	ArrayList<MyRectangleUnit> tempFirstline1 = (ArrayList<MyRectangleUnit>) firstArmy.getFirstLine().clone();
//	ArrayList<MyRectangleUnit> tempFirstline2 = (ArrayList<MyRectangleUnit>) secondArmy.getFirstLine().clone();
//	
//	//TODO add unit removing if 0 morale
//	//is unit destroyed in one battle order than the other
//	int currentUnitId = 0;
//	for (MyRectangleUnit unit: tempFirstline1) {
//		
//		if (unit.getUnit().getNumber() <= BREAKINGPOINT * unit.getUnit().getMaxNumber()
//				|| unit.getUnit().getMorale() == 0) {
//			firstArmy.getFirstLine().remove(unit);
//			unit.getUnit().setStatus(UnitsStatusEnum.RETREATED);
//			
//			//use reserve unit fill the gap
//			if(firstArmy.getReserveLine().size() != 0) {
//				firstArmy.getFirstLine().add(currentUnitId, firstArmy.getReserveLine().get(0));
//				firstArmy.getReserveLine().remove(0);
//			} else if (firstArmy.getSecondLine().size() != 0) {
//				firstArmy.getFirstLine().add(currentUnitId, firstArmy.getSecondLine().get(0));
//				firstArmy.getSecondLine().remove(0);
//			}
//		}
//		currentUnitId++;
//	}
//	currentUnitId = 0;
//	for (MyRectangleUnit unit: tempFirstline2) {
//		if (unit.getUnit().getNumber() <= BREAKINGPOINT * unit.getUnit().getMaxNumber()
//				|| unit.getUnit().getMorale() == 0) {
//			secondArmy.getFirstLine().remove(unit);
//			unit.getUnit().setStatus(UnitsStatusEnum.RETREATED);
//		}
//		currentUnitId++;
//	}
	
}
	
}
