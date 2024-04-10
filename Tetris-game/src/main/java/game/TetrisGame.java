package main.java.game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.setting.screenadjustsize.ScreenAdjustSizeMenu;


public class TetrisGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel statusbar;

    public TetrisGame() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        Board board = new Board(this);
        add(board);
        board.start();
        
        setTitle("테트리스 게임");
        if(ScreenAdjustSizeMenu.size == 0)
        setSize(400, 550);
        else if(ScreenAdjustSizeMenu.size == 1)
        setSize(440,605);
        else
        setSize(480,660);
        	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar() {

        return statusbar;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            TetrisGame game = new TetrisGame();
            game.setVisible(true);
        });
    }
}