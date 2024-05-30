package main.java.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.java.game.Blocks.Tetrominoe;
import main.java.setting.SettingFileWriter;
import main.java.setting.colorblindmode.ColorBlindModeMenu;

public class BlockDrawer {    //블록을 그리는 클래스
	
	public static void drawBlock(Graphics g, int x, int y, int w, int h, Tetrominoe block, int index, int LBlock) 
	{
		
        Color colors[] = {	//일반 색깔
            new Color(0, 0, 0), //NoBlock
                new Color(204, 102, 102), //ZBlock
            new Color(102, 204, 102), //SBlock
                new Color(102, 102, 204), //IBlock
            new Color(204, 204, 102), //TBlock
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
            new Color(255, 255, 255),
            new Color(128, 128, 128)
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
                new Color(255, 255, 255),
                new Color(128, 128, 128)
        };

        Color bycbcolors[] = {    // 청황 색맹 모드 전용 색깔
                new Color(0, 0, 0), //NoBlock 색상 수정
                new Color(0, 204, 0),
                new Color(0, 0, 153),
                new Color(255, 165, 0),
                new Color(255, 192, 203),
                new Color(128, 0, 128),
                new Color(255, 255, 0), //회색 색상 수정 > 노란색
                new Color(64, 224, 208),
                new Color(0, 0, 0),
                new Color(0, 0, 0),	
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(0, 0, 0),
                new Color(255, 255, 255),
                new Color(128, 128, 128)
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
        
        
        if (SettingFileWriter.readSize() == 0) {
        	x += 8;
            y -= 16;
        } else if (SettingFileWriter.readSize() == 1) {
        	x += 8;
            y -= 27;
        } else {
        	x += 8;
            y -= 16;
        }
        
		g.setFont(Board.tetrisFont);
        if(block == Tetrominoe.GrayBlock || block == Tetrominoe.NoBlock) {
            if(SettingFileWriter.readSize() == 0) // 화면 크기에 따른 폰트 크기 변경
            	g.setFont(new Font("Arial", Font.BOLD, 19
            			));
            else if(SettingFileWriter.readSize() == 1)
            	g.setFont(new Font("Arial", Font.BOLD, 20));
            else
            	g.setFont(new Font("Arial", Font.BOLD, 22));
        }
        
        if(index != LBlock)
        g.drawString("O", x + w / 2 - 5, y + h / 2 + 5);
        else
        g.drawString("L", x + w / 2 - 5, y + h / 2 + 5);	
        
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
