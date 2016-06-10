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

	int currentDir;
	int length = 4;
	int rectSize = 12;
	List<Rect> snake;
	Rect food;

	public GameProgram() {
		super(Display.getDefaultDisplay());
		Display disp = this.getDisplay();
		screenHgt = disp.getHeight();
		screenWdt = disp.getWidth();

		snake = new ArrayList<Rect>();
		// Gen Snake
		Rect rect = popRandomRect();
		snake.add(rect);
		for (int i = 1; i< length+1; i++) {
			snake.add(new Rect(rect.x-i*rectSize, rect.y));
		}

		// pop food
		food = popRandomRect();
		
		
		// Game routine
		currentDir = Dir.DROITE;
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				// move snake
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

				// If snake didn't got food, remove last rect
				if (next.x == food.x && next.y == food.y) {
					food = popRandomRect();
				} else
					snake.remove(snake.size()-1);
				
				if(isDead(next)) {
					this.cancel();
				}

				repaint();
			}
		}, 0, 400);
	}

	private boolean isDead(Rect next) {
		boolean dead = false;
		for(int i=1; i < snake.size(); ++i) {
			dead = equality(next, snake.get(i));
		}
		dead = dead || outOfScreen(next);
		return dead;
	}
	
	private boolean outOfScreen(Rect next) {
		return (next.x >= screenWdt || next.y >= screenHgt || next.y < 0 || next.x < 0);
	}

	private boolean equality(Rect a, Rect b) {
		return (a.x == b.x && a.y == b.y);
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
		
		//Draw food
		g.setColor(Colors.RED);
		g.fillRect(food.x, food.y, rectSize+1, rectSize+1);
	}
	
	private Rect popRandomRect() {
		int x = (int) Math.floor(Math.random()*(screenWdt-rectSize));
		int y = (int) Math.floor(Math.random()*(screenHgt-rectSize));
		
		// Correction modulo rectsize
		x = x - (x % rectSize);
		y = y - (y % rectSize);
		return new Rect(x, y);
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
