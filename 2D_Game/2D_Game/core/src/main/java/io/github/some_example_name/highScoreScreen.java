package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class highScoreScreen implements Screen {

    Texture background;
    MainGame game;
    SpriteBatch batch;

    Stage stage;
    Skin skin;

    public highScoreScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        background = new Texture("HighScoreBackground.png");
        skin = new Skin(
            Gdx.files.internal("uiskin.json"),
            new TextureAtlas(Gdx.files.internal("uiskin.atlas"))
        );

        // ROOT LAYOUT (two columns)
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // LEFT BUTTON COLUMN
        TextButton backButton = new TextButton("Character Selection Menu", skin);
        TextButton exitButton = new TextButton("Exit Game", skin);

        Table leftColumn = new Table();
        leftColumn.add(backButton).pad(15).width(220).height(60);
        leftColumn.row();
        leftColumn.add(exitButton).pad(15).width(220).height(60);

        // RIGHT SCORE COLUMN
        Table scoreColumn = new Table();
        Label title = new Label("HIGH SCORES", skin);
        scoreColumn.add(title).pad(20);
        scoreColumn.row();

        for (HighScore s : game.highScores) {
            Label scoreLabel = new Label(s.getName() + " : " + s.getScore(), skin);
            scoreColumn.add(scoreLabel).pad(5);
            scoreColumn.row();
        }

        // add to root
        root.add(leftColumn).left().padLeft(20).padTop(40);

        root.add(scoreColumn).left().expandY().padLeft(50);

        // button actions
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new characterSelection(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1280, 720);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
