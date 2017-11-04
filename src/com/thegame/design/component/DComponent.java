package com.thegame.design.component;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class DComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	protected ActionListener listener;
	
	public DComponent(ActionListener listener) {
		this.listener = listener;
	}
}
