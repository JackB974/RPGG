package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Collections;
import java.util.Comparator;

public class GameOverScreen implements Screen {
    Texture background;
    SpriteBatch batch;
    int finalScore;
    MainGame game;

    boolean qualifies;

    public GameOverScreen(MainGame game, int score) {
        this.game = game;
        this.finalScore = score;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("GameOver.png");
        Collections.sort(game.highScores, Comparator.comparing(HighScore::getScore).reversed());

        int lowestScore;

        // If there are NO high scores yet
        if (game.highScores.isEmpty()) {
            lowestScore = 0;
        } else {
            lowestScore = game.highScores.get(game.highScores.size() - 1).getScore();
        }
        if (game.highScores.size() < 10 || finalScore > lowestScore) {
            qualifies = true;
        }
        else {
            qualifies = false;
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1280, 720);
        batch.end();


        if (Gdx.input.justTouched()) {
            if (qualifies) {
                game.setScreen(new EnterNameScreen(game, finalScore));
            } else {
                game.setScreen(new highScoreScreen(game));
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
    }
}
