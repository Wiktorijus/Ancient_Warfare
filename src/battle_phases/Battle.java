package battle_phases;

import java.util.ArrayList;

import gui.MyRectangleUnit;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import model.Game;
import soldier_types.UnitsStatusEnum;

public class Battle {

	private ArrayList<MyRectangleUnit> battleOrder1;
	private ArrayList<MyRectangleUnit> battleOrder2;
	
	private int widthOfBattlefield = 10;
	private final double breakingPoint = 0.5; // percentage of unit after which it runs away  
	
	private GridPane fieldGrid;
	private Node[][] gridPaneArray = null;
	
	

	
	public Battle (ArrayList<MyRectangleUnit> battleOrder1, ArrayList<MyRectangleUnit> battleOrder2, 
			int widthOfBattlefield, GridPane fieldGrid) {
		this.battleOrder1 = battleOrder1;
		this.battleOrder2 = battleOrder2;
		this.widthOfBattlefield = widthOfBattlefield;
		this.fieldGrid = fieldGrid;
		
		
		
		initializeGridPaneArray();
		
		
	}
	
	private void initializeGridPaneArray() {  
		
		this.gridPaneArray = new Node[fieldGrid.getRowCount()][fieldGrid.getColumnCount()];
		System.out.println(fieldGrid.getRowCount());
		
		for (Node child : fieldGrid.getChildren()) {
		    Integer column = GridPane.getColumnIndex(child);
		    Integer row = GridPane.getRowIndex(child);
		    if (column != null && row != null) {
		    	gridPaneArray[row][column] = child ;
		    	 System.out.println(gridPaneArray[row][column]);
		    } else { //TODO asi vymazat
		    	//gridPaneArray[row][column] = null;
		    }
		}
		
//		
//       for(int row = 0 ; row < fieldGrid.getRowCount(); row++) {
//    	   for (int column = 0; column < fieldGrid.getColumnCount(); column++) {
//    		   this.gridPaneArray[]
//    	   }
//       }
    }
	
	public void fightMoment () {
		
		for(int battleOrderPosition = 0, position = 0; position < fieldGrid.getRowCount(); position++) {
			
			if(gridPaneArray[position][1] != null && gridPaneArray[position][3] != null) {
				
				int leftUnitDamage = battleOrder1.get(battleOrderPosition).getUnit().getDamage();
				int rightUnitDamage = battleOrder2.get(battleOrderPosition).getUnit().getDamage();
				
				battleOrder1.get(battleOrderPosition).getUnit().decreaseNumber(rightUnitDamage);
				battleOrder2.get(battleOrderPosition).getUnit().decreaseNumber(leftUnitDamage);
				
				battleOrderPosition++;			
			}
		}
		unitStatus();	
	}
	
	private void unitStatus() {
		
		ArrayList<MyRectangleUnit> tempBattleOrder1 = (ArrayList<MyRectangleUnit>) battleOrder1.clone();
		ArrayList<MyRectangleUnit> tempBattleOrder2 = (ArrayList<MyRectangleUnit>) battleOrder2.clone();
		
		//is unit destroyed in one battle order than the other
		for (MyRectangleUnit unit: tempBattleOrder1) {
			if (unit.getUnit().getNumber() <= breakingPoint * unit.getUnit().getMaxNumber()) {
				battleOrder1.remove(unit);
				unit.getUnit().setStatus(UnitsStatusEnum.RETREATED);
			}
		}
		for (MyRectangleUnit unit: tempBattleOrder2) {
			if (unit.getUnit().getNumber() <= breakingPoint * unit.getUnit().getMaxNumber()) {
				battleOrder2.remove(unit);
				unit.getUnit().setStatus(UnitsStatusEnum.RETREATED);
			}
		}
		
	}
	
}
