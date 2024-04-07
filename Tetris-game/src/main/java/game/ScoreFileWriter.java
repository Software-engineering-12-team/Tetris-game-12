package main.java.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreFileWriter {
    private static final String FILE_PATH = "scoreboard.txt";

    public static void writeScore(String name, String difficulty, String mode, int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
            writer.write("이름: " + name + " / 난이도: " + difficulty + " / 모드: " + mode + " / 점수: " + score + "점\n");
            writer.close();
            System.out.println("스코어 정보가 파일에 저장되었습니다.");
        } catch (IOException e) {
            System.err.println("파일에 스코어 정보를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}