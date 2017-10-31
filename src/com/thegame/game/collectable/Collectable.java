package com.thegame.game.collectable;

import com.thegame.game.entity.Entity;
import com.thegame.game.graphics.AnimatedSprite;
import com.thegame.game.graphics.Screen;
import com.thegame.game.graphics.SpriteSheet;

public class Collectable extends Entity {

	public static final int col_bronze_one = 0xffff0000;
	public static final int col_bronze_heart = 0xffff0010;
	public static final int col_bronze_star = 0xffff0020;
	public static final int col_silver_one = 0xffb8b8b8;
	public static final int col_silver_heart = 0xffc9c9c9;
	public static final int col_silver_star = 0xffdedede;
	public static final int col_gold_one = 0xffffff00;
	public static final int col_gold_heart = 0xffffff10;
	public static final int col_gold_star = 0xffffff20;
	
	private AnimatedSprite animSprite;
		
	public Collectable(SpriteSheet sheet, int x, int y) {
		animSprite = new AnimatedSprite(sheet, 32, 32, 10);
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		animSprite.update();
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite(false);
		screen.renderSprite((int) (x - 64), (int) (y - 110), sprite, true);
	}
	
}
