package com.thegame.game.mob;

import com.thegame.game.entity.Entity;
import com.thegame.game.graphics.Screen;
import com.thegame.game.tile.Tile;

/**
 * The Class Mob.
 * Spielfigur im Spiel.
 */
public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	protected int health;

	protected Direction dir;

	/**
	 * Mögliche Bewegungsrichtungen
	 */
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}


	/* (non-Javadoc)
	 * @see com.thegame.game.entity.Entity#update()
	 */
	public abstract void update();	
	
	/* (non-Javadoc)
	 * @see com.thegame.game.entity.Entity#render(com.thegame.game.graphics.Screen)
	 */
	public abstract void render(Screen screen);
	
	/**
	 * Bewegt die Spielfigur relativ zur aktuellen Position im Level
	 *
	 * @param xa the xa
	 * @param ya the ya
	 */
	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(0, ya);
			move(xa, 0);
			return;
		}

		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		
		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa-=abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;				
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya-=abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;				
			}
		}
	}
	
	/**
	 * Betragsfunktion: Liefert -1, wenn value negativ ist, ansonsten 1
	 *
	 * @param value the value
	 * @return the int
	 */
	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}
		
	/**
	 * Liefert true, wenn die Spielfigur an übergebener Position im Level
	 * mit einer Tile kollidieren würde.
	 *
	 * @param xa the xa
	 * @param ya the ya
	 * @return true, if is collision
	 */
	private boolean collision(double xa, double ya) {
		boolean solid = false;		
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c % 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			Tile tile = level.getTile(ix, iy);
			if (tile != null && tile.solid()) solid = true;
		}
		
		return solid;
	}	
	

	
	/**
	 * GETTER & SETTER
	 */
	
	public int getDirection() {
		switch(dir) {
			case LEFT: return -1;
			case RIGHT: return 1;
			default: return 0;
		}
	}
}
