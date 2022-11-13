package main;

import javax.swing.*;
import java.awt.*;

public class DialogBox extends JPanel implements PanelSize {
    String str = "";
    JPanel panel;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        setBackground(Color.WHITE);

        int width = screenWidth - tileSize*4;
        int height = tileSize*4;

        g2.setColor(new Color(0,0,0,180));
        g2.fillRoundRect(tileSize*2,tileSize*6,width,height,35,35);
        g2.setColor(new Color(255,255,255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(tileSize*2+5,tileSize*6+5,width-10,height-10,25,25);
    }

    public void setDialog(JPanel panel, String string) {
        this.str = string;
        this.panel=panel;
    }
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            char[] strArray = str.toCharArray();
            Font font = new Font("Sam3KRFont", Font.PLAIN, 23);

            for(int i=0; i<strArray.length; i++){
                int size = 20;
                JLabel label = new JLabel(String.valueOf(strArray[i]));
                label.setFont(font);
                label.setForeground(Color.WHITE);
                label.setSize(40,40);
                label.setLocation(tileSize*2+10+size*i, tileSize*6+3);
                System.out.println(strArray[i]);
                panel.add(label);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
}