package com.thegame.design.events.types;

import com.thegame.game.events.Event;
import com.thegame.game.events.types.ActionEvent;

public class DoActionEvent extends ActionEvent {

	private String command;
	
	public DoActionEvent(String command) {
		super(Event.Type.ACTION_DO);
		this.command = command;		
	}
	
	public String getCommand() {
		return command;
	}
}
