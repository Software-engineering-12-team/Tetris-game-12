package component.setting;

import javax.swing.*;
import java.awt.*;


public class SettingElement extends JFrame {
    private JLabel titleLabel;
    private JButton sizeAdjustButton, controlKeyButton, scoreboardButton, colorBlindModeButton, resetSettingButton;
    private JButton[] buttons;
    private int selectedButtonIndex;

    // 버튼 스타일 적용 메소드
    private void applyButtonStyle(JButton button) {
        button.setFont(new Font("NanumGothic", Font.BOLD, 18)); // 폰트 설정
        button.setBackground(new Color(30, 60, 90)); // 배경색 설정
        button.setForeground(Color.WHITE); // 글씨색 설정
        button.setFocusPainted(false); // 포커스 표시 제거
    }

    // 선택된 버튼 스타일 업데이트 메소드
    private void updateButtonStyles() {
        for (int i = 0; i < buttons.length; i++) {
            if (i == selectedButtonIndex) {
                buttons[i].setBackground(new Color(120, 150, 180)); // 선택된 버튼 색 변경
            } else {
                buttons[i].setBackground(new Color(30, 60, 90)); // 나머지 버튼 색 유지
            }
        }
    }

    public SettingElement() {
        setTitle("설정"); // 창 제목 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false); // 창 크기 조절 비활성화
        setSize(400, 550); // 창 크기 설정
        setLocationRelativeTo(null); // 창을 화면 가운데에 위치시킴

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30)); // 타이틀 라벨 폰트 설정
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
        panel.add(titleLabel, BorderLayout.NORTH);

        // 버튼 생성 및 스타일 적용
        sizeAdjustButton = new JButton("크기 조절");
        controlKeyButton = new JButton("조작키 설정");
        scoreboardButton = new JButton("스코어보드");
        colorBlindModeButton = new JButton("색맹 모드");
        resetSettingButton = new JButton("설정 초기화");

        applyButtonStyle(sizeAdjustButton);
        applyButtonStyle(controlKeyButton);
        applyButtonStyle(scoreboardButton);
        applyButtonStyle(colorBlindModeButton);
        applyButtonStyle(resetSettingButton);

        buttons = new JButton[]{sizeAdjustButton, controlKeyButton, scoreboardButton, colorBlindModeButton, resetSettingButton};
        selectedButtonIndex = 0; // 기본 선택 버튼 인덱스

        // 버튼 액션 리스너 설정 부분은 기능 구현에 따라 추가

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 0, 15)); // 5개의 버튼을 위한 그리드 레이아웃
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // 패널 여백 설정

        for (JButton button : buttons) {
            buttonPanel.add(button); // 버튼들을 패널에 추가
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel); // 메인 패널을 프레임에 추가

        setVisible(true); // 창을 보이게 설정
    }
}
