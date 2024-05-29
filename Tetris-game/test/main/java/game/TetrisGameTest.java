package main.java.game;

import static org.junit.Assert.*;

import main.java.setting.SettingFileWriter;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class TetrisGameTest {

    private TetrisGame game;

    @Before
    public void setUp() {
        // Mocking the selected modes for testing
        String specialMode = "Single Player";
        String gameMode = "Normal";
        String difficulty = "Easy";
        game = new TetrisGame(specialMode, gameMode, difficulty);
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game);
        assertEquals("Single Player", game.specialMode);
        assertEquals("Normal", game.gameMode);
        assertEquals("Easy", game.difficulty);
    }

    @Test
    public void testAdjustFrameSizeForSingleBoard() {
        game.adjustFrameSizeForSingleBoard();
        int expectedWidth = 400;
        int expectedHeight = 550;

        switch (SettingFileWriter.readSize()) {
            case 0:
                expectedWidth = 400;
                expectedHeight = 550;
                break;
            case 1:
                expectedWidth = 440;
                expectedHeight = 605;
                break;
            case 2:
                expectedWidth = 480;
                expectedHeight = 660;
                break;
        }

        assertEquals(expectedWidth, game.getWidth());
        assertEquals(expectedHeight, game.getHeight());
    }

    @Test
    public void testAdjustFrameSizeForDualBoards() {
        game.specialMode = "대전 모드";
        game.adjustFrameSizeForDualBoards();
        int expectedWidth = 800;
        int expectedHeight = 550;

        switch (SettingFileWriter.readSize()) {
            case 0:
                expectedWidth = 800;
                expectedHeight = 550;
                break;
            case 1:
                expectedWidth = 880;
                expectedHeight = 605;
                break;
            case 2:
                expectedWidth = 960;
                expectedHeight = 660;
                break;
        }

        assertEquals(expectedWidth, game.getWidth());
        assertEquals(expectedHeight, game.getHeight());
    }

    @Test
    public void testGameOverDisplay() {
        game.gameOver(1);
        assertTrue(game.gameOverDisplayed);
    }

/*    @Test
    public void testDisplayWinnerMessage() {
        game.displayWinnerMessage("Player 1 Wins!");
        String expectedMessage = "Player 1 Wins!";
        String actualMessage = JOptionPane.showInputDialog("Player 1 Wins!");
        assertEquals(expectedMessage, actualMessage);
    }*/

/*    @Test
    public void testShowExitConfirmation() {
        game.showExitConfirmation();
        String expectedMessage = "타이틀로 돌아가기 또는 종료하시겠습니까?";
        String actualMessage = JOptionPane.showInputDialog("타이틀로 돌아가기 또는 종료하시겠습니까?");
        assertEquals(expectedMessage, actualMessage);
    }*/

    @Test
    public void testReturnToStartMenu() {
        game.returnToStartMenu();
        assertFalse(game.isDisplayable());
    }

/*    @Test
    public void testKeyPressEscape() {
        KeyEvent escKeyEvent = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
        game.dispatchEvent(escKeyEvent);
        assertTrue(game.isDisplayable());
    }*/
}
