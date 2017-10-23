package com.thegame.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.thegame.game.events.Event;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.Layer;
import com.thegame.game.graphics.Screen;
import com.thegame.game.input.Keyboard;
import com.thegame.game.input.Mouse;
import com.thegame.game.level.Level;
import com.thegame.game.mob.Player;

public class GameController extends Canvas implements Runnable, EventListener {
	private static final long serialVersionUID = 1L;

	private static int width = 1024;
	private static int height = 512; //width / 16 * 9;
	private static int scale = 1;
	public static String title = "The Awesome Game";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private List<Layer> layerStack = new ArrayList<Layer>();
	
	public GameController() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		player = new Player("Vens", 50, 50, key);
		level.addPlayer(player);
		addLayer(level);
				
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	public JFrame getFrame() {
		return this.frame;
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(GameController.title + " | " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}

	public void update() {
		key.update();
		
		//update layer
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).update();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		//int xScroll = player.getX() - screen.width / 2;
		//int yScroll = player.getY() - screen.height / 2;
		//level.setScroll(xScroll, yScroll);
		
		//render layer
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).render(screen);
		}
		
		//font.render(50, 50, -10, "Hey this is a long Text to output\nand continues in new line...", screen);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
}
