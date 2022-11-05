package main;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale; // 48x48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	Thread gameThread;
	
	public GamePanel() {
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		// FlowLayout은 setSize를 적용할 수 없으므로 setPreferredSize를 사용해줌.
		// Dimension 객체를 인자로 받으면서 컴포넌트의 기본 크기를 설정해줌.
		// this.setDoubleBuffered(true);
	}
	
	public void startGameThead() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
	}
}
