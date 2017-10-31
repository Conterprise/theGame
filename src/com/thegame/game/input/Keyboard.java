package com.thegame.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[1000];
	public boolean left, right, jump, run, attack;
	
	public void update() {
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		jump = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_W];
		run = keys[KeyEvent.VK_SHIFT] || keys[KeyEvent.VK_ALT];
		attack = keys[KeyEvent.VK_CONTROL] || keys[KeyEvent.VK_ENTER];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;		
	}

	public void keyTyped(KeyEvent e) {
		
	}

}