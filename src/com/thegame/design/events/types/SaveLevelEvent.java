package com.thegame.design.events.types;

import com.thegame.game.events.Event;
import com.thegame.game.events.types.ActionEvent;

public class SaveLevelEvent extends ActionEvent {
	
	public SaveLevelEvent() {
		super(Event.Type.ACTION_SAVE_LEVEL);
	}
}
