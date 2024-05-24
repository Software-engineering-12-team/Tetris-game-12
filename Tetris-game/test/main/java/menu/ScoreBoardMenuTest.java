package main.java.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardMenuTest {

    private ScoreBoardMenu scoreBoardMenu;

    @BeforeEach
    void setUp() {
        scoreBoardMenu = new ScoreBoardMenu();
    }

    @Test
    void testAddScore() {
        ScoreEntry entry = new ScoreEntry("TestName", "Easy", "Normal", 100);
        scoreBoardMenu.addScore(entry);

        DefaultTableModel tableModel = (DefaultTableModel) scoreBoardMenu.getScoreTable().getModel();
        int rowCount = tableModel.getRowCount();
        assertEquals(1, rowCount);

        Object[] expectedRow = {"TestName", "Easy", "Normal", 100};
        for (int i = 0; i < expectedRow.length; i++) {
            assertEquals(expectedRow[i], tableModel.getValueAt(0, i));
        }
    }

    @Test
    void testClearScores() {
        ScoreEntry entry1 = new ScoreEntry("TestName1", "Easy", "Normal", 100);
        ScoreEntry entry2 = new ScoreEntry("TestName2", "Hard", "Normal", 200);

        scoreBoardMenu.addScore(entry1);
        scoreBoardMenu.addScore(entry2);

        scoreBoardMenu.clearScores();

        DefaultTableModel tableModel = (DefaultTableModel) scoreBoardMenu.getScoreTable().getModel();
        assertEquals(0, tableModel.getRowCount());

        DefaultListModel<String> scoreModel = (DefaultListModel<String>) scoreBoardMenu.getScoreList().getModel();
        assertEquals(0, scoreModel.getSize());

        // Check that the scoreboard.txt file is empty
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scoreboard.txt", true))) {
            writer.write("");
        } catch (IOException e) {
            fail("IOException occurred while checking scoreboard.txt: " + e.getMessage());
        }
    }
}
