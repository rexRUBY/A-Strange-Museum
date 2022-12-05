package ending;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import main.Tile;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;
import main.DialogBox;

public class EndingCredit implements Tile {
    protected File file = new File("audios/ending.wav");
    protected Clip clip = AudioSystem.getClip();
    JFrame creditFrame = new JFrame();

    EndingCreditPanel endingCreditPanel = new EndingCreditPanel();
    private static EndingCredit ec;
    DialogBox dialogBox = new DialogBox();


    //싱글턴 패턴
    public static EndingCredit getInstance() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        if (ec == null) {
            ec = new EndingCredit();
        }
        return ec;
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        new EndingCredit();
    }

    //생성자
    public EndingCredit() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        creditFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creditFrame.setResizable(false); // 창크기 변경X
        creditFrame.setTitle("기묘한 박물관");

        creditFrame.add(endingCreditPanel);
        endingCreditPanel.darker.start();

        creditFrame.pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절
        creditFrame.setSize(screenWidth, screenHeight);
        creditFrame.setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        creditFrame.setVisible(true);
    }

    class EndingCreditPanel extends JPanel implements Tile {
        static float alpha = 0;
        Thread darker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (alpha < 60) {
                    setBackground(new Color(0, 0, 0,(int)alpha));
                    try {
                        sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    alpha+=0.02f;
                }
                endingCreditPanel.setVisible(false);
                Moving moving = null;
                try {
                    moving = new Moving();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                creditFrame.repaint();
                creditFrame.add(moving);
                try {
                    clip.open(AudioSystem.getAudioInputStream(file));
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                }
                clip.start();
            }
        });

        public EndingCreditPanel() {
            setLayout(null);
            setBounds(0, 0, screenWidth, screenHeight);
            setVisible(true);
        }
    }
}

class Moving extends JPanel implements Tile, ActionListener {
    Font font = new Font("Sam3KRFont", Font.PLAIN, 20);
    Timer creditTimer = new Timer(20, this);
    String text;
    int textY = screenHeight;
    ImageIcon icon = new ImageIcon("images/kkorazi.gif");
    JLabel kkorazi = new JLabel(icon);
    public Moving() throws InterruptedException {
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(0, 0, screenWidth, screenHeight);
        kkorazi.setBounds(15,160,297,200);
        add(kkorazi);
        text = "팀장                          한 솔\n\n"
                +"게임 기획                     신승하\n\n"
                +"메인게임 개발                 김주희\n"
                +"                              박예서\n\n"
                +"미니게임 개발                 김주희\n"
                +"                              박예서\n\n"
                +"배경음악/음향                  한 솔\n\n"
                +"배경 제작                      한 솔\n\n"
                +"주인공 제작                    신승하\n\n"
                +"에니메이션 제작                신승하\n\n"
                +"대사                           한 솔\n\n"
                +"프레젠테이션 제작              신승하\n\n"
                +"발표                          박예서\n\n\n\n"
                +"주연                         임복환\n"
                +"조연                         병 졸\n"
                +"                             충무공\n"
                +"                             송 강\n\n\n\n"
                +"지도교수              최보금 교수님\n\n"
                +"테스터                김주희 어머니\n"
                +"                      박예서 아버지\n"
                +"                      한  솔 여동생\n\n"
                +"도움주신 분            송선미 김미주\n"
                +"                      안태환 선배님\n"
                +"                      유튜버 라이쇼\n"
                +"                      정세희 조교님\n"
                +"                      정승우 조교님\n\n"
                +"징소 제공             공학관 812호\n"
                +"                      한솔 집\n"
                +"                      본관 스터디룸\n\n"
                +"팀명 후보               보금자리\n\n"
                +"좌우명                  불광불급\n\n\n\n\n\n"
                +"모두 한학기동안 정말 수고 많으셨습니다.\n\n"
                +"       이상 비단길팀 이었습니다.";
        creditTimer.restart();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        int y = textY;
        for(String line : text.split("\n"))
            g2d.drawString(line,340,y+=28);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textY--;
        repaint();
    }
}