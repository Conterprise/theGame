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

public class Level extends Layer {

	protected int width, height;
	protected int bgwidth, bgheight;
	protected int[] tiles;
	protected int tile_size;
	
	private int[] background;
	private int xScroll, yScroll;
	
	private List<Mob> players = new ArrayList<Mob>();
	
	public static Level spawn = new Level("/levels/spawn/map.png", "/levels/spawn/bg_night.png");

	public Level(int width, int height, String bgPath) {
		loadBackground(bgPath);
		this.width = width;
		this.height = height;
		generateLevel();
	}

	public Level(String mapPath, String bgPath) {
		loadBackground(bgPath);
		loadLevel(mapPath);
		generateLevel();
	}
	
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

	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
		tile_size = 16;
	}

	public void update() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		remove();
	}

	private void remove() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

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

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player) {
			players.add((Player) e);
		}
	}
	
	public void addPlayer(Mob player) {
		player.init(this);
		players.add(player);
	}

	public List<Mob> getPlayers() {
		return players;
	}

	public Mob getPlayerAt(int index) {
		return players.get(index);
	}

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


	// Grass = 0xFF00
	// Flower = 0xFFFF00
	// Rock = 0x7F7F00
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Screen.ALPHA_COL) return null;
		if (tiles[x + y * width] == Tile.col_grass) return Tile.grass;
		if (tiles[x + y * width] == Tile.col_rock) return Tile.rock;

		return Tile.voidTile;
	}
}
