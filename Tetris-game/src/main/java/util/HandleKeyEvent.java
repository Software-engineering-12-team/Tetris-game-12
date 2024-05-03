package main.java.util;

import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class HandleKeyEvent {
	public static int selectedButtonIndex = 0;
	
	public static void handleKeyEvent(KeyEvent e, JButton[] buttons, boolean isBackButton) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_RIGHT) {
            selectedButtonIndex = (selectedButtonIndex + 1) % buttons.length;
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT) {
            selectedButtonIndex = (selectedButtonIndex - 1 + buttons.length) % buttons.length;
        } else if (keyCode == KeyEvent.VK_ENTER) {
        	System.out.println(selectedButtonIndex);
            buttons[selectedButtonIndex].doClick();
            return; // Enter 키 입력 후 추가 동작을 방지하기 위해 여기서 종료
        }
        ButtonStyle.updateButtonStyles(buttons, selectedButtonIndex, isBackButton);
        System.out.println(selectedButtonIndex);
    }

}
