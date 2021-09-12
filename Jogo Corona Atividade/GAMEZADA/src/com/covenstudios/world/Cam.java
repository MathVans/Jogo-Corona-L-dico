package com.covenstudios.world;


public class Cam {
public static int  y = 0;
public static int x= 0;

				//Camera que segue o player no jogo

		public static int varCam(int atual,int  min, int max) {
			
			if(atual<min) {
				
				atual= min;
			}
			if(atual>max) {
				
				atual= max;
			}
			return atual;
		}


}

