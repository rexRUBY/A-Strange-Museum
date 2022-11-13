package main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class MainPanel implements PanelSize {
    protected JLabel gameTitle = new JLabel("기묘한 박물관");
    protected JLabel gameTitleShadow = new JLabel("기묘한 박물관");
    public JLabel imbokhwan;
    protected ImagePanel titlePanel = new ImagePanel();
    protected JFrame mainFrame = new JFrame();
    protected boolean isNext = false;
    protected File file = new File("audios/strange_museum_op.wav");
    protected Clip clip = AudioSystem.getClip();

    public MainPanel() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false); // 창크기 변경X
        mainFrame.setTitle("기묘한 박물관");

        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();

        //버튼
        JButton startBtn = new JButton("시작");
        startBtn.setBounds(349, 380, 70, 30);
        titlePanel.add(startBtn);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tutorial();
                isNext = true;
                mainFrame.setVisible(false);
                clip.stop();
            }
        });

        //타이틀
        Font font = new Font("Sam3KRFont", Font.BOLD, 85);

        gameTitle.setFont(font);
        gameTitle.setForeground(Color.WHITE);
        gameTitle.setBounds(100, 4, 700, 88);
        titlePanel.add(gameTitle);

        gameTitleShadow.setFont(font);
        gameTitleShadow.setForeground(Color.BLACK);
        gameTitleShadow.setBounds(104, 0, 700, 88);
        titlePanel.add(gameTitleShadow);

        mainFrame.add(titlePanel);

        //메뉴
        MenuPanel menuPanel = new MenuPanel();
        mainFrame.setJMenuBar(menuPanel.mb);

        mainFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절

        mainFrame.setSize(screenWidth, screenHeight);
        mainFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        mainFrame.setVisible(true);
    }

    class ImagePanel extends JPanel implements Runnable {
        private ImageIcon museum = new ImageIcon("images/museum.png");
        private Image img = museum.getImage();

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }

        public ImagePanel() {
            setLayout(null);
            Thread th = new Thread(this);
            th.start();
        }

        public void run() {
            while (true) {
                int y1 = gameTitle.getY();
                int y2 = gameTitleShadow.getY();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    return;
                }
                if (y1 < 150) {
                    gameTitle.setLocation(gameTitle.getX(), y1 + 5);
                }
                if (y2 < 146) {
                    gameTitleShadow.setLocation(gameTitleShadow.getX(), y2 + 5);
                }
            }
        }
    }
}