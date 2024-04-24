package main.java.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SettingFile {
    public static String loadStatus() {
        try (BufferedReader reader = new BufferedReader(new FileReader("status.txt"))) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return null;  // 파일 읽기 실패 시 null 반환
        }
    }
}