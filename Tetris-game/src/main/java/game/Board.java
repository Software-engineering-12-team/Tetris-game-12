package main.java.game;

import java.awt.Color;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.game.Blocks.Tetrominoe;
import main.java.setting.scoreboard.ScoreBoardMenu;

import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 15;
    private final int BOARD_HEIGHT = 22;
    private int INITIAL_DELAY = 100;
    private int PERIOD_INTERVAL = 1000; // 동적 변경을 위해 변경

    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;
    private JLabel statusbar;
    private Blocks curPiece;
    private Tetrominoe[] board;

    public Board(TetrisGame parent) {

        initBoard(parent);
    }

    private void initBoard(TetrisGame parent) {		//게임 보드 초기화

        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

        curPiece = new Blocks();

        statusbar = parent.getStatusBar();
        board = new Blocks.Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];
        addKeyListener(new TAdapter());
        clearBoard();
    }

    private int squareWidth() {		//한칸의 너비
        return (int) getSize().getWidth() / BOARD_WIDTH;
    }

    private int squareHeight() {	//한칸의 높이
        return (int) getSize().getHeight() / BOARD_HEIGHT;
    }

    private Tetrominoe blockAt(int x, int y) {		//해당 좌표의 블록 반환
        return board[(y * BOARD_WIDTH) + x];
    }

    public void start() {		//게임 시작

        isStarted = true;
        clearBoard();
        newPiece();
    }

    private void pause() {		//일시정지

        if (!isStarted) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {

            statusbar.setText("Paused");
        } else {

            statusbar.setText(String.valueOf(numLinesRemoved));
        }
    }

    private void doDrawing(Graphics g) {

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        for (int i = 0; i < BOARD_HEIGHT; ++i) {

            for (int j = 0; j < BOARD_WIDTH; ++j) {

                Tetrominoe block = blockAt(j, BOARD_HEIGHT - i - 1);

                if (block != Tetrominoe.NoBlock) {
                    
                    drawBlock(g, 0 + j * squareWidth(),
                            boardTop + i * squareHeight(), block);
                }
            }
        }

        if (curPiece.getBlock() != Tetrominoe.NoBlock) {

            for (int i = 0; i < 4; ++i) {

                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);
                drawBlock(g, 0 + x * squareWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
                        curPiece.getBlock());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
    	
    	setBackground(Color.BLACK);
        super.paintComponent(g);
        doDrawing(g);

    }

    private void dropDown() {		//블록을 한 번에 맨 아래로 떨어뜨리기

        int newY = curY;

        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1)) {  
            	
                break;
            }
            --newY;
        }
        pieceDropped();
    }

    private void oneLineDown() {		//블록을 한 칸 떨어뜨리기

        if (!tryMove(curPiece, curX, curY - 1)) {
            
            pieceDropped();
        }
    }

    private void clearBoard() {			//게임보드 초기화

        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; ++i) {
            board[i] = Tetrominoe.NoBlock;
        }
    }

    private void pieceDropped() {		//블록이 떨어진 후 

        for (int i = 0; i < 4; ++i) {

            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getBlock();
        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece(); 
        }
    }


    private void newPiece() {		//새로운 블록 생성

        curPiece.setRandomBlock();
        curX = BOARD_WIDTH / 2 + 1;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {

            curPiece.setBlock(Tetrominoe.NoBlock);		//블록을 생성할 수 없으면 게임오버
            timer.cancel();
            isStarted = false;
            statusbar.setText("GAME OVER!");
            
            int linesRemoved = numLinesRemoved;
            SwingUtilities.invokeLater(new Runnable() {               //게임오버가 되면 점수를 스코어보드에 기록
                @Override
                public void run() {
                    ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
                    scoreBoardMenu.addScore("Score: " + linesRemoved);
                }
            });
        }
    }

    private boolean tryMove(Blocks newPiece, int newX, int newY) {		//블록을 이동시킬 수 있는지 확인

        for (int i = 0; i < 4; ++i) {

            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                return false;
            }

            if (blockAt(x, y) != Tetrominoe.NoBlock) {
                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    private void removeFullLines() {		//가득 찬 줄 제거

        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; ++j) {
                
                if (blockAt(j, i) == Tetrominoe.NoBlock) {
                    
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                
                ++numFullLines;
                
                for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
                    for (int j = 0; j < BOARD_WIDTH; ++j) {
                        
                        board[(k * BOARD_WIDTH) + j] = blockAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numLinesRemoved += numFullLines;
            statusbar.setText(String.valueOf(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setBlock(Tetrominoe.NoBlock);
            repaint();
            
            adjustSpeed(numFullLines);
            
        }
    }
    
    ///속도 변경 메소드, 추후 적정수치 논의 필요 
    private void adjustSpeed(int numFullLines) {
    	int newDelay = Math.max(INITIAL_DELAY - numFullLines*50, 0);
    	int newInterval = Math.max(PERIOD_INTERVAL - numFullLines*10, 0); 
    	
    	if (newInterval <= 0) {
    		newInterval = 1;
    	}
    	
    	timer.cancel();
    	timer = new Timer();
    	timer.scheduleAtFixedRate(new ScheduleTask(), newDelay, newInterval);
    	
    	INITIAL_DELAY = newDelay;
    	PERIOD_INTERVAL = newInterval;
    }
    
    private void drawBlock(Graphics g, int x, int y, 
            Tetrominoe block) {

        Color colors[] = {
            new Color(0, 0, 0), new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[block.ordinal()];

        g.setColor(Color.BLACK);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        
        g.setColor(color); 
        Font boldFont = new Font("Arial", Font.BOLD, 20);
        g.setFont(boldFont);
        g.drawString("O", x + squareWidth() / 2 - 5, y + squareHeight() / 2 + 5); 

    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private void update() {		//게임 상태 업데이트
        
        if (isPaused) {
            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    private class TAdapter extends KeyAdapter {		//키 이벤트 핸들러

        @Override
        public void keyPressed(KeyEvent e) {
            
            System.out.println("key pressed");

            if (!isStarted || curPiece.getBlock() == Tetrominoe.NoBlock) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == KeyEvent.VK_P) {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            switch (keycode) {

                case KeyEvent.VK_LEFT:
                    tryMove(curPiece, curX - 1, curY);
                    break;

                case KeyEvent.VK_RIGHT:
                    tryMove(curPiece, curX + 1, curY);
                    break;

                case KeyEvent.VK_DOWN:
                    tryMove(curPiece, curX, curY - 1);
                    break;

                case KeyEvent.VK_UP:
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;

                case KeyEvent.VK_Q:
                    dropDown();
                    break;

                case KeyEvent.VK_D:
                    oneLineDown();
                    break;
                    
            }
        }
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {

            doGameCycle();
        }
    }
}