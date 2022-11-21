package main;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class Tutorial implements Tile{
    static String[][] dialog = {{"아니..", "분명 이곳에서 모이기로 했는데...", "왜 아무도 없지..?"},
            {"내가 역사 박물관에 온 이유는 단순하다. ", "팀프로젝트 자료조사 차 방문했지만", "어쩐지 아무도 없어 의아하던 찰나..!"},
            {"덜컹..."},
            {"쾅"},
            {"어 저기서 빛나는건 뭐지?"},
            {"가까이 가보자!!!"}
    };

    public static JFrame tutorialFrame = new JFrame();
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

    public static Player player = new Player();
    protected File file = new File("audios/strange_museum_tutorial.wav");
    protected Clip clip = AudioSystem.getClip();
    protected MapPanel map = new MapPanel();
    public Tutorial() throws LineUnavailableException, IOException, UnsupportedAudioFileException
    {
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
        map.add(dialogBox);
        dialogBox.setDialog(dialogBox, dialog[0], dialog[0].length);
        dialogBox.thread.start();

        //임복환
        map.add(player);
        player.addEventListener(player, tutorialFrame);

        //프레임
        tutorialFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        tutorialFrame.setSize(screenWidth, screenHeight);
        tutorialFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        tutorialFrame.setVisible(true);
    }

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        new Tutorial();
    }
}

class MapPanel extends JPanel implements Tile {
    private ImageIcon museumMap = new ImageIcon("images/tutorial_map.png");
    private Image mapImg = museumMap.getImage();

    public MapPanel() {
        setLayout(null);
        setBounds(0,0,screenWidth,screenHeight);
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImg,0, 0, screenWidth, screenHeight,this);
    }
}

class EmptyPanel extends JPanel implements Tile {
    public ImageIcon museumMap_empty = new ImageIcon("images/strange_museum_empty.jpg");
    public Image emptyImg = museumMap_empty.getImage();

    public EmptyPanel() {
        setLayout(null);
        setBounds(0,0,screenWidth,screenHeight);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(emptyImg, 0, 0, screenWidth, screenHeight, this);
    }
}