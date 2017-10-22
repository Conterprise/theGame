package com.thegame.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.thegame.game.events.Event;
import com.thegame.game.events.EventListener;
import com.thegame.game.input.Keyboard;
import com.thegame.game.input.Mouse;
import com.thegame.game.level.Level;
import com.thegame.game.view.Screen;

public class GameController extends Canvas implements Runnable, EventListener {
	private static final long serialVersionUID = 1L;

	private static int width = 600;
	private static int height = width / 16 * 9;
	private static int scale = 3;
	public static String title = "The Awesome Game";

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	//private Player player;
	private boolean running = false;
	
	private static UIManager uiManager;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public GameController() { 
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		uiManager = new UIManager();
		frame = new JFrame();
		key = new Keyboard();
				
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	public static UIManager getUIManager() {
		return uiManager;
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
		// Game Loop
	}

	public void update() {
		//
	}

	public void render() {
		//
	}

	@Override
	public void onEvent(Event event) {
		//
	}
}
