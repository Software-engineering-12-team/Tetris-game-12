package main.java.setting.controlkeysetting;

import javax.swing.*;

import main.java.setting.SettingFileWriter;
import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;

import java.awt.*;
import java.awt.event.*;

import static main.java.setting.colorblindmode.ColorBlindModeMenu.colorBlindStatus;
import static main.java.setting.screenadjustsize.ScreenAdjustSizeMenu.size;

public class ControlKeySettingMenu extends JFrame {
    private JLabel titleLabel, subLabel, lowLabel;
    public JLabel[] labels;
    private static JButton typeAButton, typeBButton, backButton;
    public JButton[] buttons;
    private int selectedButtonIndex;
    public boolean isBackButton;
    public static String controlKeyStatus = "타입A";
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

    private void initComponents() {
        titleLabel = new JLabel("조작키 설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subLabel = new JLabel("q : 한번에 떨어지기, p : 일시정지, esc : 종료");
        subLabel.setFont(new Font("NanumGothic", Font.BOLD, 20));
        subLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lowLabel = new JLabel("블럭을 이동시킬 조작키를 아래에서 선택해주세요");
        lowLabel.setFont(new Font("NanumGothic",Font.ITALIC, 10));
        lowLabel.setHorizontalAlignment(SwingConstants.CENTER);

        labels = new JLabel[]{titleLabel, subLabel, lowLabel};

        typeAButton = new JButton("WASD키");
        typeBButton = new JButton("방향키 \\u2190 / ! \\u2192 /  \\u2191 /  \\u2193");

        backButton = new JButton("뒤로가기");

        buttons = new JButton[]{typeAButton, typeBButton, backButton};
        selectedButtonIndex = 0;

        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
    }
    private void layoutComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(3, 1, 10, 15));

        for (JLabel label : labels) {
            labelPanel.add(label);
        }
        panel.add(labelPanel, BorderLayout.NORTH);
        // 버튼을 패널에 추가하기 위한 새로운 JPanel 인스턴스 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 5, 20)); // GridLayout을 사용해 버튼을 세로로 배치
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 40, 30));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);

        panel.add(backButton, BorderLayout.SOUTH);
    }
    private void addEventListeners(){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
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
        typeAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlKeyStatus = "타입A";
                updateControlKeyModeUI(controlKeyStatus);
                size = SettingFileWriter.readSize();
                colorBlindStatus = SettingFileWriter.readBlindMode();
                SettingFileWriter.clearSetting();
                SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });

        typeBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlKeyStatus = "타입B";
                updateControlKeyModeUI(controlKeyStatus);
                size = SettingFileWriter.readSize();
                colorBlindStatus = SettingFileWriter.readBlindMode();
                SettingFileWriter.clearSetting();
                SettingFileWriter.writeSetting(size, controlKeyStatus, colorBlindStatus);
            }
        });
    }

    public static void updateControlKeyModeUI(String controlKeyStatus) {
        switch (controlKeyStatus) {
            case "타입A":
                typeAButton.setBackground(new Color(30, 90, 100));
                typeAButton.setText("WASD키 선택됨");

                typeBButton.setBackground(new Color(30, 60, 90));
                typeBButton.setText("방향키 ↑ / ↓ / ← / →");
                break;
            case "타입B":
                typeBButton.setBackground(new Color(30, 90, 100));
                typeBButton.setText("방향키 ↑ / ↓ / ← / → 선택됨");

                typeAButton.setBackground(new Color(30, 60, 90));
                typeAButton.setText("WASD키");
                break;
        }
    }

    public ControlKeySettingMenu() {
        setTitle("조작키 설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents();
        layoutComponents();
        addEventListeners();
        controlKeyStatus = SettingFileWriter.readControlKey();
        updateControlKeyModeUI(controlKeyStatus);
        pack();

        // 사이즈
        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);

        // 조작키로 버튼 선택 이벤트 핸들러
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e);
            }
        });
        setFocusable(true);
    }

}