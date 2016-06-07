package microej.labs.eval;

import java.util.ArrayList;
import java.util.List;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;

public class GameProgram extends Displayable implements EventHandler {
	
	int screenHgt;
	int screenWdt;
	
	int canvasWdt;
	int canvasHgt;
	int currentDir;
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
		
		currentDir = Dir.DROITE;
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Rect head = snake.get(0);
				Rect next = null;
				switch (currentDir) {
				case Dir.DROITE:
					next = new Rect(head.x + rectSize, head.y);
					break;
				case Dir.GAUCHE:
					next = new Rect(head.x - rectSize, head.y);
					break;
				case Dir.HAUT:
					next = new Rect(head.x, head.y - rectSize);
					break;
				case Dir.BAS:
					next = new Rect(head.x, head.y + rectSize);
					break;
				default: break;
				}
				
				snake.add(0, next);
				snake.remove(snake.size()-1);
				repaint();
			}
		}, 0, 200);
	}

	//----------------------------------------------
	//--------- Gestion du rafraichissement
	//----------------------------------------------
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



	//----------------------------------------------
	//--------- Gestion des evenements
	//----------------------------------------------
	@Override
	public EventHandler getController() {
		return this;
	}

	@Override
	public boolean handleEvent(int event) {
		if(Event.getType(event) == Event.POINTER)
		{
			if(Pointer.isPressed(event) || Pointer.isDragged(event))
			{
				Pointer ptr= (Pointer)Event.getGenerator(event);
				if(ptr.getX() >= (screenWdt/2)) 
					changeDirection(true);
				else
					changeDirection(false);
				return true;
			}
		}	
		return false;
	}

	/**
	 * Change la direction courante en tournant à droite (true) ou a gauche (true)
	 * @param droite
	 */
	private void changeDirection(boolean droite) {
		switch(currentDir) {
		case Dir.DROITE: 
			if(droite) currentDir = Dir.BAS;
			else currentDir = Dir.HAUT;
			break;
		case Dir.BAS: 
			if(droite) currentDir = Dir.GAUCHE;
			else currentDir = Dir.DROITE;
			break;
		case Dir.GAUCHE: 
			if(droite) currentDir = Dir.HAUT;
			else currentDir = Dir.BAS;
			break;
		case Dir.HAUT: 
			if(droite) currentDir = Dir.DROITE;
			else currentDir = Dir.GAUCHE;
			break;
		default : break; //breakdance 
		}
	}

}
