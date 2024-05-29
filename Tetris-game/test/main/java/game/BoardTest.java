package main.java.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class BoardTest {

    private TetrisGame game;
    private Board board;

    @Before
    public void setUp() {
        String specialMode = "Single Player";
        String gameMode = "Normal";
        String difficulty = "Easy";
        game = new TetrisGame(specialMode, gameMode, difficulty);
        board = new Board(game, specialMode, gameMode, difficulty, 1);
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(board);
        assertEquals(Board.BOARD_WIDTH, 12);
        assertEquals(Board.BOARD_HEIGHT, 22);
    }

    @Test
    public void testClearBoard() {
        board.clearBoard();
        for (int i = 1; i < Board.BOARD_HEIGHT - 1; i++) {
            for (int j = 1; j < Board.BOARD_WIDTH - 1; j++) {
                assertEquals(Blocks.Tetrominoe.NoBlock, board.blockAt(j, i));
            }
        }
    }

    @Test
    public void testNewPiece() {
        board.start();
        assertNotNull(board.curPiece);
        assertNotNull(board.nextPiece);
        assertTrue(board.curPiece.getBlock() != Blocks.Tetrominoe.NoBlock);
        assertTrue(board.nextPiece.getBlock() != Blocks.Tetrominoe.NoBlock);
    }

    @Test
    public void testPieceDrop() {
        board.start();
        int initialY = board.curY;
        board.dropDown();
        //assertTrue(board.curY < initialY);
    }

    @Test
    public void testPieceMoveLeft() {
        board.start();
        int initialX = board.curX;
        board.tryMove(board.curPiece, initialX - 1, board.curY);
        assertEquals(initialX - 1, board.curX);
    }

    @Test
    public void testPieceMoveRight() {
        board.start();
        int initialX = board.curX;
        board.tryMove(board.curPiece, initialX + 1, board.curY);
        assertEquals(initialX + 1, board.curX);
    }

    @Test
    public void testPieceRotate() {
        board.start();
        Blocks initialPiece = board.curPiece;
        board.tryMove(board.curPiece.rotateRight(), board.curX, board.curY);
        //assertNotEquals(initialPiece, board.curPiece);
    }

    @Test
    public void testPauseGame() {
        board.start();
        board.pause();
        assertTrue(board.isPaused);
        board.pause();
        assertFalse(board.isPaused);
    }

    @Test
    public void testGameOver() {
        board.start();
        board.isStarted = false;
        board.isGameOver = true;
        board.parent.gameOver(1);
        assertTrue(board.isGameOver);
    }

    @Test
    public void testScoreCalculation() {
        board.start();
        int initialScore = board.getTotalScore();
        board.TotalScore += 10;
        assertEquals(initialScore + 10, board.getTotalScore());
    }

    @Test
    public void testItemEffect() {
        board.start();
        board.curPiece.setBlock(Blocks.Tetrominoe.LineDelItem);
        board.applyItemEffect(Blocks.Tetrominoe.LineDelItem);
        assertEquals(1, board.getTotalScore());
    }

    @Test
    public void testKeyPresses() {
        board.start();
        KeyEvent leftKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent rightKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent rotateKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        KeyEvent dropKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);

        board.dispatchEvent(leftKey);
        //assertEquals(board.curX, 5);

        board.dispatchEvent(rightKey);
        //assertEquals(board.curX, 5);

        board.dispatchEvent(rotateKey);
        assertNotEquals(board.curPiece, board.curPiece.rotateRight());

        board.dispatchEvent(dropKey);
        //assertTrue(board.curY < 20);
    }
}
