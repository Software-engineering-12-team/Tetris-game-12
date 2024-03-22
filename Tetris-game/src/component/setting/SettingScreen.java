package component.setting;

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
}
