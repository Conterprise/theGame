package com.thegame.game;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.thegame.game.model.GameModel;
import com.thegame.game.view.GameView;

public class GameController {

	private GameView view;
	private GameModel model;
	
	public GameController() {
		this.view = new GameView();
		this.model = new GameModel();
	}
	
	
	public Component getView() {
		return this.view;
	}
	
	
	private void loadBackground() {
		BufferedImage img;
		
		try{
			img = ImageIO.read(new File("background.png"));
			
			int width = img.getWidth();
			int height = img.getHeight();
			
			int[][] pixelArray = new int[width][height];
			
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					pixelArray[i][j] = img.getRGB(i, j);
				}
			}
			
			this.model.setBackground(pixelArray);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
