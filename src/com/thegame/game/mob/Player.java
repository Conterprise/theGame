package com.thegame.game.mob;

import com.thegame.game.events.Event;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.AnimatedSprite;
import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.input.Keyboard;

/**
 * The Class Player.
 * Steuerbare Spielfigur.
 */
public class Player extends Mob implements EventListener {

	private String name;
	private Keyboard input;
	
	private int condition;

	private AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_idle, 128, 128, 4);
	private AnimatedSprite walk = new AnimatedSprite(SpriteSheet.player_walk, 128, 128, 4);
	private AnimatedSprite run = new AnimatedSprite(SpriteSheet.player_run, 128, 128, 4);
	private AnimatedSprite jump = new AnimatedSprite(SpriteSheet.player_jump, 128, 128, 4);
	private AnimatedSprite attack = new AnimatedSprite(SpriteSheet.player_attack, 128, 128, 4);
	private AnimatedSprite hurt = new AnimatedSprite(SpriteSheet.player_hurt, 128, 128, 4);
	private AnimatedSprite die = new AnimatedSprite(SpriteSheet.player_die, 128, 128, 4);

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
		sprite = animSprite.getSprite(false);
				
		// Player default attributes
		health = 100;
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.events.EventListener#onEvent(com.thegame.game.events.Event)
	 */
	public void onEvent(Event event) {
		//EventDispatcher dispatcher = new EventDispatcher(event);
		//dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent)e)); 
		//dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent)e));
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.mob.Mob#update()
	 */
	public void update() {
		double xa = 0, ya = 0;
		double speed = 3.4;

		walking = input.left || input.right;
		running = input.run && condition > 0;
		attacking = input.attack && health > 0;
		
		// recover condition
		if (!walking && condition < 100) condition++;

		// horizontal movement
		if (input.left) {
			if (running) {								// running left
				xa -= 2*speed;
				condition--;
				animSprite = run;				
			} else {									// walking left				
				xa -= speed;
				animSprite = walk;				
			}
		}
		if (input.right) {
			if (running) {								// running right
				xa += 2*speed;
				condition--;
				animSprite = run;				
			} else {									// walking right				
				xa += speed;
				animSprite = walk;				
			}
		}
		
		// jumping
		if (input.jump && onfloor) {
			jumping = true;
			jumpHeight = 0;
		}
		if (jumping) {
			if (jumpHeight < jumpHeight_MAX) {
				jumpHeight++;
				ya -= speed;
			} else {
				jumping = false;
			}

			animSprite = (input.left) ? jump : jump;
		}
		if (!jumping && !onfloor) {
			ya += GFORCE;
		}
		
		// attack
		if (attacking) {
			animSprite = attack;
		}
		
		// death
		if (health < 1) {
			animSprite = die;			
		}
		
		// animate sprite
		if (walking || attacking || jumping) {
			animSprite.update();
		} else  {
			animSprite.setFrame(0);
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.mob.Mob#render(com.thegame.game.graphics.Screen)
	 */
	public void render(Screen screen) {
		boolean flip = Direction.LEFT.equals(dir);
		sprite = animSprite.getSprite(flip);
		screen.renderMob((int) (x - 64), (int) (y - 110), sprite, 0);
	}
	

	
	/*
	 * GETTER and SETTER
	 */
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
