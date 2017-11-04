package com.thegame.design.events.types;

import com.thegame.game.events.Event;
import com.thegame.game.events.types.ActionEvent;

public class LoadLevelEvent extends ActionEvent {
	
	public LoadLevelEvent() {
		super(Event.Type.ACTION_LOAD_LEVEL);
	}
}
