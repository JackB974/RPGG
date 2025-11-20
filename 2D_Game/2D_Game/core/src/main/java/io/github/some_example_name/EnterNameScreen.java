package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import java.util.Collections;
import java.util.Comparator;

public class EnterNameScreen implements Screen {

    MainGame game;
    int finalScore;

    Stage stage;
    Skin skin;

    public EnterNameScreen(MainGame game, int finalScore) {
        this.game = game;
        this.finalScore = finalScore;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(
            Gdx.files.internal("uiskin.json"),
            new TextureAtlas(Gdx.files.internal("uiskin.atlas"))
        );


        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextField nameField = new TextField("", skin);
        TextButton okButton = new TextButton("OK", skin);

        table.add(nameField).width(300).pad(50);
        table.row();
        table.add(okButton).width(200).pad(50);

        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String name = nameField.getText();
                HighScore score = new HighScore(name, finalScore);
                game.highScores.add(score);
                Collections.sort(game.highScores, Comparator.comparing(HighScore::getScore).reversed());
                if (game.highScores.size() > 10) {
                    game.highScores.remove(game.highScores.size() - 1);
                }
                game.saveHighScores();
                game.setScreen(new highScoreScreen(game));
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);   // updates UI logic (button clicks, cursor blink, etc.)
        stage.draw();       // draws the UI elements
        }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
