package component;

import javax.swing.*;
import java.awt.*;

public class SettingElement extends JPanel {

    public SettingElement() {
        setLayout(new GridLayout(3, 1));

        // 첫 번째 설정 항목 예시: 라벨과 체크박스
        JLabel label1 = new JLabel("Option 1");
        JCheckBox checkBox1 = new JCheckBox();
        add(label1);
        add(checkBox1);

        // 두 번째 설정 항목 예시: 라벨과 라디오 버튼 그룹
        JLabel label2 = new JLabel("Option 2");
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radioButton1 = new JRadioButton("Option A");
        JRadioButton radioButton2 = new JRadioButton("Option B");
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        add(label2);
        add(radioButton1);
        add(radioButton2);

        // 세 번째 설정 항목 예시: 라벨과 콤보 박스
        JLabel label3 = new JLabel("Option 3");
        String[] options = {"Choice 1", "Choice 2", "Choice 3"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        add(label3);
        add(comboBox);
    }
}
