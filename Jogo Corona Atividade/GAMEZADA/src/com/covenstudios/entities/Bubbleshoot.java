package com.covenstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.covenstudios.world.Cam;

public class Bubbleshoot extends Entity{
	private double dy;
	private double dx;
	private double spd=4.0;

	
	
	public Bubbleshoot(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy) {
		super(x, y, width, height, sprite);
		this.dy=dy;
		this.dx= dx;
		this.sprite = sprite;
		
	}
	
	public void tick() {
		
		x+=dx*spd;
		y+=dy*spd;
		
		
		
	}
	public void render(Graphics g) {
	
		
	g.drawImage(sprite,this.getX()-Cam.x, this.getY()-Cam.y, null);
	//g.setColor(Color.YELLOW);
//	g.fillOval(this.getX()-Cam.x, this.getY()+5-Cam.y,5, 5);
	
		
	}
	
	
	
	
	

}
	
	
	
	

