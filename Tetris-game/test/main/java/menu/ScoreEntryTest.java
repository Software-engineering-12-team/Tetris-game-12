package main.java.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreEntryTest {

    @Test
    void getName() {
        ScoreEntry entry = new ScoreEntry("TestName", "Easy", "Normal", 100);
        assertEquals("TestName", entry.getName());
    }

    @Test
    void getDifficulty() {
        ScoreEntry entry = new ScoreEntry("TestName", "Easy", "Normal", 100);
        assertEquals("Easy", entry.getDifficulty());
    }

    @Test
    void getMode() {
        ScoreEntry entry = new ScoreEntry("TestName", "Easy", "Normal", 100);
        assertEquals("Normal", entry.getMode());
    }

    @Test
    void getScore() {
        ScoreEntry entry = new ScoreEntry("TestName", "Easy", "Normal", 100);
        assertEquals(100, entry.getScore());
    }

    @Test
    void compareTo() {
        ScoreEntry entry1 = new ScoreEntry("Player1", "Easy", "Normal", 200);
        ScoreEntry entry2 = new ScoreEntry("Player2", "Easy", "Normal", 100);
        assertTrue(entry1.compareTo(entry2) < 0); // entry1's score is higher, so it should come before entry2

        ScoreEntry entry3 = new ScoreEntry("Player3", "Easy", "Normal", 200);
        assertEquals(0, entry1.compareTo(entry3)); // entry1 and entry3 have the same score
    }

    @Test
    void testToString() {
        ScoreEntry entry = new ScoreEntry("TestName", "Easy", "Normal", 100);
        String expected = "이름: TestName / 난이도: Easy / 모드: Normal / 점수: 100점";
        assertEquals(expected, entry.toString());
    }
}
