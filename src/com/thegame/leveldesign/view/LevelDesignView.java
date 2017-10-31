package com.thegame.leveldesign.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.thegame.leveldesign.LevelDesigner;

public class LevelDesignView extends JPanel {

	private static final long serialVersionUID = 1L;

	private LevelDesigner controller;
	private int width, height;

	private OptionsPanel options;
	private DraftPanel draftPanel;
	private JScrollPane pane = new JScrollPane(draftPanel);
	
	public LevelDesignView(LevelDesigner controller, int width, int height) {
		this.controller = controller;
		this.width = width;
		this.height = height;
		
		options = new OptionsPanel(controller);
		draftPanel = new DraftPanel(controller, width, height);
		
		Dimension size = new Dimension(width, height);
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		setPreferredSize(size);
		setSize(size);
		
		pane.add(draftPanel);
		pane.setPreferredSize(size);
		pane.setSize(size);

		add(options, BorderLayout.EAST);
		add(pane, BorderLayout.CENTER);
	}	
	
	public DraftPanel getDraftPanel() {
		return draftPanel;
	}
}
