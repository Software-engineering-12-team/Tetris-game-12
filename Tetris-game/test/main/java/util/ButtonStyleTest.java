package main.java.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

import static org.junit.jupiter.api.Assertions.*;

class ButtonStyleTest {

    private JButton[] buttons;

    @BeforeEach
    void setUp() {
        buttons = new JButton[3];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i + 1));
        }
    }

    @Test
    void applyButtonStyle() {
        ButtonStyle.applyButtonStyle(buttons, false);

        for (int i = 0; i < buttons.length; i++) {
            assertEquals(new Font("NanumGothic", Font.BOLD, 18), buttons[i].getFont());
            assertEquals(new Color(30, 60, 90), buttons[i].getBackground());
            assertEquals(Color.WHITE, buttons[i].getForeground());
            assertFalse(buttons[i].isFocusPainted());
        }
    }

    @Test
    void applyButtonStyleWithBackButton() {
        ButtonStyle.applyButtonStyle(buttons, true);

        for (int i = 0; i < buttons.length - 1; i++) {
            assertEquals(new Font("NanumGothic", Font.BOLD, 18), buttons[i].getFont());
            assertEquals(new Color(30, 60, 90), buttons[i].getBackground());
            assertEquals(Color.WHITE, buttons[i].getForeground());
            assertFalse(buttons[i].isFocusPainted());
        }

        JButton backButton = buttons[buttons.length - 1];
        assertEquals(new Font("NanumGothic", Font.BOLD, 16), backButton.getFont());
        assertEquals(new Color(240, 240, 240), backButton.getBackground());
        assertEquals(Color.BLACK, backButton.getForeground());
        assertFalse(backButton.isFocusPainted());
    }

    @Test
    void updateButtonStyles() {
        ButtonStyle.applyButtonStyle(buttons, false);
        ButtonStyle.updateButtonStyles(buttons, 1, false);

        for (int i = 0; i < buttons.length; i++) {
            if (i == 1) {
                assertEquals(new Color(120, 150, 180), buttons[i].getBackground());
            } else {
                assertEquals(new Color(30, 60, 90), buttons[i].getBackground());
            }
        }
    }

    @Test
    void updateButtonStylesWithBackButton() {
        ButtonStyle.applyButtonStyle(buttons, true);
        ButtonStyle.updateButtonStyles(buttons, 2, true);

        for (int i = 0; i < buttons.length - 1; i++) {
            if (i == 2) {
                assertEquals(new Color(120, 150, 180), buttons[i].getBackground());
            } else {
                assertEquals(new Color(30, 60, 90), buttons[i].getBackground());
            }
        }

        JButton backButton = buttons[buttons.length - 1];
        if (2 == buttons.length - 1) {
            assertEquals(new Color(180, 180, 180), backButton.getBackground());
        } else {
            assertEquals(new Color(240, 240, 240), backButton.getBackground());
        }
    }
}
