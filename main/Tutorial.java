package main;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class Tutorial implements PanelSize{
    JFrame tutorialFrame = new JFrame();
    MenuPanel menuPanel = new MenuPanel();
    DialogBox dialogBox = new DialogBox();
    Player player = new Player();

    public Tutorial()
    {
        tutorialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tutorialFrame.setResizable(false); // 창크기 변경X
        tutorialFrame.setJMenuBar(menuPanel.mb);
        tutorialFrame.setTitle("기묘한 박물관");
        tutorialFrame.setLayout(null);
        tutorialFrame.setContentPane(dialogBox);
        dialogBox.setLayout(null);

        tutorialFrame.add(player);
        player.addEventListener(player, tutorialFrame);

        tutorialFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        tutorialFrame.setSize(screenWidth, screenHeight);
        tutorialFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        tutorialFrame.setVisible(true);

        //dialogBox
        dialogBox.setDialog(dialogBox, "구냥 당신이랑 여기있구싶다구");
        dialogBox.thread.run();
    }
    public static void main(String[] args){
        new Tutorial();
    }
}