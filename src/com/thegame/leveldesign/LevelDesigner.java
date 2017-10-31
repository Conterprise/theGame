package com.thegame.leveldesign;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.thegame.game.collectable.Collectable;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.level.Level;
import com.thegame.game.mob.Knight;
import com.thegame.game.mob.Knight2;
import com.thegame.game.mob.Player;
import com.thegame.game.tile.Tile;
import com.thegame.leveldesign.view.LevelDesignView;


public class LevelDesigner {

	public static String title = "{TAG} - The Awesome Game - LEVEL DESIGNER";
	private static int width = 1024;
	private static int height = width / 16 * 9;
	
	private JFrame frame;
	private LevelDesignView view;
	private LevelDraft draft;
	private String actionMode;
	
	public LevelDesigner() {	
		draft = new LevelDraft();
		view = new LevelDesignView(this, width, height);
		frame = new JFrame();
		
		draft.addObserver(view.getDraftPanel());
		
		frame.add(view);
	}

	public void start() {
		// todo: abfrage levelgröße, width/height
		newDraft(1600, 480);
	}
	
	public void newDraft(int width, int height) {
		draft = new LevelDraft();
		draft.setSize(width, height);
	}

	public JFrame getFrame() {
		return frame;
	}

	public Component getView() {
		return view;
	}
	
	public ActionListener getActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e == null || e.getActionCommand() == null)
					return;
				
				actionMode = e.getActionCommand();
			}
		};
	}
	
	public MouseListener getMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (actionMode != null && !"".equals(actionMode)) {
					int x = e.getX();
					int y = e.getY();
					
					switch (actionMode) {
						case "ADD_TILE_GRASS": 
							draft.addTile(Tile.grass, x, y); break;
						case "ADD_TILE_ROCK":
							draft.addTile(Tile.rock, x, y); break;
						case "ADD_PLAYER_ELF":
							draft.addPlayer(new Player("draftplayer", x, y, null)); break;
						case "ADD_MOB_KNIGHT":
							draft.addMob(new Knight(x, y)); break;
						case "ADD_MOB_KNIGHT2":
							draft.addMob(new Knight2(x, y)); break;
						case "ADD_COIN_BRONZE_ONE":
							draft.addCollectable(new Collectable(SpriteSheet.bronze_one, x, y)); break;
						case "ADD_COIN_BRONZE_HEART":
							draft.addCollectable(new Collectable(SpriteSheet.bronze_heart, x, y)); break;
						case "ADD_COIN_BRONZE_STAR":
							draft.addCollectable(new Collectable(SpriteSheet.bronze_star, x, y)); break;
						case "ADD_COIN_SILVER_ONE":
							draft.addCollectable(new Collectable(SpriteSheet.silver_one, x, y)); break;
						case "ADD_COIN_SILVER_HEART":
							draft.addCollectable(new Collectable(SpriteSheet.silver_heart, x, y)); break;
						case "ADD_COIN_SILVER_STAR":
							draft.addCollectable(new Collectable(SpriteSheet.silver_star, x, y)); break;
						case "ADD_COIN_GOLD_ONE":
							draft.addCollectable(new Collectable(SpriteSheet.gold_one, x, y)); break;
						case "ADD_COIN_GOLD_HEART":
							draft.addCollectable(new Collectable(SpriteSheet.gold_heart, x, y)); break;
						case "ADD_COIN_GOLD_STAR":
							draft.addCollectable(new Collectable(SpriteSheet.gold_star, x, y)); break;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};
	}
	
	/**
	 * Lädt eine Level-Map aus Bilddatei auf lokalem Speicher
	 * und speichert die Pixel-Werte in tiles[]
	 *
	 * @param mapPath
	 *            the map path
	 */
	protected void loadLevel(String mapPath) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(mapPath));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			int[] tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
			
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					//draft.addTile(tile, x, y);
				}
			}
		} catch (IOException ex) {
			System.out.println("Exception! Could not load level file!");
		}

	}
}
