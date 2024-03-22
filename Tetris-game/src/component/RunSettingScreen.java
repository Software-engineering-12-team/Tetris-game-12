package component;

public class RunSettingScreen implements Runnable {

    @Override
    public void run() {
        new SettingScreen();
    }
}
