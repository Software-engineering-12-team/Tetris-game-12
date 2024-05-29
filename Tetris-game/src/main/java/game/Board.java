package main.java.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import main.java.game.Blocks.Tetrominoe;
import main.java.menu.ScoreBoardMenu;
import main.java.setting.SettingFileWriter;
import main.java.setting.colorblindmode.ColorBlindModeMenu;
import main.java.setting.controlkeysetting.ControlKeySettingMenu;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;
import main.java.menu.ScoreEntry;
import main.java.game.BlockDrawer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Comparator;

import static java.awt.Color.black;
import static main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus;

public class Board extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int BOARD_WIDTH = 12;
    public static final int BOARD_HEIGHT = 22;
    private int INITIAL_DELAY = 100;
    private int PERIOD_INTERVAL = 1000; // 동적 변경을 위해 변경
    private String specialMode, gameMode, difficulty; // 게임모드 설정 관련 수정
    private Timer timer;
    private Timer timerMode;
    private boolean isFallingFinished = false;
    public boolean isStarted = false;
    public boolean isPaused = false;
    private boolean isItem = false;
    private boolean isTouchedBlocks = false;
    private boolean istimerModeCancelled = false;
    private int remainRowsForItems = 10;
    public int TotalScore = 0;
    public int curX = 0;
    public int curY = 0;
    private int timerModeLimit; 
    private JLabel statusbar;
    public Blocks curPiece;
    public Blocks nextPiece;
    public static Font tetrisFont;
    private Tetrominoe[] board;
    private Board opponentBoard; // 상대방 보드 추가
    private int playerNumber; // 플레이어 번호 추가
    public boolean isGameOver = false; //게임 종료 상태 추가
    public TetrisGame parent;
    private List<int[]> queuedLines = new ArrayList<>(); // 대기 상태의 공격 블록을 저장하는 리스트
    private List<int[]> queuedExcludedBlocks = new ArrayList<>(); // 대기 상태의 제외 블록을 저장하는 리스트

    // 게임모드 설정 관련 수정
    public Board(TetrisGame parent, String specialMode, String gameMode, String difficulty, int playerNumber) {
        this.parent = parent;
        this.specialMode = specialMode;
        this.gameMode = gameMode;
        this.difficulty = difficulty;
        this.playerNumber = playerNumber;
        initBoard(parent);
    }

    public Board(String colorBlindStatus) {
        ColorBlindModeMenu.colorBlindStatus = colorBlindStatus;
    }
    
    public void setOpponent(Board opponent) {
        this.opponentBoard = opponent;
    }

    private void initBoard(TetrisGame parent) { // 게임 보드 초기화
        setFocusable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
        curPiece = new Blocks(difficulty);
        nextPiece = new Blocks(difficulty);
        nextPiece.setRandomBlock(difficulty, isItem); // 게임모드 설정 관련 수정
        if(SettingFileWriter.readSize() == 0) // 화면 크기에 따른 폰트 크기 변경
            tetrisFont = new Font("Arial", Font.BOLD, 20);
        else if(SettingFileWriter.readSize() == 1)
            tetrisFont = new Font("Arial", Font.BOLD, 22);
        else
            tetrisFont = new Font("Arial", Font.BOLD, 24);
        
        board = new Blocks.Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];
        
        if (getKeyListeners().length == 0) {
            addKeyListener(new TAdapter(playerNumber));
            requestFocusInWindow();
        }
        
        clearBoard();
    }

    private int squareWidth() {        //한칸의 너비
        return (int) getSize().getWidth() / BOARD_WIDTH / 2;
    }

    private int squareHeight() {    //한칸의 높이
        return (int) getSize().getHeight() / BOARD_HEIGHT - 2;
    }

    public Tetrominoe blockAt(int x, int y) {        //해당 좌표의 블록 반환
        return board[(y * BOARD_WIDTH) + x];
    }

    public void start() {        //게임 시작

        isStarted = true;
        clearBoard();
        newPiece();
    }

    public void pause() {        //일시정지

        if (!isStarted) {
            return;
        }

        isPaused = !isPaused;
    }
    
    private void doDrawing(Graphics g) {    // 화면의 구성요소 그리기

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight() - 10;

    	g.setColor(Color.BLACK);
    	g.fillRect(9, 22, BOARD_WIDTH * squareWidth() + 2, BOARD_HEIGHT * squareHeight());

        for (int i = 0; i < BOARD_HEIGHT; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                Tetrominoe block = blockAt(j, BOARD_HEIGHT - i - 1);
                if (block != Tetrominoe.NoBlock) {
                    BlockDrawer.drawBlock(g, 0 + j * squareWidth(),
                            boardTop + i * squareHeight(), squareWidth(), squareHeight(), block);
                }
            }
        }

        if (curPiece.getBlock() != Tetrominoe.NoBlock) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);
                BlockDrawer.drawBlock(g, 0 + x * squareWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(), squareWidth(), squareHeight(),
                        curPiece.getBlock());
            }
        }

        Graphics2D g2d = (Graphics2D) g;

        // 선의 굵기 설정
        float thickness = 3.0f; // 선의 굵기
        g2d.setStroke(new BasicStroke(thickness));
        
        // 다음 블록 그리기
        int nextPieceX = BOARD_WIDTH * squareWidth() + 60;
        int nextPieceY = 50;

        //다음 블록이 들어갈 박스 그리기
        int boxWidth = 6 * squareWidth();
        int boxHeight = 4 * squareHeight();

        // 내부를 검정색으로 채우기
        g.setColor(Color.BLACK);
        g.fillRect(nextPieceX - 35 + 3 * squareWidth() / 2, nextPieceY, boxWidth, boxHeight);


        // 테두리 1
        g2d.setColor(new Color(250, 250, 250));
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.drawRect(nextPieceX - 35 - 4 + 3 * squareWidth() / 2, nextPieceY - 4, boxWidth + 8, boxHeight + 8);

        // 테두리 2
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(nextPieceX - 35 - 8 + 3 * squareWidth() / 2, nextPieceY - 8, boxWidth + 16, boxHeight + 16);

        // 다음 블록 그리기
        for (int i = 0; i < 4; ++i) {
            int x = nextPiece.x(i) + 1;
            int y = nextPiece.y(i) + 1;
            BlockDrawer.drawBlock(g, nextPieceX + x * squareWidth() - 32 + 3 * squareWidth() / 2,
                    nextPieceY + y * squareHeight() + 24, squareWidth(), squareHeight(), nextPiece.getBlock());
        }
     
        // 점수 표시 위치 계산
        int scoreX = nextPieceX - 30 + 3 * squareWidth() / 2; 
        int scoreY = nextPieceY + squareHeight() * 6; 

        // 점수 표시
        g.setColor(Color.BLACK);
        g.setFont(tetrisFont);
        if(!isPaused) {
            g.drawString("Score: " + TotalScore, scoreX, scoreY);
        } else {
            g.drawString("Paused", scoreX, scoreY);
        }
        
        // 남은 시간 표시 위치
        int timerX = nextPieceX - 30 + 3 * squareWidth() / 2; 
        int timerY = nextPieceY + squareHeight() * 7; 

        // 남은 시간 표시
        g.setColor(Color.BLACK);
        g.setFont(tetrisFont);
        if(gameMode == "타이머") {
            g.drawString("Time: " + timerModeLimit, timerX, timerY);            
        }

        if(specialMode == "대전 모드") {
            int boxWidth2 = 9 * squareWidth();
            int boxHeight2 = 10 * squareHeight();
            
            g2d.setColor(Color.WHITE); //다음 공격 블록이 들어갈 박스 그리기
            if(SettingFileWriter.readSize() == 0) { // 화면 크기에 따른 박스 위치 변경
	            // 내부를 검정색으로 채우기
	            g.setColor(Color.BLACK);
	            g.fillRect(nextPieceX - 35, nextPieceY + 200, boxWidth2 + 18, boxHeight2);
	            
	            // 테두리 1
	            g2d.setColor(new Color(250, 250, 250));
	            g2d.setStroke(new BasicStroke(3.0f));
	            g2d.drawRect(nextPieceX - 35 - 4, nextPieceY + 200 - 4, boxWidth2 + 18 + 8, boxHeight2 + 8);
	
	            // 테두리 2
	            g2d.setColor(Color.GRAY);
	            g2d.setStroke(new BasicStroke(1.5f));
	            g2d.drawRect(nextPieceX - 35 - 8, nextPieceY + 200 - 8, boxWidth2 + 18 + 16, boxHeight2 + 16);
            }
            else if(SettingFileWriter.readSize() == 1) {
	            // 내부를 검정색으로 채우기
	            g.setColor(Color.BLACK);
	            g.fillRect(nextPieceX - 38, nextPieceY + 235, boxWidth2 + 18, boxHeight2);
	            
	            // 테두리 1
	            g2d.setColor(new Color(250, 250, 250));
	            g2d.setStroke(new BasicStroke(3.0f));
	            g2d.drawRect(nextPieceX - 38 - 4, nextPieceY + 235 - 4, boxWidth2 + 18 + 8, boxHeight2 + 8);
	
	            // 테두리 2
	            g2d.setColor(Color.GRAY);
	            g2d.setStroke(new BasicStroke(1.5f));
	            g2d.drawRect(nextPieceX - 38 - 8, nextPieceY + 235 - 8, boxWidth2 + 18 + 16, boxHeight2 + 16);
            }
            else{
	            // 내부를 검정색으로 채우기
	            g.setColor(Color.BLACK);
	            g.fillRect(nextPieceX - 35, nextPieceY + 260, boxWidth2 + 18, boxHeight2);
	            
	            // 테두리 1
	            g2d.setColor(new Color(250, 250, 250));
	            g2d.setStroke(new BasicStroke(3.0f));
	            g2d.drawRect(nextPieceX - 35 - 4, nextPieceY + 260 - 4, boxWidth2 + 18 + 8, boxHeight2 + 8);
	
	            // 테두리 2
	            g2d.setColor(Color.GRAY);
	            g2d.setStroke(new BasicStroke(1.5f));
	            g2d.drawRect(nextPieceX - 35 - 8, nextPieceY + 260 - 8, boxWidth2 + 18 + 16, boxHeight2 + 16);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
    	setBackground(new Color(220, 220, 220));
        super.paintComponent(g);
        doDrawing(g);
        drawQueuedLines(g);
    }

    private void drawQueuedLines(Graphics g) {
        int maxY = queuedLines.stream().mapToInt(coord -> coord[1]).max().orElse(0);
        int blockSize = squareHeight();
        int baseX;
        int baseY;
        
        if (SettingFileWriter.readSize() == 0) {
        	baseX = BOARD_WIDTH * squareWidth() + 23;
            baseY = getHeight() - 28; // 기본 Y 위치를 윗쪽으로 고정
        } else if (SettingFileWriter.readSize() == 1) {
        	baseX = BOARD_WIDTH * squareWidth() + 21;
            baseY = getHeight() - 12; // 기본 Y 위치를 윗쪽으로 고정
        } else {
        	baseX = BOARD_WIDTH * squareWidth() + 23;
            baseY = getHeight() - 24; // 기본 Y 위치를 윗쪽으로 고정
        }

        // GrayBlock을 그리기
        for (int[] line : queuedLines) {
            if (line[1] > maxY - 10) { // maxY - 10 이하의 값만 그리기
                int x = line[0];
                int y = line[1];
                BlockDrawer.drawBlock(g, baseX + (x - 1) * squareWidth(), baseY - (y * blockSize), squareWidth() / 18, squareHeight() / 18, Tetrominoe.GrayBlock);
            }
        }

        // NoBlock으로 대체된 부분을 그리기
        for (int[] coord : queuedExcludedBlocks) {
            if (coord[1] > maxY - 10) { // maxY - 10 이하의 값만 그리기
                int x = coord[0];
                int y = coord[1];
                BlockDrawer.drawBlock(g, baseX + (x - 1) * squareWidth(), baseY - (y * blockSize), squareWidth() / 18, squareHeight() / 18, Tetrominoe.NoBlock);
            }
        }
    }

    public void dropDown() {        //블록을 한 번에 맨 아래로 떨어뜨리기
        int dropDistance = 0;
        int newY = curY;
        
        // WeightItem인 경우
        if (curPiece.getBlock() == Tetrominoe.WeightItem) {
            // 현재 블록이 놓인 위치에서 아래에 있는 모든 블록을 지우기
            while (newY > 0) {
                for (int i = 0; i < 4; ++i) {
                    int x = curX + curPiece.x(i);
                    int y = newY - curPiece.y(i) - 1; // WeightItem 아래에 있는 블록의 위치로 수정
                    if (y >= 0 && blockAt(x, y) != Tetrominoe.BorderBlock) {
                        board[(y * BOARD_WIDTH) + x] = Tetrominoe.NoBlock; // 아래에 있는 블록을 지우기
                    }
                }
                --newY;
            }
        }

        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1)) {  
                break;
            }
            --newY;
            ++dropDistance;
        }
        
        TotalScore += dropDistance;
        
        pieceDropped();
    }

    private void oneLineDown() {        //블록을 한 칸 떨어뜨리기
        if (!tryMove(curPiece, curX, curY - 1)) {
            pieceDropped();
        }
    }

    public void clearBoard() {            //게임보드 초기화
        for (int i = 0; i < BOARD_HEIGHT; ++i) {        //게임 보드의 테두리 그리기
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                if (i == 0 || i == BOARD_HEIGHT - 1 || j == 0 || j == BOARD_WIDTH - 1) {
                    board[(i * BOARD_WIDTH) + j] = Tetrominoe.BorderBlock;
                } else {
                    board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
                }
            }
        }
    }

   public void applyItemEffect(Tetrominoe item) {
       switch (item) {
            case WeightItem:
                ItemEffectHandler.deleteWeightItem(board, curPiece, curX, curY);
                break;
            case LineDelItem:
                ItemEffectHandler.applyLineDelItem(board, curX, curY);
                TotalScore++;
                break;
            case ThreeItem:
                ItemEffectHandler.applyThreeItem(board, curX, curY);
                break;
            case FiveItem:
                ItemEffectHandler.applyFiveItem(board, curX, curY);
                break;
            case SevenItem:
                ItemEffectHandler.applySevenItem(board, curX, curY);
                break;
            case PlusItem:
                ItemEffectHandler.applyPlusItem(board, curX, curY);
                TotalScore++;
                break;
            case AllDelItem:
                ItemEffectHandler.applyAllDelItem(board);
                break;
            default:
                break;
       }
   }

   private List<int[]> lastMovedBlocks = new ArrayList<>(); 

   private int countGrayBlocks() {
	    int grayLines = 0;
	    for (int y = 1; y < BOARD_HEIGHT - 1; y++) {
	        boolean hasGrayBlock = false;
	        for (int x = 1; x < BOARD_WIDTH - 1; x++) {
	            if (blockAt(x, y) == Tetrominoe.GrayBlock) {
	                hasGrayBlock = true;
	                break;
	            }
	        }
	        if (hasGrayBlock) {
	            grayLines++;
	        }
	    }
	    return grayLines;
	}
   
   private void pieceDropped() {
	    lastMovedBlocks.clear();
	    for (int i = 0; i < 4; ++i) {
	        int x = curX + curPiece.x(i);
	        int y = curY - curPiece.y(i);
	        board[(y * BOARD_WIDTH) + x] = curPiece.getBlock();
	        lastMovedBlocks.add(new int[]{x, y});
	    }

	    applyItemEffect(curPiece.getBlock());
	    removeFullLines();

	    if (!isFallingFinished) {
	        if (!queuedLines.isEmpty()) {
	            processQueuedLines();
	        }
	        newPiece();
	    }
	}

    private void newPiece() {        // 새로운 블록 생성
        if(remainRowsForItems <= 0 && gameMode.equals("아이템")) {    
            isItem = true;            // 선택된 것이 아이템임
            curPiece = nextPiece;    // 현재 블록을 방금 표시되었던 블록으로 설정
            nextPiece = new Blocks(difficulty); // 다음 블록 생성
            nextPiece.setRandomBlock(difficulty, isItem);
            curX = BOARD_WIDTH / 2 - 1;
            curY = BOARD_HEIGHT - 2 + curPiece.minY();
            remainRowsForItems = 10;        // 아이템이 나오기까지 남은 줄 초기화
        } else {
            isItem = false;        // 선택된 것이 블록임
            curPiece = nextPiece;    // 현재 블록을 방금 표시되었던 블록으로 설정
            nextPiece = new Blocks(difficulty); // 다음 블록 생성
            nextPiece.setRandomBlock(difficulty, isItem);
            curX = BOARD_WIDTH / 2 - 1;
            curY = BOARD_HEIGHT - 2 + curPiece.minY();
        }
        
        if (gameMode.equals("타이머")) {
            if (timerMode == null) {
                timerMode = new Timer();
                if(difficulty == "Easy") {
                    timerModeLimit = 151;
                } else if (difficulty == "Normal") {
                    timerModeLimit = 101;
                } else {
                    timerModeLimit = 51;
                }
           
                timerMode.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isPaused) { // isPaused가 false일 때만 타이머 동작
                            timerModeLimit--; // 1초씩 감소
                            if (timerModeLimit <= 0) {
                                // 0.075초의 딜레이를 추가
                                try {
                                    Thread.sleep(75);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                timerMode.cancel(); // 타이머 중단
                                istimerModeCancelled = true;
                                isFallingFinished = true; // 게임 진행 중지
                                if (!isGameOver) {
                                    isGameOver = true;
                                    parent.gameOver(0);
                                }
                            }
                        }
                    }
                }, 0, 1000); // 1초마다 실행
            }
        }

        if (!tryMove(curPiece, curX, curY) || istimerModeCancelled) {
            // 게임오버일 때 대전 모드인지 확인
            timer.cancel();
            isStarted = false;

            if (specialMode.equals("대전 모드")) {
                if(!isGameOver) {
                    isGameOver = true;
                    parent.gameOver(playerNumber);
                }
            } else {
                // 솔로 모드일 때는 기존 로직 유지
                int linesRemoved = TotalScore;
                String name = JOptionPane.showInputDialog("Enter your name:");

                HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        endGame(name, difficulty, gameMode, linesRemoved);

                        ScoreEntry newScore = new ScoreEntry(name, difficulty, gameMode, linesRemoved);
                        ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
                        scoreBoardMenu.addScore(newScore);

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
    }
    
    public int getTotalScore() {
        return TotalScore;
    }

    public void declareWinner(int winnerPlayerNumber) {
        String winnerMessage = "Player " + winnerPlayerNumber + " Wins!";
        parent.displayWinnerMessage(winnerMessage);
        System.exit(0); // 게임 종료
    }
   
    public void endGame(String name, String difficulty, String mode, int score) {
    	ScoreFileWriter.writeScore(name, difficulty, mode, score);
    }
    
    public void stopTimers() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerMode != null) {
            timerMode.cancel();
        }
    }
    
    public boolean tryMove(Blocks newPiece, int newX, int newY) {        //블록을 이동시킬 수 있는지 확인

        // WeightBlock인 경우 처리
        if (newPiece.getBlock() == Tetrominoe.WeightItem) {
            boolean canMove = true;
            isTouchedBlocks = false;
            
            // 새로운 위치에 이동할 수 있는지 확인
            for (int i = 0; i < 4; ++i) {
                int x = newX + newPiece.x(i);
                int y = newY - newPiece.y(i);
                if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                    canMove = false;
                    break;
                }
                if (blockAt(x, y) == Tetrominoe.BorderBlock) {
                    canMove = false;
                    break;
                } else if (blockAt(x, y) != Tetrominoe.NoBlock) {        // 한 번 다른 블록을 파괴하면 더 이상 좌우로 움직일 수 없음
                    isTouchedBlocks = true;
                }
            }
            if (canMove) {
                curPiece = newPiece;
                curX = newX;
                curY = newY;
                for (int i = 0; i < 4; ++i) {
                    int x = newX + newPiece.x(i);
                    int y = newY - newPiece.y(i); 
                    if (y >= 0 && blockAt(x, y) != Tetrominoe.BorderBlock) {
                        board[(y * BOARD_WIDTH) + x] = Tetrominoe.NoBlock;    // 아래 있던 블록을 파괴함
                    }
                }
                repaint();
                return true;
            } else {
                return false;
            }
        } else {
            // WeightBlock이 아닌 경우 기존 동작 수행
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
    }

    private void removeFullLines() {
        int numFullLines = 0;
        int consecutiveLines = 0;
        List<Integer> fullLines = new ArrayList<>(); // 강조된 줄의 인덱스를 저장할 리스트
        List<int[]> excludedBlocks = new ArrayList<>(); // 제외할 블록 좌표를 저장할 리스트

        // 보드의 각 행을 확인하여 꽉 찬 줄인지 확인
        for (int i = BOARD_HEIGHT - 2; i >= 1; --i) {
            boolean lineIsFull = true;
            boolean hasGrayBlock = false;

            // 해당 줄이 꽉 찼는지 확인
            for (int j = 1; j < BOARD_WIDTH - 1; ++j) {
                if (blockAt(j, i) == Tetrominoe.NoBlock || blockAt(j, i) == Tetrominoe.BorderBlock) {
                    lineIsFull = false;
                    break;
                }
                if (blockAt(j, i) == Tetrominoe.GrayBlock) {
                    hasGrayBlock = true;
                }
            }

            // 만약 해당 줄이 꽉 찼다면, 리스트에 추가
            if (lineIsFull) {
                if (gameMode == "아이템") {
                    --remainRowsForItems;
                } // 게임모드 설정 관련 수정
                ++numFullLines;
                ++consecutiveLines;
                fullLines.add(i);
                
                // 마지막으로 움직인 블록이 삭제된 줄에 포함되면, 그 좌표를 제외할 블록 좌표 리스트에 추가
                for (int[] coord : lastMovedBlocks) {
                    if (coord[1] == i) {
                        excludedBlocks.add(coord);
                    }
                }
                if (hasGrayBlock) {
                    --numFullLines; // GrayBlock이 있는 줄을 제외
                }
            }
        }
        
        // 모든 꽉 찬 줄을 동시에 제거
        if (!fullLines.isEmpty()) {
            for (int fullLineIndex : fullLines) {
                // 강조된 줄을 흰색으로 변경
                for (int j = 1; j < BOARD_WIDTH - 1; ++j) {
                    board[(fullLineIndex * BOARD_WIDTH) + j] = Tetrominoe.HighlightBlock;
                }
            }

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 강조된 줄들을 삭제하고 위의 줄들을 아래로 이동
                    for (int fullLineIndex : fullLines) {
                        for (int k = fullLineIndex; k < BOARD_HEIGHT - 2; ++k) {
                            for (int j = 1; j < BOARD_WIDTH - 1; ++j) {
                                // 윗줄의 블록을 아래 줄로 이동
                                board[(k * BOARD_WIDTH) + j] = blockAt(j, k + 1);
                            }
                        }
                    }

                    repaint();
                }
            }, 100); // 딜레이 추가

            if (consecutiveLines > 0) {
                TotalScore += (consecutiveLines - 1);
            }
        }

        if (numFullLines > 0) {
            TotalScore += numFullLines*10;
            isFallingFinished = true;
            curPiece.setBlock(Tetrominoe.NoBlock);
            repaint();
            adjustSpeed(numFullLines);
        }
        // 상대방 보드에 줄 추가
        if (specialMode.equals("대전 모드") && opponentBoard != null && numFullLines >= 2) {
            opponentBoard.queueLines(numFullLines, excludedBlocks);
        }
        
        lastMovedBlocks.clear();
    }

    private static List<int[]> fixExcludedBlocks(List<int[]> excludedBlocks) {
        excludedBlocks.sort((a, b) -> {
            int yCompare = Integer.compare(a[1], b[1]);
            if (yCompare == 0) {
                return Integer.compare(a[0], b[0]);
            }
            return yCompare;
        });

        List<int[]> fixedExcludedBlocks = new ArrayList<>();
        int currentY = 1;
        int previousY = excludedBlocks.get(0)[1];
        for (int i = 0; i < excludedBlocks.size(); i++) {
            int[] coord = excludedBlocks.get(i);
            if (coord[1] != previousY) {
                currentY++;
            }
            fixedExcludedBlocks.add(new int[]{coord[0], currentY});
            previousY = coord[1];
        }

        System.out.println("Fixed Excluded Blocks:");
        for (int[] coord : fixedExcludedBlocks) {
            System.out.println("Block Coordinate: (" + coord[0] + ", " + coord[1] + ")");
        }

        return fixedExcludedBlocks;
    }

    public void queueLines(int numLines, List<int[]> excludedBlocks) {
        // 현재 보드 위의 GrayBlock 줄 수를 확인합니다.
        int grayOnBoard = countGrayBlocks();

        // 대기 블록의 최대 줄 수를 확인합니다.
        int queuedLineCount = (int) queuedLines.stream().mapToInt(coord -> coord[1]).distinct().count();
        int totalLines = grayOnBoard + queuedLineCount;

        // 새로 추가되는 줄 수를 제한합니다.
        int maxNewLines = 10 - totalLines;
        int linesToAdd = Math.min(numLines, maxNewLines);
        int linesToElimination = numLines - linesToAdd;

        // 탈락하는 줄의 y값을 로그에 출력합니다.
        if (linesToElimination > 0) {
            List<int[]> removedLines = queuedLines.stream()
                    .sorted(Comparator.comparingInt(coord -> coord[1]))
                    .limit(linesToElimination)
                    .collect(Collectors.toList());
            for (int[] coord : removedLines) {
                System.out.println("Removing line at y: " + coord[1]);
            }
        }

        // 기존 대기 블록의 y 값을 증가시킵니다.
        for (int[] line : queuedLines) {
            line[1] += linesToAdd;
        }
        for (int[] coord : queuedExcludedBlocks) {
            coord[1] += linesToAdd;
        }

        // 새로운 줄을 추가합니다.
        int newMaxY = linesToAdd;
        for (int i = 0; i < linesToAdd; i++) {
            for (int x = 1; x < BOARD_WIDTH - 1; x++) {
                queuedLines.add(new int[]{x, newMaxY - i});
            }
        }

        // 제외 블록도 대기 리스트에 추가
        List<int[]> fixedExcludedBlocks = fixExcludedBlocks(excludedBlocks);
        for (int[] coord : fixedExcludedBlocks) {
            coord[1] -= linesToElimination;
            if (coord[1] > 0) { // y 좌표가 0보다 큰 경우에만 추가
                queuedExcludedBlocks.add(new int[]{coord[0], coord[1]});
            }
        }

        repaint();
    }


    
    private void processQueuedLines() {
        if (queuedLines.isEmpty()) {
            return;
        }

        // 현재 보드 위의 GrayBlock 줄 수를 확인합니다.
        int grayOnBoard = countGrayBlocks();
        int maxAllowedLines = 10 - grayOnBoard;

        // 대기 블록의 최대 줄 수를 초과하지 않도록 제한합니다.
        int queuedLineCount = (int) queuedLines.stream().mapToInt(coord -> coord[1]).distinct().count();
        if (queuedLineCount > maxAllowedLines) {
            int linesToRemove = queuedLineCount - maxAllowedLines;

            // 남은 블록들의 y 값을 줄어든 만큼 조정합니다.
            for (int[] coord : queuedLines) {
                coord[1] -= linesToRemove;
            }
            for (int[] coord : queuedExcludedBlocks) {
                coord[1] -= linesToRemove;
            }

            // 초과된 줄 제거
            queuedLines = queuedLines.stream()
                    .sorted(Comparator.comparingInt(coord -> coord[1]))
                    .skip(linesToRemove)
                    .collect(Collectors.toList());
            queuedExcludedBlocks = queuedExcludedBlocks.stream()
                    .sorted(Comparator.comparingInt(coord -> coord[1]))
                    .skip(linesToRemove)
                    .collect(Collectors.toList());
        }

        // Step 1: 보드의 블록들을 위로 들어올리기
        int maxY = queuedLines.stream().mapToInt(coord -> coord[1]).max().orElse(0);
        int linesToAdd = Math.min(10, maxY);

        for (int y = BOARD_HEIGHT - 2 - linesToAdd; y >= 1; y--) {
            for (int x = 1; x < BOARD_WIDTH - 1; x++) {
                board[(y + linesToAdd) * BOARD_WIDTH + x] = board[y * BOARD_WIDTH + x];
            }
        }

        // Step 2: 공격 블록 생성
        for (int[] line : queuedLines) {
            if (line[1] > maxY - 10) {
                for (int x = 1; x < BOARD_WIDTH - 1; x++) {
                    board[line[1] * BOARD_WIDTH + x] = Tetrominoe.GrayBlock;
                }
            }
        }

        // Step 3: 제외할 블록의 좌표를 수정하여 NoBlock으로 변경
        replaceWithNoBlock(queuedExcludedBlocks);

        queuedLines.clear(); // 대기 리스트 초기화
        queuedExcludedBlocks.clear(); // 대기 제외 블록 리스트 초기화
    }

    private void replaceWithNoBlock(List<int[]> fixedExcludedBlocks) {
        for (int[] coord : fixedExcludedBlocks) {
            int x = coord[0];
            int y = coord[1];
            if (y > 0 && y < BOARD_HEIGHT) {
                board[y * BOARD_WIDTH + x] = Tetrominoe.NoBlock;
            }
        }
    }
    
    private void adjustSpeed(int numFullLines) {
        int newDelay;
        int newInterval;
        int additionalScore = 0; // 추가 점수를 추적하기 위한 변수

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
        
        if (PERIOD_INTERVAL < 1000) {
            additionalScore += (1000 - PERIOD_INTERVAL) / 100;
        }

        // TotalScore에 추가 점수 더하기
        TotalScore += additionalScore;

        if (newInterval <= 0) {
            newInterval = 1;
        }

        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), newDelay, newInterval);

        INITIAL_DELAY = newDelay;
        PERIOD_INTERVAL = newInterval;
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private void update() {        //게임 상태 업데이트
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

    public class TAdapter extends KeyAdapter { // 키 이벤트 핸들러
        private int playerNumber;

        public TAdapter(int playerNumber) {
            this.playerNumber = playerNumber;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!isStarted || curPiece.getBlock() == Tetrominoe.NoBlock) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_P) {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            if (specialMode.equals("대전 모드")) {
                handleKeyEventForPlayer(e);
            } else {
                handleKeyEventForSolo(e);
            }
        }

        private void handleKeyEventForPlayer(KeyEvent e) {
            if (playerNumber == 1) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        tryMove(curPiece.rotateRight(), curX, curY);
                        break;
                    case KeyEvent.VK_A:
                        if (!isTouchedBlocks) tryMove(curPiece, curX - 1, curY);
                        break;
                    case KeyEvent.VK_S:
                        tryMove(curPiece, curX, curY - 1);
                        TotalScore += 1;
                        break;
                    case KeyEvent.VK_D:
                        if (!isTouchedBlocks) tryMove(curPiece, curX + 1, curY);
                        break;
                    case KeyEvent.VK_Q:
                        dropDown();
                        break;
                }
            } else if (playerNumber == 2) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        tryMove(curPiece.rotateRight(), curX, curY);
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!isTouchedBlocks) tryMove(curPiece, curX - 1, curY);
                        break;
                    case KeyEvent.VK_DOWN:
                        tryMove(curPiece, curX, curY - 1);
                        TotalScore += 1;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!isTouchedBlocks) tryMove(curPiece, curX + 1, curY);
                        break;
                    case KeyEvent.VK_SHIFT:
                        dropDown();
                        break;
                }
            }
        }

        private void handleKeyEventForSolo(KeyEvent e) {
            if (ControlKeySettingMenu.controlKeyStatus.equals("타입A")) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        tryMove(curPiece.rotateRight(), curX, curY);
                        break;
                    case KeyEvent.VK_A:
                        if (!isTouchedBlocks) tryMove(curPiece, curX - 1, curY);
                        break;
                    case KeyEvent.VK_S:
                        tryMove(curPiece, curX, curY - 1);
                        TotalScore += 1;
                        break;
                    case KeyEvent.VK_D:
                        if (!isTouchedBlocks) tryMove(curPiece, curX + 1, curY);
                        break;
                    case KeyEvent.VK_Q:
                        dropDown();
                        break;
                }
            } else if (ControlKeySettingMenu.controlKeyStatus.equals("타입B")) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        tryMove(curPiece.rotateRight(), curX, curY);
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!isTouchedBlocks) tryMove(curPiece, curX - 1, curY);
                        break;
                    case KeyEvent.VK_DOWN:
                        tryMove(curPiece, curX, curY - 1);
                        TotalScore += 1;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!isTouchedBlocks) tryMove(curPiece, curX + 1, curY);
                        break;
                    case KeyEvent.VK_Q:
                        dropDown();
                        break;
                }
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
