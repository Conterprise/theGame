package com.thegame.design.component;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.thegame.design.DesignController;
import com.thegame.design.Designer;
import com.thegame.design.input.DesignActions;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DesignController controller;
	private ActionOptions options;
	private ActionGroup actions;
	
	public ActionPanel(DesignController designController, DesignActions listener) {
		this.controller = designController;
		setLayout(new GridLayout(2,1));
		
		options = new ActionOptions(listener);
		actions = new ActionGroup(listener);
		
		add(options);
		add(actions);		
		setVisible(true);
	}
	
	public void update() {
		if (controller != null && controller.getLevel() != null)
			actions.updateToggleMode(controller.getLevel().getToggleMode());
	}
}
