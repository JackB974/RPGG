package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private Music music;

    public void play(String path, float volume, boolean looping) {
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(looping);
        music.setVolume(volume);
        music.play();
    }

    public void stop() {
        if (music != null) music.stop();
    }

    public void dispose() {
        if (music != null) music.dispose();
    }
}
