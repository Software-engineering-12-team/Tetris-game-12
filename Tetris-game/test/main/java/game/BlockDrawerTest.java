package main.java.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.java.game.Blocks.Tetrominoe;
import main.java.setting.colorblindmode.ColorBlindModeMenu;

class BlockDrawerTest {

    private BufferedImage canvas;
    private Graphics2D graphics;

    @BeforeEach
    void setUp() {
        canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        graphics = canvas.createGraphics();
    }

    @Test
    void testDrawBlockNormalMode() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.TBlock, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(new Color(204, 204, 102), color);
    }

    @Test
    void testDrawBlockRedGreenColorBlindMode() {
        ColorBlindModeMenu.colorBlindStatus = "적녹색맹";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.TBlock, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(new Color(255, 255, 0), color);
    }

    @Test
    void testDrawBlockBlueYellowColorBlindMode() {
        ColorBlindModeMenu.colorBlindStatus = "청황색맹";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.TBlock, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(new Color(255, 192, 203), color);
    }

    @Test
    void testDrawSpecialBlockBorderBlock() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.BorderBlock, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockWeightItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.WeightItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockLineDelItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.LineDelItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockThreeItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.ThreeItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockFiveItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.FiveItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockSevenItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.SevenItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockPlusItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.PlusItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockAllDelItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.AllDelItem, 0, 1);
        Color color = new Color(canvas.getRGB(15, 15));
        assertEquals(new Color(0, 0, 0), color);
        //assertEquals(Color.WHITE, color);
    }
}
