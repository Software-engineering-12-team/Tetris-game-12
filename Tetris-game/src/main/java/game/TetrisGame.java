	package main.java.game;
	
	import java.awt.BorderLayout;
	import java.awt.EventQueue;
	import java.awt.GridLayout;
	import java.awt.event.KeyAdapter;
	import java.awt.event.KeyEvent;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel; // 추가된 임포트
	import javax.swing.JOptionPane;
	import main.java.menu.StartMenu;
	import main.java.menu.gamestart.GameStartMenu;
	import main.java.setting.SettingFileWriter;
	import main.java.setting.colorblindmode.ColorBlindModeMenu;
	import main.java.setting.controlkeysetting.ControlKeySettingMenu;
		
	public class TetrisGame extends JFrame {
	    private String specialMode, gameMode, difficulty; // 게임모드 설정 관련 수정
	    private static final long serialVersionUID = 1L;
	    private JLabel statusbar1;
	    private JLabel statusbar2;
	    private Board board1;
	    private Board board2;
	    private boolean gameOverDisplayed = false;
	
	    // 게임모드 설정 관련 수정
	    public TetrisGame(String specialMode, String gameMode, String difficulty) {
	        this.specialMode = specialMode;
	        this.gameMode = gameMode;
	        this.difficulty = difficulty;
	        initUI();
	    }
	
	    private void initUI() {
	        setLayout(new BorderLayout());
	 
	        if (specialMode.equals("대전 모드")) {
	 
	            board1 = new Board(this, specialMode, gameMode, difficulty, 1);
	            board2 = new Board(this, specialMode, gameMode, difficulty, 2);
	            board1.setOpponent(board2);
	            board2.setOpponent(board1);
	
	            JPanel boardPanel = new JPanel(new GridLayout(1, 2));
	            boardPanel.add(board1);
	            boardPanel.add(board2);
	            add(boardPanel, BorderLayout.CENTER);
	
	            adjustFrameSizeForDualBoards();
	            addKeyListener(new KeyAdapter() {
	                @Override
	                public void keyPressed(KeyEvent e) {
	                	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                        showExitConfirmation();
	                    }
	                        board1.dispatchEvent(e); 
	                        board2.dispatchEvent(e); 
	                	}
	            });
	        } else {
	            board1 = new Board(this, specialMode, gameMode, difficulty, 1);
	            add(board1, BorderLayout.CENTER);
	            adjustFrameSizeForSingleBoard();
	            
	            addKeyListener(new KeyAdapter() {
	                @Override
	                public void keyPressed(KeyEvent e) {
	                	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                        showExitConfirmation();
	                    }
	                        board1.dispatchEvent(e); 
	                	}
	            });
	        }
	
	        board1.start();
	        if (board2 != null) {
	            board2.start();
	        }
	
	        setTitle("테트리스 게임");
	        ColorBlindModeMenu.colorBlindStatus = SettingFileWriter.readBlindMode();
	        ControlKeySettingMenu.controlKeyStatus = SettingFileWriter.readControlKey();
	
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                // 창이 닫힐 때 타이머 중지
	                board1.stopTimers();
	                if (board2 != null) {
	                    board2.stopTimers();
	                }
	            }
	        });
	        
	        setLocationRelativeTo(null);
	        setFocusable(true); // 프레임 포커스 설정
	        requestFocusInWindow(); // 프레임 포커스 요청
	    }
	
	    private void adjustFrameSizeForSingleBoard() {
	        if (SettingFileWriter.readSize() == 0) {
	            setSize(400, 550);
	        } else if (SettingFileWriter.readSize() == 1) {
	            setSize(440, 605);
	        } else {
	            setSize(480, 660);
	        }
	    }
	
	    private void adjustFrameSizeForDualBoards() {
	        if (SettingFileWriter.readSize() == 0) {
	            setSize(800, 550);
	        } else if (SettingFileWriter.readSize() == 1) {
	            setSize(880, 605);
	        } else {
	            setSize(960, 660);
	        }
	    }
	
	    public JLabel getStatusBar(int playerNumber) {
	        return playerNumber == 1 ? statusbar1 : statusbar2;
	    }
	
	    public void gameOver(int loserPlayerNumber) {
	        if (!gameOverDisplayed) {
	            gameOverDisplayed = true;
	            String winnerMessage;
	            if (loserPlayerNumber == 1) {
	                winnerMessage = "Player 2 Wins!";
	            } else if (loserPlayerNumber == 2) {
	                winnerMessage = "Player 1 Wins!";
	            } else {
	                int score1 = board1.getTotalScore();
	                int score2 = board2.getTotalScore();
	                if (score1 == score2) {
	                    winnerMessage = "It's a Tie!";
	                } else if (score1 > score2) {
	                    winnerMessage = "Player 1 Wins!";
	                } else {
	                    winnerMessage = "Player 2 Wins!";
	                }
	            }
	            displayWinnerMessage(winnerMessage);
	        }
	    }
	
	    public void displayWinnerMessage(String message) {
	        JOptionPane.showMessageDialog(this, message);
	        returnToStartMenu();
	    }
	    
	    public void showExitConfirmation() {	//종료 여부 확인하기
	    	Object[] options = {"종료", "타이틀로 돌아가기", "취소"};
	        int option = JOptionPane.showOptionDialog(
	            null,
	            "타이틀로 돌아가기 또는 종료하시겠습니까?",
	            "게임 종료",
	            JOptionPane.YES_NO_CANCEL_OPTION,
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            options,
	            options[2]
	        );
	
	        if (option == JOptionPane.YES_OPTION) {
	            System.exit(0);
	        } else if (option == JOptionPane.NO_OPTION) {
	            returnToStartMenu();
	        }
	    }
	    
	    public void returnToStartMenu() {
	        dispose(); // 현재 게임 창을 닫음
	        StartMenu StartMenu = new StartMenu();
	        StartMenu.setVisible(true);
	    }
	    
	    public static void main(String[] args) {
	        // 게임모드 설정 관련 수정
	        GameStartMenu gameStartMenu = new GameStartMenu();
	        String specialMode = gameStartMenu.selectedModes[0];
	        String gameMode = gameStartMenu.selectedModes[1];
	        String difficulty = gameStartMenu.selectedModes[2];
	        EventQueue.invokeLater(() -> {
	            TetrisGame game = new TetrisGame(specialMode, gameMode, difficulty);
	            game.setVisible(true);
	        });
	    }
	}
