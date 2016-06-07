package microej.labs.eval;

import java.util.ArrayList;
import java.util.List;

import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

public class GameProgram extends Displayable implements EventHandler {
	
	int height;
	int width;
	
	int length = 4;
	int rectSize = 4;
	List<Rect> snake;

	public GameProgram() {
		super(Display.getDefaultDisplay());
		Display disp = this.getDisplay();
		height = disp.getHeight();
		width = disp.getWidth();
		snake = new ArrayList<Rect>();
		
		// First rect
		Rect rect = new Rect(width/2, height/2);
		snake.add(rect);
		for (int i = 1; i< length+1; i++) {
			snake.add(new Rect(width/2 -i*rectSize, height/2));
		}
	}

	@Override
	public boolean handleEvent(int event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paint(GraphicsContext g) {
		// White background
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, width, height);	
	
		//Draw snake
		g.setColor(Colors.BLACK);
		for (Rect rect : snake) {
			g.drawRect(rect.x, rect.y, rectSize, rectSize);
		}
		
	}

	@Override
	public EventHandler getController() {
		return this;
	}

}
