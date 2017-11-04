package com.thegame.design.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.thegame.game.collectable.Collectable;
import com.thegame.game.events.EventListener;
import com.thegame.game.events.types.MouseClickedEvent;
import com.thegame.game.events.types.MouseMovedEvent;
import com.thegame.game.events.types.MousePressedEvent;
import com.thegame.game.events.types.MouseReleasedEvent;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.mob.Knight;
import com.thegame.game.mob.Knight2;
import com.thegame.game.mob.Player;
import com.thegame.game.tile.Tile;

public class DesignMouse implements MouseListener, MouseMotionListener {

	
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	private EventListener eventListener;
	
	public DesignMouse(EventListener listener) {
		eventListener = listener;
	}
	
	public static int getX() {
		return mouseX;
	}
	
	public static int getY() {
		return mouseY;
	}
	
	public static int getButton() {
		return mouseB;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), true);
		eventListener.onEvent(event);
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), false);
		eventListener.onEvent(event);
	}
	
	public void mouseClicked(MouseEvent e) {
		MouseClickedEvent event = new MouseClickedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
		
		MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = MouseEvent.NOBUTTON;
		
		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
		eventListener.onEvent(event);
	}

}
