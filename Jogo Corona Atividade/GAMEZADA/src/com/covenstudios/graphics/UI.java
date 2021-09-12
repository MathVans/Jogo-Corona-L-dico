package com.covenstudios.graphics;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.covenstudios.main.Game;


public class UI {

	private int posLife= 10;
	private int posImuni= 10;
	private int posLimp= 40;
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8,4+posLife,50,8 );
		g.setColor(Color.green);
		g.fillRect(8, 4+posLife,(int)((Game.player.life/Game.player.maxlife)*50),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxlife  , 22, 12+posLife);
		
		
		
		g.setColor(Color.red);
		g.fillRect(8,4+posLimp,50,8 );
		g.setColor(Color.lightGray);
		g.fillRect(8, 4+posLimp,(int)((Game.player.limpeza/Game.player.maxLimpeza)*50),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString((int)Game.player.limpeza+"/"+(int)Game.player.maxLimpeza  , 12, 12+posLimp);
		
		
			
		g.setColor(Color.red);
		g.fillRect(170,4+posImuni,50,8 );
		g.setColor(Color.BLUE);
		g.fillRect(170, 4+posImuni,(int)((Game.player.Imunidade/Game.player.maxImunidade)*50),8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString((int)Game.player.Imunidade+"/"+(int)Game.player.maxImunidade  , 185, 12+posImuni);
		
		

	}
	
	
}
