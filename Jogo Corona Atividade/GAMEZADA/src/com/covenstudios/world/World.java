package com.covenstudios.world;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.covenstudios.entities.Vacina;
import com.covenstudios.graphics.Spritesheet;
import com.covenstudios.graphics.UI;
import com.covenstudios.entities.Boss;
import com.covenstudios.entities.Enemy;
import com.covenstudios.entities.EnemyRed;
import com.covenstudios.entities.Entity;
import com.covenstudios.entities.Gun;
import com.covenstudios.entities.LifePacks;
import com.covenstudios.entities.Player;
import com.covenstudios.entities.Sabao;
import com.covenstudios.main.Game;



public class World {
	
	public static Tile[] Tiles;

	public static int WIDTH, HEIGHT;
	
	public static int TILE_SIZE = 16;// Tamanho das tiles
	
    public World (String path) {
	try {
		BufferedImage map =ImageIO.read(getClass().getResource(path));
		
		
		//Função que verifica o pixel da Imagem e seta o mundo de acordo com as condições
		//Desde blocos a entidades no mundo
		//Fonte Curso da Danki Code
		
		int[]pixels = new int[map.getWidth()*map.getHeight()];
		Tiles = new Tile[map.getWidth()*map.getHeight()];
		map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
		
		WIDTH  = map.getWidth();
		HEIGHT = map.getHeight();
		
		for(int xx = 0; xx <map.getWidth();xx++) {
			for(int yy = 0; yy <map.getHeight();yy++) {
			
				int pixelAtual = pixels[xx+(yy*map.getWidth())];
				
			
				Tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					
						switch(pixelAtual)
						{
						    case 0xFF000000:
						    	//FLOOR
						      Tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						    break;
						    
						    case 0xFFFFFFFF:
						    	//WALL
				Tiles[xx+(yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
							break;
						
					
						case 0xFFD1008B:
								//LIFEPACK
								LifePacks pack =new LifePacks(xx*16,yy*16, 32,32,Entity.SABAO_EN);
								
								Game.entities.add(pack);
								pack.setMask( 8,8,8,8);
								Game.lifepacks.add(pack); 
								break;
								
						case 0xFFFFD800:
							//VACINA
							Vacina vacina =new Vacina(xx*16,yy*16, 32,32,Entity.VACINA_EN);
							
							Game.entities.add(vacina);
							vacina.setMask( 8,8,8,8);
							Game.vacinas.add(vacina); 
							break;
							
						case 0xFF4CFF00:
							//GEL
							Sabao sabao =new Sabao(xx*16,yy*16, 32,32,Entity.GEL_EN);
							
							Game.entities.add(sabao);
							sabao.setMask( 8,8,16,16);
							Game.sabaos.add(sabao); 
							break;
							
										
								
						case 0xFFFF0000:
							//CORONA
							Enemy corona =new Enemy(xx*16,yy*16, 32,32,Entity.CORONA_EN);
						//	Game.entities.add(corona);
							corona.setMask( 8,8,32,32);
							 Game.enemies.add(corona);
							 break;
							 
						case 0xFFFF7FB6:
							//CORONAred
							EnemyRed coronared =new EnemyRed(xx*16,yy*16, 32,32,Entity.CORONARED_EN);
						//	Game.entities.add(corona);
							coronared.setMask( 8,8,32,32);
							 Game.enemiesred.add(coronared);
							break;	
							
						case 0xFF56FFCF:
							//BOSS
							Boss boss = new Boss(xx*16,yy*16, 84,84,Entity.BOSS_EN);
						//	Game.entities.add(corona);
							boss.setMask( 42,42,84,84);
							 Game.boss.add(boss);
							break;	

						    case 0xFF0026FF: 
								//player
								Game.player.setX(xx*16);
								Game.player.setY(yy*16);
								break;
								
								
							case 0xFF21007F:
								//ARMA
								Gun gun =new Gun(xx*16,yy*16, 32,32,Entity.ARMA_EN);
								Game.entities.add(gun);
								gun.setMask( 8,8,8,8);
								Game.guns.add(gun);
								
								
								 
								break;
								
						
					}
					
			}
		
			
			
			
		}
	
					
					
			
		
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
    }
    
    //Verifica se os tiles a frente são instancias do tipo WallTile e se for liga a colisão 
    public static boolean isFree(int z, int xnext, int ynext) {
		
    	int x1 = xnext/ TILE_SIZE;
    	int y1 = ynext/ TILE_SIZE;
    	
    	int x2 = (xnext + TILE_SIZE-2) / TILE_SIZE;
    	int y2 = ynext / TILE_SIZE;
    	
    	int x3 = xnext / TILE_SIZE;
    	int y3 = (ynext + TILE_SIZE-2) / TILE_SIZE;
    	
    	int x4 = (xnext + TILE_SIZE-2) / TILE_SIZE;
    	int y4 = (ynext + TILE_SIZE-2) / TILE_SIZE;
    	
    	if(!((Tiles[x1+(y1*World.WIDTH)] instanceof WallTile)||
    			(Tiles[x2+(y2*World.WIDTH)] instanceof WallTile)||
    			(Tiles[x3+(y3*World.WIDTH)] instanceof WallTile)||
    			(Tiles[x4+(y4*World.WIDTH)] instanceof WallTile))){
    		return true;
    	}
    	if(z > 0) {
    		
    		return true;
    	}
    	return false;
    }
    
    //Resete do Game
    
    public void restartGame(String level) {
		
		Game.spritesheet= new Spritesheet("/spritesheet.png");
		Game.playerspritesheet= new Spritesheet("/playercorona.png");
		Game.mapaspritesheet = new Spritesheet("/"+level);
					
		
							//ArrayLists de Entities
		Game.entities.clear();
		Game.enemies.clear();
		Game.enemiesred.clear();
		Game.lifepacks.clear();
		Game.vacinas.clear();
		Game.guns.clear();
		Game.sabaos.clear();
		Game.boss.clear();
		
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.boss = new ArrayList<Boss>();
		Game.enemiesred = new ArrayList<EnemyRed>();
		Game.lifepacks = new ArrayList<LifePacks>();
		Game.vacinas = new ArrayList<Vacina>();
		Game.guns = new ArrayList<Gun>();
		Game.sabaos = new ArrayList<Sabao>();
		
							//Objeto das entities e outros
		Game.ui = new UI();
		Game.player = new Player(100, 100, 16, 32,Game.spritesheet.getSprite(0 ,0, 16, 32));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		
		
		
		
		
		}
    
    //Renderiza na tela o mundo proporcional a posição da camera.
    public void render(Graphics g) {
    	
    	int xstart = Cam.x>>4;
		int ystart = Cam.y>>4;	
			int xfinal = xstart+(Game.WIDTH>>4)+2;
			int yfinal = ystart+(Game.HEIGHT>>4)+2;
			
    		for(int xx=xstart;xx<xfinal;xx++) {
    			for(int yy=ystart;yy<yfinal;yy++) {
    				if(xx<0||yy<0||xx>=WIDTH||yy>=HEIGHT) 
    					continue;
    				Tile tile = Tiles[xx+ (yy*WIDTH)];
    				tile.render(g);
    			}
    		}
    	
    }

}

