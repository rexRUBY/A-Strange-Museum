package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPanel extends JFrame{
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public MainPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 창크기 변경X
        setTitle("기묘한 박물관");
        Container c = getContentPane();
        c.setBackground(Color.BLACK);

        createMenu();

        pack(); // JFrame 내용물 크기에 맞게 윈도우 크기 조절

        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null); // 윈도우 창 화면 가운데 배치
        setVisible(true);

    }

    private void createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu option = new JMenu("옵션"); // 소리, 단축키 설정
        JMenu help = new JMenu("도움말"); // 설명
        JMenu info = new JMenu("정보"); // 버전, 제작진...
        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(MainPanel.this, "Back-End  김주희, 박예서\n" +
                        "Front-End 신승하, 한솔 ","개발자 정보", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenu exit = new JMenu("게임 종료");
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

        JMenuItem sound = new JMenuItem("소리");
        JMenuItem key = new JMenuItem("단축키 설정");
        option.add(sound);
        option.add(key);

        mb.add(option);
        mb.add(help);
        mb.add(info);
        mb.add(exit);

        setJMenuBar(mb);
    }

}