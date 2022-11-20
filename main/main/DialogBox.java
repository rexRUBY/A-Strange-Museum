package main;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

import static main.Tutorial.dialog;

public class DialogBox extends JLabel implements Tile, Runnable {
    public static Vector<String> dialogVec = new Vector<>();
    public static Iterator<String> it = dialogVec.iterator();
    Thread thread = new Thread(this);
    DialogBox dialogBox;

    public DialogBox() {
        this.setLayout(null);
        this.setSize(screenWidth, 240);
        this.setLocation(0, 336);
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

    public void setDialog(DialogBox panel, String[] dialog, int size) {
        for (int i = 0; i < size; i++) {
            dialogVec.add(dialog[i]);
        }
        this.dialogBox = panel;
    }

    public void run() {
        Font font = new Font("Sam3KRFont", Font.PLAIN, 23);
        try {
            for (int k = 0; k < dialog.length; k++) {
                dialogVec.clear();
                setDialog(dialogBox, dialog[k], dialog[k].length);
                for (int i = 0; i < dialogVec.size(); i++) {
                    int size = 23;
                    JLabel label[] = new JLabel[dialogVec.elementAt(i).length()];
                    char[] strArray = dialogVec.elementAt(i).toCharArray();
                    for (int j = 0; j < dialogVec.elementAt(i).length(); j++) {
                        label[j] = new JLabel(String.valueOf(strArray[j]));
                        label[j].setFont(font);
                        label[j].setForeground(Color.WHITE);
                        label[j].setSize(40, 40);
                        label[j].setLocation(tileSize * 2 + 10 + size * j, tileSize + size * i - 25);
                        dialogBox.add(label[j]);
                        repaint();
                        initSleep(25);
                    }
                    initSleep(120);
                    }
                removeAll();
                }
                initSleep(2000);
            } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initSleep(int sleepNum) {
        try {
            Thread.sleep(sleepNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}