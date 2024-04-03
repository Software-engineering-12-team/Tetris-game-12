package main.java.setting.scoreboard;

import javax.swing.*;

import main.java.menu.StartMenu;
import main.java.util.ButtonStyle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoardMenu extends JFrame {
    private JLabel titleLabel;
    private JList<String> scoreList;
    private DefaultListModel<String> scoreModel;
    private JButton backButton;
    private JButton[] buttons;
    private int selectedButtonIndex;
    private boolean isBackButton;

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
                    	StartMenu StartMenu = new StartMenu();
                    	StartMenu.setVisible(true);
                    }
                });
            }
        });
        panel.add(backButton, BorderLayout.SOUTH); // 뒤로가기 버튼을 패널의 SOUTH에 추가

        getContentPane().add(panel); // 패널을 프레임에 추가
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 점수 추가 메서드
    public void addScore(String score) {
        scoreModel.addElement(score);
    }



}
