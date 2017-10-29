package com.thegame.game.mob;

import com.thegame.game.events.Event;
import com.thegame.game.events.EventDispatcher;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.AnimatedSprite;
import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.input.Keyboard;

public class Player extends Mob implements EventListener {

	private String name;
	private Keyboard input;

	private AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_idle, 128, 128, 4);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 128, 128, 4);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 128, 128, 4);
	private AnimatedSprite jumpleft = new AnimatedSprite(SpriteSheet.player_jump_left, 128, 128, 4);
	private AnimatedSprite jumpright = new AnimatedSprite(SpriteSheet.player_jump_right, 128, 128, 4);

	private AnimatedSprite animSprite = idle;

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
		sprite = animSprite.getSprite();
				
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
		double speed = 3.4;
				
		if (walking) {
			animSprite.update();
		} else {
			animSprite.setFrame(0);
		}
		

		walking = input.left || input.right;
		if (input.left) {
			xa -= speed;
			animSprite = left;
		}
		if (input.right) {
			xa += speed;
			animSprite = right;
		}
		
		if (input.up && onfloor) {
			jumping = true;
			jumpHeight = 0;
		}
		if (jumping) {
			if (jumpHeight < jumpHeight_max) {
				jumpHeight++;
				ya -= speed;
			} else {
				jumping = false;
			}

			animSprite = (input.left) ? jumpleft : jumpright;
		}
		if (!jumping && !onfloor) {
			//ya += (speed * 9.801);
			ya += 9.801;
		}
		

		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 64), (int) (y - 110), sprite, 0);
	}
}
