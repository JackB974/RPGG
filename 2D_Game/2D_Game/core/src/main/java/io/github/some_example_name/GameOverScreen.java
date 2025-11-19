package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen implements Screen {
    Texture background;
    SpriteBatch batch;

    MainGame game;

    public GameOverScreen (MainGame game){
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        background = new Texture("GameOver.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, 1280, 720);
        batch.end();
        if (Gdx.input.justTouched()) {
            game.setScreen(new characterSelection(game));

        }
    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
    }
}
