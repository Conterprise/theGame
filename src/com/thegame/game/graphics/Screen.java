package com.thegame.game.graphics;

import com.thegame.game.mob.Mob;
import com.thegame.game.tile.Tile;

/**
 * The Class Screen.
 * Spielfläche, die gezeichnet werden kann.
 */
public class Screen {
	
	public final static int ALPHA_COL = 0xFFFF00FF;
	public int width, height;
	public int[] pixels;
	public int xOffset, yOffset;

	/**
	 * Instantiates a new screen.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	/**
	 * Löscht alle Pixel auf Spielfläche --> schwarz
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	/**
	 * Zeichnet den Hintergrund
	 *
	 * @param bg the Array aus Int-Pixel mit Hintergrundbild
	 * @param bgwidth the Breite des Hintergrundbildes
	 * @param bgheight the Höhe des Hintergrundbildes
	 */
	public void renderBackground(int[] bg, int bgwidth, int bgheight) {
		if (bg == null)
			return;
		
		for (int y = 0; y < bgheight; y++) {
			for (int x = 0; x < bgwidth; x++) {
				if (x < 0 || x >= width || y < 0 || y >= height) continue;
				pixels[x + y * width] = bg[x + y * bgwidth];
			}
		}
	}
	
	/**
	 * Zeichnet ein Grafikobjekt auf der Spielfläche
	 *
	 * @param xp the xp
	 * @param yp the yp
	 * @param sprite the sprite
	 * @param fixed the fixed
	 */
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL) pixels[xa + ya * width] = col;
			}
		}
	}

	/**
	 * Zeichnet eine Tile auf Spielfläche (bspw. eine Kachel als Teil des Levels)
	 * 
	 * @param xp the xp
	 * @param yp the yp
	 * @param tile the tile
	 */
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[(x + y * tile.sprite.SIZE)];
			}
		}
	}

	/**
	 * Zeichnet eine Spielfigur auf das Spielfeld
	 *
	 * @param xp the xp
	 * @param yp the yp
	 * @param sprite the sprite
	 * @param flip the flip
	 */
	public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			int ys = y;
			if (flip == 2 || flip == 3) ys = sprite.getHeight() - 1 - y;
			
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				int xs = x;
				if (flip == 1 || flip == 3) xs = sprite.getWidth() - 1 - x;				
				if (xa < sprite.getWidth() * -1 || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;				
				int col = sprite.pixels[xs + ys * sprite.getWidth()];
				if (col != ALPHA_COL) pixels[xa + ya * width] = col;
			}
		}
	}	
	
	/**
	 * Setzt den Offset für das Scrolling des Levels auf der (sichtbaren) Spielfläche
	 *
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
