package main.java.menu;

import main.java.setting.SettingScreenRunner;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JFrame {
    private JLabel titleLabel, instructionLabel;
    private JButton startButton, settingsButton, scoreboardButton, exitButton;
    private JButton[] buttons;
    private int selectedButtonIndex;

    private void applyButtonStyle(JButton button) {
        button.setFont(new Font("NanumGothic", Font.BOLD, 18));
        button.setBackground(new Color(30, 60, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

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
        updateButtonStyles();
    }

    private void updateButtonStyles() {
        for (int i = 0; i < buttons.length; i++) {
            if (i == selectedButtonIndex) {
                buttons[i].setBackground(new Color(120, 150, 180));
            } else {
                buttons[i].setBackground(new Color(30, 60, 90));
            }
        }
    }

    public StartMenu() {
        setTitle("테트리스 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("테트리스 게임");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);

        startButton = new JButton("게임 시작");
        settingsButton = new JButton("설정");
        scoreboardButton = new JButton("스코어보드");
        exitButton = new JButton("게임 종료");


        // 설정 버튼 클릭 시 설정 창 열기
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new SettingScreenRunner());
            }
        });

        applyButtonStyle(startButton);
        applyButtonStyle(settingsButton);
        applyButtonStyle(scoreboardButton);
        applyButtonStyle(exitButton);

        buttons = new JButton[]{startButton, settingsButton, scoreboardButton, exitButton};
        selectedButtonIndex = 0;

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 프로그램 종료
                System.exit(0);
            }
        });

        // 버튼 패널 생성 및 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(scoreboardButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);

        instructionLabel = new JLabel("이동: \u2190 / \u2192 / \u2191 / \u2193   선택: Enter");
        instructionLabel.setFont(new Font("NanumGothic", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(instructionLabel, BorderLayout.SOUTH);

        // 프레임 설정
        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);

        // 키 이벤트 핸들러
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e);
            }
        });
        setFocusable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartMenu();
            }
        });
    }
}