package stageOne;

import main.DialogBox;
import main.Tile;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.DialogBox.dialogVec;

public class Intro extends JPanel implements Tile {
    static private Intro intro;
    public static Intro getInstance() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (intro == null) {
            synchronized (StageOne_Main.class) {
                intro = new Intro();
            }
        }
        return intro;
    }

    JFrame introFrame = new JFrame();
    ImageIcon icon = new ImageIcon("images/stage1_background.png");
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
        ImageIcon sd = new ImageIcon("images/solder.png");
        JLabel solder = new JLabel(sd);

        static String[][] dialog = {{"여, 여긴 어디지?"}, {"이봐!", "자네 여기서 뭐 하는 거야?", "당장 자리로 가지 않고"},
                {"네?","자리로 가라고요?", "아니 그보다 여긴 대체..."},
                {"무슨 얼빠진 소리를 하는 거야?", "우린 지금 대군 나리의 명을 받을어", "역적 김종서와 그 일당을 척살하러","가는 길이 아닌가!"},
                {"(김종서? 그럼 여기가", "조선시대라고?","말도 안 돼!", "일단 여기서 벗어나야겠어.)"},
                {"아 그게... 난... 저기...","뒷간에 좀 다녀와겠는데..."},
                {"한시가 급한 상황에 뒷간이라니?","설마 이제와서 발을 빼려는 겐가?","겁쟁이 같은 소리 말고, 자 어서 가세!"},
                {"아니, 내 말좀 들어봐요!", "잠깐...!"}
        };
        static String[] dialInit = {"여, 여긴 어디지?", "이봐!", "네?", "무슨 얼빠진 소리를 하는 거야?", "(김종서? 그럼 여기가",
                "아 그게... 난... 저기...", "한시가 급한 상황에 뒷간이라니?", "아니, 내 말좀 들어봐요!" };
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
            solder.setBounds(500,152,200,300);
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
                            if(idx<=7) {
                                if (dialogVec.elementAt(i).equals(dialInit[idx])) {
                                    if(cnt==5 && idx==5){
                                        panel.add(bokhwan);
                                        panel.remove(solder);
                                        panel.repaint();
                                        cnt--;
                                    }
                                    else if (cnt % 2 == 0) {
                                        panel.add(bokhwan);
                                        panel.remove(solder);
                                        panel.repaint();
                                    } else if(cnt %2 ==1){
                                        panel.add(solder);
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
                solder.setVisible(false);
                introFrame.setVisible(false); //확인 안해봄
                new StageOne_Main();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}