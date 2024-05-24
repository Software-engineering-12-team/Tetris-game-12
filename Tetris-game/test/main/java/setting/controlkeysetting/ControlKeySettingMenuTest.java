package main.java.setting.controlkeysetting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ControlKeySettingMenuTest {

    private ControlKeySettingMenu menu;

    @BeforeEach
    void setUp() {
        menu = new ControlKeySettingMenu();
    }

    @Test
    void testInitComponents() {
        menu.initComponents();
        assertNotNull(menu.titleLabel);
        assertNotNull(menu.labels);
        assertNotNull(menu.typeAButton);
        assertNotNull(menu.typeBButton);
        assertNotNull(menu.backButton);
        assertEquals("조작키 설정", menu.titleLabel.getText());
    }

    @Test
    void testLayoutComponents() {
        menu.layoutComponents();
        Container contentPane = menu.getContentPane();
        assertNotNull(contentPane);
        assertTrue(contentPane.getLayout() instanceof BorderLayout);

        Component[] components = contentPane.getComponents();
        assertEquals(2, components.length);

        JPanel mainPanel = (JPanel) components[0];
        assertNotNull(mainPanel);
        assertTrue(mainPanel.getLayout() instanceof BorderLayout);

        Component[] mainPanelComponents = mainPanel.getComponents();
        assertEquals(1, mainPanelComponents.length);
    }

    @Test
    void testAddEventListeners() {
        JButton typeAButton = menu.typeAButton;
        JButton typeBButton = menu.typeBButton;
        JButton backButton = menu.backButton;

        // TypeA 버튼 클릭 테스트
        typeAButton.doClick();
        assertEquals("타입A", ControlKeySettingMenu.controlKeyStatus);
        assertButtonSelected(typeAButton, true);
        assertButtonSelected(typeBButton, false);

        // TypeB 버튼 클릭 테스트
        typeBButton.doClick();
        assertEquals("타입B", ControlKeySettingMenu.controlKeyStatus);
        assertButtonSelected(typeAButton, false);
        assertButtonSelected(typeBButton, true);

        // Back 버튼 클릭 테스트
        backButton.doClick();
        assertFalse(menu.isVisible());
    }

    @Test
    void testUpdateControlKeyModeUI() {
        ControlKeySettingMenu.updateControlKeyModeUI("타입A");
        assertButtonSelected(menu.typeAButton, true);
        assertButtonSelected(menu.typeBButton, false);

        ControlKeySettingMenu.updateControlKeyModeUI("타입B");
        assertButtonSelected(menu.typeAButton, false);
        assertButtonSelected(menu.typeBButton, true);
    }

    private void assertButtonSelected(JButton button, boolean isSelected) {
        Color expectedColor = isSelected ? new Color(30, 90, 100) : new Color(30, 60, 90);
        assertEquals(expectedColor, button.getBackground());
        if (isSelected) {
            assertTrue(button.getText().contains("선택됨"));
        } else {
            assertFalse(button.getText().contains("선택됨"));
        }
    }
}
