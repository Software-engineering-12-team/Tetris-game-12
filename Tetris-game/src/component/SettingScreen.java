package component;

import javax.swing.*;

public class SettingScreen extends JFrame {

    public SettingScreen() {
        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       SettingElement settingElement = new SettingElement();
       getContentPane().add(settingElement);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SettingScreen();
            }
        });
    }
}