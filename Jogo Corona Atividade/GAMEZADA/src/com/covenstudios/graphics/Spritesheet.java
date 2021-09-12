package com.covenstudios.graphics;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	//Classe que irá obter as sprites que estão na pasta de recursos, atraves da variável Spritsheet que é um buffredImage para então ser desenhado na tela através do Graphics
	
	//Fontes
	
	//https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
	//https://pt.stackoverflow.com/questions/177456/qual-a-vantagem-de-usar-bufferedimage-para-imagens
	//https://pt.stackoverflow.com/questions/177456/qual-a-vantagem-de-usar-bufferedimage-para-imagens
	//https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwixu-WVxt7rAhUuHbkGHZ7GD00QFjAAegQIBBAB&url=https%3A%2F%2Fdocs.oracle.com%2Fjavase%2F7%2Fdocs%2Fapi%2Fjava%2Fawt%2Fimage%2FBufferedImage.html&usg=AOvVaw05NtTQzxwvr_nREjOInNCj
	//https://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html
	
	private BufferedImage spritesheet;
	
	public Spritesheet (String path) {
		try {
			spritesheet= ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getSprite(int x,int y,int width,int height) {
		
		return spritesheet.getSubimage(x, y, width, height);
		
		
		
	}
}
