package view;


import armies.ArmiesEnum;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import soldier_types.Units;

public class MyRectangleUnit extends Rectangle {

	private Tooltip tooltip = new Tooltip();
	
	private Units unit;
	private final static double HEIGH = 35;
	private final static double WIDTH = 55;
	
	MyRectangleUnit() {}
	
	public MyRectangleUnit(Units newUnit, boolean Simulation) {
		
		setHeight(HEIGH);
		setWidth(WIDTH);
		
		this.unit = newUnit;
		//this.addEventFilter(MouseEvent.MOUSE_PRESSED, new PickUnit());
		this.addEventFilter(MouseEvent.ANY, new HoverOverUnit());
		
		if(!Simulation) { 
			setImageBackground();
			setTooltip();
		}
		
		
	}	

	private class HoverOverUnit implements EventHandler<MouseEvent> {


		@Override
		public void handle(MouseEvent event) {
			if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
				setColor();
			} else if(event.getEventType() == MouseEvent.MOUSE_EXITED) {
				setImageBackground();
			}

		}
	}
	
	public void setColor() {
		this.setFill(Color.GOLD);
	}
	public void setColor(Color color) {
		this.setFill(color);
	}
	public void setFocusOnThisUnit() {
		Controller.currentUnitSelected = this;
	}
	
	public void setImageBackground() {
		try {
			//Image backgroundImage = new Image(getClass().getResourceAsStream(unit.getSymbolPath()));
			//this.setFill(new ImagePattern(backgroundImage));
			//this.setEffect(ColorInput(10,10,10,10,Color.RED));
			this.setFill(Controller.getUnitPattern(unit));
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
	private void setColorBackground() {
		if(unit.getAllegienceToArmy().equals(ArmiesEnum.FIRSTARMY)) {
			this.setFill(Color.BLACK);
		} else {
			this.setFill(Color.RED);
		}
	}
	
	public Units getUnit() { return this.unit; }
	
}
