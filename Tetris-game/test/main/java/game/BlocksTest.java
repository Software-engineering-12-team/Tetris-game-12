package main.java.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BlocksTest {
    private Blocks blocks;

    @BeforeEach
    void setUp() {
        blocks = new Blocks("Normal");
    }

    @Test
    void setBlock() {
        blocks.setBlock(Blocks.Tetrominoe.TBlock);
        assertEquals(Blocks.Tetrominoe.TBlock, blocks.getBlock());
        assertArrayEquals(new int[][]{{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, blocks.getCoords());
    }

    @Test
    void x() {
        blocks.setBlock(Blocks.Tetrominoe.TBlock);
        assertEquals(-1, blocks.x(0));
        assertEquals(0, blocks.x(1));
        assertEquals(1, blocks.x(2));
        assertEquals(0, blocks.x(3));
    }

    @Test
    void y() {
        blocks.setBlock(Blocks.Tetrominoe.TBlock);
        assertEquals(0, blocks.y(0));
        assertEquals(0, blocks.y(1));
        assertEquals(0, blocks.y(2));
        assertEquals(1, blocks.y(3));
    }

    @Test
    void getBlock() {
        blocks.setBlock(Blocks.Tetrominoe.ZBlock);
        assertEquals(Blocks.Tetrominoe.ZBlock, blocks.getBlock());
    }

    @Test
    void setRandomBlock() {
        blocks.setRandomBlock("Easy", false);
        assertNotNull(blocks.getBlock());
        assertTrue(blocks.getBlock().ordinal() >= 0 && blocks.getBlock().ordinal() <= 7);

        blocks.setRandomBlock("Hard", false);
        assertNotNull(blocks.getBlock());
        assertTrue(blocks.getBlock().ordinal() >= 0 && blocks.getBlock().ordinal() <= 7);

        blocks.setRandomBlock("Normal", true);
        assertNotNull(blocks.getBlock());
        assertTrue(blocks.getBlock().ordinal() >= 9 && blocks.getBlock().ordinal() <= 15);
    }

    @Test
    void minX() {
        blocks.setBlock(Blocks.Tetrominoe.ZBlock);
        assertEquals(-1, blocks.minX());
    }

    @Test
    void minY() {
        blocks.setBlock(Blocks.Tetrominoe.ZBlock);
        assertEquals(0, blocks.minY());
    }

    @Test
    void rotateLeft() {
        blocks.setBlock(Blocks.Tetrominoe.TBlock);
        Blocks rotated = blocks.rotateLeft();
        assertArrayEquals(new int[][]{{0, 1}, {0, 0}, {0, -1}, {1, 0}}, rotated.getCoords());
    }

    @Test
    void rotateRight() {
        blocks.setBlock(Blocks.Tetrominoe.TBlock);
        Blocks rotated = blocks.rotateRight();
        assertArrayEquals(new int[][]{{0, -1}, {0, 0}, {0, 1}, {-1, 0}}, rotated.getCoords());
    }
}
