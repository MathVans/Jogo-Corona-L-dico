package com.covenstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.covenstudios.entities.Vacina;
import com.covenstudios.entities.Boss;
import com.covenstudios.entities.Bubbleshoot;
import com.covenstudios.entities.Enemy;
import com.covenstudios.entities.EnemyRed;
import com.covenstudios.entities.Entity;
import com.covenstudios.entities.Gun;
import com.covenstudios.entities.LifePacks;
import com.covenstudios.entities.Player;
import com.covenstudios.entities.Sabao;
import com.covenstudios.graphics.Spritesheet;
import com.covenstudios.graphics.UI;
import com.covenstudios.world.World;



public class Game extends Canvas implements Runnable, KeyListener,MouseListener{
	/**
	 * 
	 */
							//Congifigurações do JFrame
	
	//=====================================================================================//
									
	public static JFrame frame;             //Objeto tela
	public final static int WIDTH = 240;	//tamanho da janela
	public final static int HEIGHT = 160;   //tamanho da janela
	public static final int SCALE = 3;		    //tamanho da janela


	//Booleans de Controle
	//=====================================================================================//
	
	private boolean isRunning = true;//Variavel para controle do inicio do game
	private Thread thread;			//Objeto thread, para iniciação da Thread
	public static String gameState = "MENU"; //GameState
	private boolean showMessageGameOver = false;//Boolean para o Gameover
	private boolean restartGame = false;// Boolean para resetar o jogo
	
	//Listas de objetos
	//=====================================================================================//
	
	public static List<Entity> entities;//Classe pai de entidade, a qual todas herdarão
	public static List<Enemy> enemies;//Inimigos
	public static List<EnemyRed> enemiesred;//2° tipo de inimigos
	public static List<Boss> boss;//O boss	
	public static List<LifePacks> lifepacks;//pacotes de vida
	public static List<Vacina> vacinas;//Bonus de velocidade
	public static List<Gun> guns;// Arma
	public static List<Sabao> sabaos;//Munição
	public static List<Bubbleshoot> bubbles;//Balas
	
	// Spritesheets dos objetos
	//=====================================================================================//
	
	public static Spritesheet spritesheet;
	public static Spritesheet bossspritesheet;
	public static Spritesheet playerspritesheet;
	public static Spritesheet mapaspritesheet;
	public static Spritesheet background;
	
	//BufferedImage
	
	private BufferedImage image;
	
	//Declaração de Objetos
									
	public static Player player;
	public static Random rand;
	public static UI ui;
	public static World world;
	public static Menu menu;
	public static boolean restartgame;
	
	//Variavéis para controle do limite de levels
	
	public static  int CUR_LEVEL = 1;
	public int MAX_LEVEL = 4;										
	
	
	
	
	
										//Interface Main
	//=====================================================================================//
	
	public static void main(String[] args) {

		Game game= new Game();	
	
		game.start();//inicializa o jogo chamando o metodo start
	}
	
	
	

									//Metodo contrutor do jogo
	//=====================================================================================//
	
	
	public Game() {
		
		
		addMouseListener(this);//Adcionando o listener do mouse ao programa
		addKeyListener(this);//Adcionando o listener do teclado ao programa
		
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));//chamando o canvas, para obter os tamanhos da janela. Importando a classe Dimension tambem do java.
		Initframe();// chama o metodo que inicializa o frame, para manipulação da janela.
		
		
						//inicializando os objetos
		
								
		     //Objeto do Buffer
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
					
			//Sprites
		spritesheet= new Spritesheet("/spritesheet.png");
		playerspritesheet= new Spritesheet("/playercorona.png");
		mapaspritesheet = new Spritesheet("/level1.png");
		background = new Spritesheet("/background.jpg");
		bossspritesheet= new Spritesheet("/Bosssprite.png");
					
		
			//ArrayLists de Entities
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		enemiesred = new ArrayList<EnemyRed>();
		boss = new ArrayList<Boss>();
		lifepacks = new ArrayList<LifePacks>();
		vacinas = new ArrayList<Vacina>();
		guns = new ArrayList<Gun>();
		sabaos = new ArrayList<Sabao>();
		bubbles = new ArrayList<Bubbleshoot>();
		
			//Objeto das entities e outros
		ui = new UI(); // Objeto da Classe de Interface do jogo
	
		player = new Player(100, 100, 16, 32,spritesheet.getSprite(0 ,0, 16, 32));//instaciando objeto player, com suas configurações iniciais
		entities.add(player);//Adcionando player a classe pai entities
		world = new World("/level1.png");//Criando objeto world passando a sprite como referência
		
		rand = new Random();//Criando uma variável aleatória
		menu = new Menu();//Criando um objeto menu
		
		//Colocando as musicas do jogo para loopar
		Sound.musicbackground.loop();
		Sound.wind.loop();
		
	}
	
	
	
										//Método para inicializar e configurar o JFrame	
	//=====================================================================================//
						
public void Initframe() {
	
	frame= new JFrame("Carlinhos contra o corona");// inicializando frame, podendo mudar o titulo do frame. (	JANELA == FRAME)
	frame.add(this);						//Frame conseguirá pegar as propriedades do canvas(THIS) é o canvas.
	frame.setResizable(false);				//comando para usuário não conseguir mudar o tamanho da tela.
	frame.pack();							// metodo frame para calcular as dimensões e mostrar.
	frame.setLocationRelativeTo(null);		// comando para deixar a tela no centro	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// quando clicar pra fechar, ele de fato feche.
	frame.setVisible(true);					//Para que quando inicialize ele fique visível.
	
}
	


								//Interface para iniciar a THREAD
       //=====================================================================================//

	public synchronized void start () {//Syncronized serve para que não haja conflito entre os métodos das threads
		
		Thread thread = new Thread(this);//Instacia a variavel thread passando como parametro a classe que deverá ter a interface runnable.
		isRunning=true;					 //Confirma que a variavel de controle está verdadeira
		thread.start(); 				 //Inicia a thread, na interface (run) da Runnable
	
	}
	

	
							//Interface para parar a THREAD
	//=====================================================================================//
	public synchronized void stop(){
		
		isRunning = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
							
								//Método com os ticks do Player e das Entities
	     //=====================================================================================//
	
	public void tick() {
		
		
		if(gameState == "NORMAL") {
		
		restartGame = false;
		
		//Tick das entities
		for(int i=0;i<entities.size();i++) {
			Entity e = entities.get(i);
			
			e.tick();
		}
		//Tick dos enemies
		for(int i=0;i<enemies.size();i++) {
			
				enemies.get(i).tick();
			 
			}
		for(int i=0;i<enemiesred.size();i++) {
			
			enemiesred.get(i).tick();
		 
		}
		for(int a=0;a<boss.size();a++) {
			
			 boss.get(a).tick();
		}
						
		//Tick das bubbles
		for(int a=0;a<bubbles.size();a++) {
			
			 bubbles.get(a).tick();
		}
		
		//Tick do player
		player.tick();
		
		
		
		
		//Level do mundo
		if(enemies.size()==0 && enemiesred.size()==0 ) {
			
			CUR_LEVEL++;
			
			if(CUR_LEVEL>MAX_LEVEL) {
				
				Enemy.dano++;
				EnemyRed.dano++;
				Boss.dano+=10;
				CUR_LEVEL=1;
				
			}
			
			Sound.nextLevel.play();
			String newWorld = "level" + CUR_LEVEL + ".png";
			world.restartGame(newWorld);
			
		}
		
		}else if(gameState == "GAME_OVER"){
			
			showMessageGameOver= true;
			
			if(restartGame) {
				
				CUR_LEVEL=1;
					showMessageGameOver = false;
						gameState="NORMAL";
							String newWorld = "level" + CUR_LEVEL + ".png";
							world.restartGame(newWorld);
				
			}
		} else  {
			
		
				Game.menu.tick();
				
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
						//Interface de Renderização do jogo
	//=====================================================================================//
	
	public void render() {
		
		//https://www.guj.com.br/t/resolvido-como-utilizar-bufferstrategy-em-imagens-carregadas-exteriormente/133285
		//http://resources.mpi-inf.mpg.de/d5/teaching/ss05/is05/javadoc/java/awt/image/BufferStrategy.html
		//https://pt.stackoverflow.com/questions/177456/qual-a-vantagem-de-usar-bufferedimage-para-imagens
		
		BufferStrategy bs = this.getBufferStrategy();	//Objeto de Buffers onde serão desenhadas as imagens.O buffer funciona como papel, onde serão carregados os dados das imagens
		
		if(bs==null) {									//Carregam os Buffers na na tela caso não existam. 
		this.createBufferStrategy(4);							
		return;
		}
		//https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
		//https://www.guj.com.br/t/graphics2d/48864
		//https://www.tutorialspoint.com/awt/awt_graphics2d_class.htm
		Graphics g = image.getGraphics();  // Cria objeto da Biblioteca Graphics que permite criar formas e desenhar cores nos buffers
		

		
	
		world.render(g);// Renderiza o mundo		 
		
		
		//Render Entidades
		for(int i=0;i<entities.size();i++) {
			
			Entity e = entities.get(i);
			e.render(g);
		}
		//Render Enemies
		for(int i=0;i<enemies.size();i++) {
			
			 enemies.get(i).render(g);
			}
		
		for(int i=0;i<enemiesred.size();i++) {
			
			 enemiesred.get(i).render(g);
			}
		for(int i=0;i<boss.size();i++) {
			
			 boss.get(i).render(g);
			}
		//Render bubbles
	for(int i=0;i<bubbles.size();i++) {
			
		 bubbles.get(i).render(g);
		 
		}
	
	
						
		ui.render(g);//Renderizador da interface gráfica para o jogador

	
								
						//Carrega tudo que esta renderizado no JFrame através do Graphics
		//=====================================================================================//
		
		g=bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE, HEIGHT*SCALE, null);
		
		//vida
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Vida :", 10*SCALE, 12*SCALE);
		
		//Imunidade	
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Imunidade :"  , 170*SCALE, 12*SCALE);
		
		//Limpeza
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Limpeza :", 10*SCALE, 42*SCALE);
		
		//Level
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("LVL :"+CUR_LEVEL, 110*SCALE, 11*SCALE);
		//pontuação
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Points :" + player.pontuação, 110*SCALE, 22*SCALE);
		
		
		 if(showMessageGameOver) {//Caso tenha dado game over

				Graphics2D g2 = (Graphics2D)g;
				
				g2.setColor(new Color(0,0,0,100));
				g.fillRect(0,0,WIDTH*SCALE, HEIGHT*SCALE);
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("GAME OVER", 75*SCALE, 80*SCALE);
				
					g.setColor(Color.white);
					g.setFont(new Font("arial", Font.BOLD, 25));
					g.drawString("Pressione {ENTER} para reiniciar", 60*SCALE, 100*SCALE);
					
			}  
		 		else if(gameState =="MENU") {//Status menu 
		 			
					menu.render(g);
						
						
					}
		 
		 //Método da classe que Desenha tudo na tela
					
		//=====================================================================================//		
		bs.show(); //Carregar tudo na tela
		//=====================================================================================//
	}		
	
	
	
							//Interface THREAD
	//=====================================================================================//
	
	public void run() {						
		
		
		long lastTime = System.nanoTime();  //variável que pega o tempo do sistema em nanosegundos, tornando ele mais preciso.	
		double amountsOfTicks = 60.0;		//quantidade de frames que eu quero por segundo.
		double ns = 1000000000/ amountsOfTicks;//dividindo um segundo pela quantidade de ticks, será utilizado no cálculo
		double delta = 0; 					//variavel de controle no laço
		int frames=0;						//zera a quantidade de frames
		double timer = System.currentTimeMillis();//Pega o tempo atual do sistema novamente
		
		requestFocus();						//Solicita o foco do teclado a tela
		
		
		while(isRunning){
			
			long now = System.nanoTime();//pega o tempo atual do sistema e atribui a variável
			delta+=(now-lastTime)/ns;    //calculo de intervalo para que o tick seja executado, ou seja para que delta chegue em = 1 segundo com mais precisão.
			lastTime=now;				 //atribui a lastTime o valor do tempo anterior atribuido.
		
			if(delta>=1) {				// se delta for igual a 1 segundo ou mais;
				
			tick();						//tick com a lógica do jogo
			render();					//render com os gráficos do jogo
			
			frames++;					//acrescenta na variavel frame
			delta--;					// decrescenta na variavel delta
			
			}
			
			if(System.currentTimeMillis()-timer>=1000 ) {// caso o timer alcance a condição de 1 segundo ele entra na condição
			
				System.out.println("fps"+ frames);// printa a quantidade de frames	
			frames=0;							  //reseta os frames
			timer+=1000;	                      // soma mais um segundo ao timer
			
			}
			
		}
			stop();									//função que para a thread
	}

	
	
	
	
	
					//Interface de controle do KEYLISTENNER
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode()==KeyEvent.VK_RIGHT|| e.getKeyCode()==KeyEvent.VK_D) {
		player.right = true;
		//direita
		
	}else if(e.getKeyCode()==KeyEvent.VK_LEFT|| e.getKeyCode()==KeyEvent.VK_A) {
		player.left = true;
		//esquerda
	}
	
	if(e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()==KeyEvent.VK_W) {
		player.up = true;
		//CIMA
		
	}else if(e.getKeyCode()==KeyEvent.VK_DOWN|| e.getKeyCode()==KeyEvent.VK_S) {
		player.down = true;
		//BAIXO
	}
	if(e.getKeyCode()==KeyEvent.VK_X|| e.getKeyCode()==KeyEvent.VK_X) {
		Game.player.shoot = true;
		//ATIRAR
		
	}
	if(e.getKeyCode()==KeyEvent.VK_Z|| e.getKeyCode()==KeyEvent.VK_Z) {
		Game.player.jump = true;
		System.out.println("pulando");
		//PULAR
		
	}
	
	if(e.getKeyCode()==KeyEvent.VK_ENTER ) {
	
		if( gameState == "GAME_OVER") {
			restartGame = true;
			
		}
		
		if( gameState == "MENU") {
			menu.enter =true;
			//Seleção de menu
		}
		
		//RESETAR
		
	}
	if(e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()==KeyEvent.VK_W && gameState == "MENU") {
		menu.up =true;
		//CIMA
		
	}else if(e.getKeyCode()==KeyEvent.VK_DOWN|| e.getKeyCode()==KeyEvent.VK_S && gameState == "MENU") {
		menu.down =true;
		//BAIXO
	}
	if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			
			gameState = "MENU";
			menu.pause=true;
			//Pause
		}
		
		
		
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT|| e.getKeyCode()==KeyEvent.VK_D) {
			player.right = false;
			//direita
			
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT|| e.getKeyCode()==KeyEvent.VK_A) {
			player.left = false;
			//esquerda
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()==KeyEvent.VK_W) {
			player.up = false;
			//CIMA
			
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN|| e.getKeyCode()==KeyEvent.VK_S) {
			player.down = false;
			//BAIXO
		}
		if(e.getKeyCode()==KeyEvent.VK_X|| e.getKeyCode()==KeyEvent.VK_X) {
			Game.player.shoot = false;
			//ATIRAR
			
		}
		

		
	}



	public void keyTyped(KeyEvent arg0) {
		
		
	}

					//Interface de controle do MOUSELISTENER
	public void mouseClicked(MouseEvent e) {
	
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseshoot = true;
		//pega a posição do mouse no local do jogo
		player.mx = (e.getX()/3);
		player.my= (e.getY()/3);
	
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		player.mouseshoot = false;
		
	}
}