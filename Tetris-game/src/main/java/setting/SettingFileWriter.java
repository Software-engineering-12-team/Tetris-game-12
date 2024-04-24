package main.java.setting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileReader;

public class SettingFileWriter {
	private static final String FILE_PATH = "setting.txt";
	
	// 설정 저장 파일을 생성하는 메서드
    public static void createSettingFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.createNewFile()) {
                System.out.println("설정 파일이 생성되었습니다.");
            	SettingFileWriter.writeSetting(0, "controlKey", "blindMode");
            } else {
                System.out.println("설정 파일이 이미 존재합니다.");
                if (Files.size(Paths.get("setting.txt")) == 0) {
                    System.out.println("파일이 존재하지만 비어 있습니다.");
                	SettingFileWriter.writeSetting(0, "controlKey", "blindMode");
                } else {
                    System.out.println("파일이 비어 있지 않습니다.");
                }
            }
        } catch (IOException e) {
            System.err.println("설정 파일을 생성하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    public static void clearSetting() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("setting.txt"))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace(); // 오류 발생 시 출력
        }
    }
    
    public static int readSize() {
        String size_temp = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("setting.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {  
                int sizeIndex = line.indexOf("크기:");
                
                if (sizeIndex != -1) {
                    // 키워드를 기준으로 정보 추출
                	size_temp = line.substring(sizeIndex + 4, line.indexOf(" ", sizeIndex + 4)).trim();
                }
            } 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "설정을 로드하는 중에 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
        int size = Integer.parseInt(size_temp);
        return size;
    }
    
    private String readControlKey() {
        String controlKey = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("setting.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {  
                int controlKeyIndex = line.indexOf("조작키:");
                
                if (controlKeyIndex != -1) {
                    // 키워드를 기준으로 정보 추출
                    controlKey = line.substring(controlKeyIndex + 5, line.indexOf(" ", controlKeyIndex + 5)).trim();
                }
            } 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "설정을 로드하는 중에 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
        return controlKey;
    }
    private String readBlindMode() {
        String blindMode = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("setting.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {            	
                int blindModeIndex = line.indexOf("색맹모드:");
                
                if (blindModeIndex != -1) {
                    // 키워드를 기준으로 정보 추출
                    blindMode = line.substring(blindModeIndex + 6, line.indexOf(" ", blindModeIndex + 6)).trim();
                }
            } 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "설정을 로드하는 중에 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
        return blindMode;
    }
    
    public static void writeSetting(int size, String controlKey, String blindMode) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(Integer.toString(size)); // 설정 값을 텍스트로 변환하여 파일에 씁니다.
            writer.write("크기: " + size + " / 조작키: " + controlKey + " / 색맹모드: " + blindMode +"\n");
            writer.close();
            System.out.println("설정 정보가 파일에 저장되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("파일에 설정 정보를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}