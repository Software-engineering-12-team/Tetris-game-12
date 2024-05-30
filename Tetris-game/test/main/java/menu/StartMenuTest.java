package main.java.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class StartMenuTest {

    private StartMenu startMenu;

    @BeforeEach
    void setUp() {
        startMenu = new StartMenu();
    }

    @Test
    void testUIComponents() {
        // Test if labels are created
        assertNotNull(startMenu.titleLabel);
        assertNotNull(startMenu.instructionLabel);

        // Test if buttons are created
        assertNotNull(startMenu.gameStartButton);
        assertNotNull(startMenu.settingsButton);
        assertNotNull(startMenu.scoreboardButton);
        assertNotNull(startMenu.exitButton);

        // Test if buttons array is correctly initialized
        assertEquals(4, startMenu.buttons.length);
        assertSame(startMenu.gameStartButton, startMenu.buttons[0]);
        assertSame(startMenu.settingsButton, startMenu.buttons[1]);
        assertSame(startMenu.scoreboardButton, startMenu.buttons[2]);
        assertSame(startMenu.exitButton, startMenu.buttons[3]);
    }

    /*@Test
    void testButtonActions() {
        // Test game start button action
        startMenu.gameStartButton.doClick();
        assertFalse(startMenu.isVisible());

        // Test settings button action
        startMenu = new StartMenu(); // Reinitialize to reset state
        startMenu.settingsButton.doClick();
        assertFalse(startMenu.isVisible());

        // Test scoreboard button action
        startMenu = new StartMenu(); // Reinitialize to reset state
        startMenu.scoreboardButton.doClick();
        assertFalse(startMenu.isVisible());

        // Test exit button action
        startMenu = new StartMenu(); // Reinitialize to reset state
        startMenu.exitButton.doClick();
        assertFalse(startMenu.isVisible());
    }*/

/*    @Test
    void testSetLookAndFeel() {
        // Test if Nimbus LookAndFeel is set
        StartMenu.setLookAndFeel();
        assertEquals("Nimbus", UIManager.getLookAndFeel().getName());
    }*/

    @Test
    void testMain() {
        // Test main method to ensure it runs without exceptions
        assertDoesNotThrow(() -> StartMenu.main(new String[]{}));
    }
}
