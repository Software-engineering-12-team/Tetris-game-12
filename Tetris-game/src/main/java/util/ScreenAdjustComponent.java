package main.java.util;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenAdjustComponent {
	public static void sizeAdjust(JLabel[] labels, JButton[] buttons, boolean isBackButton, int size) {
		JFrame frame = new JFrame();
		if (size == 0) {
            ScreenAdjustComponent.adjustLabelSize(labels, 1.0);
            ScreenAdjustComponent.adjustButtonSize(buttons, 1.0, isBackButton);
            frame.revalidate();
		}
		else if (size == 1) {
            ScreenAdjustComponent.adjustLabelSize(labels, 1.1);
            ScreenAdjustComponent.adjustButtonSize(buttons, 1.1, isBackButton);
            frame.revalidate();
		}
		else {
            ScreenAdjustComponent.adjustLabelSize(labels, 1.2);
            ScreenAdjustComponent.adjustButtonSize(buttons, 1.2, isBackButton);
            frame.revalidate();
		}
    }
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
	
	public static void adjustButtonSize(JButton[] buttons, double scaleFactor, boolean isBackButton) {
        int buttonFontSize = 18;
        int backButtonFontSize = 16;

        int adjustedButtonFontSize = (int) (buttonFontSize * scaleFactor);
        for (int i = 0; i < buttons.length - 1; i++) {
        	buttons[i].setFont(new Font("NanumGothic", Font.BOLD, adjustedButtonFontSize));
        }
        
        int adjustedbackButtonFontSize = (int) (backButtonFontSize * scaleFactor);
        buttons[buttons.length - 1].setFont(new Font("NanumGothic", Font.BOLD, adjustedbackButtonFontSize));
    }

}
