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
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.TBlock);
        Color color = new Color(canvas.getRGB(15, 15));
        //assertEquals(new Color(204, 204, 102), color);
    }

    @Test
    void testDrawBlockRedGreenColorBlindMode() {
        ColorBlindModeMenu.colorBlindStatus = "적녹색맹";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.TBlock);
        Color color = new Color(canvas.getRGB(20, 20));
        // assertEquals(new Color(0, 0, 255), color);
    }

    @Test
    void testDrawBlockBlueYellowColorBlindMode() {
        ColorBlindModeMenu.colorBlindStatus = "청황색맹";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.TBlock);
        Color color = new Color(canvas.getRGB(20, 20));
        //assertEquals(new Color(128, 0, 128), color);
    }

    @Test
    void testDrawSpecialBlockBorderBlock() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.BorderBlock);
        Color color = new Color(canvas.getRGB(20, 20));
        //assertEquals(Color.WHITE, color);
    }

    @Test
    void testDrawSpecialBlockWeightItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.WeightItem);
        Color color = new Color(canvas.getRGB(20, 20));
        //assertEquals(Color.WHITE, color);
    }

    // 줄삭제 아이템 테스트
    /*@Test
    void testDrawSpecialBlockLineDelItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.LineDelItem);
        Color color = new Color(canvas.getRGB(20, 20));
        assertEquals(Color.WHITE, color);
    }*/

    @Test
    void testDrawSpecialBlockThreeItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.ThreeItem);
        Color color = new Color(canvas.getRGB(20, 20));
        assertEquals(Color.BLACK, color);
    }

    @Test
    void testDrawSpecialBlockFiveItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.FiveItem);
        Color color = new Color(canvas.getRGB(20, 20));
        assertEquals(Color.BLACK, color);
    }

    @Test
    void testDrawSpecialBlockSevenItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.SevenItem);
        Color color = new Color(canvas.getRGB(20, 20));
        assertEquals(Color.BLACK, color);
    }

    @Test
    void testDrawSpecialBlockPlusItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.PlusItem);
        Color color = new Color(canvas.getRGB(20, 20));
        assertEquals(Color.BLACK, color);
    }

    @Test
    void testDrawSpecialBlockAllDelItem() {
        ColorBlindModeMenu.colorBlindStatus = "정상";
        BlockDrawer.drawBlock(graphics, 10, 10, 20, 20, Tetrominoe.AllDelItem);
        Color color = new Color(canvas.getRGB(20, 20));
        assertEquals(Color.BLACK, color);
    }
}
