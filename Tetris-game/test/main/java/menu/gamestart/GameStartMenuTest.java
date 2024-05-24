package main.java.menu.gamestart;

import main.java.menu.StartMenu;
import main.java.setting.SettingFileWriter;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;
import main.java.game.TetrisGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class GameStartMenuTest {

    private GameStartMenu gameStartMenu;

    @BeforeEach
    void setUp() {
        gameStartMenu = new GameStartMenu();
    }

    @Test
    void testUIComponents() {
        // Test if titleLabel is created
        assertNotNull(gameStartMenu.titleLabel);

        // Test if inputLabels are created
        assertNotNull(gameStartMenu.inputLabels);
        assertEquals(3, gameStartMenu.inputLabels.length);

        // Test if modeButtons are created
        assertNotNull(gameStartMenu.modeButtons);
        assertEquals(3, gameStartMenu.modeButtons.length);
        assertEquals(2, gameStartMenu.modeButtons[0].length); // "특수 모드"의 버튼 수
        assertEquals(3, gameStartMenu.modeButtons[1].length); // "게임 모드"의 버튼 수
        assertEquals(3, gameStartMenu.modeButtons[2].length); // "난이도 설정"의 버튼 수

        // Test if backButton is created
        assertNotNull(gameStartMenu.backButton);
    }

    @Test
    void testButtonActions() {
        // Simulate clicking "솔로 모드" button
        JButton soloModeButton = gameStartMenu.modeButtons[0][0];
        soloModeButton.doClick();
        assertEquals("솔로 모드", gameStartMenu.selectedModes[0]);

        // Simulate clicking "노멀" button
        JButton normalModeButton = gameStartMenu.modeButtons[1][0];
        normalModeButton.doClick();
        assertEquals("노멀", gameStartMenu.selectedModes[1]);

        // Simulate clicking "Easy" button
        JButton easyButton = gameStartMenu.modeButtons[2][0];
        easyButton.doClick();
        assertEquals("Easy", gameStartMenu.selectedModes[2]);
    }

    @Test
    void testBackButtonAction() {
        // Simulate clicking back button
        gameStartMenu.backButton.doClick();
        assertFalse(gameStartMenu.isVisible());
    }

    @Test
    void testGameStart() {
        // Set all modes
        gameStartMenu.selectedModes[0] = "솔로 모드";
        gameStartMenu.selectedModes[1] = "노멀";
        gameStartMenu.selectedModes[2] = "Easy";

        // Simulate starting the game
        gameStartMenu.checkAndStartGame();
        assertFalse(gameStartMenu.isVisible());
    }
}
