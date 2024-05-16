import main.java.setting.SettingFileWriter;
import main.java.setting.screenadjustsize.ScreenAdjustSizeMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

public class ScreenAdjustSizeMenuTest {
    private ScreenAdjustSizeMenu menu;
    private JButton smallButton;
    private JButton mediumButton;
    private JButton largeButton;
    private JButton backButton;

    @BeforeEach
    public void setUp() {
        // SettingFileWriter의 정적 메서드를 호출하기 전에 필요한 초기화 작업 수행
        SettingFileWriter.createSettingFile();

        menu = new ScreenAdjustSizeMenu();
        smallButton = menu.buttons[0];
        mediumButton = menu.buttons[1];
        largeButton = menu.buttons[2];
        backButton = menu.buttons[3];
    }

    @Test
    public void testComponentInitialization() {
        assertNotNull(menu.titleLabel, "Title label should not be null");
        assertEquals("크기 조정", menu.titleLabel.getText(), "Title label text mismatch");
        assertNotNull(menu.buttons, "Buttons array should not be null");
        assertEquals(4, menu.buttons.length, "Should have four buttons");
        assertEquals("small", menu.buttons[0].getText(), "First button text mismatch");
        assertEquals("medium", menu.buttons[1].getText(), "Second button text mismatch");
        assertEquals("large", menu.buttons[2].getText(), "Third button text mismatch");
        assertEquals("뒤로가기", menu.buttons[3].getText(), "Fourth button text mismatch");
    }

    @Test
    public void testSmallButtonAction() {
        smallButton.doClick();
        assertEquals(0, ScreenAdjustSizeMenu.size, "Size should be 0 after small button click");
        assertEquals(400, menu.getWidth(), "Width should be 400 after small button click");
        assertEquals(550, menu.getHeight(), "Height should be 550 after small button click");
    }

    @Test
    public void testMediumButtonAction() {
        mediumButton.doClick();
        assertEquals(1, ScreenAdjustSizeMenu.size, "Size should be 1 after medium button click");
        assertEquals(440, menu.getWidth(), "Width should be 440 after medium button click");
        assertEquals(605, menu.getHeight(), "Height should be 605 after medium button click");
    }

    @Test
    public void testLargeButtonAction() {
        largeButton.doClick();
        assertEquals(2, ScreenAdjustSizeMenu.size, "Size should be 2 after large button click");
        assertEquals(480, menu.getWidth(), "Width should be 480 after large button click");
        assertEquals(660, menu.getHeight(), "Height should be 660 after large button click");
    }

    @Test
    public void testBackButtonAction() {
        // Back button action test should focus on the JFrame disposal
        backButton.doClick();
        assertFalse(menu.isDisplayable(), "Menu should be disposed after back button click");
    }

    @Test
    public void testLayoutComponents() {
        //menu.layoutComponents();
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
        assertEquals(3, buttonPanelComponents.length, "Button panel should have three buttons");
    }

    @Test
    public void testAddEventListeners() {
        ActionListener[] smallButtonListeners = smallButton.getActionListeners();
        assertEquals(1, smallButtonListeners.length, "Small button should have one action listener");
        smallButtonListeners[0].actionPerformed(new ActionEvent(smallButton, ActionEvent.ACTION_PERFORMED, null));
        assertEquals(0, ScreenAdjustSizeMenu.size, "Size should be 0 after small button click");

        ActionListener[] mediumButtonListeners = mediumButton.getActionListeners();
        assertEquals(1, mediumButtonListeners.length, "Medium button should have one action listener");
        mediumButtonListeners[0].actionPerformed(new ActionEvent(mediumButton, ActionEvent.ACTION_PERFORMED, null));
        assertEquals(1, ScreenAdjustSizeMenu.size, "Size should be 1 after medium button click");

        ActionListener[] largeButtonListeners = largeButton.getActionListeners();
        assertEquals(1, largeButtonListeners.length, "Large button should have one action listener");
        largeButtonListeners[0].actionPerformed(new ActionEvent(largeButton, ActionEvent.ACTION_PERFORMED, null));
        assertEquals(2, ScreenAdjustSizeMenu.size, "Size should be 2 after large button click");

        ActionListener[] backButtonListeners = backButton.getActionListeners();
        assertEquals(1, backButtonListeners.length, "Back button should have one action listener");
        backButtonListeners[0].actionPerformed(new ActionEvent(backButton, ActionEvent.ACTION_PERFORMED, null));
        assertFalse(menu.isDisplayable(), "Menu should be disposed after back button click");
    }
}
