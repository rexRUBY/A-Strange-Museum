package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPanel extends JFrame implements PanelSize{
    public JLabel gameTitle = new JLabel("기묘한 박물관");
    public JLabel gameTitleShadow = new JLabel("기묘한 박물관");
    public JLabel imbokhwan;
    public ImagePanel titlePanel = new ImagePanel();

    public MainPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 창크기 변경X
        setTitle("기묘한 박물관");
        Container c = getContentPane();

        //버튼
        JButton startBtn = new JButton("시작");
        startBtn.setBounds(349,380, 70, 30);
        titlePanel.add(startBtn);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tutorial tutorial = new Tutorial();
                c.add(tutorial);
            }
        });

        //타이틀
        Font font = new Font("Sam3KRFont", Font.BOLD, 85);

        gameTitle.setFont(font);
        gameTitle.setForeground(Color.WHITE);
        gameTitle.setBounds(100,4,700,88);
        titlePanel.add(gameTitle);

        gameTitleShadow.setFont(font);
        gameTitleShadow.setForeground(Color.BLACK);
        gameTitleShadow.setBounds(104,0,700,88);
        titlePanel.add(gameTitleShadow);

        //캐릭터 이미지
        ImageIcon img = new ImageIcon("images/LBH.png");
        imbokhwan = new JLabel(img);
        imbokhwan.setBounds(tileSize, tileSize,50,50);
        titlePanel.add(imbokhwan);

        c.add(titlePanel);

        //메뉴
        createMenu();

        pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절

        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        setVisible(true);
    }

    public void createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu option = new JMenu("옵션"); // 소리, 단축키 설정
        JMenu help = new JMenu("도움말"); // 설명
        JMenu info = new JMenu("정보"); // 버전, 제작진...
        JMenu exit = new JMenu("게임 종료");

        JMenuItem sound = new JMenuItem("소리");
        JMenuItem key = new JMenuItem("단축키 설정");
        option.add(sound);
        option.add(key);

        info.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(MainPanel.this, "Back-End  김주희, 박예서\n" +
                        "Front-End 신승하, 한솔 ","개발자 정보", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "게임 종료", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else {
                    return;
                }
            }
        });

        mb.add(option);
        mb.add(help);
        mb.add(info);
        mb.add(exit);

        setJMenuBar(mb);
    }


    class ImagePanel extends JPanel implements Runnable{
        private ImageIcon museum = new ImageIcon("images/museum.jpg");
        private Image img = museum.getImage();

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
        public ImagePanel() {
            setLayout(null);
            Thread th=new Thread(this);
            th.start();

        }

        public void run() {
            while(true) {
                try {
                    Thread.sleep(90);
                }
                catch(InterruptedException e) {
                    return;
                }
                int y1 = gameTitle.getY();
                int y2 = gameTitleShadow.getY();
                if(y1<150) {
                    gameTitle.setLocation(gameTitle.getX(), y1+5);
                }
                if(y2<146) {
                    gameTitleShadow.setLocation(gameTitleShadow.getX(), y2+5);
                }
            }

        }
    }
}