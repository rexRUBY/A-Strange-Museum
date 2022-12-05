
package stageTwo;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
   //variable
    private int x;
    private int y;
    private int r;
    private int dx;
    private int dy;
    private int speed;
    private int lives;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    
    private boolean firing;
    private long firingTimer;
    private long firingDelay;
    
    private boolean recovery;
    private long recoveryTimer;
    
    private int score;
    private int powerLevel;
    private int power;
    private int[] requiredpower={1,2,3,4,5};
    
    public ImageIcon tBoatImg = new ImageIcon("images/TBoat.png");
    public Image tBoat= tBoatImg.getImage();
    public ImageIcon damageImg = new ImageIcon("images/damageTboat.png");
    public Image damage = damageImg .getImage();
  
//constractor
public Player(){
    x=GamePanel.width/2;
    y=GamePanel.height/2;
    r=5;
    
    speed=5;
    lives=3;
    
    firing =false;
    firingTimer=System.nanoTime();
    firingDelay=200;
    
    recovery=false;
    recoveryTimer=0;
    
}


//functions
public void update(){

    if(left){
    dx=-speed;
    }
    else if(right){
    dx=speed;
    }
    else if(up){
     dy=-speed;
    }
    else if(down){
     dy=speed;
    }
    x=x+dx;
    y=y+dy;
    
    if(x<r){x=r;}
    else if(y<r){y=r;}
    else if(x>GamePanel.width-r)x=GamePanel.width-r;
    else if(y>GamePanel.height-r)y=GamePanel.height-r;
    
    dx=0;
    dy=0;
    //firing  
    if(firing){
      long elapsed =(System.nanoTime()-firingTimer)/1000000;
      if(elapsed>firingDelay){
         firingTimer=System.nanoTime();
         GamePanel.bullets.add(new Bullet(270,x,y));
      }
    }
    if(recovery){
    long elapsed=(System.nanoTime()-recoveryTimer)/1000000;
    if(elapsed>2000){ //2 sec
     recovery =false;
     recoveryTimer=0;
     }
    }
}

public void draw(Graphics2D g){
   if(recovery){
	   g.drawImage(damage,(int)(x-r),(int)(y-r),tBoatImg.getIconWidth()-25+r*5,tBoatImg.getIconHeight()-25+r*5, null); 
   } 
   else{
   g.drawImage(tBoat,(int)(x-r),(int)(y-r),tBoatImg.getIconWidth()-25+r*5,tBoatImg.getIconHeight()-25+r*5, null);
     }
}


    void setLeft(boolean b) {
         left = b;
    }

    void setRight(boolean b) {
         right = b;
    }

    void setUp(boolean b) {
          up = b;
    }

    void setDown(boolean b) {
        
        down = b;
    }
  void setFiring(boolean b){
   firing = b;
}
  

  

public int getX(){return x;}
public int getY(){return y;}
public int getR(){return r;}
public int getLives(){return lives;}

public boolean Recovery(){return recovery;}

public void lostLife(){
    lives--;
    recovery=true;
    recoveryTimer=System.nanoTime();

   }

    void gainLife() {
      lives++;  
    }

  public  boolean isDead() {
    return lives<=0;      
  }
}
