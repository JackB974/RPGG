package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Input.Keys;
import static jdk.jfr.internal.consumer.EventLog.update;

public class GameLogic implements Screen{
    SpriteBatch batch;
    Texture background;
    MainGame game;
    String selectedCharacter;
    Player player;
    Ennemies basicEnnemy;
    Ennemies flyingEnnemy;
    Ennemies Boss;

    public GameLogic(MainGame game, String selectedCharacter){
        this.game = game;
        this.selectedCharacter = selectedCharacter;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        batch = new SpriteBatch();
        background = new Texture("Level1_background.png");
        if (selectedCharacter.equals("bfsmg")) {
            player = new BFSMG();
        }
        else if (selectedCharacter.equals("gamerGuy")) {
            player = new GamerGuy();
        }

        else if (selectedCharacter.equals("classicMage")) {
            player = new ClassicMage();
        }
        basicEnnemy = new BasicEnnemies();
        flyingEnnemy = new FlyingEnnemies();
        Boss = new Boss();
    }
    private void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.D)){
            player.moveRight();
        }
        else if(Gdx.input.isKeyPressed(Keys.A)){
            player.moveLeft();
        }
       // else if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
        //    player.jump();
        //} ==> need Gravity
        else{
            player.stopMoving();
        }
        player.update(delta);
        basicEnnemy.update(delta);

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        batch.draw(basicEnnemy.getTexture(), basicEnnemy.getX(), basicEnnemy.getY(), basicEnnemy.getWidth(), basicEnnemy.getHeight());
        batch.draw(flyingEnnemy.getTexture(), flyingEnnemy.getX(), flyingEnnemy.getY(), flyingEnnemy.getWidth(), flyingEnnemy.getHeight());
        batch.draw(Boss.getTexture(), Boss.getX(), Boss.getY(), Boss.getWidth(), Boss.getHeight());
        batch.end();
    }



    @Override
    public void resize(int width,int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();

    }

}


