package com.covenstudios.world;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.covenstudios.main.Game;

public class Tile {
	//Cria os Objetos que formarão o terreno e obstaculos do jogo
	
public static BufferedImage TILE_FLOOR =Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage TILE_WALL =Game.spritesheet.getSprite(32, 0, 32, 32);
		
	
		private int x,y;
		private BufferedImage sprite;
		
		
		public Tile(int x, int y, BufferedImage sprite) {
			this.x=x;
			this.y=y;
			this.sprite=sprite; 
			
			
		}
		
		public void render(Graphics g) {
			g.drawImage(sprite, x-Cam.x, y- Cam.y, null);
		}
		
		
}

