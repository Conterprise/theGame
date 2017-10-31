package com.thegame.leveldesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.thegame.game.collectable.Collectable;
import com.thegame.game.mob.Mob;
import com.thegame.game.mob.Player;
import com.thegame.game.tile.Tile;

/**
 * @author vens
 *
 */
public class LevelDraft extends Observable {
	
	private String levelname;
	private int width, height;
	private int[] map;
	private int[] background;

	private List<Tile> tiles = new ArrayList<Tile>();
	private List<Collectable> collectables = new ArrayList<Collectable>();
	private List<Player> player = new ArrayList<Player>();
	private List<Mob> mob = new ArrayList<Mob>();
		

	/*
	 * GETTER
	 */
	
	public String getLevelname() {
		return levelname;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getMap() {
		return map;
	}

	public int[] getBackground() {
		return background;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public List<Collectable> getCollectables() {
		return collectables;
	}

	public List<Player> getPlayer() {
		return player;
	}

	public List<Mob> getMob() {
		return mob;
	}
	
	
	
	
	/*
	 * SETTER
	 */

	public void setLevelname(String levelname) {
		this.levelname = levelname;
		setChanged();
		notifyObservers();
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new int[width * height];
		setChanged();
		notifyObservers();
	}

	public void setMap(int[] map) {
		this.map = map;
		setChanged();
		notifyObservers();
	}

	public void setBackground(int[] background) {
		this.background = background;
		setChanged();
		notifyObservers();
	}

	public void addTile(Tile tile, int x, int y) {
		this.tiles.add(tile);
		setChanged();
		notifyObservers();
	}

	public void addCollectable(Collectable collectable) {
		this.collectables.add(collectable);
		setChanged();
		notifyObservers();
	}

	public void addPlayer(Player player) {
		this.player.add(player);
		setChanged();
		notifyObservers();
	}

	public void addMob(Mob mob) {
		this.mob.add(mob);
		setChanged();
		notifyObservers();
	}


}
