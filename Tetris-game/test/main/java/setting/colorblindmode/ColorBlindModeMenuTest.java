package main.java.setting.colorblindmode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ColorBlindModeMenuTest {

    private ColorBlindModeMenu menu;

    @BeforeEach
    public void setUp() {
        menu = new ColorBlindModeMenu();
    }

    @Test
    public void testInitialState() {
        assertEquals("정상", ColorBlindModeMenu.colorBlindStatus);
        assertButtonSelected(menu.normalButton, true);
        assertButtonSelected(menu.redGreenBlindButton, false);
        assertButtonSelected(menu.blueYellowBlindButton, false);
    }

    @Test
    public void testNormalButtonAction() {
        menu.normalButton.doClick();
        assertEquals("정상", ColorBlindModeMenu.colorBlindStatus);
        assertButtonSelected(menu.normalButton, true);
        assertButtonSelected(menu.redGreenBlindButton, false);
        assertButtonSelected(menu.blueYellowBlindButton, false);
    }

    @Test
    public void testRedGreenBlindButtonAction() {
        menu.redGreenBlindButton.doClick();
        assertEquals("적녹색맹", ColorBlindModeMenu.colorBlindStatus);
        assertButtonSelected(menu.normalButton, false);
        assertButtonSelected(menu.redGreenBlindButton, true);
        assertButtonSelected(menu.blueYellowBlindButton, false);
    }

    @Test
    public void testBlueYellowBlindButtonAction() {
        menu.blueYellowBlindButton.doClick();
        assertEquals("청황색맹", ColorBlindModeMenu.colorBlindStatus);
        assertButtonSelected(menu.normalButton, false);
        assertButtonSelected(menu.redGreenBlindButton, false);
        assertButtonSelected(menu.blueYellowBlindButton, true);
    }

    private void assertButtonSelected(JButton button, boolean isSelected) {
        Color expectedColor = isSelected ? new Color(30, 90, 100) : new Color(30, 60, 90);
        assertEquals(expectedColor, button.getBackground());
        if (isSelected) {
            assertTrue(button.getText().contains("선택됨"));
        } else {
            assertFalse(button.getText().contains("선택됨"));
        }
    }
}
