package com.thegame.game.entity;

import java.util.Random;

import com.thegame.game.graphics.Screen;
import com.thegame.game.level.Level;

public class Entity {

	protected int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public Entity() {
		
	}
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
	}
	
	public void render(Screen screen) {
		//
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
}
