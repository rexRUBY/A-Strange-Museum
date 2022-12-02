package main;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class AfterStageOne implements Tile, Runnable{
    static String[][] dialog = {{"휴", "겨우 돌아왔다", "정말 죽을 뻔 했네…"},
            {"앗", "검이 돌아왔잖아?", "이런 식으로 하나씩 되찾으면 되는 건가?"},
            {"하지만 방금 죽었다 살아났는데", "다시 죽을지도 모르는 곳으로", "들어가긴 싫은데…"},
            {"무슨 약한 소리 하는거야!", "나주 임씨 32대손, 나 임.복.환", "두려움 따윈 없는 남자다!!!"},
            {"다시 한 번 가보자!"},
    };
    public static JFrame AfterOneFrame = new JFrame();
    protected MenuBar menuBar = new MenuBar();
    protected static DialogBox dialogBox;

    static {
        try {
            dialogBox = new DialogBox();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player player;

    static {
        try {
            player = new Player();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch(LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    protected File file = new File("audios/strange_museum_tutorial.wav");
    protected AfterOneMapPanel map = new AfterOneMapPanel();
    public AfterStageOne() {
        AfterOneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AfterOneFrame.setResizable(false); // 창크기 변경X
        AfterOneFrame.setJMenuBar(menuBar.mb);
        AfterOneFrame.setTitle("기묘한 박물관");
        AfterOneFrame.setLayout(null);

        //오디오
        try {
            clip.open(AudioSystem.getAudioInputStream(file));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        clip.start();
        clip.loop(5);

        //맵
        AfterOneFrame.setContentPane(map);
        map.add(dialogBox);
        dialogBox.setDialog(dialogBox, dialog[0], dialog[0].length);
        dialogBox.thread = new Thread(this);
        dialogBox.thread.start();

        //임복환
        map.add(player);
        player.addEventListener(player, AfterOneFrame);

        //프레임
        AfterOneFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        AfterOneFrame.setSize(screenWidth, screenHeight);
        AfterOneFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        AfterOneFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new AfterStageOne();
    }

    public void run() {
        Font font = new Font("Sam3KRFont", Font.PLAIN, 23);
        try {
            for (int k = 0; k < dialog.length; k++) {
                dialogBox.dialogVec.clear();
                dialogBox.setDialog(dialogBox, dialog[k], dialog[k].length);
                for (int i = 0; i < dialogBox.dialogVec.size(); i++) {
                    int size = 23;
                    JLabel label[] = new JLabel[dialogBox.dialogVec.elementAt(i).length()];
                    char[] strArray = dialogBox.dialogVec.elementAt(i).toCharArray();
                    for (int j = 0; j < dialogBox.dialogVec.elementAt(i).length(); j++) {
                        label[j] = new JLabel(String.valueOf(strArray[j]));
                        label[j].setFont(font);
                        label[j].setForeground(Color.WHITE);
                        label[j].setSize(40, 40);
                        label[j].setLocation(tileSize * 2 + 10 + size * j, tileSize + size * i - 25);
                        dialogBox.add(label[j]);
                        dialogBox.repaint();
                        dialogBox.initSleep(30);
                    }
                    dialogBox.initSleep(180);
                }
                dialogBox.removeAll();
                dialogBox.initSleep(2000);
            }
            dialogBox.initSleep(2000);
            dialogBox.setVisible(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

class AfterOneMapPanel extends JPanel implements Tile {
    private ImageIcon museumMap = new ImageIcon("images/after_stage_one.png");
    private Image mapImg = museumMap.getImage();

    public AfterOneMapPanel() {
        setLayout(null);
        setBounds(0,0,screenWidth,screenHeight);
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0, 0, screenWidth, screenHeight,this);
    }
}