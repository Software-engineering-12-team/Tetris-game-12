package main.java.setting;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class SettingFileWriterTest {
    private static final String TEST_FILE_PATH = "test_setting.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // 테스트용 임시 파일을 생성
        Files.createFile(Paths.get(TEST_FILE_PATH));
        // 임시 파일 경로를 SettingFileWriter에 설정
        setFilePath(TEST_FILE_PATH);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // 테스트용 임시 파일을 삭제
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    // Helper method to set file path for testing
    private void setFilePath(String path) {
        try {
            Field filePathField = main.java.setting.SettingFileWriter.class.getDeclaredField("FILE_PATH");
            filePathField.setAccessible(true);
            filePathField.set(null, path);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set file path for testing", e);
        }
    }

    @Test
    public void testCreateSettingFile() throws IOException {
        // 파일이 이미 있는 경우 삭제
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        main.java.setting.SettingFileWriter.createSettingFile();
        assertTrue(Files.exists(Paths.get(TEST_FILE_PATH)), "설정 파일이 생성되어야 합니다.");
        assertFalse(Files.size(Paths.get(TEST_FILE_PATH)) == 0, "설정 파일은 비어 있으면 안 됩니다.");
    }

    @Test
    public void testClearSetting() throws IOException {
        // 임시 파일에 내용 쓰기
        Files.write(Paths.get(TEST_FILE_PATH), "Test content".getBytes());
        main.java.setting.SettingFileWriter.clearSetting();
        assertEquals(0, Files.size(Paths.get(TEST_FILE_PATH)), "설정 파일은 비어 있어야 합니다.");
    }

    @Test
    public void testReadSize() throws IOException {
        String content = "크기: 1 / 조작키: 타입A / 색맹모드: 정상\n";
        Files.write(Paths.get(TEST_FILE_PATH), content.getBytes());

        int size = main.java.setting.SettingFileWriter.readSize();
        assertEquals(1, size, "크기 값은 1이어야 합니다.");
    }

    @Test
    public void testReadControlKey() throws IOException {
        String content = "크기: 1 / 조작키: 타입B / 색맹모드: 정상\n";
        Files.write(Paths.get(TEST_FILE_PATH), content.getBytes());

        String controlKey = main.java.setting.SettingFileWriter.readControlKey();
        assertEquals("타입B", controlKey, "조작키 값은 '타입B'이어야 합니다.");
    }

    @Test
    public void testReadBlindMode() throws IOException {
        String content = "크기: 1 / 조작키: 타입A / 색맹모드: 색약\n";
        Files.write(Paths.get(TEST_FILE_PATH), content.getBytes());

        String blindMode = main.java.setting.SettingFileWriter.readBlindMode();
        assertEquals("색약", blindMode, "색맹모드 값은 '색약'이어야 합니다.");
    }

    @Test
    public void testWriteSetting() throws IOException {
        main.java.setting.SettingFileWriter.writeSetting(1, "타입A", "정상");

        String content = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)));
        assertTrue(content.contains("크기: 1 / 조작키: 타입A / 색맹모드: 정상"), "설정 내용이 파일에 저장되어야 합니다.");
    }
}