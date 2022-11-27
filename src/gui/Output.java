package gui;

import battle_phases.ArmyBuild;
import javafx.scene.control.TextArea;

public class Output {
	
	private TextArea mainTextArea;

	public Output(TextArea mainTextArea) {
		this.mainTextArea = mainTextArea;
	}

	public boolean setTextmainTextArea(ArmyBuild victoriousArmy) {
		mainTextArea.setText("It's done! "  + victoriousArmy.toString() +" has won!" );
		return true;
	}
	
	
	
}
