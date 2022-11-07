package main;

import javax.swing.*;
import java.awt.*;

class Intro extends JPanel {
    private ImageIcon museum = new ImageIcon("images/museum.jpg");
    private Image img = museum.getImage();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    public void ImagePanel() {
        setLayout(null);
    }
}