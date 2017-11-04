package com.thegame.design.component;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.border.TitledBorder;

import com.thegame.design.DesignLevel;

public class InfoSettings extends DComponent {

	private static final long serialVersionUID = 5973601515480696750L;
	
	public InfoSettings(ActionListener listener) {
		super(listener);

		this.setBorder(new TitledBorder("Einstellungen"));
	}
}
