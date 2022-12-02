package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class MainPanel implements Tile {
    protected JLabel gameTitle = new JLabel("기묘한 박물관");
    protected JLabel gameTitleShadow = new JLabel("기묘한 박물관");
    protected ImagePanel titlePanel = new ImagePanel();
    protected File file = new File("audios/strange_museum_op.wav");
    protected Clip clip = AudioSystem.getClip();
    protected JLabel startLabel = new JLabel("시작");

    public MainPanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false); // 창크기 변경X
        mainFrame.setTitle("기묘한 박물관");
        Font font = new Font("Sam3KRFont", Font.BOLD, 85);
        Font font1 = new Font("Sam3KRFont", Font.BOLD, 40);

        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();

        //시작 라벨
        startLabel.setLocation(330, 380);
        startLabel.setSize(tileSize*4,tileSize*2);
        startLabel.setForeground(Color.WHITE);
        startLabel.setFont(font1);
        startLabel.setVisible(false);
        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    new Tutorial();
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                mainFrame.setVisible(false);
                clip.stop();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startLabel.setForeground(Color.BLACK);
            }
        });
        titlePanel.add(startLabel);


        // 타이틀
        gameTitle.setFont(font);
        gameTitle.setForeground(Color.WHITE);
        gameTitle.setBounds(100, 5, 700, 88);
        titlePanel.add(gameTitle);

        gameTitleShadow.setFont(font);
        gameTitleShadow.setForeground(Color.BLACK);
        gameTitleShadow.setBounds(105, 0, 700, 88);
        titlePanel.add(gameTitleShadow);

        mainFrame.add(titlePanel);

        //메뉴
        MenuBar menuBar = new MenuBar();
        mainFrame.setJMenuBar(menuBar.mb);

        mainFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절

        mainFrame.setSize(screenWidth, screenHeight);
        mainFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        mainFrame.setVisible(true);
    }

    class ImagePanel extends JPanel implements Runnable {
        private ImageIcon museum = new ImageIcon("images/museum2.png");
        private Image img = museum.getImage();
        Thread th = new Thread(this);

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }

        public ImagePanel() {
            setLayout(null);
            th.start();
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    return;
                }
                int y1 = gameTitle.getY();
                int y2 = gameTitleShadow.getY();
                if (y1 < 151) {
                    gameTitle.setLocation(gameTitle.getX(), y1 + 5);
                }
                if (y2 < 146) {
                    gameTitleShadow.setLocation(gameTitleShadow.getX(), y2 + 5);
                }
                if (y1 == 150 && y2 == 145)
                    th.interrupt();
                if (th.isInterrupted())
                    startLabel.setVisible(true);
            }
        }
    }
}