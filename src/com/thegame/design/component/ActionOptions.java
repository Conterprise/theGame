package com.thegame.design.component;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

public class ActionOptions extends DComponent {

	private static final long serialVersionUID = 1L;
	private List<JToggleButton> options = new ArrayList<JToggleButton>();
    private ButtonGroup optionsGrp = new ButtonGroup();
	
	public ActionOptions(ActionListener listener) {
		super(listener);

		initComponents();
		
		this.setBorder(new TitledBorder("Funktionen"));
	}
	
	private void initComponents() {
		setLayout(new GridLayout(8,1));
		
		JButton btn_new = new JButton("Neuer Entwurf");
		btn_new.setActionCommand("CREATE_NEW_DRAFT");
		btn_new.addActionListener(listener);
		add(btn_new);
		
		JButton btn_bg = new JButton("Lade Hintergundbild");
		btn_bg.setActionCommand("LOAD_BACKGROUND_IMAGE");
		btn_bg.addActionListener(listener);
		add(btn_bg);
		
		JButton btn_load = new JButton("Level laden");
		btn_load.setActionCommand("LOAD_LEVEL");
		btn_load.addActionListener(listener);
		add(btn_load);
		
		JButton btn_save = new JButton("Level speichern");
		btn_save.setActionCommand("SAVE_LEVEL");
		btn_save.addActionListener(listener);
		add(btn_save);
		
		addToggleOption("Tiles hinzufügen", "TOGGLE_TILES");
		addToggleOption("Player hinzufügen", "TOGGLE_PLAYER");
		addToggleOption("Mobs hinzufügen", "TOGGLE_MOBS");
		addToggleOption("Coins hinzufügen", "TOGGLE_COINS");
	}

	private void addToggleOption(String title, String command) {
		JToggleButton btn = new JToggleButton(title);
		btn.setActionCommand(command);
		btn.addActionListener(listener);
		options.add(btn);
		optionsGrp.add(btn);
		add(btn);
	}
}
