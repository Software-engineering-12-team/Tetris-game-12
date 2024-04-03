package main.java.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class ButtonStyle {
    public static void applyButtonStyle(JButton[] buttons, boolean isBackButton) {
        for (int i = 0; i < buttons.length; i++) {
        	buttons[i].setFont(new Font("NanumGothic", Font.BOLD, 18));
        	buttons[i].setBackground(new Color(30, 60, 90));
        	buttons[i].setForeground(Color.WHITE);
        	buttons[i].setFocusPainted(false);
        }
    	if (isBackButton) {
    		buttons[buttons.length - 1].setFont(new Font("NanumGothic", Font.BOLD, 16));
        	buttons[buttons.length - 1].setBackground(new Color(240, 240, 240));
        	buttons[buttons.length - 1].setForeground(Color.BLACK);
        	buttons[buttons.length - 1].setFocusPainted(false);
    	}
    }
    
    public static void updateButtonStyles(JButton[] buttons, int selectedButtonIndex, boolean isBackButton) {
    	for (int i = 0; i < buttons.length; i++) {
        	if (i == selectedButtonIndex) {
                buttons[i].setBackground(new Color(120, 150, 180));
            } else {
                buttons[i].setBackground(new Color(30, 60, 90));
            }
        }
    	if (isBackButton) {
    		if (selectedButtonIndex == buttons.length - 1) {
                buttons[buttons.length - 1].setBackground(new Color(180, 180, 180));
            } else {
                buttons[buttons.length - 1].setBackground(new Color(240, 240, 240));
            }
    	}
    }
}
