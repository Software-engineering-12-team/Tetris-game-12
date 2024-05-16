package setting.colorblindmode;

import main.java.setting.SettingFileWriter;
import main.java.setting.colorblindmode.ColorBlindModeMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


public class ColorBlindModeMenuTest {
    private ColorBlindModeMenu menu;
    private JButton normalButton;
    private JButton redGreenBlindButton;
    private JButton blueYellowBlindButton;
    private JButton backButton;

    @Test
    void initComponents() {
    }

    @Test
    void updateColorBlindModeUI() {
    }

    @BeforeEach
    public void setUp() {
        // SettingFileWriter의 정적 메서드를 호출하기 전에 필요한 초기화 작업 수행
        SettingFileWriter.createSettingFile();

        menu = new ColorBlindModeMenu();
        normalButton = menu.buttons[0];
        redGreenBlindButton = menu.buttons[1];
        blueYellowBlindButton = menu.buttons[2];
        backButton = menu.buttons[3];
    }

    @Test
    public void testComponentInitialization() {
        assertNotNull(menu.titleLabel, "Title label should not be null");
        assertEquals("색맹 모드 선택", menu.titleLabel.getText(), "Title label text mismatch");
        assertNotNull(menu.buttons, "Buttons array should not be null");
        assertEquals(4, menu.buttons.length, "Should have four buttons");
    }

    @Test
    public void testNormalButtonAction() {
        normalButton.doClick();
        assertEquals("정상", ColorBlindModeMenu.colorBlindStatus, "Color blind status should be '정상'");
        assertEquals("정상 모드 선택됨", normalButton.getText(), "Normal button text should be '정상 모드 선택됨'");
        assertEquals("적녹색맹 모드", redGreenBlindButton.getText(), "Red-green blind button text should be '적녹색맹 모드'");
        assertEquals("청황색맹 모드", blueYellowBlindButton.getText(), "Blue-yellow blind button text should be '청황색맹 모드'");
    }

    @Test
    public void testRedGreenBlindButtonAction() {
        redGreenBlindButton.doClick();
        assertEquals("적녹색맹", ColorBlindModeMenu.colorBlindStatus, "Color blind status should be '적녹색맹'");
        assertEquals("적녹색맹 모드 선택됨", redGreenBlindButton.getText(), "Red-green blind button text should be '적녹색맹 모드 선택됨'");
        assertEquals("정상 모드", normalButton.getText(), "Normal button text should be '정상 모드'");
        assertEquals("청황색맹 모드", blueYellowBlindButton.getText(), "Blue-yellow blind button text should be '청황색맹 모드'");
    }

    @Test
    public void testBlueYellowBlindButtonAction() {
        blueYellowBlindButton.doClick();
        assertEquals("청황색맹", ColorBlindModeMenu.colorBlindStatus, "Color blind status should be '청황색맹'");
        assertEquals("청황색맹 모드 선택됨", blueYellowBlindButton.getText(), "Blue-yellow blind button text should be '청황색맹 모드 선택됨'");
        assertEquals("정상 모드", normalButton.getText(), "Normal button text should be '정상 모드'");
        assertEquals("적녹색맹 모드", redGreenBlindButton.getText(), "Red-green blind button text should be '적녹색맹 모드'");
    }

    @Test
    public void testBackButtonAction() {
        // Back button action test should focus on the JFrame disposal
        backButton.doClick();
        assertFalse(menu.isDisplayable(), "Menu should be disposed");
    }
}
