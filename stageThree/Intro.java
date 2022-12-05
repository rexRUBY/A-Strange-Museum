package stageThree;

import main.DialogBox;
import main.Tile;
import stageTwo.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.DialogBox.dialogVec;

public class Intro extends JPanel implements Tile {
    static private stageThree.Intro intro;
    public static Intro getInstance() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (intro == null) {
            synchronized (StageThree_Main.class) {
                intro = new stageThree.Intro();
            }
        }
        return intro;
    }

    JFrame introFrame = new JFrame();
    ImageIcon icon = new ImageIcon("images/stage3_background.png");
    Image img = icon.getImage();
    stageThree.Intro.MapPanel panel = new stageThree.Intro.MapPanel();

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
        new stageThree.Intro();
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
        ImageIcon sd = new ImageIcon("images/songgang.png");
        JLabel songgang = new JLabel(sd);

        static String[][] dialog = {{"여, 여기는... 산 속?", "생각보다 너무 평화로운데..."},
                {"이보게 젊은이","이곳엔 무슨 일인가?"},
                {"깜짝이야! 혹시 누구신지...?"},
                {"나는 정철이라고 하네, 자네는?"},
                {"저는 임복환이라고 합니다"},
                {"그렇군...","내가 지금은 몰골이 이래도", "얼마 전 까지는 전하를 모시던 충신이었다네..."},
                {"아, 그렇습니까?"},
                {"게다가... (1시간 후)"},
                {"네, 아 예","그렇군요...","(대체 언제까지 듣고만 있어야 하는거야!?)"},
                {"내가 한창 할때는 말이지"},
                {"으악 그만!!!"}
        };
        static String[] dialInit = {"여, 여기는... 산 속?", "이보게 젊은이",  "깜짝이야! 혹시 누구신지...?", "나는 정철이라고 하네, 자네는?", "저는 임복환이라고 합니다",
                "그렇군...", "아, 그렇습니까?", "게다가... (1시간 후)", "네, 아 예", "내가 한창 할때는 말이지", "으악 그만!!!"
        };
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
            songgang.setBounds(420,110,400,380);
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
                                        panel.remove(songgang);
                                        panel.repaint();
                                    } if(cnt %2 ==1){
                                        panel.add(songgang);
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
                songgang.setVisible(false);
                introFrame.setVisible(false); //확인 안해봄
                new StageThree_Main();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}