package main.java.setting;

import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SettingMenuTest {

    private SettingMenu settingMenu;

    @BeforeAll
    void setUpOnce() {
        SettingFileWriter.createSettingFile();
    }

    @BeforeEach
    void setUp() {
        SettingFileWriter.clearSetting();
        SettingFileWriter.writeSetting(0, "타입A", "정상");

        // SettingMenu 인스턴스를 이벤트 디스패치 스레드에서 생성
        try {
            SwingUtilities.invokeAndWait(() -> settingMenu = new SettingMenu());
        } catch (Exception e) {
            e.printStackTrace();
            fail("SettingMenu 인스턴스 생성 중 오류 발생");
        }
    }

    @AfterEach
    void tearDown() {
        // SettingMenu 프레임을 이벤트 디스패치 스레드에서 닫기
        SwingUtilities.invokeLater(() -> {
            if (settingMenu != null) {
                settingMenu.dispose();
            }
        });
    }

    @Test
    void testSizeAdjustButton() {
        JButton button = getButtonByText(settingMenu, "크기 조절");
        assertNotNull(button, "'크기 조절' 버튼이 존재해야 합니다.");

        button.doClick();
        waitForWindowToOpen("크기 조정");
        assertNotNull(getFrameByTitle("크기 조정"), "'크기 조정' 창이 열려야 합니다.");
    }

    @Test
    void testControlKeyButton() {
        JButton button = getButtonByText(settingMenu, "조작키 설정");
        assertNotNull(button, "'조작키 설정' 버튼이 존재해야 합니다.");

        button.doClick();
        waitForWindowToOpen("조작키 설정");
        assertNotNull(getFrameByTitle("조작키 설정"), "'조작키 설정' 창이 열려야 합니다.");
    }

    @Test
    void testResetScoreButton() {
        JButton button = getButtonByText(settingMenu, "스코어보드 초기화");
        assertNotNull(button, "'스코어보드 초기화' 버튼이 존재해야 합니다.");

        button.doClick();
        waitForWindowToOpen("스코어보드");
        assertNotNull(getFrameByTitle("스코어보드"), "'스코어보드' 창이 열려야 합니다.");
    }

    @Test
    void testColorBlindModeButton() {
        JButton button = getButtonByText(settingMenu, "색맹 모드");
        assertNotNull(button, "'색맹 모드' 버튼이 존재해야 합니다.");

        button.doClick();
        waitForWindowToOpen("색맹 모드 선택");
        assertNotNull(getFrameByTitle("색맹 모드 선택"), "'색맹 모드' 창이 열려야 합니다.");
    }

    @Test
    void testResetSettingButton() throws IOException {
        JButton button = getButtonByText(settingMenu, "설정 초기화");
        assertNotNull(button, "'설정 초기화' 버튼이 존재해야 합니다.");

        button.doClick();
        assertTrue(Files.size(Paths.get("setting.txt")) > 0, "설정 파일이 초기화되고 값이 작성되어야 합니다.");
    }

    @Test
    void testBackButton() {
        JButton button = getButtonByText(settingMenu, "뒤로가기");
        assertNotNull(button, "'뒤로가기' 버튼이 존재해야 합니다.");

        button.doClick();
        waitForWindowToOpen("테트리스 게임");
        assertNotNull(getFrameByTitle("테트리스 게임"), "'테트리스 게임' 창이 열려야 합니다.");
    }

    private JButton getButtonByText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(text)) {
                    return button;
                }
            } else if (component instanceof Container) {
                JButton button = getButtonByText((Container) component, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }

    private JFrame getFrameByTitle(String title) {
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof JFrame) {
                JFrame jFrame = (JFrame) frame;
                String frameTitle = jFrame.getTitle();
                if (frameTitle != null && frameTitle.equals(title) && jFrame.isVisible()) {
                    return jFrame;
                }
            }
        }
        return null;
    }

    private void waitForWindowToOpen(String title) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 2000) { // 2초 동안 대기
            if (getFrameByTitle(title) != null) {
                break;
            }
            try {
                Thread.sleep(100); // 100밀리초 대기 후 다시 체크
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
