package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tutorial extends JPanel implements Runnable, PanelSize{

    //main
    public static void main(String[] args){
        Tutorial tutorial = new Tutorial();
    }

    //Constructor
    public Tutorial() {
        setSize(screenWidth,screenHeight);
        setVisible(true);
    }

    //Thread
    @Override
    public void run() {

    }
}

