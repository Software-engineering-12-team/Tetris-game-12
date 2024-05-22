package main.java.menu;

import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;
import main.java.util.HandleKeyEvent;
import main.java.menu.gamestart.GameStartMenu;
import main.java.game.ScoreFileWriter;
import main.java.setting.SettingFileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class StartMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel, instructionLabel;
    public JLabel[] labels;
    private JButton gameStartButton, settingsButton, scoreboardButton, exitButton;
    public JButton[] buttons;
    public boolean isBackButton;

	public StartMenu() {
		ScoreFileWriter.createScoreboardFile();
		SettingFileWriter.createSettingFile();
		
        setTitle("테트리스 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("테트리스 게임");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        gameStartButton = new JButton("게임 시작");
        settingsButton = new JButton("설정");
        scoreboardButton = new JButton("스코어보드");
        exitButton = new JButton("게임 종료");
        
        buttons = new JButton[]{gameStartButton, settingsButton, scoreboardButton, exitButton};
        
        isBackButton = false;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
        
        // 버튼 패널 생성 및 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);
        
        //게임 시작 창 열기
        gameStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	GameStartMenu gameStart = new GameStartMenu();
                    	gameStart.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(gameStart.labels, gameStart.buttons, gameStart.isBackButton, SettingFileWriter.readSize());
                        gameStart.setVisible(true);
                    }
                });
            }
        });

        // 설정 창 열기
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        SettingMenu settingMenu = new SettingMenu();
                        settingMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(settingMenu.labels, settingMenu.buttons, settingMenu.isBackButton, SettingFileWriter.readSize());
                        settingMenu.setVisible(true);
                    }
                });
            }
        });

        // 스코어 보드 창 열기
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
                        scoreBoardMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(scoreBoardMenu.labels, scoreBoardMenu.buttons, scoreBoardMenu.isBackButton, SettingFileWriter.readSize());
                        scoreBoardMenu.setVisible(true);
                    }
                });
            }
        });

        // 프로그램 종료
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        instructionLabel = new JLabel("이동: \u2190 / \u2192 / \u2191 / \u2193   선택: Enter");
        instructionLabel.setFont(new Font("NanumGothic", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(instructionLabel, BorderLayout.SOUTH);
        
        labels = new JLabel[]{titleLabel, instructionLabel};

        // 프레임 설정
        if(SettingFileWriter.readSize() == 0)
   		 setSize(400, 550);
            else if(SettingFileWriter.readSize() == 1)
           	 setSize(440, 605);
            else
           	 setSize(480, 660);
    	ScreenAdjustComponent.sizeAdjust(labels, buttons, isBackButton, SettingFileWriter.readSize());
        setLocationRelativeTo(null);
        setVisible(true);

        // 키 이벤트 핸들러
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	HandleKeyEvent.handleKeyEvent(e, buttons, isBackButton);
            }
        });
        setFocusable(true);
    }

    public static void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Nimbus를 사용할 수 없을 때 기본 LookAndFeel을 사용하거나, 다른 LookAndFeel을 설정할 수 있습니다.
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
    
    public static void main(String[] args) {
        setLookAndFeel();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartMenu();
            }
        });
    }
}