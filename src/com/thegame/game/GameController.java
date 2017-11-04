package com.thegame.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import com.thegame.game.config.XmlLevelConfig;
import com.thegame.game.events.Event;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.Layer;
import com.thegame.game.graphics.Screen;
import com.thegame.game.input.Keyboard;
import com.thegame.game.input.Mouse;
import com.thegame.game.level.Level;
import com.thegame.game.mob.Knight;
import com.thegame.game.mob.Knight2;
import com.thegame.game.mob.Player;

/**
 * The Class GameController.
 * Controller für das Spiel. Umfasst alle Komponenten nach MVC-Struktur.
 */
public class GameController extends Canvas implements Runnable, EventListener {
	
	private static final long serialVersionUID = 1L;
	private static int width = 1024;
	private static int height = 480; //width / 16 * 9;
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
	
	/**
	 * Konstruktor - erzeugt eine Instanz von GameController
	 */
	public GameController() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		
		File levelFile = new File("/home/vens/test.xml");
		level = new Level(50, 50);
		XmlLevelConfig.loadLevelConfig(level, levelFile);
		
		player = new Player("Vens", 50, 50, key);		
		level.add(player);		
		level.add(new Knight(100, 100));		
		level.add(new Knight2(200, 200));
		
		addLayer(level);
				
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	/**
	 * Fügt einen Layer zum LayerStack hinzu.
	 * Layer sind Schichten, die übereinander auf der Spielfläche gerendert werden (bspw. Level)
	 *
	 * @param layer the layer
	 */
	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}

	/**
	 * Startet die GameLoop (Spieltakt für Update und Rendering)
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	/**
	 * Stoppt die GameLoop
	 */
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * run-Funktion, die bei jedem Spieltakt von der GameLoop aufgerufen wird
	 * @see java.lang.Runnable#run()
	 */
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
	
	/**
	 * Wird ausgeführt, wenn der EventListener ein neues Event meldet
	 * @see com.thegame.game.events.EventListener#onEvent(com.thegame.game.events.Event)
	 */
	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}

	/**
	 * Aktualisiert das Spiel. Wird durch GameLoop aufgerufen.
	 */
	public void update() {
		// gibt es Eingaben?
		key.update();
		
		// Aktualisiere alle Layer (bspw. Level)
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).update();
		}
	}

	/**
	 * Zeichne das Spiel auf Spielfläche
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		int xScroll = player.getX() - screen.width / 2;
		int yScroll = (int) (player.getY() - screen.height * 0.8);
		level.setScroll(xScroll, yScroll);
		
		// render layer
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).render(screen);
		}
		
		// übertrage jedes Pixel von Screen
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		// Zeichne Spielfläche als Bild (image ist das Bild von pixels[])
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	

	
	/*
	 * GETTER and SETTER
	 */
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
}
