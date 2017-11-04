package com.thegame.design;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.thegame.design.events.types.DoActionEvent;
import com.thegame.design.events.types.LoadBackgroundEvent;
import com.thegame.design.events.types.LoadLevelEvent;
import com.thegame.design.events.types.SaveLevelEvent;
import com.thegame.design.events.types.ToggleObjectsEvent;
import com.thegame.game.collectable.Collectable;
import com.thegame.game.config.XmlLevelConfig;
import com.thegame.game.events.Event;
import com.thegame.game.events.types.MouseClickedEvent;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.level.Level;
import com.thegame.game.mob.Knight;
import com.thegame.game.mob.Knight2;
import com.thegame.game.mob.Player;
import com.thegame.game.tile.Tile;

public class DesignLevel extends Level {
	
	public DesignLevel(int width, int height) {
		super(width, height);
	}

	private boolean initialized;
	private String toggleMode;
	private String actionMode;
	
	/**
	 * Lädt das Hintergrundbild aus Bilddatei auf lokalem Speicher
	 *
	 * @param bgPath
	 *            the bg path
	 */
	protected void loadBackground(File bgFile) {
		try {
			BufferedImage image = ImageIO.read(bgFile);
			int w = bgwidth = image.getWidth();
			int h = bgheight = image.getHeight();
			background = new int[w * h];
			image.getRGB(0, 0, w, h, background, 0, w);
		} catch (IOException ex) {
			System.out.println("Exception! Could not load background file!");
		}

	}		
	
	
	
	/*
	 * GETTER
	 */

	public boolean isInitialized() {
		return initialized;
	}

	public String getToggleMode() {
		return toggleMode;
	}
	
	public String getLevelname() {
		return levelname;
	}
	
	
	/*
	 * SETTER
	 */

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void addTile(Tile tile, int x, int y) {
		tile.x = x >> 4;
		tile.y = y >> 4;
				
		tiles[tile.x + tile.y * width] = tile.getMapColor();
		
		System.out.println("Added Tile on load x=" + x + " y=" + y + " tile=" + tile);
	}

	public void onEvent(Event event) {
		if (event instanceof MouseClickedEvent) {
			MouseClickedEvent e = (MouseClickedEvent) event;
			System.out.println("Clicked x=" + e.getX() + " y=" + e.getY() + " command=" + actionMode);
			if (actionMode != null && !"".equals(actionMode)) {
				handleMouseAction(e.getX(), e.getY());
			}
		}
		
		if (event instanceof ToggleObjectsEvent) {
			ToggleObjectsEvent e = (ToggleObjectsEvent) event;
			toggleMode = e.getCommand();
		}
		
		if (event instanceof DoActionEvent) {
			DoActionEvent e = (DoActionEvent) event;
			actionMode = e.getCommand();
		}
		
		if (event instanceof LoadBackgroundEvent) {
			LoadBackgroundEvent e = (LoadBackgroundEvent) event;
			File bg = DesignController.getFileDialog("Öffnen: Hintergrundgrafik wählen");
			loadBackground(bg);
		}
		
		if (event instanceof LoadLevelEvent) {
			LoadLevelEvent e = (LoadLevelEvent) event;
			File xmlFile = DesignController.getFileDialog("Öffnen: Leveldatei wählen");
			XmlLevelConfig.loadLevelConfig(this, xmlFile);
		}
		
		if (event instanceof SaveLevelEvent) {
			SaveLevelEvent e = (SaveLevelEvent) event;
			File xmlFile = DesignController.getFileDialog("Speichern unter: Leveldatei wählen");
			XmlLevelConfig.writeLevelConfig(this, xmlFile);
		}
	}

	private void handleMouseAction(int x0, int y0) {
		int x = x0 + xScroll;
		int y = y0 + yScroll;
		
		if (actionMode == null)
			return;
		
		String[] actions = actionMode.split("_");
		
		if (actions.length > 2 && "ADD".equals(actions[0])) {
			switch (actions[1]) {
			case "TILE":
				switch(actions[2]) {
				case "GRASS": addTile(Tile.grass, x, y); break;
				case "ROCK": addTile(Tile.rock, x, y); break;
				}
				break;
				
			case "PLAYER":
				switch(actions[2]) {
				case "ELF": add(new Player("playername", x, y, null)); break;
				}
				break;
				
			case "MOB":
				switch(actions[2]) {
				case "KNIGHT": add(new Knight(x, y)); break;
				case "KNIGHT2": add(new Knight2(x, y)); break;
				}
				break;
				
			case "COIN":
				if (actions.length < 4) break;
				switch(actions[2]) {
				case "BRONZE": 
					switch (actions[3]) {
					case "ONE": add(new Collectable(SpriteSheet.bronze_one, x, y)); break; 
					case "HEART": add(new Collectable(SpriteSheet.bronze_heart, x, y)); break; 
					case "STAR": add(new Collectable(SpriteSheet.bronze_star, x, y)); break; 
					}
					break;
					
				case "SILVER": 
					switch (actions[3]) {
					case "ONE": add(new Collectable(SpriteSheet.silver_one, x, y)); break; 
					case "HEART": add(new Collectable(SpriteSheet.silver_heart, x, y)); break; 
					case "STAR": add(new Collectable(SpriteSheet.silver_star, x, y)); break; 
					}
					break;
				
				case "GOLD": 
					switch (actions[3]) {
					case "ONE": add(new Collectable(SpriteSheet.gold_one, x, y)); break; 
					case "HEART": add(new Collectable(SpriteSheet.gold_heart, x, y)); break; 
					case "STAR": add(new Collectable(SpriteSheet.gold_star, x, y)); break; 
					}
					break;
				
			}
			break;				
			}
		}
	}


}
