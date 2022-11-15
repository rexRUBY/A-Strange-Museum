package main;

import javax.swing.*;
import java.awt.*;

public class DialogBox extends JPanel implements Tile, Runnable {
    String str[] = new String[5];
    Thread thread = new Thread(this);
    JPanel panel;

    public DialogBox() {
        this.setLayout(null);
        this.setSize(screenWidth, 240);
        this.setLocation(0, 336);
        //this.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        setBackground(new Color(0, 0, 0, 0));

        int width = screenWidth - tileSize * 4;
        int height = tileSize * 3;

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(tileSize * 2, tileSize - 30, width, height, 35, 35);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(tileSize * 2 + 5, tileSize - 25, width - 10, height - 10, 25, 25);
    }

    public void setDialog(JPanel panel, String[] dialogues) {
        for (int i = 0; i < dialogues.length; i++) {
            str[i] = dialogues[i];
        }
        this.panel = panel;
        thread.start();
    }

    public void run() {
        //char[] strArray = str.toCharArray();
        //JLabel label[] = new JLabel[];
        Font font = new Font("Sam3KRFont", Font.PLAIN, 23);
        try {
            for (int i = 0; i < str.length; i++) {
                int size = 23;
                JLabel label[] = new JLabel[str[i].length()];
                char[] strArray = str[i].toCharArray();
                for (int j = 0; j < strArray.length; j++) {
                    label[j] = new JLabel(String.valueOf(strArray[j]));
                    label[j].setFont(font);
                    label[j].setForeground(Color.WHITE);
                    label[j].setSize(40, 40);
                    label[j].setLocation(tileSize * 2 + 10 + size * j, tileSize + size * i - 20);
                    panel.add(label[j]);
                    repaint();
                    initSleep(20);
                }
                initSleep(120);
            }
        }
        catch (NullPointerException e){  }
    }


    public void initSleep(int sleepNum) {
        try {
            Thread.sleep(sleepNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}