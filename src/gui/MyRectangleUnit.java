package gui;

import armies.ArmiesEnum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Controller;
import soldier_types.Units;

public class MyRectangleUnit extends Rectangle {

	private Tooltip tooltip = new Tooltip();
	
	private Units unit;
	private final static double HEIGH = 25;
	private final static double WIDTH = 30;
	
	MyRectangleUnit() {}
	
	public MyRectangleUnit(Units newUnit) {
		
		setHeight(HEIGH);
		setWidth(WIDTH);
		
		this.unit = newUnit;
		//this.addEventFilter(MouseEvent.MOUSE_PRESSED, new PickUnit());
		
		setBackground();
		setTooltip();
	}	

	private class PickUnit implements EventHandler<MouseEvent> {


		@Override
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.PRIMARY) {
				setColor();
				setFocusOnThisUnit();
			} else {
				
			}

		}
	}
	
	public void setColor() {
		this.setFill(Color.RED);
	}
	public void setColor(Color color) {
		this.setFill(color);
	}
	public void setFocusOnThisUnit() {
		Controller.currentUnitSelected = this;
	}
	
	public void setBackground() {
		try {
			this.setFill(Color.RED);
			Image backgroundImage = new Image(getClass().getResourceAsStream(unit.getSymbolPath()));
			this.setFill(new ImagePattern(backgroundImage));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("BAD URL");
		}
	}
	
	public void setTooltip() {
		//TODO add names
		if(unit != null) {
			tooltip.setText(unit.getName() + " of " + unit.getAllegienceToArmy() +
					" " + unit.getNumber() + " " + unit.getMorale());
			Tooltip.install(this, tooltip);
		} else
			System.out.println("EMPTY unit");
	}
	public Units getUnit() { return this.unit; }
	
}
