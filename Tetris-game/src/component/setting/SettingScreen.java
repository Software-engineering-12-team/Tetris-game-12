package component.setting;

import javax.swing.*;

public class SettingScreen extends JFrame {

    public SettingScreen() {
        SettingElement settingElement = new SettingElement();
        getContentPane().add(settingElement);

        setVisible(true);
    }
}
