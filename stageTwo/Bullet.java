
package stageTwo;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet {
	
    public ImageIcon bombImg = new ImageIcon("images/bomb.png");
    public Image bomb = bombImg .getImage();
    
    //variable
      private double x;
      private double y;
      private int r;
      
      private double dx;
      private double dy;
      private double rad;
      private double speed;
       
      //private Color color1;
      
    public  Bullet(double angle,int x,int y){
     this.x=x;
     this.y=y;
     r=2;
     
     rad=Math.toRadians(angle);
      speed=10;
     dx=Math.cos(rad)*speed;
     dy=Math.sin(rad)*speed;
     }
    
    public double getX(){return x;}
    public double getY(){return y;}
    public double getR(){return r;}
    
    
    
    //function
    public boolean update(){
    
        x+=dx;
        y+=dy;
        if(x<-r || x > GamePanel.width + r || y< - r||y > GamePanel.height + r){
          return true;
        }
        
        return false;

    }
    
    public void draw(Graphics2D g){
    	g.drawImage(bomb, (int)(x-r)+20,(int)(y-r),bombImg.getIconWidth(),bombImg.getIconHeight(), null);
    }
    
    
}
