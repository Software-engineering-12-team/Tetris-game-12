package main.java.game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.menu.gamestart.DifficultySettingMenu;
import main.java.setting.SettingFileWriter;
import main.java.setting.colorblindmode.ColorBlindModeMenu;


public class TetrisGame extends JFrame {
	
	private String difficulty;
	private static final long serialVersionUID = 1L;
	private JLabel statusbar;

    public TetrisGame(String difficulty) {
    	this.difficulty = difficulty;
        initUI();
    }

    private void initUI() {

        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        Board board = new Board(this, difficulty);
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
        	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar() {
        return statusbar;
    }

    public static void main(String[] args) {
    	DifficultySettingMenu difficultySettingMenu = new DifficultySettingMenu();
        String difficulty = difficultySettingMenu.ENHdifficulty;
        EventQueue.invokeLater(() -> {
            TetrisGame game = new TetrisGame(difficulty);
            game.setVisible(true);
        });
    }
}