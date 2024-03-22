package component.setting;
import component.setting.RunSettingScreen;
import component.setting.SettingElement;

import javax.swing.*;

public class SettingScreen extends JFrame {

    public SettingScreen() {
        setTitle("Settings");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        SettingElement settingElement = new SettingElement();
        getContentPane().add(settingElement);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RunSettingScreen());
    }
    //Setting Screen의 main 메서드에서는 RunSettingScreen 클래스의 인스턴스를 만들어
    //SwingUtilities.invokeLater() 메서드에 전달하면 됩니다.
}
