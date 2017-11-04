package com.thegame.design.events.types;

import com.thegame.game.events.Event;
import com.thegame.game.events.types.ActionEvent;

public class ToggleObjectsEvent extends ActionEvent {

	private String command;
	
	public ToggleObjectsEvent(String command) {
		super(Event.Type.ACTION_TOGGLE_OBJECTS);
		this.command = command;
	}
	
	public String getCommand() {
		return this.command;
	}
}
