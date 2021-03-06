package com.thegame.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {

	private static JFrame menuFrame;
	private static JFrame playFrame;
	
	private static JButton starten;
	private static JButton einstellungen;
	private static JButton credits;
	private static JButton ende;


	public static void main(String[] args) {
		
		menuFrame = new JFrame("Menü");
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setSize(400,400);
		menuFrame.setLayout(null);
		menuFrame.setVisible(true);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setTitle("Menü");
		menuFrame.setResizable(false);
		createButtons(menuFrame);
		
	}
	
	public static void createButtons(JFrame frame){
		starten = new JButton("Spiel starten");
		starten.setBounds(120, 40, 160, 40);
		starten.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				play();
			}
		});
		frame.add(starten);
		
		einstellungen = new JButton("Einstellungen");
		einstellungen.setBounds(120, 120, 160, 40);
		einstellungen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//showOptions();
			}
		});
		frame.add(einstellungen);
		
		credits = new JButton("Credits");
		credits.setBounds(120, 200, 160, 40);
		credits.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showOptionDialog(null, "v 0.1", "Credits", 
						 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
			}
		});
		frame.add(credits);
		
		ende = new JButton("Ende");
		ende.setBounds(120, 280, 160, 40);
		ende.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		frame.add(ende);
	}
	
	
	
	public static void play(){
		menuFrame.setVisible(false);
		menuFrame.dispose();
		menuFrame = null;
		
		GameController game = new GameController();
		playFrame = game.getFrame();
		
		playFrame.setResizable(false);
		playFrame.setTitle(GameController.title);
		playFrame.add(game);
		playFrame.pack();
		playFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playFrame.setLocationRelativeTo(null);
		playFrame.setVisible(true);
		playFrame.repaint();

		game.start();
	}

}