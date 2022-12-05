package ending;

import main.DialogBox;
import main.Tile;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

import static java.lang.Thread.sleep;
import static main.DialogBox.dialogVec;

public class EndingIntro implements Tile, Runnable {
    EndingPlayer endingPlayer = new EndingPlayer();
    static String[][] dialog = {{"끄, 끝인가?","겨우 돌아왔다..."}, {"전쟁터는 아니었지만","다른 의미로 죽을 뻔 했어"},
            {"아 참, 그러고 보니", "여기 조별 과제를 하러 왔었지?", "말도 안되는 일에 이리 저리 휩쓸리다 보니", "정작 중요한 걸 까먹고 있었어..."},
            {"그럼 조사를 하러 가 볼까?", "마지막으로 한 번 더 가자 임복환!!"}
    };

    public static JFrame endingFrame = new JFrame();
    protected static DialogBox dialogBox;
    Thread th = new Thread(this);

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

    protected static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch(LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    File a = new File("audios/after_round3_mix.wav");
    AudioInputStream b = AudioSystem.getAudioInputStream(a);
    Clip c = AudioSystem.getClip();
    protected Map map = new Map();
    public EndingIntro() throws LineUnavailableException, IOException, UnsupportedAudioFileException
    {
        endingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endingFrame.setResizable(false); // 창크기 변경X
        endingFrame.setTitle("기묘한 박물관");
        endingFrame.setLayout(null);

        c.open(b);
        c.start();

        //맵, 다이얼로그
        endingFrame.setContentPane(map);
        map.add(dialogBox);
        dialogBox.setDialog(dialogBox, dialog[0], dialog[0].length);

        //임복환
        endingFrame.add(endingPlayer);
        endingPlayer.addEventListener(endingPlayer, endingFrame);

        //프레임
        endingFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        endingFrame.setSize(screenWidth, screenHeight);
        endingFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        endingFrame.setVisible(true);

        th.start();
    }

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        new EndingIntro();
    }

    @Override
    public void run() {
        Font font = new Font("Sam3KRFont", Font.PLAIN, 23);
        try {
            for (int k = 0; k < dialog.length; k++) {
                dialogVec.clear();
                dialogBox.setDialog(dialogBox, dialog[k], dialog[k].length);
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
                        dialogBox.repaint();
                        dialogBox.initSleep(30);
                    }
                    dialogBox.initSleep(180);
                }
                dialogBox.removeAll();
                dialogBox.initSleep(2000);
            }
            dialogBox.initSleep(2000);
            clip.stop();
            dialogBox.setVisible(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Map extends JPanel implements Tile, Runnable {
    private ImageIcon endingMap = new ImageIcon("images/tutorial_map.png");
    private Image endingImg = endingMap.getImage();
    public Thread th = new Thread(this);
    int alpha = 0;
    Color color = new Color(0,0,0,alpha);
    public Map() {
        setLayout(null);
        setBounds(0,0,screenWidth,screenHeight);
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(endingImg,0, 0, screenWidth, screenHeight,this);
    }

    @Override
    public void run(){
        while (alpha < 256) {
            setBackground(color);
            alpha++;
            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}