package main.java.game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.menu.gamestart.GameStartMenu;
import main.java.setting.SettingFileWriter;
import main.java.setting.colorblindmode.ColorBlindModeMenu;
import main.java.setting.controlkeysetting.ControlKeySettingMenu;


public class TetrisGame extends JFrame {
	private String specialMode, gameMode, difficulty; // 게임모드 설정 관련 수정
	private static final long serialVersionUID = 1L;
	private JLabel statusbar;

	// 게임모드 설정 관련 수정
    public TetrisGame(String specialMode, String gameMode, String difficulty) {
    	this.specialMode = specialMode;
    	this.gameMode = gameMode;
    	this.difficulty = difficulty;
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        Board board = new Board(this, specialMode, gameMode, difficulty);  // 게임모드 설정 관련 수정
        add(board);
        board.start();
        
        setTitle("테트리스 게임");
        if(SettingFileWriter.readSize() == 0)
        setSize(400, 550);
        else if(SettingFileWriter.readSize() == 1)
        setSize(440,605);
        else
        setSize(480,660);
        ColorBlindModeMenu.colorBlindStatus = SettingFileWriter.readBlindMode();
        ControlKeySettingMenu.controlKeyStatus = SettingFileWriter.readControlKey();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar() {
        return statusbar;
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