package main.java.scoreboard;

import javax.swing.*;
import java.awt.*;

public class ScoreBoardMenu extends JFrame {
    private JList<String> scoreList;
    private DefaultListModel<String> scoreModel;

    public ScoreBoardMenu() {
        setTitle("스코어보드");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // 점수 모델과 리스트 초기화
        scoreModel = new DefaultListModel<>();
        scoreList = new JList<>(scoreModel);
        scoreList.setFont(new Font("NanumGothic", Font.BOLD, 14));

            // 점수 리스트를 스크롤 패널에 추가
        JScrollPane scrollPane = new JScrollPane(scoreList);
        add(scrollPane, BorderLayout.CENTER);

            // 임시로 몇 개의 점수 추가
        addScore("Player1: 100");
        addScore("Player2: 200");
        addScore("Player3: 300");

        setVisible(true);
    }

    // 점수 추가 메서드
    public void addScore(String score) {
        scoreModel.addElement(score);
    }



    public static void main(String[] args) {
        // 스윙 유틸리티를 사용하여 스코어보드 실행
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScoreBoardMenu();
            }
        });
    }
}

