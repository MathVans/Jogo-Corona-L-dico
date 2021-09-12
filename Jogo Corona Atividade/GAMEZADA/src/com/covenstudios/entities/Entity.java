package com.covenstudios.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.covenstudios.main.Game;
import com.covenstudios.world.Cam;



public class Entity {
protected double x;
protected double y;
protected int z;
private int width;
private int height;
public int maskx, masky, mwidth, mheight;

//Sprites das Entidades
//====================================================================//
public BufferedImage sprite;
public static BufferedImage GEL_EN = Game.spritesheet.getSprite(64, 0, 32,32);
public static BufferedImage CORONA_EN = Game.playerspritesheet.getSprite(0, 64,32, 32);
public static BufferedImage BOSS_EN = Game.bossspritesheet.getSprite(0, 85,85, 85);
public static BufferedImage CORONARED_EN = Game.playerspritesheet.getSprite(160, 64,32, 32);
public static BufferedImage VACINA_EN = Game.spritesheet.getSprite(96, 0,32, 32);
public static BufferedImage ARMA_EN = Game.spritesheet.getSprite(0, 32,32, 32);
public static BufferedImage BUBBLE_EN = Game.spritesheet.getSprite(32, 32,16, 16);
public static BufferedImage SABAO_EN = Game.spritesheet.getSprite(128, 0 ,32, 32);
public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(96, 48, 16,32);
public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(64, 48, 16,32);


//Método construtor da Classe

public Entity(int x,int y,int width,int height, BufferedImage sprite) {
	//Configurações das sprites
	this.x = x;
	this.y = y;
	this.width = width;
	this.height=height;
	this.sprite = sprite;
	
	//Configuração da máscara da caixa de colisão
	this.maskx=0;
	this.masky=0;
	this.mwidth=width;
	this.mheight=height;
}
//Seta a mascara de colisão da entidade
public void setMask(int maskx,int masky,int mwidth,int mheight) {
	this.maskx=maskx;
	this.masky=masky;
	this.mwidth=mwidth;
	this.mheight=mheight+8;
	
}


public int getX() {
	return (int)this.x;
}
public int getY() {
	return (int)this.y;
}
public void setX(int x) {
	this.x = x;
}
public void setY(int y) {
	 this.y= y;
}

public int getWidth() {
	return this.width;
}

public int getHeight() {
	return this.height;
}
public void tick() {
	
	
}

//Método que cria retangulos para as entidades, e verifica se estão colidindo
public boolean isColidding(Entity e1, Entity e2) {
	Rectangle entityE1 = new Rectangle(e1.getX()+e1.maskx,e1.getY()+e1.masky,mwidth, mheight);
	Rectangle entityE2 = new Rectangle(e2.getX()+e2.maskx,e2.getY()+e2.masky,mwidth, mheight);
	
	if(entityE1.intersects(entityE2)&& e1.z == e2.z) {
		
		return true;
	}
	
	return false; 	
	
}



public void render(Graphics g) {
	g.drawImage(sprite,this.getX()- Cam.x,this.getY()-Cam.y,null);
//	g.setColor(Color.BLUE);
	//g.fillRect(this.getX()+maskx- Cam.x,this.getY()+masky-Cam.y, mwidth, mheight);
}


}


