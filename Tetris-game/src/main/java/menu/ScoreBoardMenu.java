package main.java.menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
	private JLabel nameLabel, difficultyLabel, modeLabel, scoreLabel, titleLabel;
    public JLabel[] labels;
    private JTable scoreTable;
    private DefaultTableModel tableModel;
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
        
        // 스코어 테이블 초기화
        tableModel = new DefaultTableModel();
        tableModel.addColumn("이름");
        tableModel.addColumn("난이도");
        tableModel.addColumn("모드");
        tableModel.addColumn("점수");

        scoreTable = new JTable(tableModel);
        scoreTable.setFont(new Font("NanumGothic", Font.BOLD, 14));
        
        // 테이블 행 제목 렌더러 설정
        JTableHeader header = scoreTable.getTableHeader();
        header.setFont(new Font("NanumGothic", Font.BOLD, 16)); // 폰트 설정
        header.setBackground(Color.WHITE); // 배경색 설정
        header.setForeground(Color.BLACK); // 전경색 설정


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
        JScrollPane tableScrollPane = new JScrollPane(scoreTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        

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
        
        add(panel);
    }
    
    private void loadScores() {
        List<ScoreEntry> scoreEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("scoreboard.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {            	
                // 각 줄에서 키워드를 찾습니다.
                int nameIndex = line.indexOf("이름:");
                int difficultyIndex = line.indexOf("난이도:");
                int modeIndex = line.indexOf("모드:");
                int scoreIndex = line.indexOf("점수:");
                
                // 모든 키워드가 존재하는지 확인합니다.
                if (nameIndex != -1 && difficultyIndex != -1 && modeIndex != -1 && scoreIndex != -1) {
                    // 키워드를 기준으로 정보를 추출합니다.
                	String name = line.substring(nameIndex + 4, line.indexOf(" ", nameIndex + 4)).trim();
                    String difficulty = line.substring(difficultyIndex + 5, line.indexOf(" ", difficultyIndex + 5)).trim();
                    String mode = line.substring(modeIndex + 4, line.indexOf(" ", modeIndex + 4)).trim();
                    int score = Integer.parseInt(line.substring(scoreIndex + 4, line.indexOf("점", scoreIndex + 4)).trim());
                    
                    // ScoreEntry 객체를 생성하여 리스트에 추가합니다.
                    scoreEntries.add(new ScoreEntry(name, difficulty, mode, score));
                    
                    Object[] rowData = {name, difficulty, mode, score};
                    tableModel.addRow(rowData);
                    
                 // 콘솔에 각 정보를 출력합니다.
                    System.out.println("이름: " + name);
                    System.out.println("난이도: " + difficulty);
                    System.out.println("모드: " + mode);
                    System.out.println("점수: " + score);
                } 
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "스코어보드를 로드하는 중에 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }

        // 내림차순으로 정렬
        Collections.sort(scoreEntries);

        // 모델 초기화
        scoreModel.clear(); // 기존 점수를 모두 지우기

        // 정렬된 점수를 모델에 추가
        for (ScoreEntry entry : scoreEntries) {
            scoreModel.addElement(entry.toString()); // ScoreEntry 객체를 문자열로 변환하여 추가
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
