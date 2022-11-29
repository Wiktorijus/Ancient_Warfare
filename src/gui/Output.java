package gui;

import battle_phases.ArmyBuild;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import model.Controller;

public class Output {
	
	private TextArea mainTextArea;
	private Label label_bar_1, label_bar_2, armyCount1, armyCount2, armyLosses1, armyLosses2;
	private Controller output;

	public Output(Controller output, TextArea mainTextArea, 
			Label label_bar_1, Label label_bar_2, Label armyCount1, Label armyCount2,
			Label armyLosses1, Label armyLosses2,
			ProgressBar armySummaryBar1, ProgressBar armySummaryBar2, ProgressBar armyMoraleBar1, ProgressBar armyMoraleBar2) {
		this.mainTextArea = mainTextArea;
		this.label_bar_1 = label_bar_1;
		this.label_bar_2 = label_bar_2;
		this.armyCount1 = armyCount1;
		this.armyCount2 = armyCount2;
		this.armyLosses1 = armyLosses1;
		this.armyLosses2 = armyLosses2;
		this.output = output;
		
	}

	public boolean setTextmainTextArea(ArmyBuild victoriousArmy) {
		mainTextArea.setText(victoriousArmy.getFactionName() +" has won!" );
		return true;
	}

	public void setTextLabel_bar_1(String label_bar_1) {
		this.label_bar_1.setText(label_bar_1);
	}

	public void setTextLabel_bar_2(String label_bar_2) {
		this.label_bar_2.setText(label_bar_2);
	}

	public void setTextArmyCount1(String armyCount1) {
		this.armyCount1.setText(armyCount1);
	}

	public void setTextArmyCount2(String armyCount2) {
		this.armyCount2.setText(armyCount2);
	}

	public void setTextArmyLosses1(String armyLosses1) {
		this.armyLosses1.setText(armyLosses1);
	}

	public void setTextArmyLosses2(String armyLosses2) {
		this.armyLosses2.setText(armyLosses2);
		
	}
	
	public String getTextArmyCount1 () {
		return armyCount1.getText();
	}
	public String getTextArmyCount2 () {
		return armyCount2.getText();
	}
	
	
	
	
}
