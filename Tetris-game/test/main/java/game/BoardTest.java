package main.java.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;

import main.java.game.Blocks.Tetrominoe;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private TetrisGame parent;
    private final String specialMode = "normal";
    private final String gameMode = "classic";
    private final String difficulty = "easy";

    @BeforeEach
    void setUp() {
        //parent = new TetrisGame(); // TetrisGame 클래스가 필요합니다.
        board = new Board(parent, specialMode, gameMode, difficulty, 1);
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(board);
        assertEquals(Board.BOARD_WIDTH, 12);
        assertEquals(Board.BOARD_HEIGHT, 22);
    }

    @Test
    void setOpponent() {
        Board opponent = new Board(parent, specialMode, gameMode, difficulty, 2);
        board.setOpponent(opponent);
        assertNotNull(board.opponentBoard);
        assertEquals(opponent, board.opponentBoard);
    }

    @Test
    void blockAt() {
        board.clearBoard();
        assertEquals(Blocks.Tetrominoe.NoBlock, board.blockAt(1, 1));
        board.board[1] = Tetrominoe.IBlock;
        //assertEquals(Tetrominoe.IBlock, board.blockAt(0, 1));
    }

    @Test
    void start() {
        board.start();
        assertTrue(board.isStarted);
        assertNotEquals(Tetrominoe.NoBlock, board.curPiece.getBlock());
    }

    @Test
    void pause() {
        board.start();
        assertFalse(board.isPaused);
        board.pause();
        assertTrue(board.isPaused);
        board.pause();
        assertFalse(board.isPaused);
    }

    /*@Test
    void paintComponent() {
        Graphics g = new JPanel().getGraphics();
        board.paintComponent(g);
        assertNotNull(g);
    }*/

    @Test
    void dropDown() {
        board.start();
        int startY = board.curY;
        board.dropDown();
        assertFalse(board.curY < startY);
        assertFalse(board.isFallingFinished);
    }

    @Test
    void clearBoard() {
        board.clearBoard();
        for (int i = 0; i < board.board.length; i++) {
            if (i < Board.BOARD_WIDTH || i >= board.board.length - Board.BOARD_WIDTH ||
                    i % Board.BOARD_WIDTH == 0 || (i + 1) % Board.BOARD_WIDTH == 0) {
                assertEquals(Tetrominoe.BorderBlock, board.board[i]);
            } else {
                assertEquals(Tetrominoe.NoBlock, board.board[i]);
            }
        }
    }

    @Test
    void applyItemEffect() {
        board.start();
        board.curPiece.setBlock(Tetrominoe.ThreeItem);
        board.applyItemEffect(Tetrominoe.ThreeItem, 0);
        // 추가적인 검증이 필요합니다.
    }

    @Test
    void pieceDropped() {
        board.start();
        board.pieceDropped();
        //assertEquals(Tetrominoe.NoBlock, board.curPiece.getBlock());
    }

    @Test
    void newPiece() {
        board.start();
        Tetrominoe oldPiece = board.curPiece.getBlock();
        board.newPiece();
        //assertNotEquals(oldPiece, board.curPiece.getBlock());
    }

    @Test
    void getTotalScore() {
        board.TotalScore = 100;
        assertEquals(100, board.getTotalScore());
    }

/*    @Test
    void declareWinner() {
        board.declareWinner(1);
        // 게임 종료 검증이 필요합니다.
    }*/

    @Test
    void endGame() {
        String name = "Test";
        String difficulty = "Easy";
        String mode = "Classic";
        int score = 100;
        board.endGame(name, difficulty, mode, score);
        // 추가적인 검증이 필요합니다.
    }

    @Test
    void stopTimers() {
        board.start();
        board.stopTimers();
        //assertNull(board.timer);
    }

    @Test
    void tryMove() {
        board.start();
        Blocks newPiece = new Blocks(difficulty);
        newPiece.setRandomBlock(difficulty, false);
        boolean moved = board.tryMove(newPiece, board.curX, board.curY - 1);
        assertTrue(moved);
    }

    @Test
    void removeFullLines() {
        board.start();
        // 초기 보드는 비어 있음
        board.removeFullLines();
        assertEquals(0, board.TotalScore);
        // 특정 행을 채워서 제거 테스트
        for (int j = 1; j < Board.BOARD_WIDTH - 1; j++) {
            board.board[1 * Board.BOARD_WIDTH + j] = Tetrominoe.IBlock;
        }
        board.removeFullLines();
        assertEquals(10, board.TotalScore); // 1 줄 제거 시 10점 추가
    }

    @Test
    void queueLines() {
        //board.queueLines(0, new ArrayList<>());
        assertEquals(0, board.queuedLines.size() / (Board.BOARD_WIDTH - 2));
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
    public void testItemEffect() {
        board.start();
        board.curPiece.setBlock(Blocks.Tetrominoe.LineDelItem);
        //board.applyItemEffect(Blocks.Tetrominoe.LineDelItem);
        assertEquals(0, board.getTotalScore());
    }

    @Test
    public void testKeyPresses() {
        board.start();
        KeyEvent leftKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent rightKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent rotateKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);
        KeyEvent dropKey = new KeyEvent(board, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);

        board.dispatchEvent(leftKey);
        assertEquals(board.curX, 5);

        board.dispatchEvent(rightKey);
        assertEquals(board.curX, 5);

        board.dispatchEvent(rotateKey);
        assertNotEquals(board.curPiece, board.curPiece.rotateRight());

        board.dispatchEvent(dropKey);
        assertFalse(board.curY < 20);
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
    void testPieceMoveLeft() {
        board.start();
        int initialX = board.curX;
        board.tryMove(board.curPiece, initialX - 1, board.curY);
        assertEquals(initialX - 1, board.curX);
    }

    @Test
    void testPieceMoveDown() {
        board.start();
        int initialY = board.curY;
        board.tryMove(board.curPiece, board.curX, initialY - 1);
        assertEquals(initialY - 1, board.curY);
    }

    @Test
    void testDropToBottom() {
        board.start();
        int initialY = board.curY;
        board.dropDown();
        assertFalse(board.curY < initialY);
    }

    @Test
    void testFullLineRemoval() {
        board.start();
        for (int j = 1; j < Board.BOARD_WIDTH - 1; j++) {
            board.board[1 * Board.BOARD_WIDTH + j] = Tetrominoe.IBlock;
        }
        board.removeFullLines();
        for (int j = 1; j < Board.BOARD_WIDTH - 1; j++) {
            //assertEquals(Tetrominoe.NoBlock, board.blockAt(j, 1));
        }
    }

    @Test
    void testTimerMode() {
        Board timerBoard = new Board(parent, "normal", "타이머", "easy", 1);
        timerBoard.start();
        assertTrue(timerBoard.timerMode != null);
    }

    @Test
    void testBattleMode() {
        Board battleBoard = new Board(parent, "대전 모드", "classic", "easy", 1);
        battleBoard.start();
        Board opponent = new Board(parent, "대전 모드", "classic", "easy", 2);
        battleBoard.setOpponent(opponent);
        //battleBoard.queueLines(2, new ArrayList<>());
        //assertEquals(2, battleBoard.queuedLines.size() / (Board.BOARD_WIDTH - 2));
    }

}
