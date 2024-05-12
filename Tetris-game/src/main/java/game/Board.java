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
import java.util.List;
import java.util.ArrayList;

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

import static java.awt.Color.black;
import static main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 12;
    private final int BOARD_HEIGHT = 22;
    private int INITIAL_DELAY = 100;
    private int PERIOD_INTERVAL = 1000; // 동적 변경을 위해 변경

	private String specialMode, gameMode, difficulty; // 게임모드 설정 관련 수정
    private Timer timer;
    private Timer timerMode;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private boolean isItem = false;
    private boolean isTouchedBlocks = false;
    private boolean istimerModeCancelled = false;
    private int remainRowsForItems = 10;
    private int TotalScore = 0;
    private int curX = 0;
    private int curY = 0;
    private int timerModeLimit; 
    private JLabel statusbar;
    private Blocks curPiece;
    private Blocks nextPiece;
    public static Font tetrisFont;
    private Tetrominoe[] board;
    private Board opponentBoard; // 상대방 보드 추가
    private int playerNumber; // 플레이어 번호 추가
    
    // 게임모드 설정 관련 수정
    public Board(TetrisGame parent, String specialMode, String gameMode, String difficulty, int playerNumber) {
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
        timer.scheduleAtFixedRate(new ScheduleTask(),
                INITIAL_DELAY, PERIOD_INTERVAL);

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
        
     // 다음 블록 그리기
        int nextPieceX = BOARD_WIDTH * squareWidth() + 60;
        int nextPieceY = 50;
        for (int i = 0; i < 4; ++i) {
            int x = nextPiece.x(i) + 1;
            int y = nextPiece.y(i) + 1;
            BlockDrawer.drawBlock(g, nextPieceX + x * squareWidth(),
                    nextPieceY + y * squareHeight(), squareWidth(), squareHeight(), nextPiece.getBlock());
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
     	
     	// 남은 시간 표시 위치
     	int timerX = nextPieceX - squareWidth()	; 
     	int timerY = nextPieceY + squareHeight() * 7; 

     	// 남은 시간 표시
     	g.setColor(Color.WHITE);
     	g.setFont(tetrisFont);
     	if(gameMode == "타이머") {
     		g.drawString("Time: " + timerModeLimit, timerX, timerY);    		
     	}
    }

    @Override
    public void paintComponent(Graphics g) {
    	
    	setBackground(Color.BLACK);
        super.paintComponent(g);
        doDrawing(g);

    }

    private void dropDown() {		//블록을 한 번에 맨 아래로 떨어뜨리기
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
    
    private void deleteWeightItem() {
   	 for (int i = 0; i < 4; i++) {
   	        int x = curX + curPiece.x(i);
   	        int y = curY - curPiece.y(i);
   	        if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT && board[(y * BOARD_WIDTH) + x] != Tetrominoe.BorderBlock) {
   	            board[(y * BOARD_WIDTH) + x] = Tetrominoe.NoBlock;
   	        }
   	    }
   }
   
   private void applyLineDelItem() {
       int centerY = curY; 
       TotalScore++;
       
       // 가로 방향으로 한 줄 제거
       for (int j = 1; j < BOARD_WIDTH - 1; j++) {
           if (board[(centerY * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
               board[(centerY * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
           }
       }
       
    // 해당 줄 위의 모든 블록들을 한 칸씩 아래로 이동시키기
       for (int i = centerY; i < BOARD_HEIGHT - 2; ++i) {
           for (int j = 0; j < BOARD_WIDTH; ++j) {
               board[(i* BOARD_WIDTH) + j] = blockAt(j, i + 1);
           }
       }
   }

   private void applyThreeItem() {
   	int centerX = curX ; // 현재 블록의 중심 X 좌표
       int centerY = curY ; // 현재 블록의 중심 Y 좌표
       
       // 블록이 3x3 범위 내에 있는지 확인하고 중심을 기준으로 주변 블록을 삭제
       for (int i = centerY - 1; i <= centerY + 1; i++) {
           for (int j = centerX - 1; j <= centerX + 1; j++) {
               if (i >= 0 && i < BOARD_HEIGHT && j >= 0 && j < BOARD_WIDTH && board[(i * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
                   board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
               }
           }
       }
   }

   private void applyFiveItem() {
   	int centerX = curX ; 
       int centerY = curY ; 
       
       for (int i = centerY - 2; i <= centerY + 2; i++) {
           for (int j = centerX - 2; j <= centerX + 2; j++) {
               if (i >= 0 && i < BOARD_HEIGHT && j >= 0 && j < BOARD_WIDTH && board[(i * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
                   board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
               }
           }
       }
   }
   
   private void applySevenItem() {
   	int centerX = curX ; 
       int centerY = curY ; 
       
       for (int i = centerY - 3; i <= centerY + 3; i++) {
           for (int j = centerX - 3; j <= centerX + 3; j++) {
               if (i >= 0 && i < BOARD_HEIGHT && j >= 0 && j < BOARD_WIDTH && board[(i * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
                   board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
               }
           }
       }
   }
   
   private void applyPlusItem() {
   	int centerX = curX; 
       int centerY = curY; 
       TotalScore++;
       
       // 가로 방향으로 한 줄 제거
       for (int j = 1; j < BOARD_WIDTH - 1; j++) {
           if (board[(centerY * BOARD_WIDTH) + j] != Tetrominoe.BorderBlock) {
               board[(centerY * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
           }
       }
       
       // 세로 방향으로 한 줄 제거
       for (int i = 1; i < BOARD_HEIGHT - 1; i++) {
           if (board[(i * BOARD_WIDTH) + centerX] != Tetrominoe.BorderBlock) {
               board[(i * BOARD_WIDTH) + centerX] = Tetrominoe.NoBlock;
           }
       }
       
    // 해당 줄 위의 모든 블록들을 한 칸씩 아래로 이동시키기
       for (int i = centerY; i < BOARD_HEIGHT - 2; ++i) {
           for (int j = 0; j < BOARD_WIDTH; ++j) {
               board[(i* BOARD_WIDTH) + j] = blockAt(j, i + 1);
           }
       }
   }

   private void applyAllDelItem() {
   	for (int i = 1; i < BOARD_HEIGHT - 1; i++) {
           for (int j = 1; j < BOARD_WIDTH - 1; j++) {
               board[(i * BOARD_WIDTH) + j] = Tetrominoe.NoBlock;
           }
       }
   }

   private void applyItemEffect(Tetrominoe item) {
       switch (item) {
       	case WeightItem:
       		deleteWeightItem();
       		break;
       	case LineDelItem:
       		applyLineDelItem();
       		break;
           case ThreeItem:
               applyThreeItem();
               break;
           case FiveItem:
           	applyFiveItem();
           	break;
           case SevenItem:
           	applySevenItem();
           	break;
           case PlusItem:
           	applyPlusItem();
           	break;
           case AllDelItem:
               applyAllDelItem();
               break;
           default:
               break;
       }
   }


    private void pieceDropped() {		//블록이 떨어진 후 

        for (int i = 0; i < 4; ++i) {

            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getBlock();
        }
        
        applyItemEffect(curPiece.getBlock());
        removeFullLines();

        if (!isFallingFinished) {
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
    	        }
    	        else if (difficulty == "Normal") {
        	        timerModeLimit = 101;
    	        }
    	        else {
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
                                checkWinner(); // 타이머가 끝났을 때 승자 확인
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
                // 대전 모드일 때는 이름 입력 및 스코어보드 표시 없이 바로 종료
                checkWinner();
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

    // 타이머가 끝났을 때 승자 확인하는 메서드
    private void checkWinner() {
        if (opponentBoard != null) {
            String winnerMessage;
            if (TotalScore > opponentBoard.TotalScore) {
                winnerMessage = "Player " + playerNumber + " Wins!";
            } else if (TotalScore < opponentBoard.TotalScore) {
                winnerMessage = "Player " + opponentBoard.playerNumber + " Wins!";
            } else {
                winnerMessage = "It's a Tie!";
            }
            JOptionPane.showMessageDialog(this, winnerMessage);
            System.exit(0); // 게임 종료
        }
    }
   
    public void endGame(String name, String difficulty, String mode, int score) {
    	 SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ScoreFileWriter.writeScore(name, difficulty, mode, score);
            }
        });
    }
    
    public void stopTimers() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerMode != null) {
            timerMode.cancel();
        }
    }
    
    
    private boolean tryMove(Blocks newPiece, int newX, int newY) {		//블록을 이동시킬 수 있는지 확인

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
                }
                else if (blockAt(x, y) != Tetrominoe.NoBlock) {		// 한 번 다른 블록을 파괴하면 더 이상 좌우로 움직일 수 없음
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
                        board[(y * BOARD_WIDTH) + x] = Tetrominoe.NoBlock;	// 아래 있던 블록을 파괴함
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

        // 보드의 각 행을 확인하여 꽉 찬 줄인지 확인
        for (int i = BOARD_HEIGHT - 2; i >= 1; --i) {
            boolean lineIsFull = true;

            // 해당 줄이 꽉 찼는지 확인
            for (int j = 1; j < BOARD_WIDTH - 1; ++j) {
                if (blockAt(j, i) == Tetrominoe.NoBlock || blockAt(j, i) == Tetrominoe.BorderBlock) {
                    lineIsFull = false;
                    break;
                }
            }

            // 만약 해당 줄이 꽉 찼다면, 리스트에 추가
            if (lineIsFull) {
            	if (gameMode == "아이템") {--remainRowsForItems;} // 게임모드 설정 관련 수정
                ++numFullLines;
                ++consecutiveLines;
                fullLines.add(i);
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

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                showExitConfirmation();
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
