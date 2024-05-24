package main.java.setting;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class SettingFileWriterTest {
    private static final String FILE_PATH = "setting.txt";

    @BeforeEach
    void setUp() throws IOException {
        // 각 테스트 전에 파일을 초기화
        Files.deleteIfExists(Paths.get(FILE_PATH));
        SettingFileWriter.createSettingFile();
    }

    @Test
    void testCreateSettingFile() {
        File file = new File(FILE_PATH);
        assertTrue(file.exists(), "설정 파일이 존재해야 합니다.");
        assertNotEquals(0, file.length(), "설정 파일이 비어 있으면 안 됩니다.");
    }

    @Test
    void testClearSetting() throws IOException {
        SettingFileWriter.clearSetting();
        assertEquals(0, Files.size(Paths.get(FILE_PATH)), "설정 파일은 초기화 후 비어 있어야 합니다.");
    }

    @Test
    void testReadSize() {
        int size = SettingFileWriter.readSize();
        assertEquals(0, size, "크기는 초기화 값인 0이어야 합니다.");
    }

    @Test
    void testReadControlKey() {
        String controlKey = SettingFileWriter.readControlKey();
        assertEquals("타입A", controlKey, "조작키는 초기화 값인 '타입A'이어야 합니다.");
    }

    @Test
    void testReadBlindMode() {
        String blindMode = SettingFileWriter.readBlindMode();
        assertEquals("정상", blindMode, "색맹모드는 초기화 값인 '정상'이어야 합니다.");
    }

    @Test
    void testWriteSetting() throws IOException {
        SettingFileWriter.writeSetting(1, "타입B", "비활성화");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); // 첫 번째 라인을 건너뜁니다.
            line = reader.readLine(); // 새로운 설정 라인을 읽습니다.
            assertNotNull(line, "새로운 설정 라인이 존재해야 합니다.");
            assertTrue(line.contains("크기: 1"), "크기는 1이어야 합니다.");
            assertTrue(line.contains("조작키: 타입B"), "조작키는 '타입B'이어야 합니다.");
            assertTrue(line.contains("색맹모드: 비활성화"), "색맹모드는 '비활성화'이어야 합니다.");
        }
    }
}
