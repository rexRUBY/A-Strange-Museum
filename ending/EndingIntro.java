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
    static String[][] dialog = {{"(시끌시끌)"}, {"어... 뭐지? 이 오묘한 느낌은?","사람들이 많아진걸 보니","현실 세상으로 돌아온것같아."},
            {"큰일났다!", "어서 자료조사를 해야하는데...", "다른 전시관도 둘러봐야겠다."}
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

    protected File file = new File("audios/strange_museum_tutorial.wav");
    protected Map map = new Map();
    public EndingIntro() throws LineUnavailableException, IOException, UnsupportedAudioFileException
    {
        endingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endingFrame.setResizable(false); // 창크기 변경X
        endingFrame.setTitle("기묘한 박물관");
        endingFrame.setLayout(null);

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
            dialogBox.setVisible(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class Map extends JPanel implements Tile, Runnable {
    private ImageIcon endingMap = new ImageIcon("images/ending_intro.png");
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