package com.thegame.game.mob;

import com.thegame.game.events.Event;
import com.thegame.game.events.EventDispatcher;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.AnimatedSprite;
import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.input.Keyboard;

/**
 * The Class Player.
 * Steuerbare Spielfigur im Spiel.
 */
public class Player extends Mob implements EventListener {

	private String name;
	private Keyboard input;

	private AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_idle, 128, 128, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 128, 128, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 128, 128, 4);

	private AnimatedSprite animSprite = idle;

	/**
	 * Instantiates a new player.
	 *
	 * @param name the name
	 * @param input the input
	 */
	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		health = 100;
	}

	/**
	 * Instantiates a new player.
	 *
	 * @param name the name
	 * @param x the x
	 * @param y the y
	 * @param input the input
	 */
	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = animSprite.getSprite();
				
		// Player default attributes
		health = 100;
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.events.EventListener#onEvent(com.thegame.game.events.Event)
	 */
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		//dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent)e)); 
		//dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent)e));
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.mob.Mob#update()
	 */
	public void update() {
		double xa = 0, ya = 0;
		double speed = 3.4;
		
		if (walking) {
			animSprite.update();
		} else {
			animSprite.setFrame(0);
		}
		
		walking = false;
		if (input.up) {
			ya -= speed;
		} else {
			ya += (speed * 9.801);
		}
		if (input.down) {
			ya += speed;
		}
		if (input.left) {
			xa -= speed;
			animSprite = left;
			walking = true;
		}
		if (input.right) {
			xa += speed;
			animSprite = right;
			walking = true;
		}
		

		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.mob.Mob#render(com.thegame.game.graphics.Screen)
	 */
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 64), (int) (y - 110), sprite, 0);
	}
	

	
	/**
	 * GETTER & SETTER
	 */
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
