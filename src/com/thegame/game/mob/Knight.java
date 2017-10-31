package com.thegame.game.mob;

import com.thegame.game.graphics.AnimatedSprite;
import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.SpriteSheet;

/**
 * The Class Knight.
 * KI Spielfigur "Knight", Level 1.
 */
public class Knight extends Mob {

	private AnimatedSprite idle = new AnimatedSprite(SpriteSheet.knight_idle, 128, 128, 7);
	private AnimatedSprite walk = new AnimatedSprite(SpriteSheet.knight_walk, 128, 128, 7);
	private AnimatedSprite run = new AnimatedSprite(SpriteSheet.knight_run, 128, 128, 7);
	private AnimatedSprite jump = new AnimatedSprite(SpriteSheet.knight_jump, 128, 128, 7);
	private AnimatedSprite attack = new AnimatedSprite(SpriteSheet.knight_attack, 128, 128, 7);
	private AnimatedSprite hurt = new AnimatedSprite(SpriteSheet.knight_hurt, 128, 128, 7);
	private AnimatedSprite die = new AnimatedSprite(SpriteSheet.knight_die, 128, 128, 7);

	private AnimatedSprite animSprite = idle;

	/**
	 * Instantiates a new player.
	 *
	 * @param name the name
	 * @param input the input
	 */
	public Knight() {
		// Player default attributes
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
	public Knight(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = animSprite.getSprite(false);
				
		// Player default attributes
		health = 100;
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.mob.Mob#update()
	 */
	public void update() {
		double xa = 0, ya = 0;
		double speed = 3.4;
		
		ya += GFORCE;
		
		animSprite.update();

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
}
