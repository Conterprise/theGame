package com.thegame.game.events.types;

import com.thegame.game.events.Event;

public class MouseClickedEvent extends MouseButtonEvent {

	public MouseClickedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_CLICKED);
	}
	
}
