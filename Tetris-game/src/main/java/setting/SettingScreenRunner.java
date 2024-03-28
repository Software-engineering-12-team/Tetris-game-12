package main.java.setting;

public class SettingScreenRunner implements Runnable {
    @Override
    public void run() {
        new SettingMenu();
    }
}