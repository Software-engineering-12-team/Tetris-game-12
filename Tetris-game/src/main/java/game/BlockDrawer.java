package main.java.game;

import java.awt.Color;
import java.awt.Graphics;

import main.java.game.Blocks.Tetrominoe;
import main.java.setting.colorblindmode.ColorBlindModeMenu;

public class BlockDrawer {    //블록을 그리는 클래스
	
	public static void drawBlock(Graphics g, int x, int y, int w, int h, Tetrominoe block) 
	{
		
        Color colors[] = {	//일반 색깔
            new Color(0, 0, 0),
                new Color(204, 102, 102),
            new Color(102, 204, 102),
                new Color(102, 102, 204),
            new Color(204, 204, 102),
                new Color(204, 102, 204),
            new Color(102, 204, 204),
                new Color(218, 170, 0),
            new Color(0, 0, 0),	
            	new Color(0, 0, 0),
            new Color(0, 0, 0),
            	new Color(0, 0, 0),
            new Color(0, 0, 0),
            	new Color(0, 0, 0),
            new Color(0, 0, 0),
            	new Color(0, 0, 0),
            new Color(255, 255, 255)
        };

        Color rgcbcolors[] = {    // 적록 색맹 모드 전용 색깔
                new Color(0, 0, 0),         
                new Color(255, 165, 0),     
                new Color(135, 206, 235),    
                new Color(0, 255, 255),      
                new Color(255, 255, 0),      
                new Color(0, 0, 255),         
                new Color(227, 66, 52),       
                new Color(147, 112, 219),    
                new Color(0, 0, 0), 		 
                new Color(0, 0, 0),	
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(255, 255, 255)
        };

        Color bycbcolors[] = {    // 청황 색맹 모드 전용 색깔
                new Color(204, 0, 0),
                new Color(0, 204, 0),
                new Color(0, 0, 153),
                new Color(255, 165, 0),
                new Color(255, 192, 203),
                new Color(128, 0, 128),
                new Color(128, 128, 128),
                new Color(64, 224, 208),
                new Color(0, 0, 0),
                new Color(0, 0, 0),	
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(255, 255, 255)
        };

        if(ColorBlindModeMenu.colorBlindStatus.equals("정상"))
        {
        	Color color = colors[block.ordinal()];
           	 g.setColor(color); 
        }
        else if(ColorBlindModeMenu.colorBlindStatus.equals("적녹색맹"))
        {
        	Color color = rgcbcolors[block.ordinal()];
           	 g.setColor(color);
        }
        else if(ColorBlindModeMenu.colorBlindStatus.equals("청황색맹"))
        {
        	Color color = bycbcolors[block.ordinal()];
           	 g.setColor(color);
        }
        
		g.setFont(Board.tetrisFont);
        g.drawString("O", x + w / 2 - 5, y + h / 2 + 5); 
        
        if (block == Tetrominoe.BorderBlock) {
            g.setColor(Color.WHITE);
            g.drawString("X", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.WeightItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("#", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.LineDelItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("L", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.ThreeItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("3", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.FiveItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("5", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.SevenItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("7", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.PlusItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("+", x + w / 2 - 5, y + h / 2 + 5);
        }
        else if (block == Tetrominoe.AllDelItem) {
        	g.setColor(Color.WHITE);
        	g.drawString("A", x + w / 2 - 5, y + h / 2 + 5);
        }
        
	}
}
