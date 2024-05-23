package main.java.setting.colorblindmode;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class ColorBlindModeMenuTest {
    @org.junit.jupiter.api.Test
    void updateColorBlindModeUI() {
    }

    @Test // GUI 구성 요소 초기화 테스트
    public void testComponentInitialization() {
        main.java.setting.colorblindmode.ColorBlindModeMenu menu;
        menu = new main.java.setting.colorblindmode.ColorBlindModeMenu();
        assertNotNull(menu.titleLabel, "Title label should not be null");
        assertEquals("색맹 모드 선택", menu.titleLabel.getText(), "Title label text mismatch");
        assertNotNull(menu.buttons, "Buttons array should not be null");
        assertEquals(4, menu.buttons.length, "Should have four buttons");
    }

    // 이벤트 리스너 테스트

    @Test // UI 업데이트 로직 테스트
    public void testUIUpdate() {
        main.java.setting.colorblindmode.ColorBlindModeMenu menu = new main.java.setting.colorblindmode.ColorBlindModeMenu();
        menu.updateColorBlindModeUI("적녹색맹");
        assertEquals(new Color(30, 90, 100), menu.redGreenBlindButton.getBackground(), "Background color should match for red-green blind mode");
        assertEquals("적녹색맹 모드 선택됨", menu.redGreenBlindButton.getText(), "Button text should match for red-green blind mode");
    }
}