package com.covenstudios.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class Sound {
	// Utilizando do Java Applet eu armazeno na variável um arquivo de som
	
	public static final Sound pop = new Sound("/bolha.wav");
	public static final Sound nextLevel = new Sound("/orchestra.wav");
	
	public static final Sound musicbackground = new Sound("/background.wav");
	public static final Sound wind = new Sound("/vento.wav");
	
	private AudioClip audio;
	
	
	//Função que irá reproduzir o som
	
	public Sound(String name) {
		try {
			audio = Applet.newAudioClip(Sound.class.getResource(name));
			/*
			
			File file = new File("F:/covid/res" + name);
			audio = Applet.newAudioClip(file.toURL());
			
			System.out.println(file.toURL());
			*/
		} catch (Throwable e) {
			
		
		}
		
		
	}
	public void play(){
		try {
			new Thread(){
				
				public void run() {
					
					audio.play();
					
				}
				
				
			}.start();
			if(Game.gameState =="NORMAL") {
				
			
			}
		
		} catch (Throwable e) {
			
		
		}
		
		
	}
	
	public void loop(){
		try {
			new Thread(){
				
				public void run() {
					
					audio.loop();		
				}
				
			}.start();
		
		} catch (Throwable e) {
			
		
		}
		
		
	}


}
