package main.java.menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import main.java.setting.SettingFileWriter;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardMenu extends JFrame {
	private JLabel nameLabel, difficultyLabel, modeLabel, scoreLabel, titleLabel;
    public JLabel[] labels;
    private JTable scoreTable;
    private static DefaultTableModel tableModel;
    private JList<String> scoreList;
    private static DefaultListModel<String> scoreModel;
    private JButton backButton;
    public JButton[] buttons;
    public boolean isBackButton;
    private int lastAddedRowIndex = -1; // 최근에 추가된 행의 인덱스를 저장할 변수
    private final Color highlightColor = new Color(130, 200, 255); // 강조할 색상 정의

    public ScoreBoardMenu() {
        setTitle("스코어보드");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("스코어보드");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        labels = new JLabel[]{titleLabel};
        
        // 스코어 테이블 설정        
        tableModel = new DefaultTableModel();
        tableModel.addColumn("이름");
        tableModel.addColumn("난이도");
        tableModel.addColumn("모드");
        tableModel.addColumn("점수");

        scoreTable = new JTable(tableModel);
        scoreTable.setFont(new Font("NanumGothic", Font.BOLD, 14));
        scoreTable.setRowHeight(30); // 모든 행의 높이를 30으로 설정
        
        JTableHeader header = scoreTable.getTableHeader();
        header.setFont(new Font("NanumGothic", Font.BOLD, 16));
        
        // 점수 리스트 초기화
        scoreModel = new DefaultListModel<>();
        scoreList = new JList<>(scoreModel);
        scoreList.setFont(new Font("NanumGothic", Font.BOLD, 14));

        // 점수 리스트를 스크롤 패널에 추가
        JScrollPane tableScrollPane = new JScrollPane(scoreTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        // 점수 추가 메서드를 위한 라벨
        nameLabel = new JLabel();
        difficultyLabel = new JLabel();
        modeLabel = new JLabel();
        scoreLabel = new JLabel();
        
        // 뒤로가기 버튼 생성 및 이벤트 처리
        backButton = new JButton("뒤로가기");
        buttons = new JButton[]{backButton};
        
        isBackButton = true;
        ButtonStyle.applyButtonStyle(buttons, isBackButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                HandleKeyEvent.selectedButtonIndex = 0;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	StartMenu StartMenu = new StartMenu();
                    	StartMenu.setSize(getSize());
                        ScreenAdjustComponent.sizeAdjust(StartMenu.labels, StartMenu.buttons, StartMenu.isBackButton, SettingFileWriter.readSize());
                    	StartMenu.setVisible(true);
                    }
                });
            }
        });
        panel.add(backButton, BorderLayout.SOUTH); // 뒤로가기 버튼을 패널의 SOUTH에 추가

        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	HandleKeyEvent.handleKeyEvent(e, buttons, isBackButton);
            }
        });
        setFocusable(true);
        

        loadScores(); // 스코어보드가 열릴 때마다 저장된 점수를 읽기
        
        add(panel);
    }
    
    private void loadScores() {
        List<ScoreEntry> scoreEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("scoreboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int nameIndex = line.indexOf("이름:");
                int difficultyIndex = line.indexOf("난이도:");
                int modeIndex = line.indexOf("모드:");
                int scoreIndex = line.indexOf("점수:");
                if (nameIndex != -1 && difficultyIndex != -1 && modeIndex != -1 && scoreIndex != -1) {
                    String name = line.substring(nameIndex + 4, line.indexOf(" ", nameIndex + 4)).trim();
                    String difficulty = line.substring(difficultyIndex + 5, line.indexOf(" ", difficultyIndex + 5)).trim();
                    String mode = line.substring(modeIndex + 4, line.indexOf(" ", modeIndex + 4)).trim();
                    int score = Integer.parseInt(line.substring(scoreIndex + 4, line.indexOf("점", scoreIndex + 4)).trim());
                    scoreEntries.add(new ScoreEntry(name, difficulty, mode, score));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "스코어보드를 로드하는 중에 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }

        Collections.sort(scoreEntries);

        for (ScoreEntry entry : scoreEntries) {
            Object[] rowData = {entry.getName(), entry.getDifficulty(), entry.getMode(), entry.getScore()};
            tableModel.addRow(rowData);
            
        }

        // 새로운 점수가 추가되었을 때 해당 행을 강조
        if (lastAddedRowIndex != -1) {
            scoreTable.getSelectionModel().setSelectionInterval(lastAddedRowIndex, lastAddedRowIndex);
            scoreTable.setSelectionBackground(highlightColor);
        }
        
        // 모델 초기화
        scoreModel.clear(); // 기존 점수를 모두 지우기
    }

    // 새로운 점수를 추가하는 메서드
    public void addScore(ScoreEntry entry) {
        // 테이블 모델에 행 추가
        Object[] rowData = {entry.getName(), entry.getDifficulty(), entry.getMode(), entry.getScore()};
        tableModel.insertRow(0, rowData);

        // 새로 추가된 행의 인덱스를 저장
        lastAddedRowIndex = 0;

        // 새로 추가된 행을 깜빡거리게 만들기 위한 Timer 설정
        Timer timer = new Timer(475, new ActionListener() {
            private boolean highlighted = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (highlighted) {
                    // 강조 제거
                    scoreTable.removeRowSelectionInterval(0, 0);
                } else {
                    // 강조 추가
                    scoreTable.addRowSelectionInterval(0, 0);
                    scoreTable.setSelectionBackground(highlightColor);
                }
                highlighted = !highlighted;
            }
        });
        timer.start();
    }
    
    public void clearScores() {
        tableModel.setRowCount(0); // 테이블 모델의 행을 모두 지우기
        scoreModel.clear(); // 리스트 모델의 모든 요소를 지우기
        
        JOptionPane.showMessageDialog(ScoreBoardMenu.this, "스코어보드가 초기화되었습니다.", "초기화 완료", JOptionPane.INFORMATION_MESSAGE);
        // 텍스트 파일 비우기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scoreboard.txt"))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace(); // 오류 발생 시 출력
        }
    }

}