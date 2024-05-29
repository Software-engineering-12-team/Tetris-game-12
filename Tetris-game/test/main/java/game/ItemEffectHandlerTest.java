package main.java.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import main.java.game.Blocks.Tetrominoe;

public class ItemEffectHandlerTest {

    private Tetrominoe[] board;
    private final int width = ItemEffectHandler.BOARD_WIDTH;
    private final int height = ItemEffectHandler.BOARD_HEIGHT;

    @Before
    public void setUp() {
        board = new Tetrominoe[width * height];
        for (int i = 0; i < board.length; i++) {
            board[i] = Tetrominoe.NoBlock;
        }
    }

    private void setBlockAt(int x, int y, Tetrominoe block) {
        board[(y * width) + x] = block;
    }

    private Tetrominoe getBlockAt(int x, int y) {
        return board[(y * width) + x];
    }

    @Test
    public void testDeleteWeightItem() {
        Blocks curPiece = new Blocks("Normal");
        curPiece.setBlock(Tetrominoe.TBlock);
        setBlockAt(5, 5, Tetrominoe.TBlock);
        ItemEffectHandler.deleteWeightItem(board, curPiece, 5, 5);
        assertEquals(Tetrominoe.NoBlock, getBlockAt(5, 5));
    }

    @Test
    public void testApplyLineDelItem() {
        for (int i = 1; i < width - 1; i++) {
            setBlockAt(i, 5, Tetrominoe.TBlock);
        }
        ItemEffectHandler.applyLineDelItem(board, 5, 5);
        for (int i = 1; i < width - 1; i++) {
            assertEquals(Tetrominoe.NoBlock, getBlockAt(i, 5));
        }
    }

    @Test
    public void testApplyThreeItem() {
        setBlockAt(5, 5, Tetrominoe.TBlock);
        setBlockAt(6, 6, Tetrominoe.TBlock);
        setBlockAt(4, 4, Tetrominoe.TBlock);
        ItemEffectHandler.applyThreeItem(board, 5, 5);
        assertEquals(Tetrominoe.NoBlock, getBlockAt(5, 5));
        assertEquals(Tetrominoe.NoBlock, getBlockAt(6, 6));
        assertEquals(Tetrominoe.NoBlock, getBlockAt(4, 4));
    }

    @Test
    public void testApplyFiveItem() {
        setBlockAt(5, 5, Tetrominoe.TBlock);
        setBlockAt(7, 7, Tetrominoe.TBlock);
        setBlockAt(3, 3, Tetrominoe.TBlock);
        ItemEffectHandler.applyFiveItem(board, 5, 5);
        assertEquals(Tetrominoe.NoBlock, getBlockAt(5, 5));
        assertEquals(Tetrominoe.NoBlock, getBlockAt(7, 7));
        assertEquals(Tetrominoe.NoBlock, getBlockAt(3, 3));
    }

    @Test
    public void testApplySevenItem() {
        setBlockAt(5, 5, Tetrominoe.TBlock);
        setBlockAt(8, 8, Tetrominoe.TBlock);
        setBlockAt(2, 2, Tetrominoe.TBlock);
        ItemEffectHandler.applySevenItem(board, 5, 5);
        assertEquals(Tetrominoe.NoBlock, getBlockAt(5, 5));
        assertEquals(Tetrominoe.NoBlock, getBlockAt(8, 8));
        assertEquals(Tetrominoe.NoBlock, getBlockAt(2, 2));
    }

    @Test
    public void testApplyPlusItem() {
        for (int i = 1; i < width - 1; i++) {
            setBlockAt(i, 5, Tetrominoe.TBlock);
        }
        for (int i = 1; i < height - 1; i++) {
            setBlockAt(5, i, Tetrominoe.TBlock);
        }
        ItemEffectHandler.applyPlusItem(board, 5, 5);
        for (int i = 1; i < width - 1; i++) {
            assertEquals(Tetrominoe.NoBlock, getBlockAt(i, 5));
        }
        for (int i = 1; i < height - 1; i++) {
            assertEquals(Tetrominoe.NoBlock, getBlockAt(5, i));
        }
    }

    @Test
    public void testApplyAllDelItem() {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                setBlockAt(i, j, Tetrominoe.TBlock);
            }
        }
        ItemEffectHandler.applyAllDelItem(board);
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                assertEquals(Tetrominoe.NoBlock, getBlockAt(i, j));
            }
        }
    }
}
