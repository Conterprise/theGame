package com.thegame.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.thegame.game.entity.Entity;
import com.thegame.game.graphics.Layer;
import com.thegame.game.graphics.Screen;
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
	private int[] background;
	private int xScroll, yScroll;

	private List<Mob> players = new ArrayList<Mob>();

	public static Level spawn = new Level("/levels/spawn/map.png", "/levels/spawn/bg.png");
	public static Level night = new Level("/levels/night/map.png", "/levels/night/bg.png");

	/**
	 * Instantiates a new level.
	 *
	 * @param width the width
	 * @param height the height
	 * @param bgPath the bg path
	 */
	public Level(int width, int height, String bgPath) {
		loadBackground(bgPath);
		this.width = width;
		this.height = height;
		generateLevel();
	}

	/**
	 * Instantiates a new level.
	 *
	 * @param mapPath the map path
	 * @param bgPath the bg path
	 */
	public Level(String mapPath, String bgPath) {
		loadBackground(bgPath);
		loadLevel(mapPath);
		generateLevel();
	}
	
	/**
	 * Lädt das Aussehen des Levels aus Bilddatei auf lokalem Speicher
	 *
	 * @param mapPath the map path
	 */
	protected void loadLevel(String mapPath) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(mapPath));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];			
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException ex) {
			System.out.println("Exception! Could not load level file!");
		}
		
	}
	
	/**
	 * Lädt das Hintergrundbild aus Bilddatei auf lokalem Speicher
	 *
	 * @param bgPath the bg path
	 */
	protected void loadBackground(String bgPath) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(bgPath));
			int w = bgwidth = image.getWidth();
			int h = bgheight = image.getHeight();
			background = new int[w * h];			
			image.getRGB(0, 0, w, h, background, 0, w);
		} catch (IOException ex) {
			System.out.println("Exception! Could not load background file!");
		}
		
	}

	/**
	 * Generiert alle Tiles aus dem Level (Aussehen/Struktur/Form des Levels)
	 */
	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
		tile_size = 16;
	}

	/** 
	 * Aktualisiert das Level und alle Objekt im Level
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
	}
	
	/**
	 * Speichert die rechte Pixel-Spalte des Hintergrundbildes
	 *
	 * @param firstCol the first col
	 * @return the int[]
	 */
	//speichert die erste Spalte
	private int[] saveLeftCol(int[] firstCol){
		for(int i = 0; i < bgheight; i++){
			firstCol[i] = background[i * bgwidth];
		}
		return firstCol;
	}
	
	/**
	 * Verschiebt alle Pixelspalten des Hintergrundbildes nach links
	 */
	//schiebt alle übrigen Pixel nach links
	private void shiftPixelsToLeft(){
		int m = 0;
		int n = 0;
		do{
			background[m] = background[m + 1];
			if(n != bgwidth - 2){
				m++;
				n++;
			}
			else{
				m = m + 2;
				n = 0;
			}
		}while(m < background.length - 1);		
	}
	
	/**
	 * Füllt die rechte Pixelspalte des Hintergrundbildes
	 *
	 * @param firstCol the first col
	 */
	//fuellt die letzte Spalte auf
	public void fillRightCol(int[] firstCol){
		int y = bgwidth - 1;
		int s = 0;
		do{
			background[y] = firstCol[s];
			y += bgwidth;
			s++;
		}while(y < background.length);
	}
	
	/**
	 * Speichert die linke Pixel-Spalte des Hintergrundbildes
	 *
	 * @param lastCol the last col
	 * @return the int[]
	 */
	//speichert die letzte Spalte
	private int[] saveRightCol(int[] lastCol){
		for(int i = 0; i < bgheight; i++){
			lastCol[i] = background[(bgwidth-1) + i*bgwidth];
		}
		return lastCol;
	}
	
	/**
	 * Verschiebt alle Pixelspalten des Hintergrundbildes nach rechts
	 */
	//schiebt alle übrigen Pixel nach rechts
	private void shiftPixelsToRight(){
		int m = 0;
		int n = 0;
		do{
			background[(bgwidth - 1 - n) + (m * bgwidth)] = background[(bgwidth -2 - n) + (m * bgwidth)];
			if(((bgwidth - 2 - n))== 0){
				m++;
				n=0;
			}
			else{
				n++;
			}
		}while(m < bgheight);		
	}
	
	/**
	 * Füllt die linke Pixelspalte des Hintergrundbildes
	 *
	 * @param lastCol the last col
	 */
	//fuellt die letzte Spalte auf
	public void fillLeftCol(int[] lastCol){
		int y = 0;
		int s = 0;
		do{
			background[y] = lastCol[s];
			y += bgwidth;
			s++;
		}while(y < background.length);
	}

	/**
	 * Liefert true, wenn sich ein solides Tile an der Position auf dem Spielfeld befindet
	 *
	 * @param x the x
	 * @param y the y
	 * @param size the size
	 * @param xOffset the x offset
	 * @param yOffset the y offset
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
	 * Setzt den Offset für das Scrolling des Levels auf (sichtbarer) Spielfläche
	 *
	 * @param xScroll the x scroll
	 * @param yScroll the y scroll
	 */
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	/* (non-Javadoc)
	 * @see com.thegame.game.graphics.Layer#render(com.thegame.game.graphics.Screen)
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
				if (tile != null)
					tile.render(x, y, screen);
			}
		}
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	/**
	 * Fügt ein beliebiges Objekt, das von Entity erbt, in Level ein
	 *
	 * @param e the e
	 */
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player) {
			players.add((Player) e);
		}
	}
	
	/**
	 * Fügt eine Spielfigur in Level ein
	 *
	 * @param player the player
	 */
	public void addPlayer(Mob player) {
		player.init(this);
		players.add(player);
	}

	/**
	 * Liefert alle Spieler in diesem Level
	 * 
	 * @return List of als Mob in this Level
	 */
	public List<Mob> getPlayers() {
		return players;
	}

	/**
	 * Liefert die Spielfigur an Stelle index der Spielerliste
	 *
	 * @param index the index of Mob in list
	 * @return Mob at position index
	 */
	public Mob getPlayerAt(int index) {
		return players.get(index);
	}

	/**
	 * Liefert eine Liste aller Spielfiguren in einem bestimmten Umkreis
	 *
	 * @param e the e
	 * @param radius the radius
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
	 * @param x the x
	 * @param y the y
	 * @return the tile
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Screen.ALPHA_COL) return null;
		if (tiles[x + y * width] == Tile.col_grass) return Tile.grass;
		if (tiles[x + y * width] == Tile.col_rock) return Tile.rock;

		return Tile.voidTile;
	}
}
