package com.thegame.game.graphics;

/**
 * The Class Sprite.
 * Grafikobjekt, das gezeichnet werden kann
 */
public class Sprite {
	
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x0000FF00);

	/**
	 * Instantiates a new sprite.
	 *
	 * @param sheet the sheet
	 * @param width the width
	 * @param height the height
	 */
	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}

	/**
	 * Instantiates a new sprite.
	 *
	 * @param size the size
	 * @param x the x
	 * @param y the y
	 * @param sheet the sheet
	 */
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[size * size];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	/**
	 * Instantiates a new sprite.
	 *
	 * @param width the width
	 * @param height the height
	 * @param color the color
	 */
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}

	/**
	 * Instantiates a new sprite.
	 *
	 * @param size the size
	 * @param color the color
	 */
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	/**
	 * Instantiates a new sprite.
	 *
	 * @param pixels the pixels
	 * @param width the width
	 * @param height the height
	 */
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	/**
	 * Statisch: Spiegelt das übergebene Grafikobjekt an der vertikalen Achse
	 *
	 * @param sprite the sprite
	 * @return the sprite
	 */
	public static Sprite flipVertical(Sprite sprite) {
		return new Sprite(flipVertical(sprite.pixels, sprite.width, sprite.height), sprite.width, sprite.height);
	}
	
	/**
	 * Hilfsfunktion für vertikale Spiegelung
	 *
	 * @param pixels the pixels
	 * @param width the width
	 * @param height the height
	 * @return the int[]
	 */
	private static int[] flipVertical(int[] pixels, int width, int height) {
		int[] result = new int[width * height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				result[(width-x-1) + y * width] = pixels[x + y * width];
			}
		}
		
		return result;
	}
	
	/**
	 * Statisch: Rotiert das übergebene Grafikobjekt um den Winkel angle
	 *
	 * @param sprite the sprite
	 * @param angle the angle
	 * @return the sprite
	 */
	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	/**
	 * Rotiert das Grafikobjekt
	 *
	 * @param pixels the pixels
	 * @param width the width
	 * @param height the height
	 * @param angle the angle
	 * @return the int[]
	 */
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);

		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xFFFF00FF;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return result;
	}
	
	/**
	 * Hilfsfunktion für x-Rotation
	 *
	 * @param angle the angle
	 * @param x the x
	 * @param y the y
	 * @return the double
	 */
	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}
	
	/**
	 * Hilfsfunktion für y-Rotation
	 *
	 * @param angle the angle
	 * @param x the x
	 * @param y the y
	 * @return the double
	 */
	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}

	/**
	 * Teilt ein SpriteSheet in einzelne Grafikobjekte auf
	 *
	 * @param sheet the sheet
	 * @return the sprite[]
	 */
	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
				
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x+y*sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
	}

	/**
	 * Lädt die Pixelinformationen des Grafikobjekts vom SpriteSheet
	 */
	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}
	

	
	/*
	 * GETTER and SETTER
	 */
	
	private void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
