package com.thegame.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The Class SpriteSheet.
 */
public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
	
	public static SpriteSheet player = new SpriteSheet("/textures/sheets/player_elf.png", 896, 640);
	public static SpriteSheet player_idle = new SpriteSheet(player, 0, 0, 1, 5, 128);
	public static SpriteSheet player_walk = new SpriteSheet(player, 1, 0, 1, 5, 128);
	public static SpriteSheet player_run = new SpriteSheet(player, 2, 0, 1, 5, 128);
	public static SpriteSheet player_jump = new SpriteSheet(player, 3, 0, 1, 5, 128);
	public static SpriteSheet player_attack = new SpriteSheet(player, 4, 0, 1, 5, 128);
	public static SpriteSheet player_hurt = new SpriteSheet(player, 5, 0, 1, 5, 128);
	public static SpriteSheet player_die = new SpriteSheet(player, 6, 0, 1, 5, 128);
	
	public static SpriteSheet knight = new SpriteSheet("/textures/sheets/player_knight.png", 896, 896);
	public static SpriteSheet knight_idle = new SpriteSheet(knight, 0, 0, 1, 7, 128);
	public static SpriteSheet knight_walk = new SpriteSheet(knight, 1, 0, 1, 7, 128);
	public static SpriteSheet knight_run = new SpriteSheet(knight, 2, 0, 1, 7, 128);
	public static SpriteSheet knight_jump = new SpriteSheet(knight, 3, 0, 1, 7, 128);
	public static SpriteSheet knight_attack = new SpriteSheet(knight, 4, 0, 1, 7, 128);
	public static SpriteSheet knight_hurt = new SpriteSheet(knight, 5, 0, 1, 7, 128);
	public static SpriteSheet knight_die = new SpriteSheet(knight, 6, 0, 1, 7, 128);
	
	public static SpriteSheet knight2 = new SpriteSheet("/textures/sheets/player_knight2.png", 896, 896);
	public static SpriteSheet knight2_idle = new SpriteSheet(knight2, 0, 0, 1, 7, 128);
	public static SpriteSheet knight2_walk = new SpriteSheet(knight2, 1, 0, 1, 7, 128);
	public static SpriteSheet knight2_run = new SpriteSheet(knight2, 2, 0, 1, 7, 128);
	public static SpriteSheet knight2_jump = new SpriteSheet(knight2, 3, 0, 1, 7, 128);
	public static SpriteSheet knight2_attack = new SpriteSheet(knight2, 4, 0, 1, 7, 128);
	public static SpriteSheet knight2_hurt = new SpriteSheet(knight2, 5, 0, 1, 7, 128);
	public static SpriteSheet knight2_die = new SpriteSheet(knight2, 6, 0, 1, 7, 128);
	
	private Sprite[] sprites;
	
	/**
	 * Instantiates a new sprite sheet.
	 *
	 * @param sheet the sheet
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param spriteSize the sprite size
	 */
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	/**
	 * Instantiates a new sprite sheet.
	 *
	 * @param path the path
	 * @param size the size
	 */
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	/**
	 * Instantiates a new sprite sheet.
	 *
	 * @param path the path
	 * @param width the width
	 * @param height the height
	 */
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
	}
	
	/**
	 * Lädt des SpriteSheet aus der Bilddatei auf lokalem Speicher
	 */
	public void load() {
		try {
			System.out.print("Trying to load: " + path + " ... ");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			System.out.println(" succeeded!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(" failed!");
		}
	}
	

	
	/*
	 * GETTER and SETTER
	 */
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
}
