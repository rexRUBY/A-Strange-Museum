package main;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class EmptyFrame implements Tile{
    public EmptyPanel emptyPanel = new EmptyPanel();
    static String[][] dialog = {
            {"저기 빛나는 것은 뭐지...?"},
            {"가까이 가보자!"}
    };

    public static JFrame emptyFrame = new JFrame();
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
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected File file = new File("audios/strange_museum_tutorial.wav");
    protected Clip clip = AudioSystem.getClip();

    public EmptyFrame() throws LineUnavailableException, IOException, UnsupportedAudioFileException
    {
        emptyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emptyFrame.setResizable(false); // 창크기 변경X
        emptyFrame.setJMenuBar(menuBar.mb);
        emptyFrame.setTitle("기묘한 박물관");
        emptyFrame.setLayout(null);

        //오디오
        //clip.open(AudioSystem.getAudioInputStream(file));
        //clip.start();
        //clip.loop(5);

        //맵
        emptyFrame.add(emptyPanel);
        //emptyFrame.setContentPane(backGround);
//        backGround.add(dialogBox);
//        dialogBox.setDialog(dialogBox, dialog[0], dialog[0].length);
//        dialogBox.thread.start();

        //임복환
        emptyFrame.add(player);
        player.addEventListener(player, emptyFrame);

        //프레임
        emptyFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        emptyFrame.setSize(screenWidth, screenHeight);
        emptyFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        emptyFrame.setVisible(true);
    }

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        new EmptyFrame();
    }
}

class BackGround extends JLabel implements Tile {
    public ImageIcon museumMap_empty = new ImageIcon("images/strange_museum_empty.jpg");
    public Image emptyImg = museumMap_empty.getImage();

    public BackGround() {
        setLayout(null);
        setBounds(0,0,screenWidth,screenHeight);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(emptyImg, 0, 0, screenWidth, screenHeight, this);
    }
}
