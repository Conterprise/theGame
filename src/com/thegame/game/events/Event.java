package com.thegame.game.events;

public class Event {
	
	public static enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED, 
		MOUSE_CLICKED, 
		ACTION_DO, 
		ACTION_LOAD_BACKGROUND, 
		ACTION_TOGGLE_OBJECTS, 
		ACTION_LOAD_LEVEL, 
		ACTION_SAVE_LEVEL
	}
	
	private Type type;
	boolean handled;
	
	protected Event(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}

}
