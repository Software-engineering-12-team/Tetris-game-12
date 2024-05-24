package main.java.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class HandleKeyEventTest {

    private JButton[] buttons;

    @BeforeEach
    void setUp() {
        buttons = new JButton[3];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i + 1));
        }
        HandleKeyEvent.selectedButtonIndex = 0; // 초기화
    }

    @Test
    void testHandleKeyEventDown() {
        KeyEvent keyEvent = new KeyEvent(buttons[0], KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED);
        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(1, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(2, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(0, HandleKeyEvent.selectedButtonIndex);
    }

    @Test
    void testHandleKeyEventUp() {
        KeyEvent keyEvent = new KeyEvent(buttons[0], KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(2, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(1, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(0, HandleKeyEvent.selectedButtonIndex);
    }

    @Test
    void testHandleKeyEventRight() {
        KeyEvent keyEvent = new KeyEvent(buttons[0], KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(1, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(2, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(0, HandleKeyEvent.selectedButtonIndex);
    }

    @Test
    void testHandleKeyEventLeft() {
        KeyEvent keyEvent = new KeyEvent(buttons[0], KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(2, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(1, HandleKeyEvent.selectedButtonIndex);

        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals(0, HandleKeyEvent.selectedButtonIndex);
    }

    @Test
    void testHandleKeyEventEnter() {
        // Mock the button click
        JButton button = buttons[0];
        button.addActionListener(e -> button.setText("Clicked"));

        KeyEvent keyEvent = new KeyEvent(button, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        HandleKeyEvent.handleKeyEvent(keyEvent, buttons, false);
        assertEquals("Clicked", button.getText());
    }
}
