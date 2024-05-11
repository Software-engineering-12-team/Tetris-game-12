package main.java;

import java.util.Scanner;

public class testcode{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("게임 난이도를 선택하세요 (Easy, Normal, Hard):");
        String difficulty = scanner.nextLine();
        scanner.close();

        // 각 블록의 생성 횟수를 기록할 배열
        int[] blockCounts = new int[8]; 

        // 테스트 횟수
        int testCount = 100000;

        // 테스트를 수행하여 블록 생성 횟수 기록
        for (int i = 0; i < testCount; i++) {
            setRandomBlock(difficulty, blockCounts);
        }

        // 결과 출력
        for (int i = 1; i <= 7; i++) {
            double probability = (double) blockCounts[i] / testCount * 100;
            System.out.printf("블록 %d: %.2f%%\n", i, probability);
        }
    }

    // 테스트를 위한 setRandomBlock 메서드, 실제 구현과는 다르게 작성되었습니다.
    public static void setRandomBlock(String difficulty, int[] blockCounts) {
        int mode = 0;
        if (difficulty.equals("Easy")) {
            mode = 1; // Easy 모드
        } else if (difficulty.equals("Hard")) {
            mode = 2; // Hard 모드
        }

        double[] easyProbabilities = {0, 0.1389, 0.1389, 0.1667, 0.1389, 0.1389, 0.1389, 0.1389, 0};
        double[] hardProbabilities = {0, 0.1471, 0.1471, 0.1176, 0.1471, 0.1471, 0.1471, 0.1471, 0};
        double[] probabilities;

        if (mode == 1) { // Easy 모드
            probabilities = easyProbabilities;
        } else if (mode == 2) { // Hard 모드
            probabilities = hardProbabilities;
        } else { // Normal 모드
            probabilities = new double[]{0, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 1.0 / 7, 0};
        }

        double random = Math.random(); // 0 이상 1 미만의 난수
        double sum = 0.0;
        int blockIndex = 0; // 선택된 블록의 인덱스

        // 확률에 따라 블록 선택
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] == 0) continue; // 사용되지 않는 블록은 건너뜁니다.
            sum += probabilities[i];
            if (random <= sum) {
                blockIndex = i;
                break;
            }
        }

        // 블록 번호 기록
        blockCounts[blockIndex]++;
    }
}
