package main.java.menu.gamestart;

import main.java.game.TetrisGame;
import main.java.menu.ScoreBoardMenu;
import main.java.setting.SettingFileWriter;
import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;
import main.java.menu.gamestart.GameStartMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DifficultySettingMenu extends JFrame {
	private JLabel titleLabel;
    public JLabel[] labels;
    private JButton easyButton, normalButton, hardButton, backButton;
    public JButton[] buttons;
	public static int size;
    public boolean isBackButton;
    
    public String ENHdifficulty;

    public DifficultySettingMenu() {
        setTitle("난이도 설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("난이도 설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        labels = new JLabel[]{titleLabel};
        
        easyButton = new JButton("Easy");
        normalButton = new JButton("Normal");
        hardButton = new JButton("Hard");
        
        backButton = new JButton("뒤로가기");
        
        buttons = new JButton[]{easyButton, normalButton, hardButton, backButton};
        
        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);       
        
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                ENHdifficulty = "Easy";
                TetrisGame TetrisGame = new TetrisGame(ENHdifficulty);
                TetrisGame.setVisible(true);
            }
        });
        
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                ENHdifficulty = "Normal";
                TetrisGame TetrisGame = new TetrisGame(ENHdifficulty);
                TetrisGame.setVisible(true);
            }
        });
        
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                ENHdifficulty = "Hard";
                TetrisGame TetrisGame = new TetrisGame(ENHdifficulty);
                TetrisGame.setVisible(true);
            }
        });
        
        // 뒤로가기 버튼 생성 및 이벤트 처리
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	GameStartMenu gameStartMenu = new GameStartMenu();
                    	gameStartMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(gameStartMenu.labels, gameStartMenu.buttons, gameStartMenu.isBackButton, SettingFileWriter.readSize());
                        gameStartMenu.setVisible(true);
                    }
                });
            }
        });
        panel.add(backButton, BorderLayout.SOUTH); // 뒤로가기 버튼을 패널의 SOUTH에 추가
        
        // 프레임 설정
        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	HandleKeyEvent.handleKeyEvent(e, buttons, isBackButton);
            }
        });
        setFocusable(true);
    }

}
