package main.java.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

import static org.junit.jupiter.api.Assertions.*;

class ScreenAdjustComponentTest {

    private JLabel[] labels;
    private JButton[] buttons;

    @BeforeEach
    void setUp() {
        labels = new JLabel[3];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("Label " + (i + 1));
        }

        buttons = new JButton[3];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i + 1));
        }
    }

    @Test
    void sizeAdjust() {
        // Test sizeAdjust with size 0
        ScreenAdjustComponent.sizeAdjust(labels, buttons, true, 0);
        assertEquals(30, labels[0].getFont().getSize());
        assertEquals(18, labels[1].getFont().getSize());
        assertEquals(18, labels[2].getFont().getSize());
        assertEquals(18, buttons[0].getFont().getSize());
        assertEquals(18, buttons[1].getFont().getSize());
        assertEquals(16, buttons[2].getFont().getSize());

        // Test sizeAdjust with size 1
        ScreenAdjustComponent.sizeAdjust(labels, buttons, true, 1);
        assertEquals(33, labels[0].getFont().getSize());
        assertEquals(19, labels[1].getFont().getSize());
        assertEquals(19, labels[2].getFont().getSize());
        assertEquals(19, buttons[0].getFont().getSize());
        assertEquals(19, buttons[1].getFont().getSize());
        assertEquals(17, buttons[2].getFont().getSize());

        // Test sizeAdjust with size 2
        ScreenAdjustComponent.sizeAdjust(labels, buttons, true, 2);
        assertEquals(36, labels[0].getFont().getSize());
        assertEquals(21, labels[1].getFont().getSize());
        assertEquals(21, labels[2].getFont().getSize());
        assertEquals(21, buttons[0].getFont().getSize());
        assertEquals(21, buttons[1].getFont().getSize());
        assertEquals(19, buttons[2].getFont().getSize());
    }

    @Test
    void adjustLabelSize() {
        // Test adjustLabelSize with scaleFactor 1.0
        ScreenAdjustComponent.adjustLabelSize(labels, 1.0);
        assertEquals(30, labels[0].getFont().getSize());
        assertEquals(18, labels[1].getFont().getSize());
        assertEquals(18, labels[2].getFont().getSize());

        // Test adjustLabelSize with scaleFactor 1.1
        ScreenAdjustComponent.adjustLabelSize(labels, 1.1);
        assertEquals(33, labels[0].getFont().getSize());
        assertEquals(19, labels[1].getFont().getSize());
        assertEquals(19, labels[2].getFont().getSize());

        // Test adjustLabelSize with scaleFactor 1.2
        ScreenAdjustComponent.adjustLabelSize(labels, 1.2);
        assertEquals(36, labels[0].getFont().getSize());
        assertEquals(21, labels[1].getFont().getSize());
        assertEquals(21, labels[2].getFont().getSize());
    }

    @Test
    void adjustButtonSize() {
        // Test adjustButtonSize with scaleFactor 1.0
        ScreenAdjustComponent.adjustButtonSize(buttons, 1.0, true);
        assertEquals(18, buttons[0].getFont().getSize());
        assertEquals(18, buttons[1].getFont().getSize());
        assertEquals(16, buttons[2].getFont().getSize());

        // Test adjustButtonSize with scaleFactor 1.1
        ScreenAdjustComponent.adjustButtonSize(buttons, 1.1, true);
        assertEquals(19, buttons[0].getFont().getSize());
        assertEquals(19, buttons[1].getFont().getSize());
        assertEquals(17, buttons[2].getFont().getSize());

        // Test adjustButtonSize with scaleFactor 1.2
        ScreenAdjustComponent.adjustButtonSize(buttons, 1.2, true);
        assertEquals(21, buttons[0].getFont().getSize());
        assertEquals(21, buttons[1].getFont().getSize());
        assertEquals(19, buttons[2].getFont().getSize());
    }
}
