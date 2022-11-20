package main;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JFrame {
    public JMenuBar mb = new JMenuBar();

    public MenuBar() {
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
                JOptionPane.showMessageDialog(null, "Back-End  김주희, 박예서\n" +
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
}