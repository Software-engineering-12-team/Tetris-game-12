package main.java.setting.screenadjustsize;

import main.java.setting.SettingFileWriter;
import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus;
import static main.java.setting.colorblindmode.ColorBlindModeMenu.colorBlindStatus;

public class ScreenAdjustSizeMenu extends JFrame {
    private JLabel titleLabel;
    public JLabel[] labels;
    private JButton smallButton, mediumButton, largeButton, backButton;
    public JButton[] buttons;
	public static int size;
    public boolean isBackButton;

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
        
        smallButton = new JButton("small");
        mediumButton = new JButton("medium");
        largeButton = new JButton("large");
        
        backButton = new JButton("뒤로가기");
        
        buttons = new JButton[]{smallButton, mediumButton, largeButton, backButton};
        
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
            	size = 0;
            	setSize(400, 550);
            	ScreenAdjustComponent.sizeAdjust(labels, buttons, isBackButton, size);
            	controlKeyStatus = SettingFileWriter.readControlKey();
                colorBlindStatus = SettingFileWriter.readBlindMode();
            	SettingFileWriter.clearSetting();
            	SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });
        
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	size = 1;
            	setSize(440, 605);
            	ScreenAdjustComponent.sizeAdjust(labels, buttons, isBackButton, size);
            	controlKeyStatus = SettingFileWriter.readControlKey();
                colorBlindStatus = SettingFileWriter.readBlindMode();
            	SettingFileWriter.clearSetting();
            	SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });
        
        largeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	size = 2;
            	setSize(480, 660);
            	ScreenAdjustComponent.sizeAdjust(labels, buttons, isBackButton, size);
            	controlKeyStatus = SettingFileWriter.readControlKey();
                colorBlindStatus = SettingFileWriter.readBlindMode();
            	SettingFileWriter.clearSetting();
            	SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
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
                        SettingMenu settingMenu = new SettingMenu();
                        settingMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(settingMenu.labels, settingMenu.buttons, settingMenu.isBackButton, SettingFileWriter.readSize());
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
            	HandleKeyEvent.handleKeyEvent(e, buttons, isBackButton);
            }
        });
        setFocusable(true);
    }
}