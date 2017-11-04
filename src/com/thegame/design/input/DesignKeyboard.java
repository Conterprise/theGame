package com.thegame.design.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DesignKeyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean left, right, up, down;
	
	public void update() {
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}