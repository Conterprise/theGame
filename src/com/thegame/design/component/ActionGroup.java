package com.thegame.design.component;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import com.thegame.design.DesignLevel;
import com.thegame.game.graphics.Sprite;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.tile.Tile;

public class ActionGroup extends DComponent {

	private static final long serialVersionUID = 5973601515480696750L;
	private List<JToggleButton> options = new ArrayList<JToggleButton>();
    private ButtonGroup optionsGrp = new ButtonGroup();
    private String currentToggleMode = null;
	
	public ActionGroup(ActionListener listener) {
		super(listener);

		this.setBorder(new TitledBorder("Objektexplorer"));
	}

	public void updateToggleMode(String toggleMode) {
		if (toggleMode == null) {
			currentToggleMode = null;
			return;
		}
		
		if (toggleMode.equals(currentToggleMode))
			return;
		
		currentToggleMode = toggleMode;
		
		switch (currentToggleMode) {
			case "TOGGLE_TILES":
				createTilesPanel(); 
				revalidate();
				break;
			case "TOGGLE_PLAYER":
				createPlayerPanel(); 
				revalidate();
				break;
			case "TOGGLE_MOBS":
				createMobsPanel(); 
				revalidate();
				break;
			case "TOGGLE_COINS":
				createCoinsPanel(); 
				revalidate();
				break;
		}
		
	}

	private void createTilesPanel() {
		removeAll();
		setLayout(new GridLayout(2, 1));

		addToggleOption("Grass", "ADD_TILE_GRASS", Tile.grass.sprite);
		addToggleOption("Rock", "ADD_TILE_ROCK", Tile.rock.sprite);
	}

	private void createPlayerPanel() {
		removeAll();
		setLayout(new GridLayout(1, 1));

		addToggleOption("Elfe", "ADD_PLAYER_ELF", SpriteSheet.player_run.getSprites()[0]);
	}

	private void createMobsPanel() {
		removeAll();
		setLayout(new GridLayout(2, 1));

		addToggleOption("Knight", "ADD_MOB_KNIGHT", SpriteSheet.knight_run.getSprites()[0]);
		addToggleOption("Knight 2", "ADD_MOB_KNIGHT2", SpriteSheet.knight2_run.getSprites()[0]);		
	}

	private void createCoinsPanel() {
		removeAll();
		setLayout(new GridLayout(9, 1));

		addToggleOption("Bronze ONE", "ADD_COIN_BRONZE_ONE", SpriteSheet.bronze_one.getSprites()[0]);
		addToggleOption("Bronze HEART", "ADD_COIN_BRONZE_HEART", SpriteSheet.bronze_heart.getSprites()[0]);
		addToggleOption("Bronze START", "ADD_COIN_BRONZE_STAR", SpriteSheet.bronze_star.getSprites()[0]);	
		addToggleOption("Silver ONE", "ADD_COIN_SILVER_ONE", SpriteSheet.silver_one.getSprites()[0]);
		addToggleOption("Silver HEART", "ADD_COIN_SILVER_HEART", SpriteSheet.silver_heart.getSprites()[0]);
		addToggleOption("Silver START", "ADD_COIN_SILVER_STAR", SpriteSheet.silver_star.getSprites()[0]);	
		addToggleOption("Gold ONE", "ADD_COIN_GOLD_ONE", SpriteSheet.gold_one.getSprites()[0]);
		addToggleOption("Gold HEART", "ADD_COIN_GOLD_HEART", SpriteSheet.gold_heart.getSprites()[0]);
		addToggleOption("Gold START", "ADD_COIN_GOLD_STAR", SpriteSheet.gold_star.getSprites()[0]);	
	}


	private void addToggleOption(String title, String command, Sprite sprite) {
		JToggleButton btn = new JToggleButton(title);
		btn.setActionCommand(command);
		btn.addActionListener(listener);
		
		if (sprite != null)
			btn.setIcon(new ImageIcon(sprite.getImage()));
		
		options.add(btn);
		optionsGrp.add(btn);
		add(btn);
		
		btn.setVisible(true);
	}
}
