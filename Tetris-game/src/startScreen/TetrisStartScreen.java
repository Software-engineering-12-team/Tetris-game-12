package startScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisStartScreen extends JFrame {
    private JButton startButton;
    private JButton settingsButton;
    private JButton scoreboardButton;
    private JButton exitButton;

    public TetrisStartScreen() {
        setTitle("테트리스 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("테트리스 게임");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // 내부 여백 조정

        startButton = new JButton("게임 시작");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 게임 시작 버튼을 눌렀을 때 실행되는 코드 작성
            }
        });
        buttonPanel.add(startButton);

        settingsButton = new JButton("설정");
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 18));
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 설정 메뉴 버튼을 눌렀을 때 실행되는 코드 작성
            }
        });
        buttonPanel.add(settingsButton);

        scoreboardButton = new JButton("스코어보드");
        scoreboardButton.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 스코어보드 버튼을 눌렀을 때 실행되는 코드 작성
            }
        });
        buttonPanel.add(scoreboardButton);

        exitButton = new JButton("게임 종료");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 게임 종료 버튼 기능 구현
            }
        });
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setSize(350, 350); // 시작 화면의 크기를 350x350으로 설정
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
