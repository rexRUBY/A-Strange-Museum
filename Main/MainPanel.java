package main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPanel extends JFrame implements PanelSize{
	
	JLabel gameTitle = new JLabel("기묘한 박물관");
	
	public MainPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 창크기 변경X
		setTitle("기묘한 박물관-실험 파일");
		Container c = getContentPane();
		ImagePanel p = new ImagePanel();
		
		JButton start = new JButton("시작");
		start.setBounds(349,380, 70, 30);
		p.add(start);
		
		Font f = new Font("Sam3KRFont", Font.BOLD, 85);
		//ImageIcon title = new ImageIcon("image/title.PNG");
		gameTitle.setFont(f);
		gameTitle.setForeground(Color.WHITE);
		gameTitle.setBounds(100,150,700,88);
		p.add(gameTitle);
		
		c.add(p);
		
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


	class ImagePanel extends JPanel{
		private ImageIcon museum = new ImageIcon("image/museum.png");
		private Image img = museum.getImage();
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
		public ImagePanel() {
			setLayout(null);
		}
	}
}
