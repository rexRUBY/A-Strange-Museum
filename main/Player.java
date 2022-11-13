package main;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Player extends JLabel implements PanelSize, Moveable{

    private int x,y; //좌표
    private int speed = 3; //플레이어 속도 상태

    //움직임 상태
    boolean left = false; //왼
    boolean right = false; //오
    boolean up = false; //위
    boolean down = false; //아래

    private ImageIcon p_down, down1, down2;
    private ImageIcon p_up, up1, up2;
    private ImageIcon p_right, right1, right2;
    private ImageIcon p_left, left1, left2;

    public Player() {
        getPlayerImage();
        defaultSetting();
    }

    public void getPlayerImage() {
        p_down = new ImageIcon("images/front.png");
        down1 = new ImageIcon("images/front_l.png");
        down2 = new ImageIcon("images/front_r.png");
        p_up = new ImageIcon("images/back.png");
        up1 = new ImageIcon("images/back_l.png");
        up2 = new ImageIcon("images/back_r.png");
        p_right = new ImageIcon("images/right.png");
        right1 = new ImageIcon("images/right_l.png");
        right2 = new ImageIcon("images/right_r.png");
        p_left = new ImageIcon("images/left.png");
        left1 = new ImageIcon("images/left_l.png");
        left2 = new ImageIcon("images/left_r.png");
    }

    public void defaultSetting() {
        x = 100;
        y = 220;

        this.setIcon(p_down);
        this.setSize(tileSize, tileSize);
        this.setLocation(x, y);
    }
    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void left() {
        left = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (left) {
                    setIcon(p_left);
                    x -= speed;
                    if(x>=-15 && x <= screenWidth-tileSize)
                        setLocation(x, y);
                    else x+=speed;
                    initSleep(10);
                    setIcon(left1);
                    x -= speed;
                    if(x>=-15 && x <= screenWidth-tileSize)
                        setLocation(x,y);
                    else x+=speed;
                    initSleep(80);
                    setIcon(left2);
                    x -= speed;
                    if(x>=-15 && x <= screenWidth-tileSize)
                        setLocation(x, y);
                    else x+=speed;
                    setIcon(p_left);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void right() {
        right = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (right) {
                    setIcon(p_right);
                    x += speed;
                    if(x>=-15 && x <= screenWidth-tileSize)
                        setLocation(x, y);
                    else x-=speed;
                    initSleep(10);
                    setIcon(right1);
                    x += speed;
                    if(x>=-15 && x <= screenWidth-tileSize)
                        setLocation(x, y);
                    else x-=speed;
                    initSleep(80);
                    setIcon(right2);
                    x += speed;
                    if(x>=-15 && x <= screenWidth-tileSize)
                        setLocation(x, y);
                    else x-=speed;
                    setIcon(p_right);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void up() {
        up = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (up) {
                    setIcon(p_up);
                    y -= speed;
                    if(y>=-10 && y <= 480)
                        setLocation(x, y);
                    else y+=speed;
                    initSleep(10);
                    setIcon(up1);
                    y -= speed;
                    if(y>=-10 && y <= 480)
                        setLocation(x, y);
                    else y+=speed;
                    initSleep(80);
                    setIcon(up2);
                    y -= speed;
                    if(y>=-10 && y <= 480)
                        setLocation(x, y);
                    else y += speed;
                    initSleep(80);
                    setIcon(p_up);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void down() {
        down = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (down) {
                    setIcon(p_down);
                    y += speed;
                    if(y>=-10 && y <= 480)
                        setLocation(x, y);
                    else y-=speed;
                    initSleep(10);
                    setIcon(down1);
                    y += speed;
                    if(y>=-10 && y <= 480)
                        setLocation(x, y);
                    else y-=speed;
                    initSleep(80);
                    setIcon(down2);
                    y += speed;
                    if(y>=-10 && y <= 480)
                        setLocation(x, y);
                    else y-=speed;
                    initSleep(80);
                    setIcon(p_down);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void initSleep(int sleepNum) {
        try {
            Thread.sleep(sleepNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addEventListener(Player player, JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(player.isLeft() == false) {
                        player.left();//left() 한번 호출하면 isLeft --> true
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(player.isRight()==false) {
                        player.right();
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if(player.isUp() == false) {
                        player.up();
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(player.isDown() == false) {
                        player.down();
                    }
                }

            }// end of KeyPressed

            // 키보드 해제 처리
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setLeft(false);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // 오른쪽 화살표 누르고 있는 순간은 계속 오른쪽으로 가고 있는 상태 중
                    // 뗐을 때 while문 도는 걸 멈추어야 함!
                    player.setRight(false);
                } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                    player.setUp(false);
                } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.setDown(false);
                }
            }

        });

    }
}