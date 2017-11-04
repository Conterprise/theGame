package com.thegame.design.events.types;

import com.thegame.game.events.Event;
import com.thegame.game.events.types.ActionEvent;

public class LoadBackgroundEvent extends ActionEvent {

	public LoadBackgroundEvent() {
		super(Event.Type.ACTION_LOAD_BACKGROUND);
	}
	
}
