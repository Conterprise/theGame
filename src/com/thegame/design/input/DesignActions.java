package com.thegame.design.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.thegame.design.events.types.DoActionEvent;
import com.thegame.design.events.types.LoadBackgroundEvent;
import com.thegame.design.events.types.LoadLevelEvent;
import com.thegame.design.events.types.SaveLevelEvent;
import com.thegame.design.events.types.ToggleObjectsEvent;
import com.thegame.game.events.EventListener;

public class DesignActions implements ActionListener {
	
	private EventListener eventListener;

	public DesignActions(EventListener listener) {
		eventListener = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null || e.getActionCommand() == null)
			return;
		
		String cmd = e.getActionCommand();
		
		switch (cmd) {
			case "LOAD_LEVEL":
				LoadLevelEvent load_event = new LoadLevelEvent();
				eventListener.onEvent(load_event);
				break;
			case "SAVE_LEVEL":
				SaveLevelEvent save_event = new SaveLevelEvent();
				eventListener.onEvent(save_event);
				break;
			case "LOAD_BACKGROUND_IMAGE":
				LoadBackgroundEvent bg_event = new LoadBackgroundEvent();
				eventListener.onEvent(bg_event);
				break;
			case "TOGGLE_TILES": 
			case "TOGGLE_PLAYER": 
			case "TOGGLE_MOBS": 
			case "TOGGLE_COINS": 
				ToggleObjectsEvent to_event = new ToggleObjectsEvent(cmd);
				eventListener.onEvent(to_event);
				break;
			default:
				DoActionEvent do_event = new DoActionEvent(cmd);
				eventListener.onEvent(do_event);
		}
	}

}
