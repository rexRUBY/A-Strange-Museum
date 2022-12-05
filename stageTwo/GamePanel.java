
package stageTwo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;

import main.AfterStageOne;
import main.Tile;
import stageOne.MapGenerator;
import stageOne.StageOne_Main;

  public class GamePanel extends JPanel implements Tile,Runnable,KeyListener{
     
    public static int width=screenWidth;
    public static int height=screenHeight;
    
    private Thread thread;
    static boolean running;
    private BufferedImage image;
    private Graphics2D g;
    private int FPS= 30;
    private double averageFPS;
    
    public static Player player;
    public static ArrayList<Bullet>bullets;
    public static ArrayList<Enemy>enemies;
    
    private long levelStartTimer;
    private long levelStartTimerDiff;
    private int levelNumber;
    private boolean levelStart;
    private int levelDelay=2000;
   
    public ImageIcon seaImg = new ImageIcon("images/sea.gif");
    public Image sea = seaImg.getImage();
    public ImageIcon heartImg = new ImageIcon("images/heart.png");
    public Image heart = heartImg.getImage();
    
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(width,height));
        setFocusable(true);
        requestFocus();      
    }
    
    public void setLeft(boolean b){boolean left = b;}
    public void setRight(boolean b){boolean right = b;}
    public void setUp(boolean b){boolean up = b;}
    public void setDown(boolean b){boolean down = b;}
    public void setFiring(boolean b){boolean firing = b;}

    public void addNotify(){
         super.addNotify();
         if(thread==null){
         thread=new Thread(this);
         thread.start();
       }
          addKeyListener(this);
    }

    @Override
    public void run() {
       running=true;
       image=new BufferedImage( width, height,BufferedImage.TYPE_INT_RGB);
       g=(Graphics2D)image.getGraphics();
       
       player=new Player();
       bullets=new ArrayList<Bullet>();
       enemies=new ArrayList<Enemy>();

       levelStartTimer=0;
       levelStartTimerDiff=0;
       levelStart=true;
       levelNumber=0;
        
      
       long startTime;
       long TimeMillis;
       long waitTime;
       long totalTime=0;
       long tergetTime=1000/FPS;
       
       int frameCount=0;
       int maxFramecount=30;
       
       
       // Game loop
       while(running){
			gameUpdate();
            gameRender();
            gameDraw();
            startTime=System.nanoTime();
            TimeMillis=(System.nanoTime()-startTime)/1000000;
            
            waitTime=tergetTime-TimeMillis;
            try{
             Thread.sleep(waitTime);
            }
            catch(Exception e){
              
            }
             totalTime +=System.nanoTime()-startTime;
            frameCount++;
            
            if(frameCount==maxFramecount){
                averageFPS=1000.0/((totalTime/frameCount)/1000000);
                    frameCount=0;
                    totalTime=0;
            }
            
       }
       try {
           g.setColor(Color.BLUE);
           g.fillRect(0, 0, WIDTH, HEIGHT);
           g.setColor(Color.GREEN);
           g.setFont(new Font("Sam3KRFont",Font.BOLD,20));
           String s=" 성공!! 잠시 후 메인 화면으로 돌아갑니다. ";
           int length=(int)g.getFontMetrics().getStringBounds(s, g).getWidth();
           g.drawString(s,(width-length)/2,height/2);
           gameDraw();
   		   thread.sleep(3000);
   		   new AfterStageOne();
           Game.frame.setVisible(false);
           Game.clip.stop();
   	} catch (InterruptedException e) {
   		e.printStackTrace();
   	}

       
        
    }

    private void gameUpdate(){
        //new level
        if(levelStartTimer==0 && enemies.size()==0){
           levelNumber++;
           levelStart=false;
           levelStartTimer=System.nanoTime();
        }
        else{
        
         levelStartTimerDiff=(System.nanoTime()-levelStartTimer)/1000000;
         if(levelStartTimerDiff>levelDelay){
             levelStart=true;
             levelStartTimer=0;
             levelStartTimerDiff=0;
          }
        }
        
        //  create enemies
         if(levelStart && enemies.size()==0){
            createNewEnemies();
         }
        
       //player update
       player.update();
       
       //Bullet update
       for(int i=0;i<bullets.size();i++){
         boolean remove= bullets.get(i).update();
            if(remove){
                bullets.remove(i);
                 i--;            
            }
       }
        //Enemy update
          for(int i=0;i<enemies.size();i++){
              enemies.get(i).update();
          }
          
    }

    private void gameRender() {
        
        //draw backgroung
    	g.drawImage(sea, 0, 0, screenWidth, screenHeight, null);
        
        //draw player
        player.draw(g);
        
        //draw Bullet
        for(int i=0;i< bullets.size();i++){
          bullets.get(i).draw(g);
        }
        //draw Enemy
        for(int i=0;i<enemies.size();i++){
              enemies.get(i).draw(g);
        }
        
      //bullet Enemy Collision
        for(int i=0;i<bullets.size();i++){
         Bullet b=bullets.get(i);
         double bx=b.getX();
         double by=b.getY();
         double br=b.getR();
        for(int j=0;j<enemies.size();j++){
          //clip.close();
          Enemy e=enemies.get(j);
          double ex=e.getX();
          double ey=e.getY();
          double er=e.getR();
          
          double dx=bx-ex;
          double dy=by-ey;
          double dist=Math.sqrt(dx*dx+dy*dy);
          
          if(dist < br + er + 30)
          {
          e.hit();
          bullets.remove(i);
          i--;
          //clip.open(AudioSystem.getAudioInputStream(file));
          //clip.start();
          break;
             }
          }
        }
      
      //check dead enemies
       for(int i=0;i<enemies.size();i++){
           if(enemies.get(i).Dead()){
              Enemy e=enemies.get(i);
              
              // Roll for powerup
              double rand=Math.random();
             
           enemies.remove(i);
           i--;
           //e.explode();
           }
           
           //check dead player
           if(player.isDead()){ 
           running=false; 
           
           }
           //player-Enemy Collision
           if(!player.Recovery()){
              int px=player.getX();
               int py=player.getY();
              int pr=player.getR();
          for(int k=0; k<enemies.size(); k++){
               
               Enemy e=enemies.get(k);
               double ex=e.getX();
               double ey=e.getY();
               double er=e.getR();
                
               double dx=px-ex;
               double dy=py-ey;
               double dist=Math.sqrt(dx*dx +dy*dy);
               
               if(dist < pr + er + 35)
               {
               player.lostLife();
                 }
 
              }
              
           }
           
           
           
       }
        

      //draw player lives
      for(int i=0;i<player.getLives();i++){
    	  g.drawImage(heart, 20 +(30*i), 20,player.getR()*4,player.getR()*4,null);
      }
    }
    
    private void gameDraw() {
       Graphics g2=this.getGraphics();
      g2.drawImage(image,0,0, null);
       g2.dispose();
    }
    
    private void createNewEnemies() {
      
       enemies.clear();
       Enemy e;
       
       if(levelNumber==1){
          for(int i=0;i<5;i++){
             enemies.add(new Enemy(1));
          }
       }
       if(levelNumber==2){
         for(int i=0;i<6;i++){
           enemies.add(new Enemy(1));
         }
         enemies.add(new Enemy(2));
         enemies.add(new Enemy(2));
       }
       
       if(levelNumber==3){
         for(int i=0;i<4;i++){
           enemies.add(new Enemy(1));
         }
          enemies.add(new Enemy(2));
          enemies.add(new Enemy(2));
          enemies.add(new Enemy(3));
       }
       if(levelNumber == 4) {
    	   GamePanel.running=false;
       }
       //if(levelNumber==4){
    	//   enemies.add(new Enemy(0));
    	   /*
    	   g.setColor(Color.BLUE);
           g.fillRect(0, 0, WIDTH, HEIGHT);
           g.setColor(Color.WHITE);
           g.setFont(new Font("Century Gothic",Font.PLAIN,20));
           String s=" 성공! 잠시 후 메인화면으로 돌아갑니다. ";
           int length=(int)g.getFontMetrics().getStringBounds(s, g).getWidth();
           g.drawString(s,(width-length)/2,height/2);
           */
        //}
          /*
           if(levelNumber==5){
               enemies.add(new Enemy(1,4));          
           enemies.add(new Enemy(1,3));
          enemies.add(new Enemy(2,3));
           
         }
           if(levelNumber==6){
               enemies.add(new Enemy(1,3));
               for(int i=0;i<4;i++){
           enemies.add(new Enemy(2,1));
          enemies.add(new Enemy(3,1));
           
              }
          
           }
         if(levelNumber==7){
               enemies.add(new Enemy(1,3));
           enemies.add(new Enemy(2,3));
          enemies.add(new Enemy(3,3));
           
              
          
           } 
         
         if(levelNumber==8){
               enemies.add(new Enemy(1,4));
           enemies.add(new Enemy(2,4));
          enemies.add(new Enemy(3,4));
           
              
          
           } 
         if(levelNumber==9){
             running =false;
           
              
          
           } 
           */
       }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
        int keyCode=e.getKeyCode();
        if(keyCode==KeyEvent.VK_LEFT){
        player.setLeft(true);
        }
        
        if(keyCode==KeyEvent.VK_RIGHT){
        player.setRight(true);
        }
        
        if(keyCode==KeyEvent.VK_UP){
        player.setUp(true);
        }
        
        if(keyCode==KeyEvent.VK_DOWN){
        player.setDown(true);
        }
        if(keyCode==KeyEvent.VK_Z){
        player.setFiring(true);
        }
        if (keyCode == KeyEvent.VK_ENTER)
        {
        	/*
        	Game.clip.close();
        	
            if(!play)
            {
                play = true;
                try {
					new Game();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                //Thread th = new Thread();
                //th.start();
                /*
                try {
					Game gm = new Game();
					gm.frame.setContentPane(new GamePanel());
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
            }
            //else {
            //if(play==false) {
        /*
            	new AfterStageOne();
            	this.setVisible(false);
                Game.frame.setVisible(false);
                Game.clip.stop();
                */
            //}
            	
            //}
        //}
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
        int keyCode=e.getKeyCode();
        if(keyCode==KeyEvent.VK_LEFT){
        player.setLeft(false);
        }
        
        if(keyCode==KeyEvent.VK_RIGHT){
        player.setRight(false);
        }
        
        if(keyCode==KeyEvent.VK_UP){
        player.setUp(false);
        }
        
        if(keyCode==KeyEvent.VK_DOWN){
        player.setDown(false);
        }
        if(keyCode==KeyEvent.VK_Z){
        player.setFiring(false);
        }
        
    }
    
    
}
