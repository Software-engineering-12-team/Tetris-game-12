package main.java.setting.colorblindmode;

import main.java.setting.SettingFileWriter;
import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus;


public class ColorBlindModeMenu extends JFrame {
    public JLabel titleLabel;
    public JLabel[] labels;
    public static JButton normalButton, redGreenBlindButton, blueYellowBlindButton, backButton;
    public JButton[] buttons;
    public static int size;
    public boolean isBackButton;

    public static String colorBlindStatus = "정상";

    public void initComponents() {
        titleLabel = new JLabel("색맹 모드 선택");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        labels = new JLabel[]{titleLabel};

        normalButton = new JButton("정상 모드");
        redGreenBlindButton = new JButton("적녹색맹 모드");
        blueYellowBlindButton = new JButton("청황색맹 모드");

        backButton = new JButton("뒤로가기");

        buttons = new JButton[]{normalButton, redGreenBlindButton, blueYellowBlindButton, backButton};

        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
    }



    private void layoutComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);

        panel.add(backButton, BorderLayout.SOUTH);
    }
    private void addEventListeners(){
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
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindStatus = "정상";
                updateColorBlindModeUI(colorBlindStatus);
                size = SettingFileWriter.readSize();
                controlKeyStatus = SettingFileWriter.readControlKey();
                SettingFileWriter.clearSetting();
                SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });
        redGreenBlindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindStatus = "적녹색맹";
                updateColorBlindModeUI(colorBlindStatus);
                size = SettingFileWriter.readSize();
                controlKeyStatus = SettingFileWriter.readControlKey();
                SettingFileWriter.clearSetting();
                SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });
        blueYellowBlindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorBlindStatus = "청황색맹";
                updateColorBlindModeUI(colorBlindStatus);
                size = SettingFileWriter.readSize();
                controlKeyStatus = SettingFileWriter.readControlKey();
                SettingFileWriter.clearSetting();
                SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });
    }
    public static void updateColorBlindModeUI(String colorBlindStatus) {
        switch (colorBlindStatus) {
            case "정상":
                normalButton.setBackground(new Color(30, 90, 100));
                normalButton.setText("정상 모드 선택됨");

                redGreenBlindButton.setBackground(new Color(30, 60, 90));
                redGreenBlindButton.setText("적녹색맹 모드");

                blueYellowBlindButton.setBackground(new Color(30, 60, 90));
                blueYellowBlindButton.setText("청황색맹 모드");
                break;
            case "적녹색맹":
                redGreenBlindButton.setBackground(new Color(30, 90, 100));
                redGreenBlindButton.setText("적녹색맹 모드 선택됨");

                normalButton.setBackground(new Color(30, 60, 90));
                normalButton.setText("정상 모드");

                blueYellowBlindButton.setBackground(new Color(30, 60, 90));
                blueYellowBlindButton.setText("청황색맹 모드");
                break;
            case "청황색맹":
                blueYellowBlindButton.setBackground(new Color(30, 90, 100));
                blueYellowBlindButton.setText("청황색맹 모드 선택됨");

                normalButton.setBackground(new Color(30, 60, 90));
                normalButton.setText("정상 모드");

                redGreenBlindButton.setBackground(new Color(30, 60, 90));
                redGreenBlindButton.setText("적녹색맹 모드");
                break;
        }
    }
    
    public ColorBlindModeMenu() {
        setTitle("색맹 모드 선택");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);
        initComponents();
        layoutComponents();
        addEventListeners();
        colorBlindStatus = SettingFileWriter.readBlindMode();
        updateColorBlindModeUI(colorBlindStatus);
        pack();

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
