package main.java.setting;

import main.java.menu.StartMenu;
import main.java.menu.gamestart.DifficultySettingMenu;
import main.java.menu.ScoreBoardMenu;
import main.java.setting.colorblindmode.ColorBlindModeMenu;
import main.java.setting.controlkeysetting.ControlKeySettingMenu;
import main.java.setting.screenadjustsize.ScreenAdjustSizeMenu;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;
import main.java.setting.SettingFileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static main.java.setting.colorblindmode.ColorBlindModeMenu.colorBlindStatus;
import static main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus;


public class SettingMenu extends JFrame {
    private JLabel titleLabel;
    public JLabel[] labels;
    private JButton sizeAdjustButton, controlKeyButton, resetscoreButton, colorBlindModeButton, resetSettingButton, backButton;
    public JButton[] buttons;
    public boolean isBackButton;

    public SettingMenu() {
        setTitle("설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        labels = new JLabel[]{titleLabel};

        sizeAdjustButton = new JButton("크기 조절");
        controlKeyButton = new JButton("조작키 설정");
        resetscoreButton = new JButton("스코어보드 초기화");
        colorBlindModeButton = new JButton("색맹 모드");
        resetSettingButton = new JButton("설정 초기화");
        backButton = new JButton("뒤로가기");
        
        buttons = new JButton[]{sizeAdjustButton, controlKeyButton, resetscoreButton, colorBlindModeButton, resetSettingButton, backButton};
        
        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);

        // 크기 조절 창 열기
        sizeAdjustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 설정 페이지 닫기
                ScreenAdjustSizeMenu adjustSize = new ScreenAdjustSizeMenu(); // 크기 조정 페이지로 이동
                adjustSize.setSize(getSize());
                ScreenAdjustComponent.sizeAdjust(adjustSize.labels, adjustSize.buttons, adjustSize.isBackButton, SettingFileWriter.readSize());
                adjustSize.setVisible(true);
                HandleKeyEvent.selectedButtonIndex = 0;
            }
        });
        
        //스코어보드 초기화 기능
        resetscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose(); // 현재 페이지 닫기
            	ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
            	scoreBoardMenu.setSize(getSize());
                ScreenAdjustComponent.sizeAdjust(scoreBoardMenu.labels, scoreBoardMenu.buttons, scoreBoardMenu.isBackButton, SettingFileWriter.readSize());
                scoreBoardMenu.setVisible(true);
            	scoreBoardMenu.clearScores();
            	HandleKeyEvent.selectedButtonIndex = 0;
            }
        });

        // 조작키 설정 창 열기
        controlKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	ControlKeySettingMenu controlKeySettingMenu = new ControlKeySettingMenu();
                        controlKeySettingMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(controlKeySettingMenu.labels, controlKeySettingMenu.buttons, controlKeySettingMenu.isBackButton, SettingFileWriter.readSize());
                        controlKeySettingMenu.setVisible(true);
                        controlKeySettingMenu.updateControlKeyModeUI(controlKeyStatus);
                    }
                });
            }
        });



        // 색맹 모드 창 열기
        colorBlindModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ColorBlindModeMenu colorBlindModeMenu = new ColorBlindModeMenu();
                        colorBlindModeMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(colorBlindModeMenu.labels, colorBlindModeMenu.buttons, colorBlindModeMenu.isBackButton, SettingFileWriter.readSize());
                        colorBlindModeMenu.setVisible(true);
                        colorBlindModeMenu.updateColorBlindModeUI(colorBlindStatus);
                    }
                });
            }
        });

        resetSettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//창 크기 초기화(small)
            	SettingFileWriter.clearSetting();
            	SettingFileWriter.writeSetting(0, "타입A", "정상");
            	setSize(400, 550);
            	ScreenAdjustComponent.sizeAdjust(labels, buttons, isBackButton, SettingFileWriter.readSize());

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