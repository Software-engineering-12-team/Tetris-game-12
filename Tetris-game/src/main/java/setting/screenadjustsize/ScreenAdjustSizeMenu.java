package main.java.setting.screenadjustsize;

import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenAdjustSizeMenu extends JFrame {
    private JLabel titleLabel;
    private JLabel[] labels;
    private JButton smallButton, mediumButton, largeButton, backButton;
    private JButton[] buttons;
    private int selectedButtonIndex;
    private boolean isBackButton;
    
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

    public ScreenAdjustSizeMenu() {
        setTitle("크기 조정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("크기 조정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        labels = new JLabel[]{titleLabel};
        
        smallButton = new JButton("사이즈 1");
        mediumButton = new JButton("사이즈 2");
        largeButton = new JButton("사이즈 3");
        
        backButton = new JButton("뒤로가기");
        
        buttons = new JButton[]{smallButton, mediumButton, largeButton, backButton};
        selectedButtonIndex = 0;
        
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
        
        smallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(400, 550);
                ScreenAdjustComponent.adjustLabelSize(labels, 1.0);
                ScreenAdjustComponent.adjustButtonSize(buttons, 1.0);
                revalidate();
            }
        });
        
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(440, 605);
                ScreenAdjustComponent.adjustLabelSize(labels, 1.1);
                ScreenAdjustComponent.adjustButtonSize(buttons, 1.1);
                revalidate();
            }
        });
        
        largeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(480, 660);
                ScreenAdjustComponent.adjustLabelSize(labels, 1.2);
                ScreenAdjustComponent.adjustButtonSize(buttons, 1.2);
                revalidate();
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
                        SettingMenu settingMenu = new SettingMenu();
                        settingMenu.setVisible(true);
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