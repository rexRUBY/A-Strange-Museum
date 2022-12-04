package ending;

import main.Tile;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Test implements Tile, Runnable {
    JFrame frame = new JFrame();
    Thread th = new Thread(this);
    public Test(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth,screenHeight);
        frame.setLayout(null);
        frame.setVisible(true);
        th.start();
    }

    public static void main(String[] args) {
        new Test();
    }

    @Override
    public void run() {
        JLabel la = new JLabel("hello");
        la.setForeground(Color.white);
        la.setSize(400,80);
        la.setLocation(350,450);
        frame.add(la);
        while (la.getY() > 0){
            int y = la.getY();
            la.setLocation(350,y-5);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
