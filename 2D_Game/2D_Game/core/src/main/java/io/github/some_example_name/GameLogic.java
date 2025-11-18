package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys;
import static io.github.some_example_name.Collision.checkCollision;

public class GameLogic implements Screen{
    SpriteBatch batch;
    Texture background;
    MainGame game;
    String selectedCharacter;
    Player player;
    //Ennemies basicEnnemy;
    //Ennemies flyingEnnemy;
    Ennemies Boss;
    Texture HUD;
    Texture hudBackground;
    Texture basicEnnemyHud;
    Texture flyingEnnemyHud;
    BitmapFont font;

    int killCount = 0;

    int hpBarMaxWidth = 200;
    int hpBarHeight = 20;

    float enemyBarMaxWidth = 40;
    float enemyBarHeight = 3;

    List<Ennemies> enemies;
    List<Bullets> bullets;
    int spawnOffset = 0;

    public GameLogic(MainGame game, String selectedCharacter){
        this.game = game;
        this.selectedCharacter = selectedCharacter;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture("Level1_background.png");
        HUD = new Texture("white_pixel.png");
        hudBackground = new Texture("white_pixel.png");
        basicEnnemyHud = new Texture("white_pixel.png");
        flyingEnnemyHud = new Texture("white_pixel.png");

        enemies = new ArrayList<>();
        bullets = new ArrayList<>();

        if (selectedCharacter.equals("bfsmg")) {
            player = new BFSMG();
        }
        else if (selectedCharacter.equals("gamerGuy")) {
            player = new GamerGuy();
        }

        else if (selectedCharacter.equals("classicMage")) {
            player = new ClassicMage();
        }
        //basicEnnemy = new BasicEnnemies();
        //flyingEnnemy = new FlyingEnnemies();
        Boss = new Boss();
    }
    private void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.D)){
            player.moveRight();
        }
        else if(Gdx.input.isKeyPressed(Keys.A)){
            player.moveLeft();
        }
       else{
           player.stopMoving();
        }

       if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            player.jump(delta);
        }
       else{
           player.fall(delta);
       }
        player.update(delta);

//        if (player.shouldShoot()) {
//            // spawn bullet here
//        }


        if (enemies.size() == 0) {
            spawnOffset = 0;
        }


        if (enemies.size() < 5){
            Ennemies e = new BasicEnnemies();
            int spawnX = 1280 +  spawnOffset;
            int spawnY =  25;
            e.setPosition(spawnX, spawnY);
            enemies.add(e);
            spawnOffset += 150;
       }
        Iterator<Ennemies> it = enemies.iterator();

        while (it.hasNext()) {
            Ennemies e = it.next();

            //e.takeDmg(10 * delta);
            e.update(delta);
            if (checkCollision(player, e)) {
                player.takeDmg(e.dealDmg() * delta);
                System.out.println("Player HP: " + player.getHp());
            }

            if (e.getHp() <= 0) {
                it.remove();
                killCount++;
            }
        }

        //basicEnnemy.update(delta);
        //flyingEnnemy.update(delta);

//        if(checkCollision(player, basicEnnemy ) || checkCollision(player, flyingEnnemy) ){
//            //to do: use delta
//            player.takeDmg(basicEnnemy.dealDmg());
//            System.out.println(player.getHp());
//        }

    }

    @Override
    public void render(float delta) {
        update(delta);
        float hpPercent = player.getHp() / player.getMaxHp();
        float currentHpWidth = hpBarMaxWidth * hpPercent;

        //float basicEnnemyHpPercent = basicEnnemy.getHp() / basicEnnemy.getMaxHp();
        //float basicennemycurrentHpWidth = enemyBarMaxWidth * basicEnnemyHpPercent;

        //float flyingEnnemyHpPercent = flyingEnnemy.getHp() / flyingEnnemy.getMaxHp();
        //float flyingEnnemyCurrentHpWidth = enemyBarMaxWidth * flyingEnnemyHpPercent;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1280, 720);
        batch.setColor(0.2f, 0.2f, 0.2f, 1);
        batch.draw(hudBackground,20, 680, hpBarMaxWidth , hpBarHeight);
        batch.setColor(1, 1, 1, 1 );
        batch.setColor(1, 0, 0, 1 );
        batch.draw(HUD, 20, 680, currentHpWidth, hpBarHeight );
        batch.setColor(1, 1, 1, 1 );
        font.draw(batch, "Kills: " + killCount, 20, 580);

//        batch.setColor(1, 0, 0, 1 );
//        batch.draw(basicEnnemyHud, basicEnnemy.getX() + (basicEnnemy.getWidth() - enemyBarMaxWidth) / 2,  basicEnnemy.getY() + (basicEnnemy.getHeight() + 5), basicennemycurrentHpWidth, enemyBarHeight );
//        batch.setColor(1, 1, 1, 1 );
//        batch.setColor(1, 0, 0, 1 );
//        batch.draw(flyingEnnemyHud, flyingEnnemy.getX() + (flyingEnnemy.getWidth() - enemyBarMaxWidth) / 2,  flyingEnnemy.getY() + (flyingEnnemy.getHeight() + 5), flyingEnnemyCurrentHpWidth, enemyBarHeight );
//        batch.setColor(1, 1, 1, 1 );
        batch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for (Ennemies e : enemies) {
            batch.draw(e.getTexture(), e.getX(), e.getY(), e.getWidth(), e.getHeight());
            float ennemyhpPercent = e.getHp() / e.getMaxHp();
            float hpWidth = enemyBarMaxWidth * ennemyhpPercent;

            // Draw HP bar background
            batch.setColor(0.2f, 0.2f, 0.2f, 1);
            batch.draw(basicEnnemyHud,
                e.getX() + (e.getWidth() - enemyBarMaxWidth) / 2,
                e.getY() + e.getHeight() + 5,
                enemyBarMaxWidth,
                enemyBarHeight
            );
            batch.setColor(1, 0, 0, 1);
            batch.draw(basicEnnemyHud,
                e.getX() + (e.getWidth() - enemyBarMaxWidth) / 2,
                e.getY() + e.getHeight() + 5,
                hpWidth,
                enemyBarHeight
            );

            batch.setColor(1, 1, 1, 1); // reset color!
        }

//        batch.draw(basicEnnemy.getTexture(), basicEnnemy.getX(), basicEnnemy.getY(), basicEnnemy.getWidth(), basicEnnemy.getHeight());
//        batch.draw(flyingEnnemy.getTexture(), flyingEnnemy.getX(), flyingEnnemy.getY(), flyingEnnemy.getWidth(), flyingEnnemy.getHeight());
//        //batch.draw(Boss.getTexture(), Boss.getX(), Boss.getY(), Boss.getWidth(), Boss.getHeight());
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


