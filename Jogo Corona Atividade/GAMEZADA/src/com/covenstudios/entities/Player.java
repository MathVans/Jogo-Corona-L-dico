package com.covenstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.covenstudios.main.Game;
import com.covenstudios.main.Sound;
import com.covenstudios.world.Cam;
import com.covenstudios.world.World;


//Classe do Player

public class Player extends Entity  {
	//Sprites do Player
	
	private BufferedImage[] rightplayer;
	private BufferedImage[] leftplayer;
	
	private BufferedImage rightplayerDamaged;
	private BufferedImage leftplayerDamaged;
	
	//Booleans de Movimentação
	
	public boolean right, up, down, left;
	
	public int left_dir=1, right_dir=0;
	public int dir=right_dir;
	
	//Velocidade
	
	double speed = 1;
	double speedInicial = 1;
	
	//Frames das sprites
	
	private int frames=0, maxframes =9, index =0, maxindex = 8, framesdamages = 0, framesdebuff=0;
	
	//Boolean de controle do Player
	
	private boolean moved;
	private boolean picked;
	private boolean buffed=false;
	static boolean damaged;
	static boolean isDebuffed;
	public boolean jump=false, isJumping = false;
	public boolean jumpUp =false, jumpDown = false;
	
	public int jumpFrames =50, jumpCur= 0;
	public int z = 0, jumpspd = 2;
	
	
	public  int TimeBuff;
	public  double life=100;
	public  double maxlife=100;
	
	public  int pontuação=0;
	
	
	public  double Imunidade=0;
	public  double maxImunidade=100;
	
	
	
	public double limpeza = 0;
	public double maxLimpeza = 200;
	
	public double mx;
	public double my;

	
	public boolean shoot;
	public boolean mouseshoot;
	public boolean havemunit;
	
	

							//Método Contrutor do Player
	///================================================================
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		
		//Setando as Sprites de Player
	
		rightplayer = new BufferedImage[9];
		leftplayer = new BufferedImage[9];
		 rightplayerDamaged = Game.spritesheet.getSprite(0, 64, 16, 32);
		 leftplayerDamaged = Game.spritesheet.getSprite(16, 64, 16, 32);
	
		picked = false;
		damaged = false;
		
		
		for(int i=0; i<9;i++) {
			
		rightplayer[i] = Game.playerspritesheet.getSprite(0+(i*32), 0, 16, 32);
		
		}
		for(int i=0; i<9;i++) {
			
			leftplayer[i] = Game.playerspritesheet.getSprite(0+(i*32), 32, 16, 32);
		
			}
		
		
	}
	

	//Método Tick do Player
///================================================================
	
	public void tick() {
		
		if(jump) {
			if(isJumping ==false) {
				jump=false;
				
				isJumping=true;
				
				jumpUp=true;
				
				
			}	
		}
		
		if(isJumping == true) {
			
			if(jumpUp) {
				
				jumpCur+=jumpspd;
			
			}
			else if(jumpDown) {
				
				jumpCur-=jumpspd;
			
				if(jumpCur<=0) {
					System.out.println(jumpCur);
					isJumping = false;
					jumpUp =false;
					jumpDown = false;
					
				}
			}
			
			z=jumpCur;
			
			if(jumpCur>=jumpFrames) {
				
				jumpUp=false;
				jumpDown=true;
				
			}
			
	}
		
		
		
		moved = false;//Caso não haja movimentação
		
		if(right && World.isFree(z,(int)(x+speed),this.getY()+16)) 
		{//Verifica a posição do player, e se a tecla foi pressionada
			//Ativa o Moved
			//E seta a direção para a movimentada
			moved = true;
			
			dir=right_dir; 
			//System.out.println("direita");
			
			x+=speed;
			
		}else if(left && World.isFree(z,(int)(x-speed),this.getY()+16)) {
			moved = true;
			dir=left_dir;
		//	System.out.println("esquerda");
			x-=speed;
			
		}
		if(up && World.isFree(z,this.getX(),(int)(y-speed+16))) {
			moved = true;
		//	System.out.println("cima");
			y-=speed;
		
		}else if (down && World.isFree(z,this.getX(),(int)(y+speed+16))) {
			moved = true;
		//System.out.println("baixo");
			y+=speed;
			
		}
		
		//Verifica se o player esta com debuff
		
		if (isDebuffed == true) {
				Game.player.speed = 0.5; 
		}
		if(isDebuffed == false) {
			Game.player.speed = Game.player.speedInicial; 
	}
		
		//Caso o player esteja se movimentando, seus frames de sprite se moverão
		
		if (moved == true) {
			frames++;
			if(frames==maxframes) {
				frames =0;
				index++;
				if(index>maxindex) {
					index=0;
				}
			}
		}
		//Checa colisão com os objetos do game
		
		checkCollisionLifepack();
	
		checkCollisionVacina();
		
		checkCollisionSabao();
		
		checkCollisionGun();
		
		//Verifica se esta recebendo dano, e caso esteja muda sua sprite para recebendo dano
		if(damaged) {
			framesdamages++;
				if(framesdamages==20) {
					
					damaged=false;
					
					framesdamages = 0;
				}
			
			
		}
		//Caso esteja com debuff, realiza um timer para resetar o debuff.
		if(isDebuffed) {
			framesdebuff++;
				if(framesdebuff==120) {
					
					isDebuffed=false;
					
					framesdebuff = 0;
				}
			
			
		}
		
		
		
		//Caso tenha 
		if(picked && limpeza!=0) {
			
				if(shoot){
					Sound.pop.play();
			//criar balas e atirar
			
			this.shoot=false;
			
			 int dy=0;
			 int dx=0;
			 
			 if(dir == right_dir) {
				 
				 dx += 1;
			 }else {
				 
				 dx = -1;
			 }
			 
		//System.out.println("ATIRANDO");
			
			Bubbleshoot bubbles = new Bubbleshoot(this.getX(), this.getY()+10,10,10,Entity.BUBBLE_EN,dx,dy);
			Game.bubbles.add(bubbles);
			
			limpeza-=2;

			if(limpeza<=0) {
				
				havemunit=false;
				
			}
			if(Game.bubbles.size()>=40) {
				
				Game.bubbles.clear();
				
			}
			
		} 
		
		 if(mouseshoot) {
			 Sound.pop.play();
			
			mouseshoot = false;

				double angle = Math.atan2( this.my - (this.getY()+10 - Cam.y), this.mx -( this.getX()+10 - Cam.x));
				// System.out.println(angle);
				 
			 double dy= Math.sin(angle);
			 double dx= Math.cos(angle);
			 
			 int py=0;
			 int px=0;
			
			 if(dir == right_dir) {
				 	px=18;
				 
				 
			 }else {
				 px = -8;
			
				 
			 }

			
			Bubbleshoot bubbles = new Bubbleshoot(this.getX()+px, this.getY()+py+8,10,10,Entity.BUBBLE_EN,dx,dy);
			
			Game.bubbles.add(bubbles);
			
			limpeza-=2;

			if(limpeza<=0) {
				
				
				limpeza=0;
			}
			if(Game.bubbles.size()>=40) {
				
				Game.bubbles.clear();
				
			}
			
			
		}	
				
		}
			
		
		
		if(this.life<=0) {
			
			Game.gameState ="GAME_OVER";
		}
		
		//System.out.println(Game.player.speed);
		
		Cam.x= Cam.varCam(this.getX()-(Game.WIDTH/2),0 ,World.WIDTH*16- Game.WIDTH);
		Cam.y= Cam.varCam(this.getY()-(Game.HEIGHT/2),0 ,World.HEIGHT*16- Game.HEIGHT);
		
		}
	
	
	
	
	
	
	public void checkCollisionVacina() {
		for(int i= 0; i <Game.entities.size();i++) {
			
			 Entity atual = Game.entities.get(i);
			 
			 if(atual instanceof Vacina) {
				 
			 if(atual.isColidding(this, atual)){
				 
				 this.Imunidade += 25;
				 
				// System.out.println("imunidade"+Imunidade);
				 
				Game.entities.remove(atual);
				
				if(Imunidade >= 100) {
					
					speed+=0.2;
					speedInicial+=0.2;
					
					this.Imunidade -= 100;
					
				}
					return;
				} 
			 }		
		}	
	}
	
	
	
	public void checkCollisionGun() {
		
		for(int i= 0; i <Game.entities.size();i++) {
			
			 Entity atual = Game.entities.get(i);
			 
			 if(atual instanceof Gun) {
				 
			 if(atual.isColidding(this, atual)){
				 
				
				 	picked = true;
				 	
					Game.entities.remove(atual);
					
					return;
				}
				
				 
			 }
				 
		}	 
			
			
		
	
	}
	
	
	public void checkCollisionLifepack() {
		
		for(int i= 0; i <Game.entities.size();i++) {
			
			 Entity atual = Game.entities.get(i);
			 
			 if(atual instanceof LifePacks) {
				 
			 if(atual.isColidding(this, atual)){
				 
				 life+=20;
				 
				if(life>100) 
					
					life=100;
				
					Game.entities.remove(atual);
					
					return;
				}
				
				 
			 }
				 
			 
			
			
		}
		
		
		
		
	}
	
	
	public void checkCollisionSabao() {
		
		for(int i= 0; i <Game.entities.size();i++) {
			
			 Entity atual = Game.entities.get(i);
			 
			 if(atual instanceof Sabao) {
				 
			 if(atual.isColidding(this, atual)){
				 
				 havemunit = true;
				 
				 this.limpeza += 50;
				 
				Game.entities.remove(atual);
				
				if(limpeza>=200) {
					
					limpeza=200;
					Game.entities.remove(atual);
					return;
				}
				
				return;
			 }		
		}	
	}
	}
	
	public void render(Graphics g) {
	
		
		if(!damaged) {
			
//	g.setColor(Color.BLUE);
//	  g.drawRect(this.getX()-Cam.x, this.getY()-Cam.y,16,32);
			
		if(dir==right_dir) {
			
		g.drawImage(rightplayer[index],this.getX()-Cam.x, this.getY()-Cam.y-z, null);
		
			if(picked) {
				
				g.drawImage(Entity.GUN_RIGHT, this.getX()+7-Cam.x, this.getY()-Cam.y-4-z, null);
				
				
			}
		
		}else if (dir==left_dir) {
			
			g.drawImage(leftplayer[index],this.getX()-Cam.x, this.getY()-Cam.y-z, null);
			
			if(picked) {
				//armaesquerda
				
				g.drawImage(Entity.GUN_LEFT, this.getX()-Cam.x-7, this.getY()-Cam.y-3-z, null);
				
			}
		}
		
		}else {
			
			if(dir==right_dir) {
				
				g.drawImage(rightplayerDamaged,this.getX()-Cam.x, this.getY()-Cam.y-z, null);	

				if(picked) {
					
					g.drawImage(Entity.GUN_RIGHT, this.getX()+7-Cam.x, this.getY()-Cam.y-4-z, null);
					
					
				}
				
				
			}else if (dir==left_dir) {
				
				g.drawImage(leftplayerDamaged,this.getX()-Cam.x, this.getY()-Cam.y-z, null);
				if(picked) {
					//armaesquerda
					g.drawImage(Entity.GUN_LEFT, this.getX()-Cam.x-7, this.getY()-Cam.y-3-z, null);
					
				}
					
				
			}
			
			
			
			
			
			
		}
		if(isJumping) {
			g.setColor(Color.BLACK);
			g.fillOval(this.getX()-Cam.x+4, this.getY()+24-Cam.y,8,8);
		}
		
		
	
		/*
		if(!damaged) {
		if(dir==right_dir && picked == true) {
			g.drawImage(rightplayerArmed[index],this.getX()-Cam.x, this.getY()-Cam.y, null);
			}else if (dir==left_dir && picked == true) {
				g.drawImage(leftplayerArmed[index],this.getX()-Cam.x, this.getY()-Cam.y, null);
			}
			*/
		
	
	}
	
}


