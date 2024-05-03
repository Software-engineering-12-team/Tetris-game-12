package main.java.menu.gamestart;

import main.java.menu.StartMenu;
import main.java.setting.SettingFileWriter;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameStartMenu extends JFrame {
	private JLabel titleLabel;
    public JLabel[] labels;
    private JButton normalModeButton, itemModeButton, backButton;
    public JButton[] buttons;
	public static int size;
	public static boolean isItemMode;
    public boolean isBackButton;

	public GameStartMenu() {
		setTitle("게임 모드 설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("게임 모드 설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        labels = new JLabel[]{titleLabel};
        
        normalModeButton = new JButton("노멀 모드");
        itemModeButton = new JButton("아이템 모드");
        
        backButton = new JButton("뒤로가기");
        
        buttons = new JButton[]{normalModeButton, itemModeButton, backButton};
        
        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 25));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(55, 30, 70, 30));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);
        
        // 노멀 모드에서 난이도 조절 창으로 연결
        normalModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	isItemMode = false;
            	dispose(); // 현재 설정 페이지 닫기
                DifficultySettingMenu difficultySetting = new DifficultySettingMenu(); // 크기 조정 페이지로 이동
                difficultySetting.setSize(getSize());
                ScreenAdjustComponent.sizeAdjust(difficultySetting.labels, difficultySetting.buttons, difficultySetting.isBackButton, SettingFileWriter.readSize());
                difficultySetting.setVisible(true);
                HandleKeyEvent.selectedButtonIndex = 0;
            }
        });
        
        // 아이템 모드 창 열기
        itemModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	isItemMode = true;
            	dispose(); 
                DifficultySettingMenu difficultySetting = new DifficultySettingMenu(); // 크기 조정 페이지로 이동
                difficultySetting.setSize(getSize());
                ScreenAdjustComponent.sizeAdjust(difficultySetting.labels, difficultySetting.buttons, difficultySetting.isBackButton, SettingFileWriter.readSize());
                difficultySetting.setVisible(true);
                HandleKeyEvent.selectedButtonIndex = 0;

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
                    	StartMenu StartMenu = new StartMenu();
                    	StartMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(StartMenu.labels, StartMenu.buttons, StartMenu.isBackButton, SettingFileWriter.readSize());
                        StartMenu.setVisible(true);
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
