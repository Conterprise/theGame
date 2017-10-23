package com.thegame.game.mob;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.thegame.game.events.Event;
import com.thegame.game.events.EventDispatcher;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.Screen;
import com.thegame.game.input.Keyboard;

public class Player extends Mob implements EventListener {

	private String name;
	private Keyboard input;
	private boolean walking = false;
	private BufferedImage image;
	
	private boolean shooting = false;

	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		health = 100;
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		
		try {
			image = ImageIO.read(new File("res/textures/home.png"));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Player default attributes
		health = 100;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		//dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent)e)); 
		//dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent)e));
	}

	public void update() {
		double xa = 0, ya = 0;
		double speed = 1.4;
		if (input.up) {
			ya -= speed;
		}
		if (input.down) {
			ya += speed;
		}
		if (input.left) {
			xa -= speed;
		}
		if (input.right) {
			xa += speed;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		screen.drawRect((int) (x - 16), (int) (y - 16), 32, 32, 0, false);
	}
}
