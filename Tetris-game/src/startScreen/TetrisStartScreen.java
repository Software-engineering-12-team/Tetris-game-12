package startScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisStartScreen extends JFrame {
    private JButton startButton, settingsButton, scoreboardButton, exitButton;
    
    private void applyButtonStyle(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(30, 60, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    public TetrisStartScreen() {
        setTitle("테트리스 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("테트리스 게임");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        panel.add(titleLabel, BorderLayout.NORTH);

        startButton = new JButton("게임 시작");
        settingsButton = new JButton("설정");
        scoreboardButton = new JButton("스코어보드");
        exitButton = new JButton("게임 종료");

        // 각 버튼에 동일한 스타일 적용
        applyButtonStyle(startButton);
        applyButtonStyle(settingsButton);
        applyButtonStyle(scoreboardButton);
        applyButtonStyle(exitButton);
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 프로그램 종료
                System.exit(0);
            }
        });

        // 버튼 패널 생성 및 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(scoreboardButton);
        buttonPanel.add(exitButton);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        add(panel);
        
        // 프레임 크기 설정 및 가운데 정렬
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisStartScreen();
            }
        });
    }
}
