package main.java.menu;

import javax.swing.*;

import main.java.setting.screenadjustsize.ScreenAdjustSizeMenu;
import main.java.util.ButtonStyle;
import main.java.util.ScreenAdjustComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardMenu extends JFrame {
    
	private JLabel nameLabel;
    private JLabel difficultyLabel;
    private JLabel modeLabel;
    private JLabel scoreLabel;
	private JLabel titleLabel;
    public JLabel[] labels;
    private JList<String> scoreList;
    private DefaultListModel<String> scoreModel;
    private JButton backButton;
    public JButton[] buttons;
    private int selectedButtonIndex;
    public boolean isBackButton;
    
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

    public ScoreBoardMenu() {
        setTitle("스코어보드");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        nameLabel = new JLabel();
        nameLabel.setFont(new Font("NanumGothic", Font.BOLD, 14));

        difficultyLabel = new JLabel();
        difficultyLabel.setFont(new Font("NanumGothic", Font.BOLD, 14));

        modeLabel = new JLabel();
        modeLabel.setFont(new Font("NanumGothic", Font.BOLD, 14));

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("NanumGothic", Font.BOLD, 14));

        titleLabel = new JLabel("스코어보드");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        labels = new JLabel[]{titleLabel};
        
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
                    	StartMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(StartMenu.labels, StartMenu.buttons, StartMenu.isBackButton, ScreenAdjustSizeMenu.size);
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
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e);
            }
        });
        setFocusable(true);
        

        loadScores(); // 스코어보드가 열릴 때마다 저장된 점수를 읽기
    }
    
    private void loadScores() {
        scoreModel.clear(); // 기존 점수를 모두 지우기

        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("scoreboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scoreModel.addElement(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "스코어보드를 로드하는 중에 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
       
        // 내림차순으로 정렬.
        Collections.sort(scores);

        // 정렬된 점수를 모델에 추가
        for (String score : scores) {
            scoreModel.addElement(score);
        }
    }

    // 점수 추가 메서드
    public void addScore(String name, String difficulty, String mode, int score) {
    	nameLabel.setText("이름: " + name);
        difficultyLabel.setText("난이도: " + difficulty);
        modeLabel.setText("모드: " + mode);
        scoreLabel.setText("점수: " + score + "점");
        String entry = "이름: " + name + " / 난이도: " + difficulty + " / 모드: " + mode + " / 점수: " + score + "점";
        scoreModel.addElement(entry);
    }

}
