package main.java.menu.gamestart;

import main.java.menu.StartMenu;
import main.java.setting.SettingFileWriter;
import main.java.util.ButtonStyle;
import main.java.util.HandleKeyEvent;
import main.java.util.ScreenAdjustComponent;
import main.java.game.TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameStartMenu extends JFrame {
    private JLabel titleLabel;
    public JLabel[] labels;
    private JButton backButton;
    public JButton[] buttons;
    public static int size;
    public boolean isBackButton;
    private JButton[][] modeButtons;
    private JButton[] selectedButtons;
    private JLabel[] inputLabels;
    private String[] inputNames = {"특수 모드", "게임 모드", "난이도 설정"};
    private String[][] modes = {{"솔로 모드", "대전 모드"}, {"노멀", "타이머", "아이템"}, {"Easy", "Normal", "Hard"}};
    private int[] columnCounts = {2, 3, 3};
    public String[] selectedModes = new String[3]; // 각 입력에 대한 선택된 모드 저장

    public GameStartMenu() {
        setTitle("게임 모드 설정");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 시 동작 설정
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        titleLabel = new JLabel("게임 모드 설정");
        titleLabel.setFont(new Font("NanumGothic", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel innerPanel = new JPanel(new GridLayout(6, 1, 0, 0));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        inputLabels = new JLabel[3];
        modeButtons = new JButton[3][];
        selectedButtons = new JButton[3];

        for (int i = 0; i < 3; i++) {
            JPanel inputPanel = new JPanel(new GridLayout(1, columnCounts[i]));

            inputLabels[i] = new JLabel(inputNames[i]);
            inputLabels[i].setHorizontalAlignment(JLabel.CENTER);
            inputPanel.add(inputLabels[i]);

            modeButtons[i] = new JButton[modes[i].length];
            selectedButtons[i] = null;

            for (int j = 0; j < modes[i].length; j++) {
                modeButtons[i][j] = new JButton(modes[i][j]);
                final int inputIndex = i;
                final int modeIndex = j;
                modeButtons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        selectedButtons[inputIndex] = modeButtons[inputIndex][modeIndex];
                        String selectedMode = modes[inputIndex][modeIndex];
                        selectedModes[inputIndex] = selectedMode; // 선택된 모드를 저장
                        JOptionPane.showMessageDialog(panel, inputNames[inputIndex] + " : '" + selectedMode + "' (이)가 선택되었습니다");
                        checkAndStartGame();
                    }
                });
                inputPanel.add(modeButtons[i][j]);
            }

            innerPanel.add(inputLabels[i]);
            innerPanel.add(inputPanel);
        }

        panel.add(innerPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        add(panel);

        backButton = new JButton("뒤로가기");
        isBackButton = true;

        buttons = getAllButtons();
        ButtonStyle.applyButtonStyle(buttons, isBackButton);

        labels = getAllLabels();
        ScreenAdjustComponent.sizeAdjust(labels, buttons, isBackButton, SettingFileWriter.readSize());

        // 뒤로가기 버튼 생성 및 이벤트 처리
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

        // 프레임 설정
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
    }

    private JButton[] getAllButtons() {
        int totalButtons = 0;
        for (int i = 0; i < modeButtons.length; i++) {
            totalButtons += modeButtons[i].length;
        }
        totalButtons += 1; // backButton 추가
        JButton[] allButtons = new JButton[totalButtons];
        int index = 0;
        for (int i = 0; i < modeButtons.length; i++) {
            for (int j = 0; j < modeButtons[i].length; j++) {
                allButtons[index++] = modeButtons[i][j];
            }
        }
        allButtons[totalButtons - 1] = backButton;
        return allButtons;
    }

    private JLabel[] getAllLabels() {
        int totalLabels = 0;
        totalLabels += inputLabels.length;
        totalLabels += 1; // titleLabel 추가
        JLabel[] allLabels = new JLabel[totalLabels];
        int index = 0;
        allLabels[0] = titleLabel;
        index++;
        for (int i = 0; i < inputLabels.length; i++) {
            allLabels[index++] = inputLabels[i];
        }
        return allLabels;
    }
    
    private void checkAndStartGame() {
        boolean allSelected = true;
        for (String mode : selectedModes) {
            if (mode == null) {
                allSelected = false;
                break;
            }
        }
        if (allSelected) {
            // 선택된 모드에 따라 TetrisGame을 열기
            String specialMode = selectedModes[0];
            String gameMode = selectedModes[1];
            String difficulty = selectedModes[2];
            JOptionPane.showMessageDialog(this, "게임을 시작합니다.");
            dispose(); // 현재 창 닫기
            TetrisGame tetrisGame = new TetrisGame(specialMode, gameMode, difficulty);
            tetrisGame.setVisible(true);
        }
    }
}
