package com.covenstudios.entities;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.covenstudios.main.Game;
import com.covenstudios.world.Cam;
import com.covenstudios.world.World;


public class EnemyRed extends Entity{
	
	
	
		public int maskx =8, masky = 8, maskw = 16, maskh=16;
		
		private int frames=0, maxframes =15, index =0, maxindex = 3, framesdamagesE = 0;
		private int  timeCur=0, timer =10;
		
		
		private BufferedImage[] spritesdir;
		private BufferedImage[] spritesesq;
		
		public int left_dir=1, right_dir=0;
		public int dir=right_dir;
	
		public  int life=30;
		public static int dano=4;
		public  double maxlife=30;
		private boolean isDamagedE;
		private boolean isDebuffed=false;
		public double speed= 0.5;
		public double debuff= 0.3;
		private BufferedImage damagedEnemyRed;
		
		
	public EnemyRed(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		spritesdir = new BufferedImage[4];
		spritesesq = new BufferedImage[4];
		dir = right_dir;
		for(int i=0; i<=3; i++) {
		spritesdir[i] = Game.playerspritesheet.getSprite(0+(i*32), 96,32, 32);
		}
		damagedEnemyRed = Game.playerspritesheet.getSprite(288, 96, 32, 32);
	
	for(int i=0; i<=3; i++) {
		spritesesq[i] = Game.playerspritesheet.getSprite(160+(i*32), 96,32, 32);
		}
		
	
	}
				

	public void tick() {
		
		
		if(!isColiddingWithPlayer() ) {
		if((int)x < Game.player.getX() && World.isFree(z,(int)(x+speed),this.getY()+16)&& !isColidding((int)(x+speed),this.getY()+16)) {
			x+=speed;
			dir = right_dir;
		}
		else if((int)x > Game.player.getX() && World.isFree(z,(int)(x-speed),this.getY()+16) && !isColidding((int)(x-speed),this.getY()+16)
					) {
			x-=speed;
			dir = left_dir;

		} 
		
		if((int)y > Game.player.getY()&& World.isFree(z,this.getX(),(int)(y-speed+16))&& !isColidding(this.getX(),(int)(y-speed+16))) {
			
			y-=speed;
			
		}else if((int)y < Game.player.getY()&& World.isFree(z,this.getX(),(int)(y+speed+16))&& !isColidding(this.getX(),(int)(y+speed+16))) {
			
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
		
	}else{
		
		
				//DEBUFF
			
		
		//Dano no player
		if(Game.rand.nextInt(100)<10)
			
			
		
		Game.player.life-= Game.rand.nextInt(dano);
		
		Player.damaged = true;
	
		Game.player.isDebuffed=true;
	
		//System.out.println("Vida:"+ Game.player.life);
		
	
		}
		
		isColiddingWithBubble();

		if(life<=0) {
			
			Game.player.pontuação+=30;
			
			destroySelf();
		}
				
				
	
		}
	
	
public boolean isColidding(int xnext, int ynext) {//Metodo criar retangulos de colisão
	 Rectangle currentEnemy = new Rectangle(xnext+maskx, ynext,maskw, maskh);
	 
	 
		for(int i=0; i<Game.enemiesred.size();i++)
		{
			EnemyRed e = Game.enemiesred.get(i);
			
				if(e==this)
				continue;
			
		Rectangle targetEnemy = new Rectangle(e.getX()+maskx, e.getY()+masky, maskw, maskh);
		
		if(currentEnemy.intersects(targetEnemy)) {
			return true;
			}
		}
	return false;
}


public void destroySelf() {
	
	Game.enemiesred.remove(this);
	
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
	Rectangle currentEnemy = new Rectangle(this.getX(),this.getY(),maskw, maskh+16);
	Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16, 32);
	return player.intersects(currentEnemy); 	
	
}



	 

public void render(Graphics g) {
	
//	super.render(g);
	if(isDamagedE) {
		
		
		g.setColor(Color.red);
		g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(30/30)*30,3);
		g.setColor(Color.green);
		g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(int) ((life/maxlife)*30),3);
		
	
		g.drawImage(damagedEnemyRed, this.getX()-Cam.x, this.getY()-Cam.y,null);
}else {
	g.setColor(Color.red);
	g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(30/30)*30,3);
	g.setColor(Color.green);
	g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y-2,(int) ((life/maxlife)*30),3);
	
	if(dir == right_dir) {
	g.drawImage(spritesdir[index], this.getX()-Cam.x, this.getY()-Cam.y,null);
	}else if(dir == left_dir) {
		
		g.drawImage(spritesesq[index], this.getX()-Cam.x, this.getY()-Cam.y,null);
	}
	/*	g.setColor(Color.BLUE);
		g.fillRect(this.getX()-Cam.x,this.getY()-Cam.y, maskw, maskh);*/
}




}
}




