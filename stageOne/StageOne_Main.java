package stageOne;

import main.Tile;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.JFrame;

public class StageOne_Main implements Tile{
    public static JFrame mainFrame = new JFrame();
    protected File file = new File("audios/stage_one.wav");
    protected static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    Gameplay gamePlay = new Gameplay();
    
    StageOne_Main() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setTitle("기묘한 박물관");

        Font font = new Font("Sam3KRFont", Font.BOLD, 50);

        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();
        clip.loop(3);

        mainFrame.add(gamePlay);
        mainFrame.pack();
        mainFrame.setSize(screenWidth,screenHeight);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
    	new StageOne_Main();
    }
}