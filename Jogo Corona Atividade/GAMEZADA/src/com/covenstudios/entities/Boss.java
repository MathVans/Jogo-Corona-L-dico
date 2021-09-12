package com.covenstudios.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.covenstudios.main.Game;
import com.covenstudios.world.Cam;
import com.covenstudios.world.World;


public class Boss extends Entity{
	
	
	
		public int maskx =42, masky = 42, maskw = 84, maskh=84;// tamanho da caixa de colisão do chefe
		
		private int frames=0, maxframes =15, index =0, maxindex = 2, framesdamagesE = 0;//quanteidade de frames do sprite dele
		

		private BufferedImage[] spritesdir;
		private BufferedImage[] spritesesq;
		
		public int left_dir=1, right_dir=0;
		public int dir=right_dir;
	
		public  int life=200;
		public static int dano=30;
		public  double maxlife=200;
		private boolean isDamagedE;
		public double speed= 0.8;
		private BufferedImage damagedEnemy;
		
		
	public Boss(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		spritesdir = new BufferedImage[3];
		spritesesq = new BufferedImage[3];
		dir = right_dir;
		for(int i=0; i<=2; i++) {
		spritesdir[i] = Game.bossspritesheet.getSprite(0+(i*85), 0,85, 85);
		}
		damagedEnemy = Game.bossspritesheet.getSprite(0, 170, 85, 85);
	
	for(int i=0; i<=2; i++) {
		spritesesq[i] = Game.bossspritesheet.getSprite(0+(i*85), 85,85, 85);
		}
		

	}
				

	public void tick() {
		
		if(!isColiddingWithPlayer() ) {
		if((int)x < Game.player.getX() && World.isFree(z,(int)(x+speed),this.getY()+42)&& !isColidding((int)(x+speed),this.getY()+42)) {
			x+=speed;
			dir = right_dir;
		}
		else if((int)x > Game.player.getX() && World.isFree(z,(int)(x-speed),this.getY()+42) && !isColidding((int)(x-speed),this.getY()+42)
					) {
			x-=speed;
			dir = left_dir;
		} 
		
		if((int)y > Game.player.getY()&& World.isFree(z,this.getX(),(int)(y-speed+42))&& !isColidding(this.getX(),(int)(y-speed+42))) {
			
			y-=speed;
		}else if((int)y < Game.player.getY()&& World.isFree(z,this.getX(),(int)(y+speed+42))&& !isColidding(this.getX(),(int)(y+speed+42))) {
			
			y+=speed;
		
		}
		
			frames++;
			if(frames==maxframes) {
				frames =0;
				index++;
				if(index>maxindex) {
					index=0;
				}
				
				if(isDamagedE) {
					framesdamagesE++;
						if(framesdamagesE==2) {
							isDamagedE=false;
							
							framesdamagesE = 0;
						}
					
					
				}
			}		
		
	}else{//Dano no player
		
		if(Game.rand.nextInt(100)<10)
			
			
		
		Game.player.life-= Game.rand.nextInt(dano);
		
		Player.damaged = true;
		
		//System.out.println("Vida:"+ Game.player.life);
		
	
		}
		
		isColiddingWithBubble();

		if(life<=0) {
			
			Game.player.pontuação+=100;
			destroySelf();
		}
				
				
		
		}
	
	
public boolean isColidding(int xnext, int ynext) {//Metodo criar retangulos de colisão
	 Rectangle currentEnemy = new Rectangle(xnext+maskx, ynext+masky,maskw, maskh);
	 
	 
		for(int i=0; i<Game.boss.size();i++)
		{
			Boss e = Game.boss.get(i);	
			if(e==this)
				continue;
		Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), maskw, maskh);
		
		if(currentEnemy.intersects(targetEnemy)) {
			return true;
			}
		}
	return false;
}


public void destroySelf() {
	
	Game.boss.remove(this);
	
}

public void isColiddingWithBubble() {//Metodo criar colisão das bubbles

		for(int i=0; i<Game.bubbles.size();i++)
		{
			Entity e = Game.bubbles.get(i);	
			
					
		if(e.isColidding(this, e)) {
			isDamagedE = true;
		//	Sound.effectedEnemy.play(;)
			
			life-=10;
			
			Game.bubbles.remove(i);
			
			return;
		}
				
	
		}

}


public boolean isColiddingWithPlayer() {//Metodo para reconhecer Colisão com o player
	Rectangle currentEnemy = new Rectangle(this.getX(),this.getY(),maskw, maskh);
	Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16, 32);
	return player.intersects(currentEnemy); 	
	
}



	 

	public void render(Graphics g) {
		
//		super.render(g);
		if(isDamagedE) {
			
			
			g.setColor(Color.red);
			g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(80/80)*80,10);
			g.setColor(Color.green);
			g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(int) ((life/maxlife)*80),10);
			
		
			g.drawImage(damagedEnemy, this.getX()-Cam.x, this.getY()-Cam.y,null);
			
	}else {
		
		g.setColor(Color.red);
		g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(80/80)*80,10);
		g.setColor(Color.green);
		g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(int) ((life/maxlife)*80),10);
		
		if(dir == right_dir) {
			
			g.drawImage(spritesdir[index], this.getX()-Cam.x, this.getY()-Cam.y,null);
			
		}else if(dir == left_dir) {
			
			g.drawImage(spritesesq[index], this.getX()-Cam.x, this.getY()-Cam.y,null);
		}
		//	g.setColor(Color.BLUE);
		//	g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y, maskw, maskh);
	}




}
}




