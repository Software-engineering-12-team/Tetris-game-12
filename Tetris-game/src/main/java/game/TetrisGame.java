package main.java.game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; // 추가된 임포트
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
                    board1.dispatchEvent(e); // board1에 키 이벤트 전달
                    board2.dispatchEvent(e); // board2에 키 이벤트 전달
                }
            });
        } else {
            board1 = new Board(this, specialMode, gameMode, difficulty, 1);
            add(board1, BorderLayout.CENTER);
            adjustFrameSizeForSingleBoard();
            
            addKeyListener(board1.new TAdapter(1)); // board1에 KeyListener 추가
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
