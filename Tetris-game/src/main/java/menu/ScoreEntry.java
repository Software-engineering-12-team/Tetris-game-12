package main.java.menu;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private String name;
    private String difficulty;
    private String mode;
    private int score;

    public ScoreEntry(String name, String difficulty, String mode, int score) {
        this.name = name;
        this.difficulty = difficulty;
        this.mode = mode;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getMode() {
        return mode;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        return Integer.compare(other.score, score); // 내림차순으로 변경
    }

    @Override
    public String toString() {
        return "이름: " + name + " / 난이도: " + difficulty + " / 모드: " + mode + " / 점수: " + score + "점";
    }
}