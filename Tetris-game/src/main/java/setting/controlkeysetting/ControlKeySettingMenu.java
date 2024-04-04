package main.java.setting.controlkeysetting;

import javax.swing.*;

import main.java.setting.SettingMenu;
import main.java.setting.screenadjustsize.ScreenAdjustSizeMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;

import java.awt.*;
import java.awt.event.*;

public class ControlKeySettingMenu extends JFrame {
    private JLabel titleLabel, keyLabel;
    public JLabel[] labels;
    private JButton backButton;
    public JButton[] buttons;
    private int selectedButtonIndex;
    public boolean isBackButton;

    private void setTypeA() {
        // 타입 A에 대한 조작키 설정 로직
        System.out.println("타입 A 조작키 설정");
    }

    private void setTypeB() {
        // 타입 B에 대한 조작키 설정 로직
        System.out.println("타입 B 조작키 설정");
    }

    private void setTypeC() {
        // 타입 C에 대한 조작키 설정 로직
        System.out.println("타입 C 조작키 설정");
    }

    private void setUserControlKey() {
        // 사용자 정의 조작키 설정 로직
        System.out.println("사용자 정의 조작키 설정");
    }
    private int customControlKey = -1; // 사용자 정의 조작키를 저장할 변수



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

        keyLabel = new JLabel("조작키를 누르세요");
        keyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(keyLabel, BorderLayout.CENTER);




        // 조작키 타입 선택을 위한 버튼 생성 및 설정
        JButton typeAButton = new JButton("타입 A");
        JButton typeBButton = new JButton("타입 B");
        JButton typeCButton = new JButton("타입 C");
        JButton typesetUserControlKeyButton = new JButton("사용자 정의 설정");


        typeAButton.addActionListener(e -> setTypeA());
        typeBButton.addActionListener(e -> setTypeB());
        typeCButton.addActionListener(e -> setTypeC());
        typesetUserControlKeyButton.addActionListener(e -> setUserControlKey());

        // 버튼을 패널에 추가하기 위한 새로운 JPanel 인스턴스 생성
        JPanel typePanel = new JPanel(new GridLayout(4, 1, 5, 5)); // GridLayout을 사용해 버튼을 세로로 배치
        typePanel.add(typeAButton);
        typePanel.add(typeBButton);
        typePanel.add(typeCButton);
        typePanel.add(typesetUserControlKeyButton);

        // 기존 패널에 타입 선택 패널 추가
        panel.add(typePanel, BorderLayout.EAST);
        typePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 타입 선택 패널의 여백 설정


        // 사용자 정의 조작키 설정 버튼 이벤트 처리
        typesetUserControlKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyLabel.setText("사용자 정의 조작키를 입력하세요...");

                // 키 리스너를 임시로 추가하여 다음 키 입력을 캡처
                typesetUserControlKeyButton.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        customControlKey = e.getKeyCode(); // 사용자가 입력한 키 저장
                        String keyText = KeyEvent.getKeyText(customControlKey);
                        keyLabel.setText("설정된 사용자 정의 조작키: " + keyText);

                        // 리스너를 제거하여 더 이상의 입력을 받지 않도록 함
                        typesetUserControlKeyButton.removeKeyListener(this);
                    }
                });
            }
        });

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
                        ScreenAdjustComponent.sizeAdjust(settingMenu.labels, settingMenu.buttons, settingMenu.isBackButton, ScreenAdjustSizeMenu.size);
                        settingMenu.setVisible(true);
                    }
                });
            }
        });
        panel.add(backButton, BorderLayout.SOUTH); // 뒤로가기 버튼을 패널의 SOUTH에 추가


        // 박스
        JButton dummyButton = new JButton();
        dummyButton.setFocusable(true); // 버튼에 포커스 설정
        dummyButton.requestFocusInWindow(); // 포커스 설정
        panel.add(dummyButton);  // 패널에 포커스 설정된 버튼 추가
        add(panel);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20)); // 패널의 여백 설정

        // 사이즈
        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}

