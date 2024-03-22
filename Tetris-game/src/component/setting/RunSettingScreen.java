package component.setting;

public class RunSettingScreen implements Runnable {

    @Override
    public void run() {
        new SettingScreen();
    }
}
