package main.java.setting.controlkeysetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlKeySettingMenu extends JFrame {
    private JLabel titleLabel;
    private JLabel keyLabel;

    public ControlKeySettingMenu() {
        setTitle("조작키 설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("조작키 설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        keyLabel = new JLabel("조작키를 누르세요");
        keyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(keyLabel, BorderLayout.CENTER);

        // 키 이벤트를 처리할 버튼 생성
        JButton dummyButton = new JButton();
        dummyButton.setFocusable(true); // 버튼에 포커스 설정
        dummyButton.requestFocusInWindow(); // 포커스 설정

        // 키 리스너 등록
        dummyButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 사용되지 않음
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                String keyText = KeyEvent.getKeyText(keyCode);
                keyLabel.setText("조작키: " + keyText);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // 사용되지 않음
            }
        });

        // 뒤로가기 버튼 생성 및 이벤트 처리
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                // 뒤로가기 버튼에 대한 동작 추가 (예: 이전 화면으로 이동)
            }
        });
        panel.add(backButton, BorderLayout.SOUTH); // 뒤로가기 버튼을 패널의 SOUTH에 추가

        // 패널에 포커스 설정된 버튼 추가
        panel.add(dummyButton);

        add(panel);

        setSize(400, 550);
        setVisible(true);
        setFocusable(true);
    }

}
