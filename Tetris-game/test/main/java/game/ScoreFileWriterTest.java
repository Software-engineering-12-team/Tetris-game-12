package main.java.game;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScoreFileWriterTest {

    private static final String TEST_FILE_PATH = "scoreboard.txt";

    @Before
    public void setUp() {
        // 테스트 전에 테스트 파일을 삭제하여 깨끗한 상태로 시작
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @After
    public void tearDown() {
        // 테스트 후 테스트 파일 삭제
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testCreateScoreboardFile() {
        ScoreFileWriter.createScoreboardFile();
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    public void testWriteScore() {
        ScoreFileWriter.createScoreboardFile();
        String name = "Tester";
        String difficulty = "Easy";
        String mode = "Normal";
        int score = 100;

        ScoreFileWriter.writeScore(name, difficulty, mode, score);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.contains("이름: " + name));
            assertTrue(line.contains("난이도: " + difficulty));
            assertTrue(line.contains("모드: " + mode));
            assertTrue(line.contains("점수: " + score + "점"));
        } catch (IOException e) {
            fail("파일 읽기 중 오류 발생: " + e.getMessage());
        }
    }

    @Test
    public void testAppendScore() {
        ScoreFileWriter.createScoreboardFile();
        String name1 = "Tester1";
        String difficulty1 = "Easy";
        String mode1 = "Normal";
        int score1 = 100;

        String name2 = "Tester2";
        String difficulty2 = "Hard";
        String mode2 = "Challenge";
        int score2 = 200;

        ScoreFileWriter.writeScore(name1, difficulty1, mode1, score1);
        ScoreFileWriter.writeScore(name2, difficulty2, mode2, score2);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertNotNull(line1);
            assertNotNull(line2);
            assertTrue(line1.contains("이름: " + name1));
            assertTrue(line1.contains("난이도: " + difficulty1));
            assertTrue(line1.contains("모드: " + mode1));
            assertTrue(line1.contains("점수: " + score1 + "점"));
            assertTrue(line2.contains("이름: " + name2));
            assertTrue(line2.contains("난이도: " + difficulty2));
            assertTrue(line2.contains("모드: " + mode2));
            assertTrue(line2.contains("점수: " + score2 + "점"));
        } catch (IOException e) {
            fail("파일 읽기 중 오류 발생: " + e.getMessage());
        }
    }
}
