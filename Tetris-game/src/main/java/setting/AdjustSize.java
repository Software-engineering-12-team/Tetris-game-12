package main.java.setting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdjustSize extends JFrame {
    private JLabel titleLabel;
    private JButton smallButton, mediumButton, largeButton;
    private JButton[] buttons;
    private int selectedButtonIndex;

    private void applyButtonStyle(JButton button) {
        button.setFont(new Font("NanumGothic", Font.BOLD, 18));
        button.setBackground(new Color(30, 60, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
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

    public AdjustSize() {
        setTitle("크기 조정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("크기 조정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        smallButton = new JButton("사이즈 1");
        mediumButton = new JButton("사이즈 2");
        largeButton = new JButton("사이즈 3");
        
        buttons = new JButton[]{smallButton, mediumButton, largeButton};
        selectedButtonIndex = 0;
        
        for (JButton button : buttons) {
        	applyButtonStyle(button);
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);
        
        smallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(400, 550);
                adjustComponentSizes(1.0);
                revalidate();
            }
        });
        
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(440, 605);
                adjustComponentSizes(1.1);
                revalidate();
            }
        });
        
        largeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(480, 660);
                adjustComponentSizes(1.2);
                revalidate();
            }
        });
        
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
    
    private void adjustComponentSizes(double scaleFactor) {
        int titleLabelFontSize = 30;
        int buttonFontSize = 18;

        // titleLabel 폰트 크기 조정
        int adjustedTitleLabelFontSize = (int) (titleLabelFontSize * scaleFactor);
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, adjustedTitleLabelFontSize));

        // 버튼 폰트 크기 조정
        int adjustedButtonFontSize = (int) (buttonFontSize * scaleFactor);
        for (JButton button : buttons) {
            button.setFont(new Font("NanumGothic", Font.BOLD, adjustedButtonFontSize));
        }
    }
}