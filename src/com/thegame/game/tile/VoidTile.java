package com.thegame.game.tile;

import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.Sprite;

/**
 * The Class VoidTile.
 * Leere/undefinierte Kachel in Level
 */
public class VoidTile extends Tile {

	/**
	 * Instantiates a new void tile.
	 *
	 * @param sprite the sprite
	 */
	public VoidTile(Sprite sprite) {
		super(sprite);
	}
		
	/* (non-Javadoc)
	 * @see com.thegame.game.tile.Tile#render(int, int, com.thegame.game.graphics.Screen)
	 */
	public void render(int x, int y, Screen screen) {	
		//screen.renderTile(x << 4, y << 4, this);
	}
	
	/* (non-Javadoc)
	 * @see com.thegame.game.tile.Tile#solid()
	 */
	public boolean solid() {
		return true;
	}

}
