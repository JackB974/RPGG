package io.github.some_example_name;

import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    List<HighScore> highScores;

    public void loadHighScores() {
        Json json = new Json();
        FileHandle scoreFileHandle;

        if(Gdx.files.local("Highscore.json").exists()){
            scoreFileHandle = Gdx.files.local("Highscore.json");
        }
        else{
            scoreFileHandle = Gdx.files.internal("Highscore.json");
        }
        String textScore = scoreFileHandle.readString();
        highScores = json.fromJson(ArrayList.class, HighScore.class, textScore);
        if (highScores == null) {
            highScores = new ArrayList<>();
        }
    }

    public void saveHighScores() {
        Json json = new Json();
        String scoreJson = json.toJson(highScores);
        FileHandle scoreFileHandleToJson;
        scoreFileHandleToJson = Gdx.files.local("Highscore.json");
        scoreFileHandleToJson.writeString(scoreJson, false);

    }

    @Override
    public void create() {
        loadHighScores();
        setScreen(new MainScreen(this));

    }
}
