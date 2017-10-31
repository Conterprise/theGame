package com.thegame.leveldesign.view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.thegame.leveldesign.LevelDesigner;

public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private LevelDesigner controller;
	private List<JToggleButton> options = new ArrayList<JToggleButton>();
	
	public OptionsPanel(LevelDesigner controller) {
		this.controller = controller;
		setLayout(new GridLayout(15,1));
				
		initButtons();
	}
	
	private void initButtons() {
		JButton btn_new = new JButton("Neuer Entwurf");
		btn_new.setActionCommand("CREATE_NEW_DRAFT");
		btn_new.addActionListener(controller.getActionListener());
		add(btn_new);

		addToggleOption("Tile: Gras", "ADD_TILE_GRASS");
		addToggleOption("Tile: Rock", "ADD_TILE_ROCK");
		addToggleOption("Player: Elfe", "ADD_PLAYER_ELF");
		addToggleOption("Mob: Knight", "ADD_MOB_KNIGHT");
		addToggleOption("Mob: Knight2", "ADD_MOB_KNIGHT2");
		addToggleOption("Coin: Bronze ONE", "ADD_COIN_BRONZE_ONE");
		addToggleOption("Coin: Bronze HEART", "ADD_COIN_BRONZE_HEART");
		addToggleOption("Coin: Bronze STAR", "ADD_COIN_BRONZE_STAR");
		addToggleOption("Coin: Silver ONE", "ADD_COIN_SILVER_ONE");
		addToggleOption("Coin: Silver HEART", "ADD_COIN_SILVER_HEART");
		addToggleOption("Coin: Silver STAR", "ADD_COIN_SILVER_STAR");
		addToggleOption("Coin: Gold ONE", "ADD_COIN_GOLD_ONE");
		addToggleOption("Coin: Gold HEART", "ADD_COIN_GOLD_HEART");
		addToggleOption("Coin: Gold STAR", "ADD_COIN_GOLD_STAR");
	}

	private void addToggleOption(String title, String command) {
		JToggleButton btn = new JToggleButton(title);
		btn.setActionCommand(command);
		btn.addActionListener(controller.getActionListener());
		options.add(btn);
		add(btn);
	}
}
