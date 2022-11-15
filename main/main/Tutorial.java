package main;

import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Tutorial implements Tile {
    protected JFrame tutorialFrame = new JFrame();
    protected MenuBar menuBar = new MenuBar();
    protected DialogBox dialogBox = new DialogBox();
    protected Player player = new Player();
    protected File file = new File("audios/strange_museum_tutorial.wav");
    protected Clip clip = AudioSystem.getClip();
    protected MapPanel map = new MapPanel();

    public Tutorial() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        tutorialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tutorialFrame.setResizable(false); // 창크기 변경X
        tutorialFrame.setJMenuBar(menuBar.mb);
        tutorialFrame.setTitle("기묘한 박물관");
        tutorialFrame.setLayout(null);

        //오디오
        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();
        clip.loop(5);

        //맵
        tutorialFrame.setContentPane(map);

        //임복환(캐릭터 이름입니다!)
        tutorialFrame.add(player);
        player.addEventListener(player, tutorialFrame);

        //다이얼로그
//        String[] dialog = {"안녕 나는 임복환." , "인보과 비단길 팀이 만든 게임 케릭터다." , "우리 팀 화이팅이다!"};
//        map.add(dialogBox);
//        dialogBox.setDialog(dialogBox, dialog);

        //프레임 출력
        tutorialFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        tutorialFrame.setSize(screenWidth, screenHeight);
        tutorialFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        tutorialFrame.setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new Tutorial();
    }
}

class MapPanel extends JPanel implements Tile {
    private ImageIcon museumMap = new ImageIcon("images/tutorial_map.png");
    private Image mapImg = museumMap.getImage();

    public MapPanel() {
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0, 0, screenWidth, screenHeight,this);
    }
}