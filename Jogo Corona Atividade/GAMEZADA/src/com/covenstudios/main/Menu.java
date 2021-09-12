package com.covenstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;




//Classe men


public class Menu {
	
	private BufferedImage BgMenu = Game.background.getSprite(0, 0, 160, 160);//Cria um buffer da sprite do menu, para que seja desenhado na tela pelo Graphics
	public String[]Options= {"JOGAR","CARREGAR JOGO", "SAIR"};//Opções de carregar o menu
	public int curOptions=0;//Variável para saber a quantidade de opções do meno
	public int maxOptions = Options.length-1;
	
	//Variáveis de controle do menu
	public boolean up, down, enter, podeSair;
	public boolean pause = false;
	public int out=0;
	
	       //Tick do menu caso esteja ativo!!
	//==============================================================//
	public void tick() {
		
		if(up) {
			up=false;
			curOptions--;
				if(curOptions<0) {
					curOptions = maxOptions;
				}
		}else if(down) {
			down=false;
			curOptions++;
			if(curOptions>maxOptions) {
				curOptions = 0;
			}
		}
		//Opção jogar
		if(enter) {
			enter = false;
			if(Options[curOptions] == "JOGAR") {
				
				Game.gameState = "NORMAL";
				
				pause=false;
				
			}
			
			//Opção sair
			
			if(Options[curOptions] == "SAIR") {
				Game.restartgame=true;
				pause=false;
				Game.gameState = "MENU";
				podeSair = true;
			
			}
			if(podeSair) {
				
				System.exit(1);
						}
			}
			
		}
	
					//Função que irá renderizar e desenhar na tela o menu e o pause
	//=====================================================================================//
	
	public void render(Graphics g) {
		if(!pause) {
		//	g.drawImage(BgMenu , 0,0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE, null);
			Graphics2D g2 = (Graphics2D)g; // Cria um objeto do graphics 2D
			g2.setColor(new Color(0,0,0,100));//Deixa o fundo da imagem escurecido
			g.fillRect(0,0,Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);//Retangulo que realiza a função anterior
			
			
			
		}else if(pause){
					//Opções caso esteja em PauseGame
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0,100));
		g.fillRect(0,0,Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		}
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 45));
		g.drawString("Carlos Contra o Corona", 45*Game.SCALE, 50*Game.SCALE);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 30));
		if(!pause) {
		g.drawString("jogar", 85*Game.SCALE, 70*Game.SCALE);
		g.drawString("Carregar Jogo", 85*Game.SCALE, 85*Game.SCALE);
		}else if (pause) {
			g.drawString("resumir", 85*Game.SCALE, 70*Game.SCALE);
			g.drawString("Salvar Jogo", 85*Game.SCALE, 85*Game.SCALE);
		}
		
	
		g.drawString("Sair", 85*Game.SCALE, 100*Game.SCALE);
		
		
		
		
		if(Options[curOptions] == "JOGAR") {
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString(">>", 70*Game.SCALE, 70*Game.SCALE);
			
		}
		
		else if(Options[curOptions] == "CARREGAR JOGO") {
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString(">>", 70*Game.SCALE, 85*Game.SCALE);
			
		}
		
		
		
		else if(Options[curOptions] == "SAIR") {
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString(">>", 70*Game.SCALE, 100*Game.SCALE);
			
		}
		
		


		
		
		
	}


	}
