package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainScreen implements Screen {

    SpriteBatch batch;
    Texture playBtn;
    Texture background;
    Texture mage;
    Texture gamerGuy;
    Texture bfsmg;
    Texture title;

    int titleX = 150;
    int titleY = 400;
    int titleW = 300;
    int titleH = 80;

    int btnX = 200;
    int btnY = 250;
    int btnW = 200;
    int btnH = 80;

    int mageX = 100;
    int mageY = 100;
    int mageW = 100;
    int mageH = 80;

    int gamerGuyX = 250;
    int gamerGuyY = 100;
    int gamerGuyW = 100;
    int gamerGuyH = 80;

    int bfsmgX = 400;
    int bfsmgY = 100;
    int bfsmgW = 100;
    int bfsmgH = 80;


    MainGame game;

    public MainScreen(MainGame game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("mainMenu.png");
        mage = new Texture("mage.png");
        gamerGuy = new Texture("gamerGuy.png");
        bfsmg = new Texture("bfsmg.png");
        playBtn = new Texture("play.png");
        title = new Texture("title.png");

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new characterSelection(game));
                int fixedY = Gdx.graphics.getHeight() - screenY;

                if(screenX >= btnX && screenX <= btnX+btnW &&
                    fixedY >= btnY && fixedY <= btnY+btnH) {
                    System.out.println("PLAY CLICKED!");
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();


        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(title,     titleX,     titleY,     titleW,     titleH);
        batch.draw(mage,      mageX,      mageY,      mageW,      mageH);
        batch.draw(gamerGuy,  gamerGuyX,  gamerGuyY,  gamerGuyW,  gamerGuyH);
        batch.draw(bfsmg,     bfsmgX,     bfsmgY,     bfsmgW,     bfsmgH);
        batch.draw(playBtn,   btnX,       btnY,       btnW,       btnH);

        batch.end();
    }

    @Override public void resize(int width,int height){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){
        //use if =! for safer version // avoid crashing if texture wasn't created!
        if (batch!= null)batch.dispose();
        if (playBtn != null) playBtn.dispose();
        if (background != null) background.dispose();
        if (mage != null) mage.dispose();
        if (gamerGuy != null) gamerGuy.dispose();
        if (bfsmg != null) bfsmg.dispose();
        if (title != null) title.dispose();

    }
}
