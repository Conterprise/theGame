package com.thegame.design.component;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.thegame.design.DesignController;
import com.thegame.design.Designer;
import com.thegame.design.input.DesignActions;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private DesignController controller;
	private InfoPreview preview;
	private InfoData data;
	private InfoSettings settings;
	
	public InfoPanel(DesignController designController, DesignActions listener) {
		this.controller = designController;
		setLayout(new GridLayout(1,3));
		
		preview = new InfoPreview(listener);
		data = new InfoData(listener);		
		settings = new InfoSettings(listener);
		
		add(preview);
		add(data);		
		add(settings);		
		setVisible(true);
	}
}
