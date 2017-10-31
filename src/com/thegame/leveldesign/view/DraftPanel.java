package com.thegame.leveldesign.view;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Observable;
import java.util.Observer;

import com.thegame.game.collectable.Collectable;
import com.thegame.game.graphics.Screen;
import com.thegame.game.mob.Mob;
import com.thegame.game.mob.Player;
import com.thegame.game.tile.Tile;
import com.thegame.leveldesign.LevelDesigner;
import com.thegame.leveldesign.LevelDraft;

public class DraftPanel extends Canvas implements Observer {

	private static final long serialVersionUID = 1L;
	private LevelDesigner controller;
	private Screen screen;
	private BufferedImage image;
	private int[] pixels;
	private int width, height;
	
	public DraftPanel(LevelDesigner controller, int width, int height) {
		this.controller = controller;
		this.width = width;
		this.height = height;
		
		screen = new Screen(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		addMouseListener(controller.getMouseListener());
		
		setSize(width, height);
	}

	public void render(LevelDraft draft) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		for (Tile tile: draft.getTiles()) {
			tile.render(tile.x, tile.y, screen);
		}
		
		for (Collectable collectable: draft.getCollectables()) {
			collectable.render(screen);
		}
		
		for (Player player: draft.getPlayer()) {
			player.render(screen);
		}
		
		for (Mob mob: draft.getMob()) {
			mob.render(screen);
		}

		// Zeichne Level-Entwurf als Bild (image ist das Bild von pixels[])
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == null || !(o instanceof LevelDraft)) 
			return;
		
		LevelDraft draft = (LevelDraft) o;	
		render(draft);		
	}

}
