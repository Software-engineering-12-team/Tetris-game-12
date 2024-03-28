package main.java.util;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ScreenAdjustComponent {
	public static void adjustLabelSize(JLabel[] labels, double scaleFactor) {
		int titleLabelFontSize = 30;
        int labelFontSize = 16;
        
        // 0번째만 title로 받음
        int adjustedTitleLabelFontSize = (int) (titleLabelFontSize * scaleFactor);
        labels[0].setFont(new Font("NanumGothic", Font.BOLD, adjustedTitleLabelFontSize));
        
        // 나머지는 동일한 label로 처리
        int adjustedLabelFontSize = (int) (labelFontSize * scaleFactor);
        for (int i = 1; i < labels.length; i++) {
         labels[i].setFont(new Font("NanumGothic", Font.BOLD, adjustedLabelFontSize));
        }
    }
	
	public static void adjustButtonSize(JButton[] buttons, double scaleFactor) {
        int buttonFontSize = 18;

        int adjustedButtonFontSize = (int) (buttonFontSize * scaleFactor);
        for (JButton button : buttons) {
            button.setFont(new Font("NanumGothic", Font.BOLD, adjustedButtonFontSize));
        }
    }

}
