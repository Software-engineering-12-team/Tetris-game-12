package main.java.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import main.java.game.Blocks.Tetrominoe;
import main.java.menu.ScoreBoardMenu;
import main.java.setting.SettingFileWriter;
import main.java.setting.SettingMenu;
import main.java.setting.colorblindmode.ColorBlindModeMenu;
import main.java.util.ScreenAdjustComponent;
import main.java.game.ScoreFileWriter; // 점수 저장을 위해 추가
import main.java.menu.gamestart.DifficultySettingMenu;

import java.util.Timer;
import java.util.TimerTask;

import static java.awt.Color.black;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 12;
    private final int BOARD_HEIGHT = 22;
    private int INITIAL_DELAY = 100;
    private int PERIOD_INTERVAL = 1000; // 동적 변경을 위해 변경
    private ScoreBoardMenu scoreBoardMenu;

    private String difficulty;
    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int TotalScore = 0;
    private int curX = 0;
    private int curY = 0;
    private JLabel statusbar;
    private Blocks curPiece;
    private Blocks nextPiece;
    private Font tetrisFont;
    private Tetrominoe[] board;
    
    public Board(TetrisGame parent, String difficulty) {
    	this.difficulty = difficulty;
        initBoard(parent);
    }

    public Board(String colorBlindStatus) {
    	ColorBlindModeMenu.colorBlindStatus = colorBlindStatus;
    }

    private void initBoard(TetrisGame parent) {		//게임 보드 초기화

        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

        curPiece = new Blocks(difficulty);
        nextPiece = new Blocks(difficulty);
        nextPiece.setRandomBlock(difficulty);
        if(SettingFileWriter.readSize() == 0)		//화면 크기에 따른 폰트 크기 변경
        	tetrisFont = new Font("Arial", Font.BOLD, 20);
            else if(SettingFileWriter.readSize() == 1)
            tetrisFont = new Font("Arial", Font.BOLD, 22);
            else
            tetrisFont = new Font("Arial", Font.BOLD, 24);
        
        statusbar = parent.getStatusBar();
        statusbar.setText("");
        board = new Blocks.Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];
        addKeyListener(new TAdapter());
        clearBoard();
    }

    private int squareWidth() {		//한칸의 너비
        return (int) getSize().getWidth() / BOARD_WIDTH / 2;
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
       
    }
    
    private void showExitConfirmation() {	//종료 여부 확인하기
        int option = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "게임 종료", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            
            System.exit(0); 
        }
    }

    private void doDrawing(Graphics g) {	// 화면의 구성요소 그리기

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
        
     // 다음 블록 그리기
        int nextPieceX = BOARD_WIDTH * squareWidth() + 60;
        int nextPieceY = 50;
        for (int i = 0; i < 4; ++i) {
            int x = nextPiece.x(i) + 1;
            int y = nextPiece.y(i) + 1;
            drawBlock(g, nextPieceX + x * squareWidth(),
                    nextPieceY + y * squareHeight(), nextPiece.getBlock());
        }
        
        g.setColor(Color.WHITE);	//다음 블록이 들어갈 박스 그리기
        int boxWidth = 6 * squareWidth();
        int boxHeight = 4 * squareHeight();
        int borderWidth = 5; 
     
     for (int i = 0; i < borderWidth; i++) {
         g.drawRect(nextPieceX - 20 + i, nextPieceY + i, boxWidth, boxHeight);
     	}
     
     for (int i = 0; i < borderWidth; i++) {
         g.drawRect(nextPieceX - 20 + i, nextPieceY, boxWidth, boxHeight);
     	}
     
     	// 점수 표시 위치 계산
     	int scoreX = nextPieceX - squareWidth()	; 
     	int scoreY = nextPieceY + squareHeight() * 6; 

     	// 점수 표시
     	g.setColor(Color.WHITE);
     	g.setFont(tetrisFont);
     	if(!isPaused)
     	{
     		g.drawString("Score: " + TotalScore, scoreX, scoreY);
     	}
     	else
     	{
     		g.drawString("Paused", scoreX, scoreY);
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

    	for (int i = 0; i < BOARD_HEIGHT; ++i) {		//게임 보드의 테두리 그리기
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                if (i == 0 || i == BOARD_HEIGHT - 1 || j == 0 || j == BOARD_WIDTH - 1) {
                    board[(i * BOARD_WIDTH) + j] = Tetrominoe.BorderBlock;
                } else {
                    board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
                }
            }
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

    	curPiece = nextPiece;	// 현재 블록을 방금 표시되었던 블록으로 설정
    	nextPiece = new Blocks(difficulty); // 다음 블록 생성
    	nextPiece.setRandomBlock(difficulty);
        curX = BOARD_WIDTH / 2 - 1;
        curY = BOARD_HEIGHT - 2 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            // 게임오버일 때 점수를 스코어보드에 추가
            curPiece.setBlock(Tetrominoe.NoBlock);
            timer.cancel();
            isStarted = false;

            int linesRemoved = TotalScore;
            String name = JOptionPane.showInputDialog("Enter your name:");
            String mode = "일반"; // 추후에 선택 가능하도록 수정할 수 있습니다.

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	endGame(name, difficulty, mode, linesRemoved);
                	ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
                	 if(SettingFileWriter.readSize() == 0)
                		 scoreBoardMenu.setSize(400, 550);
                         else if(SettingFileWriter.readSize() == 1)
                        	 scoreBoardMenu.setSize(440, 605);
                         else
                        	 scoreBoardMenu.setSize(480, 660);
                	
                    ScreenAdjustComponent.sizeAdjust(scoreBoardMenu.labels, scoreBoardMenu.buttons, scoreBoardMenu.isBackButton, SettingFileWriter.readSize());
                }
            });
        }
    }
    
    public void endGame(String name, String difficulty, String mode, int score) {
    	 ScoreFileWriter.writeScore(name, difficulty, mode, score);
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

    private void removeFullLines() {		//가득 찬 줄 제거, 테두리를 고려하여 수정

        int numFullLines = 0;
        int consecutiveLines = 0; // 연속된 줄의 개수를 추적하는 변수 추가

        for (int i = BOARD_HEIGHT - 1; i >= 1; --i) {
            boolean lineIsFull = true;

            for (int j = 1; j < BOARD_WIDTH - 1; ++j) {
                
                if (blockAt(j, i) == Tetrominoe.NoBlock || blockAt(j, i) == Tetrominoe.BorderBlock) {
                    
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                
                ++numFullLines;
                ++consecutiveLines; // 연속된 줄의 개수 추가
                
                for (int k = i; k < BOARD_HEIGHT - 2; ++k) {
                    for (int j = 0; j < BOARD_WIDTH; ++j) {
                        board[(k * BOARD_WIDTH) + j] = blockAt(j, k + 1);
                    }
                }        
            }
            else {
                // 이전 줄이 가득 찼는데 현재 줄이 가득 차지 않았을 경우
                if (consecutiveLines > 0) {
                    // 가산점 부여
                    TotalScore += (consecutiveLines - 1);
                    consecutiveLines = 0; // 연속된 줄 개수 초기화
                }
            }
        }
        
        // 마지막 줄이 가득 찼을 경우
        if (consecutiveLines > 0) {
            // 가산점 부여
            TotalScore += (consecutiveLines - 1);
        }

        if (numFullLines > 0) {

            TotalScore += numFullLines;
            isFallingFinished = true;
            curPiece.setBlock(Tetrominoe.NoBlock);
            repaint();
            
            adjustSpeed(numFullLines);
            
        }
    }
    
    private void adjustSpeed(int numFullLines) {
    	int newDelay;
    	int newInterval;
    	
    	if (difficulty.equals("Easy")) {
            newDelay = Math.max(INITIAL_DELAY - numFullLines * 40, 0);
            newInterval = Math.max(PERIOD_INTERVAL - numFullLines * 8, 0);
        } else if (difficulty.equals("Hard")) {
            newDelay = Math.max(INITIAL_DELAY - numFullLines * 60, 0);
            newInterval = Math.max(PERIOD_INTERVAL - numFullLines * 12, 0);
        } else {
            newDelay = Math.max(INITIAL_DELAY - numFullLines * 50, 0);
            newInterval = Math.max(PERIOD_INTERVAL - numFullLines * 10, 0);
        }
    	
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
        Color colors[] = {	//일반 색깔
            new Color(0, 0, 0),
                new Color(204, 102, 102),
            new Color(102, 204, 102),
                new Color(102, 102, 204),
            new Color(204, 204, 102),
                new Color(204, 102, 204),
            new Color(102, 204, 204),
                new Color(218, 170, 0),
            new Color(0, 0, 0)
        };
/*        Color colors[] = {	//일반 색깔
                new Color(0, 0, 0),
                new Color(255, 0, 0),
                new Color(255, 0, 0),
                new Color(255, 0, 0),
                new Color(255, 0, 0),
                new Color(255, 0, 0),
                new Color(255, 0, 0),
                new Color(255, 0, 0),
                new Color(0, 0, 0)
        };*/

        Color rgcbcolors[] = {    // 적록 색맹 모드 전용 색깔
                new Color(0, 0, 0),           // Black
                new Color(255, 165, 0),       // Orange
                new Color(135, 206, 235),     // Sky Blue
                new Color(0, 255, 255),       // Bluish Green (Cyan으로 대체)
                new Color(255, 255, 0),       // Yellow
                new Color(0, 0, 255),         // Blue
                new Color(227, 66, 52),       // Vermilion
                new Color(147, 112, 219),     // Reddish Purple (Medium Purple로 대체)
                new Color(0, 0, 0)            // 추가된 Black (원하시는 배치가 맞는지 확인해주세요)
        };


/*        Color rgcbcolors[] = {	//일반 색깔
                new Color(0, 0, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 0, 0)
        };*/

        Color bycbcolors[] = {    // 청황 색맹 모드 전용 색깔
                new Color(204, 0, 0),
                new Color(0, 204, 0),
                new Color(0, 0, 153),
                new Color(255, 165, 0),
                new Color(255, 192, 203),
                new Color(128, 0, 128),
                new Color(128, 128, 128),
                new Color(64, 224, 208),
                new Color(0, 0, 0),
        };

/*        Color bycbcolors[] = {	//일반 색깔
                new Color(0, 0, 0),
                new Color(0, 0, 255),
                new Color(0, 0, 255),
                new Color(0, 0, 255),
                new Color(0, 0, 255),
                new Color(0, 0, 255),
                new Color(0, 0, 255),
                new Color(0, 0, 255),
                new Color(0, 0, 0)
        };*/


        if(ColorBlindModeMenu.colorBlindStatus.equals("정상"))
        {
        	Color color = colors[block.ordinal()];
           	 g.setColor(color); 
        }
        else if(ColorBlindModeMenu.colorBlindStatus.equals("적녹색맹"))
        {
        	Color color = rgcbcolors[block.ordinal()];
           	 g.setColor(color);
        }
        else if(ColorBlindModeMenu.colorBlindStatus.equals("청황색맹"))
        {
        	Color color = bycbcolors[block.ordinal()];
           	 g.setColor(color);
        }
         
        g.setFont(tetrisFont);
        g.drawString("O", x + squareWidth() / 2 - 5, y + squareHeight() / 2 + 5); 
        
        if (block == Tetrominoe.BorderBlock) {
            g.setColor(Color.WHITE);
            g.drawString("X", x + squareWidth() / 2 - 5, y + squareHeight() / 2 + 5);
        }
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
            
            if (keycode == KeyEvent.VK_ESCAPE) {
                // ESC 키가 눌렸을 때 종료 확인 팝업 표시
                showExitConfirmation();
                return;
            }

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
        	TotalScore++;
            doGameCycle();
        }
    }
}
