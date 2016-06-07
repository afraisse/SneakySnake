package microej.labs.eval;

import java.util.ArrayList;
import java.util.List;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

public class GameProgram extends Displayable implements EventHandler {
	
	int screenHgt;
	int screenWdt;
	
	int canvasWdt;
	int canvasHgt;
	
	int length = 4;
	int rectSize = 4;
	List<Rect> snake;

	public GameProgram() {
		super(Display.getDefaultDisplay());
		Display disp = this.getDisplay();
		screenHgt = disp.getHeight();
		screenWdt = disp.getWidth();
		canvasWdt = screenWdt / rectSize;
		canvasHgt = screenHgt / rectSize;
		
		snake = new ArrayList<Rect>();
		
		// First rect
		Rect rect = new Rect(screenWdt/2, screenHgt/2);
		snake.add(rect);
		for (int i = 1; i< length+1; i++) {
			snake.add(new Rect(screenWdt/2 -i*rectSize, screenHgt/2));
		}
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Rect head = snake.get(0);
				snake.add(0, new Rect(head.x+rectSize, head.y));
				snake.remove(snake.size()-1);
				repaint();
			}
		}, 0, 200);
	}

	@Override
	public boolean handleEvent(int event) {
		return false;
	}

	@Override
	public void paint(GraphicsContext g) {
		// White background
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, screenWdt, screenHgt);	
	
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
