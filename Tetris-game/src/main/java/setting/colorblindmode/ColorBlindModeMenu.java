package main.java.setting.colorblindmode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ColorBlindModeMenu extends JFrame {
    private JLabel titleLabel;

    public ColorBlindModeMenu() {
        setTitle("색맹 모드");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("색맹 모드");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

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

        add(panel);

        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
    }

}
