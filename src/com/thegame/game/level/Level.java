package com.thegame.game.level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thegame.game.collectable.Collectable;
import com.thegame.game.config.XmlLevelConfig;
import com.thegame.game.entity.Entity;
import com.thegame.game.graphics.Layer;
import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.mob.Mob;
import com.thegame.game.mob.Player;
import com.thegame.game.tile.Tile;

/**
 * The Class Level. 
 * Umfasst alle Objekte eines Levels.
 */
public class Level extends Layer {

	protected int width, height;
	protected int bgwidth, bgheight;
	protected int[] tiles;
	protected int tile_size;
	protected String levelname;
	protected int[] background;
	protected int xScroll, yScroll;

	protected List<Player> players = new ArrayList<Player>();
	protected List<Mob> mobs = new ArrayList<Mob>();
	protected List<Collectable> collectables = new ArrayList<Collectable>();

	public static List<Level> levels = new ArrayList<Level>();

	/**
	 * Instantiates a new level.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new int[width * height];
		generateLevel();
	}

	/**
	 * Instantiates a new level.
	 *
	 * @param xmlFile
	 *            the xml config of a level
	 */
	public Level(File xmlFile) {
		XmlLevelConfig.loadLevelConfig(this, xmlFile);
		generateLevel();
	}

	/**
	 * Generiert alle Tiles aus dem Level (Aussehen/Struktur/Form des Levels)
	 */
	protected void generateLevel() {
		tile_size = 16;
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
				generateCollectable(x, y);
			}
		}
	}

	/**
	 * Aktualisiert das Level und alle Objekt im Level
	 * 
	 * @see com.thegame.game.graphics.Layer#update()
	 */
	public void update() {
		int[] savedCol = new int[bgheight];
		int direction = 0;

		// update player
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();

			if (players.get(i) instanceof Player) {
				direction += players.get(i).getDirection();
			}
		}

		// update collectables
		for (int i = 0; i < collectables.size(); i++) {
			collectables.get(i).update();
		}

		// remove objects
		remove();

		// update background
		if (direction < 0) {
			savedCol = saveRightCol(savedCol);
			shiftPixelsToRight();
			fillLeftCol(savedCol);
		} else if (direction > 0) {
			savedCol = saveLeftCol(savedCol);
			shiftPixelsToLeft();
			fillRightCol(savedCol);
		}
	}

	/**
	 * Entfernt Objekte aus dem Level, die bereits entfernt wurden
	 */
	private void remove() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
		for (int i = 0; i < collectables.size(); i++) {
			if (collectables.get(i).isRemoved()) collectables.remove(i);
		}
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).isRemoved()) mobs.remove(i);
		}
	}

	/**
	 * Speichert die rechte Pixel-Spalte des Hintergrundbildes
	 *
	 * @param firstCol
	 *            the first col
	 * @return the int[]
	 */
	// speichert die erste Spalte
	private int[] saveLeftCol(int[] firstCol) {
		for (int i = 0; i < bgheight; i++) {
			firstCol[i] = background[i * bgwidth];
		}
		return firstCol;
	}

	/**
	 * Verschiebt alle Pixelspalten des Hintergrundbildes nach links
	 */
	// schiebt alle übrigen Pixel nach links
	private void shiftPixelsToLeft() {
		int m = 0;
		int n = 0;
		do {
			background[m] = background[m + 1];
			if (n != bgwidth - 2) {
				m++;
				n++;
			} else {
				m = m + 2;
				n = 0;
			}
		} while (m < background.length - 1);
	}

	/**
	 * Füllt die rechte Pixelspalte des Hintergrundbildes
	 *
	 * @param firstCol
	 *            the first col
	 */
	// fuellt die letzte Spalte auf
	public void fillRightCol(int[] firstCol) {
		int y = bgwidth - 1;
		int s = 0;
		do {
			background[y] = firstCol[s];
			y += bgwidth;
			s++;
		} while (y < background.length);
	}

	/**
	 * Speichert die linke Pixel-Spalte des Hintergrundbildes
	 *
	 * @param lastCol
	 *            the last col
	 * @return the int[]
	 */
	// speichert die letzte Spalte
	private int[] saveRightCol(int[] lastCol) {
		for (int i = 0; i < bgheight; i++) {
			lastCol[i] = background[(bgwidth - 1) + i * bgwidth];
		}
		return lastCol;
	}

	/**
	 * Verschiebt alle Pixelspalten des Hintergrundbildes nach rechts
	 */
	// schiebt alle übrigen Pixel nach rechts
	private void shiftPixelsToRight() {
		int m = 0;
		int n = 0;
		do {
			background[(bgwidth - 1 - n) + (m * bgwidth)] = background[(bgwidth - 2 - n) + (m * bgwidth)];
			if (((bgwidth - 2 - n)) == 0) {
				m++;
				n = 0;
			} else {
				n++;
			}
		} while (m < bgheight);
	}

	/**
	 * Füllt die linke Pixelspalte des Hintergrundbildes
	 *
	 * @param lastCol
	 *            the last col
	 */
	// fuellt die letzte Spalte auf
	public void fillLeftCol(int[] lastCol) {
		int y = 0;
		int s = 0;
		do {
			background[y] = lastCol[s];
			y += bgwidth;
			s++;
		} while (y < background.length);
	}

	/**
	 * Liefert true, wenn sich ein solides Tile an der Position auf dem
	 * Spielfeld befindet
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param size
	 *            the size
	 * @param xOffset
	 *            the x offset
	 * @param yOffset
	 *            the y offset
	 * @return true, if successful
	 */
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	/**
	 * Setzt den Offset für das Scrolling des Levels auf (sichtbarer)
	 * Spielfläche
	 *
	 * @param xScroll
	 *            the x scroll
	 * @param yScroll
	 *            the y scroll
	 */
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.thegame.game.graphics.Layer#render(com.thegame.game.graphics.Screen)
	 */
	public void render(Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		screen.renderBackground(this.background, this.bgwidth, this.bgheight);

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				Tile tile = getTile(x, y);
				if (tile != null) tile.render(x, y, screen);
			}
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}

		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render(screen);
		}

		for (int i = 0; i < collectables.size(); i++) {
			collectables.get(i).render(screen);
		}
	}

	/**
	 * Fügt ein beliebiges Objekt, das von Entity erbt, in Level ein
	 *
	 * @param e
	 *            the e
	 */
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player) {
			((Player) e).init(this);
			players.add((Player) e);
		} else if (e instanceof Mob) {
			((Mob) e).init(this);
			mobs.add((Mob) e);
		} else if (e instanceof Collectable) {
			collectables.add((Collectable) e);
		}
	}

	/**
	 * Liefert alle Spieler in diesem Level
	 * 
	 * @return List of Players in this Level
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Liefert alle KI-Spieler in diesem Level
	 * 
	 * @return List of Mob in this Level
	 */
	public List<Mob> getMobs() {
		return mobs;
	}


	/**
	 * Liefert alle Collectables in diesem Level
	 * 
	 * @return List of Collectables in this Level
	 */
	public List<Collectable> getCollectables() {
		return collectables;
	}
	
	public String getLevelname() {
		return levelname;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getBgWidth() {
		return bgwidth;
	}

	public int getBgHeight() {
		return bgheight;
	}

	public int[] getBackground() {
		return background;
	}
	
	public int[] getTiles() {
		return tiles;
	}
	
	/**
	 * Liefert die Spielfigur an Stelle index der Spielerliste
	 *
	 * @param index
	 *            the index of Mob in list
	 * @return Mob at position index
	 */
	public Mob getPlayerAt(int index) {
		return players.get(index);
	}

	/**
	 * Liefert eine Liste aller Spielfiguren in einem bestimmten Umkreis
	 *
	 * @param e
	 *            the e
	 * @param radius
	 *            the radius
	 * @return the players
	 */
	public List<Mob> getPlayers(Entity e, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < players.size(); i++) {
			Mob player = players.get(i);
			int x = (int) player.getX();
			int y = (int) player.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(player);
		}
		return result;
	}

	/**
	 * Liefert die Tile an einer Position der Level-Map
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the tile
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		return Tile.getTile(tiles[x + y * width]);
	}
	

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new int[width * height];
	}

	public void setTiles(int[] tiles) {
		this.tiles = tiles;
	}

	public void setBackground(int[] background, int width, int height) {
		this.background = background;
		this.bgwidth = width;
		this.bgheight = height;
	}

	/**
	 * Generiert anhand der Pixelfarbe auf Level-Map ein Collectable und
	 * speichert das Objekt in der lokalen Liste collectables, falls ein
	 * entsprechender Farbcode gefunden wird.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void generateCollectable(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return;

		int x0 = x * tile_size + (tile_size / 2);
		int y0 = y * tile_size + (tile_size / 2);

		switch (tiles[x + y * width]) {
		case Collectable.col_bronze_one:
			add(new Collectable(SpriteSheet.bronze_one, x0, y0));
			break;
		case Collectable.col_bronze_heart:
			add(new Collectable(SpriteSheet.bronze_heart, x0, y0));
			break;
		case Collectable.col_bronze_star:
			add(new Collectable(SpriteSheet.bronze_star, x0, y0));
			break;
		case Collectable.col_silver_one:
			add(new Collectable(SpriteSheet.silver_one, x0, y0));
			break;
		case Collectable.col_silver_heart:
			add(new Collectable(SpriteSheet.silver_heart, x0, y0));
			break;
		case Collectable.col_silver_star:
			add(new Collectable(SpriteSheet.silver_star, x0, y0));
			break;
		case Collectable.col_gold_one:
			add(new Collectable(SpriteSheet.gold_one, x0, y0));
			break;
		case Collectable.col_gold_heart:
			add(new Collectable(SpriteSheet.gold_heart, x0, y0));
			break;
		case Collectable.col_gold_star:
			add(new Collectable(SpriteSheet.gold_star, x0, y0));
			break;
		case Tile.col_grass:
		case Tile.col_rock:
		case Screen.ALPHA_COL:
			break;
		default:
			System.out.println("Unbekannte Farbe: " + tiles[x + y * width]);
		}
	}
}
