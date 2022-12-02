package stageOne;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.Tile;

public class MapGenerator implements Tile
{
    public int map[][];
    public int brickWidth;
    public int brickHeight;
	private ImageIcon blockMap = new ImageIcon("images/stage1_background.png");
	private Image mapImg = blockMap.getImage();
	private ImageIcon blockIcon = new ImageIcon("images/block.png");
	private Image block = blockIcon.getImage();

    public MapGenerator (int row, int col)
    {
        map = new int[row][col];
        for(int i = 0; i<map.length; i++)
        {
            for(int j =0; j<map[0].length; j++)
            {
                map[i][j] = 1;
            }
        }
        brickWidth = 750/col;
        brickHeight = 180/row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0)
                    g.drawImage(block, j * brickWidth + 5, i * brickHeight + 50, brickWidth, brickHeight, null);
            }
        }
    }

    public void setBrickValue(int value, int row, int col)
    {
        map[row][col] = value;
    }
}
