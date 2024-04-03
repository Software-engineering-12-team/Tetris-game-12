package main.java.setting;

import main.java.menu.ScoreBoardMenu;
import main.java.menu.StartMenu;
import main.java.setting.colorblindmode.ColorBlindModeMenu;
import main.java.setting.controlkeysetting.ControlKeySettingMenu;
import main.java.setting.screenadjustsize.ScreenAdjustSizeMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SettingMenu extends JFrame {
    private JLabel titleLabel;
    public JLabel[] labels;
    private JButton sizeAdjustButton, controlKeyButton, resetscoreButton, colorBlindModeButton, resetSettingButton, backButton;
    public JButton[] buttons;
    private int selectedButtonIndex;
    public boolean isBackButton;
    
    private void handleKeyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_RIGHT) {
            selectedButtonIndex = (selectedButtonIndex + 1) % buttons.length;
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_LEFT) {
            selectedButtonIndex = (selectedButtonIndex - 1 + buttons.length) % buttons.length;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            buttons[selectedButtonIndex].doClick();
            return; // Enter 키 입력 후 추가 동작을 방지하기 위해 여기서 종료
        }
        ButtonStyle.updateButtonStyles(buttons, selectedButtonIndex, isBackButton);
    }

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
        resetscoreButton = new JButton("스코어보드 초기화 [ON]");
        colorBlindModeButton = new JButton("색맹 모드 [ON]");
        resetSettingButton = new JButton("설정 초기화");
        backButton = new JButton("뒤로가기");
        
        buttons = new JButton[]{sizeAdjustButton, controlKeyButton, resetscoreButton, colorBlindModeButton, resetSettingButton, backButton};
        selectedButtonIndex = 0;
        
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
                ScreenAdjustComponent.sizeAdjust(adjustSize.labels, adjustSize.buttons, adjustSize.isBackButton, ScreenAdjustSizeMenu.size);
                adjustSize.setVisible(true);
            }
        });
        
        //스코어보드 초기화 기능
        resetscoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (resetscoreButton.getText().equals("스코어보드 초기화 [ON]")) {
                    resetscoreButton.setText("스코어보드 초기화 [OFF]");
                } else {
                    resetscoreButton.setText("스코어보드 초기화 [ON]");
                }
            }
        });

        // 조작키 설정 창 열기
        controlKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	ControlKeySettingMenu operatingKeysettingMenu = new ControlKeySettingMenu();
                        operatingKeysettingMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(operatingKeysettingMenu.labels, operatingKeysettingMenu.buttons, operatingKeysettingMenu.isBackButton, ScreenAdjustSizeMenu.size);
                        operatingKeysettingMenu.setVisible(true);
                    }
                });
            }
        });

        // 색맹 모드 창 열기
        colorBlindModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (colorBlindModeButton.getText().equals("색맹 모드 [ON]")) {
                    colorBlindModeButton.setText("색맹 모드 [OFF]");
                } else {
                    colorBlindModeButton.setText("색맹 모드 [ON]");
                }
            }
        });

        // 뒤로가기 버튼 생성 및 이벤트 처리
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	StartMenu StartMenu = new StartMenu();
                    	StartMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(StartMenu.labels, StartMenu.buttons, StartMenu.isBackButton, ScreenAdjustSizeMenu.size);
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
                handleKeyEvent(e);
            }
        });
        setFocusable(true);
    }
}