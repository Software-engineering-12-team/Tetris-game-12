package main.java.scoreboard;

import javax.swing.*;
import java.awt.*;

public class ScoreBoardMenu extends JFrame {
    private JLabel titleLabel;
    private JList<String> scoreList;
    private DefaultListModel<String> scoreModel;

    public ScoreBoardMenu() {
        setTitle("스코어보드");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("스코어보드");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // 점수 리스트 초기화
        scoreModel = new DefaultListModel<>();
        scoreList = new JList<>(scoreModel);
        scoreList.setFont(new Font("NanumGothic", Font.BOLD, 14));

        // 점수 리스트를 스크롤 패널에 추가
        JScrollPane scrollPane = new JScrollPane(scoreList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 임시로 몇 개의 점수 추가
        addScore("Player1: 100");
        addScore("Player2: 200");
        addScore("Player3: 300");

        getContentPane().add(panel); // 패널을 프레임에 추가

        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 점수 추가 메서드
    public void addScore(String score) {
        scoreModel.addElement(score);
    }



}
