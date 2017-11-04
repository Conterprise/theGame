package com.thegame.design;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Designer extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int width = 1600;
	public static final int height = width / 16 * 9;
	public static final int scale = 1;

	public static String title = "{TAG} - The Awesome Game - LEVEL DESIGNER";

	private boolean running = false;
	private Thread thread;	
	private JFrame frame;
	private DesignController controller;

	public Designer() {	
		Dimension size = new Dimension(width * scale, height * scale);
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		setPreferredSize(size);
		setMinimumSize(size);
		
		controller = new DesignController(width * scale, height * scale);
		
		JPanel levelContainer = new JPanel();
		levelContainer.setLayout(new FlowLayout());
		levelContainer.add(controller);
		
		add(levelContainer, BorderLayout.CENTER);
		add(controller.getActionPanel(), BorderLayout.EAST);
		add(controller.getInfoPanel(), BorderLayout.SOUTH);
		
		frame = new JFrame();
	}
	
	public void start() {
		running = true;
		thread = new Thread(this, "Display");
		
		controller.start();
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
	
	@Override
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
				controller.update();
				updates++;
				delta--;
			}
			//controller.render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(Designer.title + " | " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	public JFrame getFrame() {
		return frame;
	}

}
