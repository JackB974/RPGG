package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class characterSelection implements Screen {
    SpriteBatch batch;
    Texture background;
    Texture mage;
    Texture gamerGuy;
    Texture bfsmg;
    Texture bfsmgBtn;
    Texture classicMageBtn;
    Texture gamerGuyBtn;


    //mage btn
    int classicMageBtnX = 25;
    int classicMageBtnY = 50;
    int classicMageBtnW = 250;
    int classicMageBtnH = 200;

    //mage char
    int mageX = 25;
    int mageY = 50;
    int mageW = 250;
    int mageH = 200;

    //GG btn
    int gamerGuyBtnX = 250;
    int gamerGuyBtnY = 40;
    int gamerGuyBtnW = 250;
    int gamerGuyBtnH = 200;

    //gg char
    int gamerGuyX = 250;
    int gamerGuyY = 40;
    int gamerGuyW = 250;
    int gamerGuyH = 200;

    // selectionbtn bfsmg
    int bfsmgBtnX = 450;
    int bfsmgBtnY = 50;
    int bfsmgBtnW = 250;
    int bfsmgBtnH = 200;

    //character bfsmg
    int bfsmgX = 450;
    int bfsmgY = 50;
    int bfsmgW = 250;
    int bfsmgH = 200;

    MainGame game;

    public characterSelection(MainGame game){
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("characterSelectionBack.png");
        classicMageBtn = new Texture("characterSelectionBtn1.png");
        mage = new Texture("mage.png");
        gamerGuyBtn = new Texture("characterSelectionBtn1.png");
        gamerGuy = new Texture("gamerGuy.png");
        bfsmg = new Texture("bfsmg.png");
        bfsmgBtn = new Texture("characterSelectionBtn1.png");


        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int fixedY = Gdx.graphics.getHeight() - screenY;

                // BFSMg selection btn
                if(screenX >= bfsmgBtnX && screenX <= bfsmgBtnX+bfsmgBtnW &&
                    fixedY >= bfsmgBtnY && fixedY <= bfsmgBtnY+bfsmgBtnH) {
                    game.setScreen(new GameLogic(game, "bfsmg"));
                    return true;
                }

                //gamerGuy selection Btn
                if(screenX >= gamerGuyBtnX && screenX <= gamerGuyBtnX+gamerGuyBtnW &&
                    fixedY >= gamerGuyBtnY && fixedY <= gamerGuyBtnY+gamerGuyBtnH) {
                    game.setScreen(new GameLogic(game, "gamerGuy"));
                    return true;
                }

                //Mage selection btn
                if(screenX >= classicMageBtnX && screenX <= classicMageBtnX+classicMageBtnW &&
                    fixedY >= classicMageBtnY && fixedY <= classicMageBtnY+classicMageBtnH) {
                    game.setScreen(new GameLogic(game, "classicMage"));
                    return true;
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

        batch.setColor(1,1,1,0.5f);
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1,1,1,1);
        batch.draw(mage,      mageX,      mageY,      mageW,      mageH);
        batch.draw(gamerGuy,  gamerGuyX,  gamerGuyY,  gamerGuyW,  gamerGuyH);
        batch.draw(bfsmg,     bfsmgX,     bfsmgY,     bfsmgW,     bfsmgH);

        //transparentbtn
        batch.setColor(0,0,0,0);
        batch.draw(bfsmgBtn,   bfsmgBtnX,       bfsmgBtnY,       bfsmgBtnW,       bfsmgBtnH);
        //goback to normal color
        batch.setColor(1,1,1,1);

        batch.end();
    }

    @Override public void resize(int width,int height){}
    @Override public void pause(){}
    @Override public void resume(){}
    @Override public void hide(){}
    @Override public void dispose(){
        //dispose of everything to free up memory
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
        if (mage != null) mage.dispose();
        if (gamerGuy != null) gamerGuy.dispose();
        if (bfsmg != null) bfsmg.dispose();
        if (classicMageBtn != null) classicMageBtn.dispose();
        if (gamerGuyBtn != null) gamerGuyBtn.dispose();
        if (bfsmgBtn != null) bfsmgBtn.dispose();
    }
}


