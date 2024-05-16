package main.java.setting.controlkeysetting;

import main.java.setting.SettingFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class ControlKeySettingMenuTest {
    private main.java.setting.controlkeysetting.ControlKeySettingMenu menu;
    private JButton typeAButton;
    private JButton typeBButton;
    private JButton backButton;

    @BeforeEach
    public void setUp() {
        // SettingFileWriter의 정적 메서드를 호출하기 전에 필요한 초기화 작업 수행
        SettingFileWriter.createSettingFile();

        menu = new main.java.setting.controlkeysetting.ControlKeySettingMenu();
        typeAButton = menu.buttons[0];
        typeBButton = menu.buttons[1];
        backButton = menu.buttons[2];
    }

    @Test
    public void testComponentInitialization() {
        assertNotNull(menu.titleLabel, "Title label should not be null");
        assertEquals("조작키 설정", menu.titleLabel.getText(), "Title label text mismatch");
        assertNotNull(menu.buttons, "Buttons array should not be null");
        assertEquals(3, menu.buttons.length, "Should have three buttons");
    }

    @Test
    public void testTypeAButtonAction() {
        typeAButton.doClick();
        assertEquals("타입A", main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus, "Control key status should be '타입A'");
        assertEquals("WASD키 선택됨", typeAButton.getText(), "Type A button text should be 'WASD키 선택됨'");
        assertEquals("방향키", typeBButton.getText(), "Type B button text should be '방향키'");
    }

    @Test
    public void testTypeBButtonAction() {
        typeBButton.doClick();
        assertEquals("타입B", main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus, "Control key status should be '타입B'");
        assertEquals("방향키 선택됨", typeBButton.getText(), "Type B button text should be '방향키 선택됨'");
        assertEquals("WASD키", typeAButton.getText(), "Type A button text should be 'WASD키'");
    }

    @Test
    public void testBackButtonAction() {
        // Back button action test should focus on the JFrame disposal
        backButton.doClick();
        assertFalse(menu.isDisplayable(), "Menu should be disposed");
    }

    @Test
    void addEventListeners() {
        ActionListener[] typeAListeners = typeAButton.getActionListeners();
        assertEquals(1, typeAListeners.length, "Type A button should have one action listener");
        typeAListeners[0].actionPerformed(new ActionEvent(typeAButton, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("타입A", main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus, "Control key status should be '타입A' after Type A button click");

        ActionListener[] typeBListeners = typeBButton.getActionListeners();
        assertEquals(1, typeBListeners.length, "Type B button should have one action listener");
        typeBListeners[0].actionPerformed(new ActionEvent(typeBButton, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("타입B", main.java.setting.controlkeysetting.ControlKeySettingMenu.controlKeyStatus, "Control key status should be '타입B' after Type B button click");

        ActionListener[] backListeners = backButton.getActionListeners();
        assertEquals(1, backListeners.length, "Back button should have one action listener");
        backListeners[0].actionPerformed(new ActionEvent(backButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after back button click");
    }

    @Test
    void updateControlKeyModeUI() {
        main.java.setting.controlkeysetting.ControlKeySettingMenu.updateControlKeyModeUI("타입A");
        assertEquals("WASD키 선택됨", typeAButton.getText(), "Type A button text should be updated to 'WASD키 선택됨'");
        assertEquals("방향키", typeBButton.getText(), "Type B button text should be '방향키'");
        assertEquals(new Color(30, 90, 100), typeAButton.getBackground(), "Type A button background color mismatch");
        assertEquals(new Color(30, 60, 90), typeBButton.getBackground(), "Type B button background color mismatch");

        main.java.setting.controlkeysetting.ControlKeySettingMenu.updateControlKeyModeUI("타입B");
        assertEquals("방향키 선택됨", typeBButton.getText(), "Type B button text should be updated to '방향키 선택됨'");
        assertEquals("WASD키", typeAButton.getText(), "Type A button text should be 'WASD키'");
        assertEquals(new Color(30, 90, 100), typeBButton.getBackground(), "Type B button background color mismatch");
        assertEquals(new Color(30, 60, 90), typeAButton.getBackground(), "Type A button background color mismatch");

    }
}
