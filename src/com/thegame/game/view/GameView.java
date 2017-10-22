package com.thegame.game.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends JPanel {

	private static final long serialVersionUID = -3045195787154601628L;

	public GameView() {
		initComponents();
	}
	
	private void initComponents() {
		ImageIcon icon = new ImageIcon("background.png");
		JLabel label = new JLabel(icon);
		
		add(label);
	}

}
