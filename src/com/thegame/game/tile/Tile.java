package com.thegame.game.tile;

import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static final int col_grass = 0xff00ff00;
	public static final int col_rock = 0xff999999;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {		
	}
	
	public boolean solid() {
		return false;
	}

	public static Tile getTile(int color) {
		if (color == Screen.ALPHA_COL) return null;
		if (color == Tile.col_grass) return Tile.grass;
		if (color == Tile.col_rock) return Tile.rock;

		return Tile.voidTile;
	}

	public int getMapColor() {
		if (this instanceof GrassTile) return Tile.col_grass;
		if (this instanceof RockTile) return Tile.col_rock;
		
		return -1;
	}
}
