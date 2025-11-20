package io.github.some_example_name;

public class HighScore {
    String name;
    int score;

    // Required for LibGDX JSON loading
    public HighScore() {}

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
