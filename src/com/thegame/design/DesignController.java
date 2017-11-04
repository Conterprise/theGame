package com.thegame.design;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import javax.swing.JFileChooser;

import com.thegame.design.component.ActionPanel;
import com.thegame.design.component.InfoPanel;
import com.thegame.design.input.DesignActions;
import com.thegame.design.input.DesignKeyboard;
import com.thegame.design.input.DesignMouse;
import com.thegame.game.events.Event;
import com.thegame.game.events.EventListener;
import com.thegame.game.graphics.Screen;

public class DesignController extends Canvas implements EventListener {

	private static final long serialVersionUID = 1L;
	private int width = 1600;
	private int height = width / 16 * 9;
	private int xScroll = 0, yScroll = 0;

	private DesignKeyboard key;
	private DesignMouse mouse;
	private DesignActions actions;
	private DesignLevel level;

	private ActionPanel actionPanel;
	private InfoPanel infoPanel;

	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	
	public DesignController(int width, int height) {
		screen = new Screen(width, height);
		key = new DesignKeyboard();
		mouse = new DesignMouse(this);
		actions = new DesignActions(this);
		level = new DesignLevel(50, 50);

		actionPanel = new ActionPanel(this, actions);
		infoPanel = new InfoPanel(this, actions);
		
		Dimension size = new Dimension(width, height);
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
						
		addKeyListener(key);
		
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	@Override
	public void onEvent(Event event) {
		level.onEvent(event);
		render();
	}
	
	public void update() {
		// gibt es Eingaben?
		key.update();

		if (key.left) xScroll -= 16;
		if (key.right) xScroll += 16;
		if (key.up) yScroll -= 16;
		if (key.down) yScroll += 16;
		if (key.left || key.right || key.up || key.down)
			render();
				
		// Komponenten der View aktualisieren
		actionPanel.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		level.setScroll(xScroll, yScroll);
		
		// render draft
		level.render(screen);
		
		// übertrage jedes Pixel von Screen
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		// Zeichne Designfläche
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public Component getActionPanel() {
		return actionPanel;
	}

	public Component getInfoPanel() {
		return infoPanel;
	}

	public DesignLevel getLevel() {
		return level;
	}

	public void start() {
		level.setInitialized(true);
	}

	public static File getFileDialog(String title) {
		final JFileChooser chooser = new JFileChooser(title);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        final File file = new File("/home");

        chooser.setCurrentDirectory(file);
        chooser.setVisible(true);
        final int result = chooser.showOpenDialog(null);
        
        File inputFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {            
            inputFile = chooser.getSelectedFile();
        }
        chooser.setVisible(false);   
        
        return inputFile;
	}
}
