package stageThree;

import main.Tile;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StageThree_Main implements Tile {
    public static JFrame mainFrame = new JFrame();

    protected File file = new File("audios/song_gang.wav");
    protected static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    GamePlay threePanel = new GamePlay();

    public StageThree_Main() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setTitle("기묘한 박물관");

        Font font = new Font("Sam3KRFont", Font.BOLD, 50);

        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();
        clip.loop(3);

        mainFrame.add(threePanel);
        mainFrame.pack();
        mainFrame.setSize(screenWidth,screenHeight);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        new StageThree_Main();
    }
}
