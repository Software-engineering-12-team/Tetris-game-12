package main.java.setting.controlkeysetting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlKeyVisualizer extends JFrame implements KeyListener {
    private JLabel upLabel, downLabel, leftLabel, rightLabel, aLabel, dLabel, wLabel, sLabel;

    public ControlKeyVisualizer() {
        setTitle("Key Visualizer");
        setSize(400, 200);
        setLayout(new GridLayout(2, 4));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        upLabel = new JLabel("↑", SwingConstants.CENTER);
        downLabel = new JLabel("↓", SwingConstants.CENTER);
        leftLabel = new JLabel("←", SwingConstants.CENTER);
        rightLabel = new JLabel("→", SwingConstants.CENTER);
        aLabel = new JLabel("A", SwingConstants.CENTER);
        dLabel = new JLabel("D", SwingConstants.CENTER);
        wLabel = new JLabel("W", SwingConstants.CENTER);
        sLabel = new JLabel("S", SwingConstants.CENTER);

        addKeyListener(this);

        add(upLabel);
        add(downLabel);
        add(leftLabel);
        add(rightLabel);
        add(aLabel);
        add(dLabel);
        add(wLabel);
        add(sLabel);

        setFocusable(true);
        setVisible(true);
    }

    private void resetLabels() {
        upLabel.setOpaque(false);
        downLabel.setOpaque(false);
        leftLabel.setOpaque(false);
        rightLabel.setOpaque(false);
        aLabel.setOpaque(false);
        dLabel.setOpaque(false);
        wLabel.setOpaque(false);
        sLabel.setOpaque(false);

        upLabel.setBackground(Color.LIGHT_GRAY);
        downLabel.setBackground(Color.LIGHT_GRAY);
        leftLabel.setBackground(Color.LIGHT_GRAY);
        rightLabel.setBackground(Color.LIGHT_GRAY);
        aLabel.setBackground(Color.LIGHT_GRAY);
        dLabel.setBackground(Color.LIGHT_GRAY);
        wLabel.setBackground(Color.LIGHT_GRAY);
        sLabel.setBackground(Color.LIGHT_GRAY);

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        resetLabels();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upLabel.setOpaque(true);
                upLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_DOWN:
                downLabel.setOpaque(true);
                downLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_LEFT:
                leftLabel.setOpaque(true);
                leftLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_RIGHT:
                rightLabel.setOpaque(true);
                rightLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_A:
                aLabel.setOpaque(true);
                aLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_D:
                dLabel.setOpaque(true);
                dLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_W:
                wLabel.setOpaque(true);
                wLabel.setBackground(Color.GREEN);
                break;
            case KeyEvent.VK_S:
                sLabel.setOpaque(true);
                sLabel.setBackground(Color.GREEN);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        resetLabels();
    }

}

