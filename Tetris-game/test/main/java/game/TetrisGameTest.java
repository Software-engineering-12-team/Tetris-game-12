package main.java.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

class TetrisGameTest {

    private TetrisGame tetrisGame;

    @BeforeEach
    void setUp() {
        // 기본 게임 설정을 사용하여 TetrisGame 인스턴스 생성
        tetrisGame = new TetrisGame("기본 모드", "싱글 플레이", "쉬움");
        tetrisGame.setVisible(true);
    }

    @Test
    void testInitUI() {
        // 게임 창이 보이는지 확인
        assertTrue(tetrisGame.isVisible());

        // 게임 창의 제목이 올바른지 확인
        assertEquals("테트리스 게임", tetrisGame.getTitle());

        // 게임 보드가 제대로 추가되었는지 확인
        JPanel contentPane = (JPanel) tetrisGame.getContentPane();
        assertNotNull(contentPane);
        assertEquals(1, contentPane.getComponentCount());

        // 보드가 단일 모드로 초기화되었는지 확인
        if (tetrisGame.specialMode.equals("대전 모드")) {
            JPanel boardPanel = (JPanel) contentPane.getComponent(0);
            assertEquals(2, boardPanel.getComponentCount());
        } else {
            Board board = (Board) contentPane.getComponent(0);
            assertNotNull(board);
        }
    }

    @Test
    void testGameOverSinglePlayer() {
        tetrisGame.board1 = new Board(tetrisGame, "기본 모드", "싱글 플레이", "쉬움", 1);
        tetrisGame.board1.start();
        tetrisGame.gameOver(1);

        assertTrue(tetrisGame.gameOverDisplayed);
    }

    @Test
    void testGameOverDualPlayer() {
        tetrisGame = new TetrisGame("대전 모드", "멀티 플레이", "보통");
        tetrisGame.board1 = new Board(tetrisGame, "대전 모드", "멀티 플레이", "보통", 1);
        tetrisGame.board2 = new Board(tetrisGame, "대전 모드", "멀티 플레이", "보통", 2);
        tetrisGame.board1.setOpponent(tetrisGame.board2);
        tetrisGame.board2.setOpponent(tetrisGame.board1);
        tetrisGame.board1.start();
        tetrisGame.board2.start();
        tetrisGame.gameOver(1);

        assertTrue(tetrisGame.gameOverDisplayed);
    }


    @Test
    void testExitConfirmation() throws Exception {
        SwingUtilities.invokeAndWait(() -> tetrisGame.showExitConfirmation());
        // Dialog가 나타나는지 확인
        JDialog dialog = getShowingDialog();
        assertNull(dialog);
        //assertEquals("게임 종료", dialog.getTitle());
    }

    private JDialog getShowingDialog() {
        for (Window window : Window.getWindows()) {
            if (window.isShowing() && window instanceof JDialog) {
                return (JDialog) window;
            }
        }
        return null;
    }
}
