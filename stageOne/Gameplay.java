package stageOne;

import main.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener, Tile
{
    private boolean play = false;

    private int totalBricks = 48;

    private Timer timer;
    private int delay=8;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;
    private static ImageIcon blockMap = new ImageIcon("images/stage1_background.png");
    public static Image mapImg = blockMap.getImage();
    private ImageIcon bombImg = new ImageIcon("images/bomb.png");
    private Image bomb = bombImg.getImage();

    public Gameplay()
    {
        map = new MapGenerator(4, 12);//디폴트 4,12
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(mapImg, 0, 0, screenWidth, screenHeight, null);
        // 배경
        map.draw((Graphics2D) g);

        // 판
        g.setColor(new Color(91,68,42));
        g.fillRect(playerX, 530, 100, 8);

        // 공
        g.drawImage(bomb,ballposX, ballposY, 20, 20, null);

        // 이겼을 때
        if(totalBricks <= 0)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.WHITE);
            g.setFont(new Font("Sam3KRFont",Font.BOLD, 30));
            g.drawString("승리", 340, 270);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Sam3KRFont",Font.PLAIN, 20));
            g.drawString("Enter를 누르면 메인화면으로 돌아갑니다.", 180,310);
        }

        // 졌을 때
        if(ballposY > 554)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Sam3KRFont",Font.BOLD, 30));
            g.drawString("패배", 340,270);

            g.setColor(Color.RED);
            g.setFont(new Font("Sam3KRFont",Font.PLAIN, 20));
            g.drawString("Enter를 누르면 재시작", 270,310);
        }

        g.dispose();
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 668)
            {
                playerX = 668;
            }
            else
            {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX < 8)
            {
                playerX = 8;
            }
            else
            {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(totalBricks <= 0) {
                new AfterStageOne();
                StageOne_Main.mainFrame.setVisible(false);
                StageOne_Main.clip.stop();
            }
            if(!play)
            {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                //StageOne_Main.mainFrame.setVisible(false);

                repaint();
            }
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
                new AfterStageOne();
                StageOne_Main.mainFrame.setVisible(false);
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void moveRight()
    {
        play = true;
        playerX+=40;
    }

    public void moveLeft()
    {
        play = true;
        playerX-=40;
    }

    public void actionPerformed(ActionEvent e)
    {
        timer.start();
        if(play)
        {
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 530, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = -2;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 530, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 530, 40, 8)))
            {
                ballYdir = -ballYdir;
            }

            // 공과 맵의 충돌 체크
            A: for(int i = 0; i<map.map.length; i++)
            {
                for(int j =0; j<map.map[0].length; j++)
                {
                    if(map.map[i][j] > 0)
                    {
                        int brickX = j * map.brickWidth + 5;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect))
                        {
                            map.setBrickValue(0, i, j);
                            totalBricks--;

                            // 오른쪽이나 왼쪽 블록을 치면
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
                            {
                                ballXdir = -ballXdir;
                            }
                            // 위나 아래 블록을 치면
                            else
                            {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX < 0)
            {
                ballXdir = -ballXdir;
            }
            if(ballposY < 0)
            {
                ballYdir = -ballYdir;
            }
            if(ballposX > 746)
            {
                ballXdir = -ballXdir;
            }

            repaint();
        }
    }
}