package main.java.setting.controlkeysetting;

import javax.swing.*;

import main.java.setting.SettingFileWriter;
import main.java.setting.SettingMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;

import java.awt.*;
import java.awt.event.*;

public class ControlKeySettingMenu extends JFrame {
    private JLabel titleLabel, keyLabel;
    public JLabel[] labels;
    private JButton backButton;
    public JButton[] buttons;
    private JLabel descriptionTitleLabel, descriptionLabel;
    private int selectedButtonIndex;
    public boolean isBackButton;

    private static JLabel statusLabel;
    private static boolean isSetTypeA = true; // default
    private static boolean isSetTypeB = false;
    private static boolean isSetTypeC = false;
    private static boolean isSetUserControlKey = false;
    public static void setTypeA() {
        isSetTypeA = true;
        isSetTypeB = false;
        isSetTypeC = false;
        isSetUserControlKey = false;
        currentSelectedType = ControlKeyType.TYPE_A;
    }

    public static void setTypeB() {
        isSetTypeA = false;
        isSetTypeB = true;
        isSetTypeC = false;
        isSetUserControlKey = false;
        currentSelectedType = ControlKeyType.TYPE_B;

    }


    private static ControlKeyType selectedType;
    private static ControlKeyType currentSelectedType = ControlKeyType.TYPE_A;
    private void updateStatusLabelBasedOnCurrentType() {
        switch (currentSelectedType) {
            case TYPE_A:
                statusLabel.setText("현재 모드: 타입 A");
                break;
            case TYPE_B:
                statusLabel.setText("현재 모드: 타입 B");
                break;
        }
    }

    public static void setType(ControlKeyType type) {
        selectedType = type;
    }

    public static int getTypeValue() {
        return selectedType.getValue();
    }

    public static void setTypeValue(int value) {
        switch (value) {
            case 0:
                selectedType = ControlKeyType.TYPE_A;
                break;
            case 1:
                selectedType = ControlKeyType.TYPE_B;
                break;
            default:
                // 기본값으로 TYPE_A를 설정합니다.
                selectedType = ControlKeyType.TYPE_A;
                break;
        }
    }

    public static JButton selectedButton;

    public static void updateStatus(String status) {
        if (statusLabel != null) {
            statusLabel.setText(status);
        }
    }

    private int customControlKey = -1; // 사용자 정의 조작키를 저장할 변수
    // 현재 모드 상태 업데이트 메소드


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

    public ControlKeySettingMenu() {
        setTitle("조작키 설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 타이틀 레이블 생성 및 설정
        titleLabel = new JLabel("조작키 설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        labels = new JLabel[]{titleLabel};

        // 조작키 타입 선택을 위한 버튼 생성 및 설정
        JButton typeAButton = new JButton("타입 A");
        JButton typeBButton = new JButton("타입 B");

        typeAButton.addActionListener(e -> setTypeA());
        typeBButton.addActionListener(e -> setTypeB());

        // 버튼을 패널에 추가하기 위한 새로운 JPanel 인스턴스 생성
        JPanel typePanel = new JPanel(new GridLayout(2, 1, 5, 5)); // GridLayout을 사용해 버튼을 세로로 배치
        typePanel.add(typeAButton);
        typePanel.add(typeBButton);

        // 기존 패널에 타입 선택 패널 추가
        panel.add(typePanel, BorderLayout.EAST);
        typePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 타입 선택 패널의 여백 설정


        // 상세설명 패널
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setFocusable(true); // 버튼에 포커스 설정
        descriptionPanel.requestFocusInWindow(); // 포커스 설정
        panel.add(descriptionPanel);  // 패널에 포커스 설정된 버튼 추가
        add(panel);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20)); // 패널의 여백 설정
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // 타입 선택 패널의 여백 설정

        // 각각 레이아웃 분리
        statusLabel = new JLabel("현재 모드: 타입 A");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionPanel.add(statusLabel);

        descriptionTitleLabel = new JLabel("조작키 설명이 아래에 표시됩니다.", SwingConstants.CENTER);
        descriptionTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionPanel.add(descriptionTitleLabel);
        descriptionTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel descriptionLabel = new JLabel("조작키 설명이 여기에 표시됩니다.", SwingConstants.CENTER);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionPanel.add(descriptionLabel);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        selectedButton = new JButton("선택");
        selectedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionPanel.add(selectedButton);

        // 각 버튼의 액션 리스너 내에서 descriptionLabel의 텍스트 업데이트
        typeAButton.addActionListener(e -> {
            descriptionTitleLabel.setText("타입 A 조작키 설정");
            setType(ControlKeyType.TYPE_A);
            descriptionLabel.setText("<html><body style='text-align: left;'>" +
                    "<b>기본키</b><br><br>" +
                    "esc : 뒤로가기<br>" +
                    "p : paused<br>" +
                    "q : 한번에 아래로 이동하기<br><br><hr><br>" +
                    "<b>블럭이동키</b><br><br>" +
                    "← : 왼쪽으로 이동<br>" +
                    "→ : 오른쪽으로 이동<br>" +
                    "↓ : 아래로 이동<br>" +
                    "↑ : 회전<br>" +
                    "</body></html>");
            descriptionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        });
        typeBButton.addActionListener(e -> {
            descriptionTitleLabel.setText("타입 B 조작키 설정");
            setType(ControlKeyType.TYPE_B);
            descriptionLabel.setText("<html><body style='text-align: left;'>" +
                    "<b>기본키</b><br><br>" +
                    "esc : 뒤로가기<br>" +
                    "p : paused<br>" +
                    "q : 한번에 아래로 이동하기<br><br><hr><br>" +
                    "<b>블럭이동키</b><br><br>" +
                    "A : 왼쪽으로 이동<br>" +
                    "D : 오른쪽으로 이동<br>" +
                    "S : 아래로 이동<br>" +
                    "W : 회전<br>" +
                    "</body></html>");
            descriptionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        });

        selectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (getTypeValue()) {
                    case 0:
                        setType(ControlKeyType.TYPE_A);
                        updateStatus("현재 모드: 타입 A");
                        setTypeValue(0);
                        break;
                    case 1:
                        setType(ControlKeyType.TYPE_B);
                        updateStatus("현재 모드: 타입 B");
                        setTypeValue(1);
                        break;
                    default:
                        break;
                }
                updateStatus(statusLabel.getText()); // 현재 모드 라벨 업데이트
            }
        });

        updateStatusLabelBasedOnCurrentType();

        // 뒤로가기 버튼 생성 및 이벤트 처리
        backButton = new JButton("뒤로가기");
        buttons = new JButton[]{backButton};
        selectedButtonIndex = 0;
        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
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
        panel.add(backButton, BorderLayout.SOUTH); // 뒤로가기 버튼을 패널의 SOUTH에 추가


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