/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stageTwo;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import main.Tile;


public class Game implements Tile{
	
	private static Game game;

    public static Game getInstance() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (game == null) {
            synchronized (Game.class) {
            	game = new Game();
            }
        }
        return game;
    }
    public static JFrame frame = new JFrame();
    protected File file = new File("audios/chung_mu_gong.wav");
    protected static Clip clip;
    
    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    
    public Game() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GamePanel());
        frame.pack();
        frame.setResizable(false); // 창크기 변경X
        frame.setTitle("기묘한 박물관");
        frame.setLayout(null);
        
        clip.open(AudioSystem.getAudioInputStream(file));
        clip.start();
        clip.loop(3);
        
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
    	new Game();
    }
}
