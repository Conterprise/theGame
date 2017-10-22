package com.thegame.game.events;

public class Event {
	
	public static enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED
	}
	
	public static enum Mode {
		SELECT,
		BUILD,
		REMOVE
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
