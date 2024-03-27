package main.java.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class ButtonStyle {
    public static void applyButtonStyle(JButton button) {
        button.setFont(new Font("NanumGothic", Font.BOLD, 18));
        button.setBackground(new Color(30, 60, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }
    
    public static void updateButtonStyles(JButton[] buttons, int selectedButtonIndex) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == selectedButtonIndex) {
                buttons[i].setBackground(new Color(120, 150, 180));
            } else {
                buttons[i].setBackground(new Color(30, 60, 90));
            }
        }
    }

}
