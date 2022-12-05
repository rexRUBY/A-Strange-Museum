package stageTwo;

import main.DialogBox;
import main.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.DialogBox.dialogVec;

public class Intro extends JPanel implements Tile {
    static private Game two;
    public static Game getInstance() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (two == null) {
            synchronized (Game.class) {
                two = new Game();
            }
        }
        return two;
    }

    JFrame introFrame = new JFrame();
    ImageIcon icon = new ImageIcon("images/stage2_background.png");
    Image img = icon.getImage();
    MapPanel panel = new MapPanel();

    public Intro() throws LineUnavailableException {
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setResizable(false);
        introFrame.setTitle("기묘한 박물관");
        introFrame.setLayout(null);
        introFrame.add(panel);
        panel.setBounds(0,0,screenWidth,screenHeight);
        panel.startIntro();
        introFrame.pack();
        introFrame.setSize(screenWidth,screenHeight);
        introFrame.setLocation(0,0);
        introFrame.setLocationRelativeTo(null);
        introFrame.setVisible(true);
    }

    public static void main(String[] args) throws LineUnavailableException {
        new Intro();
    }

    class MapPanel extends JPanel implements Runnable{
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
        ImageIcon bh = new ImageIcon("images/bokhwan.png");
        JLabel bokhwan = new JLabel(bh);
        ImageIcon sd = new ImageIcon("images/chungmu.png");
        JLabel chungmu = new JLabel(sd);

        static String[][] dialog = {{"이번엔 또 어디지?", "여긴... 바다같은데?", "왠지 예측이 되는걸?","(피융...!!)"},
                {"전장 한복판에서 그렇게 멀뚱히 서있을겐가?","면모를 보아하니 앳되보이는군","노젓는 병졸같은데...", "어서 자리로 돌아가 노을 젓도록 하게!"},
                {"헉 설마!!", "이 거대한 사람이 충무공 이순신?","말도 안 돼! 내눈앞에 이순신이 있다니", "열심히 노를 저어서 역사에 길이 남는","사나이가 되어야지"},
                {"겁이나느냐?", "필사즉생 필생즉사!","살고자하면 죽고 죽고자 하면 살것이다.","가자!"},
        };
        static String[] dialInit = {"이번엔 또 어디지?", "전장 한복판에서 그렇게 멀뚱히 서있을겐가?","헉 설마!!", "겁이나느냐?" };
        static int cnt=0;
        static int idx=0;

        public MapPanel() {
            setLayout(null);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }


        //주요 메서드
        public void startIntro() {
            bokhwan.setBounds(50,152,200,300);
            chungmu.setBounds(270,110,400,380);
            add(bokhwan);
            add(dialogBox);
            dialogBox.setDialog(dialogBox, dialog[0], dialog[0].length);
            Thread th = new Thread(this);
            th.start();
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
                            if(idx<dialInit.length) {
                                if (dialogVec.elementAt(i).equals(dialInit[idx])) {
                                    if (cnt % 2 == 0) {
                                        panel.add(bokhwan);
                                        panel.remove(chungmu);
                                        panel.repaint();
                                    } if(cnt %2 ==1){
                                        panel.add(chungmu);
                                        panel.remove(bokhwan);
                                        panel.repaint();
                                    }
                                    cnt++;
                                    idx++;
                                }
                            }
                            dialogBox.initSleep(30);
                        }
                        dialogBox.initSleep(180);
                    }
                    dialogBox.removeAll();
                    dialogBox.initSleep(2000);
                }
                dialogBox.initSleep(1000);
                dialogBox.setVisible(false);
                bokhwan.setVisible(false);
                chungmu.setVisible(false);
                introFrame.setVisible(false); //확인 안해봄
                new Game();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}