
package stageTwo;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

import main.AfterStageOne;
import main.Tile;


public class Enemy implements Tile{
    
    private double x;
    private double y;
    private int r;
    private double dx;
    private double dy;
    private double rad;
    
    private double speed;
    
    private int health;
    private int rank;
    
    private boolean ready;
    private boolean dead;
    private boolean hit;
    private long hitTimer;
    
    public ImageIcon jBoatImg = new ImageIcon("images/JBoat.png");
    public Image jboat = jBoatImg.getImage();
    
    //constractor
    public Enemy(int rank){
        this.rank=rank;
        //default enemy
        if(rank==1){
        	speed=2;
        	r=5;
        	health=1;
        }
        if(rank==2){
        	speed=3;
        	r=5;
        	health=1;
        }
        if(rank==3){
        	speed=5;
        	r=5;
        	health=1;
        }
        
        if(rank==0) {
        	GamePanel.running=false;
        	//new AfterStageOne();
        	//GamePanel.setVisible(false);
            //Game.frame.setVisible(false);
            //Game.clip.stop();
        }
        
        
        x=Math.random()*GamePanel.width/2 + GamePanel.width/4; 
        y=-r;
        double angle=Math.random()*140+20;
        rad=Math.toRadians(angle);
        
        dx=Math.cos(rad)*speed;
        dy=Math.sin(rad)*speed;
        
        ready=false;
        dead=false;
        hit=false;
        hitTimer=0;
        
    }
   
    //function
    
    public double getX(){return x;}
    public double getY(){return y;}
    public double getR(){return r;}
    public boolean Dead(){return dead;}
    public int getRank(){return rank;}
    
    public void hit(){
        //clip.open(AudioSystem.getAudioInputStream(file));
        //clip.start();
        health--;
        if(health<=0){
        dead=true;
        }
        hit=true;
        hitTimer=System.nanoTime();
    }
    
    public void update(){
    
        x+=dx;
        y+=dy;
        if(!ready){
            if(x>r && x< screenWidth-100 && y>r && y<screenHeight -100) {
               ready=true;
            }
          }
         if(x<r && dx<0) dx=-dx;
         if(y<r && dy<0) dy=-dy;
         if(x>GamePanel.width-r && dx>0) dx=-dx;
         if(y>GamePanel.height-r && dy>0) dy=-dy;
         
         if(hit){
          long elapsed=(System.nanoTime()-hitTimer)/1000000;
         if(elapsed>50){
           hit=false;
           hitTimer=0;
           }
         }
       
    }
    
    
    public void draw(Graphics2D g){
   if(hit){
	   g.drawImage(jboat,(int)(x-r),(int)(y-r),jBoatImg.getIconWidth()-25+r*5,jBoatImg.getIconHeight()-25+r*5, null);
   }
   else{
	   g.drawImage(jboat,(int)(x-r),(int)(y-r),jBoatImg.getIconWidth()-25+r*5,jBoatImg.getIconHeight()-25+r*5, null);
    
    }
}

}