package main.java.setting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class SettingMenuTest {
    private main.java.setting.SettingMenu menu;
    private JButton sizeAdjustButton;
    private JButton controlKeyButton;
    private JButton resetscoreButton;
    private JButton colorBlindModeButton;
    private JButton resetSettingButton;
    private JButton backButton;

    @BeforeEach
    public void setUp() {
        menu = new main.java.setting.SettingMenu();
        sizeAdjustButton = menu.buttons[0];
        controlKeyButton = menu.buttons[1];
        resetscoreButton = menu.buttons[2];
        colorBlindModeButton = menu.buttons[3];
        resetSettingButton = menu.buttons[4];
        backButton = menu.buttons[5];
    }

    @Test
    public void testComponentInitialization() {
        assertNotNull(menu.titleLabel, "Title label should not be null");
        assertEquals("설정", menu.titleLabel.getText(), "Title label text mismatch");
        assertNotNull(menu.buttons, "Buttons array should not be null");
        assertEquals(6, menu.buttons.length, "Should have six buttons");
        assertEquals("크기 조절", menu.buttons[0].getText(), "First button text mismatch");
        assertEquals("조작키 설정", menu.buttons[1].getText(), "Second button text mismatch");
        assertEquals("스코어보드 초기화", menu.buttons[2].getText(), "Third button text mismatch");
        assertEquals("색맹 모드", menu.buttons[3].getText(), "Fourth button text mismatch");
        assertEquals("설정 초기화", menu.buttons[4].getText(), "Fifth button text mismatch");
        assertEquals("뒤로가기", menu.buttons[5].getText(), "Sixth button text mismatch");
    }

    @Test
    public void testSizeAdjustButtonAction() {
        sizeAdjustButton.doClick();
        // SizeAdjustButton 클릭 후 동작을 검증합니다.
        assertFalse(menu.isDisplayable(), "Menu should be disposed after sizeAdjustButton click");
        // 추가로 ScreenAdjustSizeMenu가 열렸는지 검증해야 합니다. 이는 통합 테스트에서 다룰 수 있습니다.
    }

    @Test
    public void testControlKeyButtonAction() {
        controlKeyButton.doClick();
        // ControlKeyButton 클릭 후 동작을 검증합니다.
        assertFalse(menu.isDisplayable(), "Menu should be disposed after controlKeyButton click");
        // 추가로 ControlKeySettingMenu가 열렸는지 검증해야 합니다.
    }

    @Test
    public void testResetscoreButtonAction() {
        resetscoreButton.doClick();
        // ResetscoreButton 클릭 후 동작을 검증합니다.
        assertFalse(menu.isDisplayable(), "Menu should be disposed after resetscoreButton click");
        // 추가로 ScoreBoardMenu가 열렸는지 검증해야 합니다.
    }

    @Test
    public void testColorBlindModeButtonAction() {
        colorBlindModeButton.doClick();
        // ColorBlindModeButton 클릭 후 동작을 검증합니다.
        assertFalse(menu.isDisplayable(), "Menu should be disposed after colorBlindModeButton click");
        // 추가로 ColorBlindModeMenu가 열렸는지 검증해야 합니다.
    }

    @Test
    public void testResetSettingButtonAction() {
        resetSettingButton.doClick();
        // ResetSettingButton 클릭 후 동작을 검증합니다.
        assertEquals(400, menu.getWidth(), "Width should be reset to 400 after resetSettingButton click");
        assertEquals(550, menu.getHeight(), "Height should be reset to 550 after resetSettingButton click");
        // 추가로 설정 파일이 초기화되었는지 검증해야 합니다.
    }

    @Test
    public void testBackButtonAction() {
        backButton.doClick();
        // BackButton 클릭 후 동작을 검증합니다.
        assertFalse(menu.isDisplayable(), "Menu should be disposed after backButton click");
        // 추가로 StartMenu가 열렸는지 검증해야 합니다.
    }

    @Test
    public void testLayoutComponents() {
        Container contentPane = menu.getContentPane();
        assertNotNull(contentPane, "Content pane should not be null");
        assertTrue(contentPane.getLayout() instanceof BorderLayout, "Content pane layout should be BorderLayout");

        Component[] components = contentPane.getComponents();
        assertEquals(1, components.length, "Content pane should have one main panel");

        JPanel mainPanel = (JPanel) components[0];
        assertTrue(mainPanel.getLayout() instanceof BorderLayout, "Main panel layout should be BorderLayout");

        Component[] mainPanelComponents = mainPanel.getComponents();
        assertEquals(3, mainPanelComponents.length, "Main panel should have three components");

        assertTrue(mainPanelComponents[0] instanceof JLabel, "First component should be JLabel");
        assertTrue(mainPanelComponents[1] instanceof JPanel, "Second component should be JPanel");
        assertTrue(mainPanelComponents[2] instanceof JButton, "Third component should be JButton");

        JPanel buttonPanel = (JPanel) mainPanelComponents[1];
        assertTrue(buttonPanel.getLayout() instanceof GridLayout, "Button panel layout should be GridLayout");

        Component[] buttonPanelComponents = buttonPanel.getComponents();
        assertEquals(6, buttonPanelComponents.length, "Button panel should have six buttons");
    }

    @Test
    public void testAddEventListeners() {
        ActionListener[] sizeAdjustListeners = sizeAdjustButton.getActionListeners();
        assertEquals(1, sizeAdjustListeners.length, "Size adjust button should have one action listener");

        ActionListener[] controlKeyListeners = controlKeyButton.getActionListeners();
        assertEquals(1, controlKeyListeners.length, "Control key button should have one action listener");

        ActionListener[] resetScoreListeners = resetscoreButton.getActionListeners();
        assertEquals(1, resetScoreListeners.length, "Reset score button should have one action listener");

        ActionListener[] colorBlindModeListeners = colorBlindModeButton.getActionListeners();
        assertEquals(1, colorBlindModeListeners.length, "Color blind mode button should have one action listener");

        ActionListener[] resetSettingListeners = resetSettingButton.getActionListeners();
        assertEquals(1, resetSettingListeners.length, "Reset setting button should have one action listener");

        ActionListener[] backButtonListeners = backButton.getActionListeners();
        assertEquals(1, backButtonListeners.length, "Back button should have one action listener");

        // 각 버튼의 리스너 동작을 테스트
        sizeAdjustListeners[0].actionPerformed(new ActionEvent(sizeAdjustButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after sizeAdjustButton action");

        controlKeyListeners[0].actionPerformed(new ActionEvent(controlKeyButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after controlKeyButton action");

        resetScoreListeners[0].actionPerformed(new ActionEvent(resetscoreButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after resetscoreButton action");

        colorBlindModeListeners[0].actionPerformed(new ActionEvent(colorBlindModeButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after colorBlindModeButton action");

        resetSettingListeners[0].actionPerformed(new ActionEvent(resetSettingButton, ActionEvent.ACTION_PERFORMED, null));
        assertEquals(400, menu.getWidth(), "Width should be reset to 400 after resetSettingButton action");
        assertEquals(550, menu.getHeight(), "Height should be reset to 550 after resetSettingButton action");

        backButtonListeners[0].actionPerformed(new ActionEvent(backButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after backButton action");
    }
}
