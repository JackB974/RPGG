package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys;
import static io.github.some_example_name.Collision.checkCollision;
import static java.lang.Math.sqrt;

public class GameLogic implements Screen {
    SpriteBatch batch;
    Texture background;
    MainGame game;
    String selectedCharacter;
    Player player;
    Texture HUD;
    Texture hudBackground;
    Texture basicEnnemyHud;
    Texture flyingEnnemyHud;
    Texture bossHud;
    BitmapFont font;
    float basicSpawntimer;
    float flyingSpawntimer;
    boolean gamePaused;

    int killCount = 0;
    int BosskillCount = 0;

    int hpBarMaxWidth = 200;
    int hpBarHeight = 20;

    float enemyBarMaxWidth = 40;
    float enemyBarHeight = 3;

    List<Ennemies> enemies;
    List<FlyingEnnemies> flyingEnemies;
    List<Bullets> bullets;
    List<Bullets> bossBullets;
    List<Platform> Platforms;
    int basicspawnOffset = 0;
    int flyingspawnOffset = 0;
    boolean bossSpawned = false;
    Boss boss = null;
    int nextBossKillRequirement = 10;

    MusicManager musicManager;

    public GameLogic(MainGame game, String selectedCharacter) {
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
        bossHud = new Texture("white_pixel.png");
        musicManager = new MusicManager();

        musicManager.play("spaceship.wav", 0.4f, true);

        enemies = new ArrayList<>();
        flyingEnemies = new ArrayList<>();
        bullets = new ArrayList<>();
        bossBullets = new ArrayList<>();
        Platforms = new ArrayList<>();

        Platforms.add(new Platform("white_pixel.png", 100, 200, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 200, 400, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 450, 550, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 500, 200, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 600, 400, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 800, 550, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 1000, 200, 150, 20, 150, 10));
        Platforms.add(new Platform("white_pixel.png", 1200, 400, 150, 20, 150, 10));

        if (selectedCharacter.equals("bfsmg")) {
            player = new BFSMG();
        } else if (selectedCharacter.equals("gamerGuy")) {
            player = new GamerGuy();
        } else if (selectedCharacter.equals("classicMage")) {
            player = new ClassicMage();
        }

    }

    private void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            gamePaused = !gamePaused;
        } else if (gamePaused) {
            return;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            player.moveRight();
        } else if (Gdx.input.isKeyPressed(Keys.A)) {
            player.moveLeft();
        } else {
            player.stopMoving();
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            player.jump(delta);
        } else {
            player.fall(delta);
        }
        player.update(delta);


        for (Platform p : Platforms) {
            float platformTop = p.posY + p.height;
            float platformLeft = p.posX;
            float platformRight = p.posX + p.width;
            float playerBottom = player.posY;
            float playerLeft = player.posX;
            float playerRight = player.posX + player.width;

            if (player.velocityY < 0) {
                if (playerRight > platformLeft && playerLeft < platformRight) {
                    if (playerBottom <= platformTop && playerBottom >= platformTop - 20) {
                        player.posY = (int) platformTop;
                        player.velocityY = 0;
                        player.isGrounded = true;
                    }
                }
            }
        }

        basicSpawntimer += delta;
        flyingSpawntimer += delta;

        if (player.shouldShoot(delta)) {
            int playercenterX = player.getX() + player.getWidth() / 2;
            int playercenterY = player.getY() + player.getHeight() / 2;

            float mouseX = Gdx.input.getX();
            float mouseY = 720 - Gdx.input.getY(); // flip Y (because inversed in libGDX)

            float dx = mouseX - playercenterX;
            float dy = mouseY - playercenterY;

            float len = (float) sqrt(dx * dx + dy * dy);

            float dX = dx / len;
            float dY = dy / len;

            Bullets bullet = new Bullets("bullet.png", playercenterX, playercenterY, 16, 16, 8, 8);
            bullet.setDirection(dX, dY);
            bullet.setSpeed(2000f);
            bullet.setDamage(player.dealDmg());
            bullets.add(bullet);
        }
        Iterator<Bullets> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullets b = bulletIterator.next();
            b.update(delta);
            for (Ennemies ennemy : enemies) {
                if (checkCollision(b, ennemy)) {
                    ennemy.takeDmg(player.dealDmg());
                    b.bulletAlive = false;
                }
            }
            for (FlyingEnnemies flying : flyingEnemies) {
                if (checkCollision(b, flying)) {
                    flying.takeDmg(player.dealDmg());
                    b.bulletAlive = false;
                }
            }
            if (bossSpawned && boss != null) {
                if (checkCollision(b, boss)) {
                    boss.takeDmg(player.dealDmg());
                    b.bulletAlive = false;
                }
            }
            if (!b.bulletAlive) {
                bulletIterator.remove();
            }
        }
        Iterator<Bullets> bossBulletIterator = bossBullets.iterator();
        while (bossBulletIterator.hasNext()) {
            Bullets bb = bossBulletIterator.next();
            bb.update(delta);

            // If hits player
            if (checkCollision(bb, player)) {
                player.takeDmg(bb.dmg * delta);
                bb.bulletAlive = false;
            }

            if (!bb.bulletAlive) {
                bossBulletIterator.remove();
            }
        }
        if (enemies.size() == 0) {
            basicspawnOffset = 0;
        }
        if (basicSpawntimer >= 1f) {
            Ennemies e = new BasicEnnemies();
            int spawnX = 1280 + basicspawnOffset;
            int spawnY = 25;
            basicSpawntimer = 0;
            e.setPosition(spawnX, spawnY);
            enemies.add(e);
            basicspawnOffset += 100;
        }
        Iterator<Ennemies> it = enemies.iterator();

        while (it.hasNext()) {
            Ennemies e = it.next();
            // ==> dmg test
            //e.takeDmg(10 * delta);
            e.update(delta);
            if (checkCollision(player, e)) {
                player.takeDmg(e.dealDmg() * delta);
                //System.out.println("Player HP: " + player.getHp());
            }
            if (player.getHp() <= 0) {
                //player.isDead = true;
                game.setScreen(new GameOverScreen(game));
                return;

            }
            if (e.getHp() <= 0) {
                it.remove();
                killCount += 2;
            }
        }
        if (flyingEnemies.size() == 0) {
            flyingspawnOffset = 0;
        }
        if (killCount > 5) {
            if (flyingSpawntimer >= 1f) {
                FlyingEnnemies f = new FlyingEnnemies();
                int spawnX = MathUtils.random(0, 1280);
                int spawnY = 720 - f.getHeight() - 10;
                flyingSpawntimer = 0;
                f.setPosition(spawnX, spawnY);
                flyingEnemies.add(f);
                flyingspawnOffset += 100;
            }
            Iterator<FlyingEnnemies> ti = flyingEnemies.iterator();

            while (ti.hasNext()) {
                FlyingEnnemies f = ti.next();
                f.update(delta);
                if (checkCollision(player, f)) {
                    player.takeDmg(f.dealDmg() * delta);
                    System.out.println("Player HP: " + player.getHp());
                }

                if (f.getHp() <= 0) {
                    ti.remove();
                    killCount += 1;
                }
            }

        }
        if (!bossSpawned && killCount >= nextBossKillRequirement) {
            boss = new Boss();
            bossSpawned = true;
        }
        if (bossSpawned && boss != null) {
            boss.update(delta);
        }
        if (bossSpawned && boss != null && boss.getHp() <= 0) {
            bossSpawned = false;
            BosskillCount++;
            nextBossKillRequirement += 10;
            boss = null;
        }
        if (bossSpawned && boss.canShoot(delta)) {
            int bosscenterX = boss.getX() + boss.getWidth() / 2;
            int bosscenterY = boss.getY() + boss.getHeight() / 2;

            int playercenterX = player.getX() + player.getWidth() / 2;
            int playercenterY = player.getY() + player.getHeight() / 2;

            float dx = playercenterX - bosscenterX;
            float dy = playercenterY - bosscenterY;

            float len = (float) sqrt(dx * dx + dy * dy);

            float dX = dx / len;
            float dY = dy / len;

            Bullets bossBullet = new Bullets("bullet.png", bosscenterX, bosscenterY, 16, 16, 8, 8);
            bossBullet.setDirection(dX, dY);
            bossBullet.setSpeed(1000f);
            bossBullet.setDamage(boss.dealDmg());
            bossBullets.add(bossBullet);
        }
    }
        @Override
        public void render ( float delta){
            update(delta);
            float hpPercent = player.getHp() / player.getMaxHp();
            float currentHpWidth = hpBarMaxWidth * hpPercent;

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.draw(background, 0, 0, 1280, 720);
            batch.setColor(0.2f, 0.2f, 0.2f, 1);
            batch.draw(hudBackground, 20, 680, hpBarMaxWidth, hpBarHeight);
            batch.setColor(1, 1, 1, 1);
            batch.setColor(1, 0, 0, 1);
            batch.draw(HUD, 20, 680, currentHpWidth, hpBarHeight);
            batch.setColor(1, 1, 1, 1);
            font.getData().setScale(2f);
            font.draw(batch, "Puny Enemies Kills: " + killCount, 20, 580);
            font.getData().setScale(1f);

            font.getData().setScale(2f);
            font.draw(batch, "Big Bad Boss Kills: " + BosskillCount, 20, 480);
            font.getData().setScale(1f);

            batch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
            for (Platform platform : Platforms) {
                batch.setColor(0.65f, 0.40f, 0.20f, 1f);
                batch.draw(platform.getTexture(), platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
                batch.setColor(1, 1, 1, 1);
            }
            for (Bullets bullet : bullets) {
                batch.draw(bullet.getTexture(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            }
            for (Bullets bb : bossBullets) {
                batch.draw(bb.getTexture(), bb.getX(), bb.getY(), bb.getWidth(), bb.getHeight());
            }
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
            for (FlyingEnnemies f : flyingEnemies) {
                batch.draw(f.getTexture(), f.getX(), f.getY(), f.getWidth(), f.getHeight());
                float flyinghpPercent = f.getHp() / f.getMaxHp();
                float flyinghpWidth = enemyBarMaxWidth * flyinghpPercent;
                batch.setColor(0.2f, 0.2f, 0.2f, 1);
                batch.draw(flyingEnnemyHud,
                    f.getX() + (f.getWidth() - enemyBarMaxWidth) / 2,
                    f.getY() + f.getHeight() + 5,
                    enemyBarMaxWidth,
                    enemyBarHeight
                );
                batch.setColor(1, 0, 0, 1);
                batch.draw(flyingEnnemyHud,
                    f.getX() + (f.getWidth() - enemyBarMaxWidth) / 2,
                    f.getY() + f.getHeight() + 5,
                    flyinghpWidth,
                    enemyBarHeight
                );
                batch.setColor(1, 1, 1, 1);
            }
            if (bossSpawned) {
                batch.draw(boss.getTexture(), boss.getX(), boss.getY(), boss.getWidth(), boss.getHeight());
                float bosshpPercent = boss.getHp() / boss.getMaxHp();
                float bosshpWidth = enemyBarMaxWidth * bosshpPercent;
                batch.setColor(0.2f, 0.2f, 0.2f, 1);
                batch.draw(bossHud,
                    boss.getX() + (boss.getWidth() - enemyBarMaxWidth) / 2,
                    boss.getY() + boss.getHeight() + 5,
                    enemyBarMaxWidth,
                    enemyBarHeight
                );
                batch.setColor(1, 0, 0, 1);
                batch.draw(bossHud,
                    boss.getX() + (boss.getWidth() - enemyBarMaxWidth) / 2,
                    boss.getY() + boss.getHeight() + 5,
                    bosshpWidth,
                    enemyBarHeight);
                batch.setColor(1, 1, 1, 1);
            }


            batch.end();
        }


        @Override
        public void resize ( int width, int height){
        }
        @Override
        public void pause () {
        }
        @Override
        public void resume () {
        }
        @Override
        public void hide () {
            musicManager.stop();
        }
        @Override
        public void dispose () {
            batch.dispose();
            musicManager.dispose();
        }
    }



