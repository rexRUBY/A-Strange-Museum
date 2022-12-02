package main;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame{

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        MainPanel mainPanel = new MainPanel();
    }
}