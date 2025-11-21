package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class characterSelection implements Screen {
    SpriteBatch batch;
    Texture background;
    Texture mage;
    Texture gamerGuy;
    Texture bfsmg;
    Texture bfsmgBtn;
    Texture classicMageBtn;
    Texture gamerGuyBtn;
    BitmapFont font;
    ShapeRenderer shapeRenderer;


    // mage btn
    int classicMageBtnX = 50;
    int classicMageBtnY = 75;
    int classicMageBtnW = 250;
    int classicMageBtnH = 150;

    // mage
    int mageX = 50;
    int mageY = 75;
    int mageW = 250;
    int mageH = 150;

    // gamerGuy btn
    int gamerGuyBtnX = 500;
    int gamerGuyBtnY = 75;
    int gamerGuyBtnW = 250;
    int gamerGuyBtnH = 150;

    // gamerGuy
    int gamerGuyX = 500;
    int gamerGuyY = 75;
    int gamerGuyW = 250;
    int gamerGuyH = 150;

    // bfsmg btn
    int bfsmgBtnX = 900;
    int bfsmgBtnY = 75;
    int bfsmgBtnW = 250;
    int bfsmgBtnH = 150;

    // bfsmg
    int bfsmgX = 900;
    int bfsmgY = 75;
    int bfsmgW = 250;
    int bfsmgH = 150;


    MainGame game;

    public characterSelection(MainGame game){
        this.game = game;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        background = new Texture("characterSelectionBack.png");
        classicMageBtn = new Texture("characterSelectionBtn1.png");
        mage = new Texture("mage.png");
        gamerGuyBtn = new Texture("characterSelectionBtn1.png");
        gamerGuy = new Texture("gamerGuy.png");
        bfsmg = new Texture("bfsmg.png");
        bfsmgBtn = new Texture("characterSelectionBtn1.png");
        shapeRenderer = new ShapeRenderer();


        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int fixedY = 720 - screenY;

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
        batch.draw(background, 0, 0, 1280, 720);
        batch.setColor(1,1,1,1);
        batch.draw(mage,      mageX,      mageY,      mageW,      mageH);
        batch.draw(gamerGuy,  gamerGuyX,  gamerGuyY,  gamerGuyW,  gamerGuyH);
        batch.draw(bfsmg,     bfsmgX,     bfsmgY,     bfsmgW,     bfsmgH);

        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 0, 0, 1); // Red rectangles

        // mage
        shapeRenderer.rect(classicMageBtnX, classicMageBtnY, classicMageBtnW, classicMageBtnH);

        // gamer guy
        shapeRenderer.rect(gamerGuyBtnX, gamerGuyBtnY, gamerGuyBtnW, gamerGuyBtnH);

        // bfsmg
        shapeRenderer.rect(bfsmgBtnX, bfsmgBtnY, bfsmgBtnW, bfsmgBtnH);

        shapeRenderer.end();

        batch.begin();
        //transparentbtn
        batch.setColor(0,0,0,0);
        batch.draw(bfsmgBtn,   bfsmgBtnX,       bfsmgBtnY,       bfsmgBtnW,       bfsmgBtnH);
        //goback to normal color
        batch.setColor(1,1,1,1);

        font.setColor(1, 0, 0, 1);
        font.getData().setScale(2f);
        font.draw(batch, "Press ESC to Pause" , 20, 700);
        font.getData().setScale(1f);
        font.getData().setScale(2f);
        font.draw(batch, "Flying Enemies kill points: 1" , 20, 650);
        font.getData().setScale(1f);
        font.getData().setScale(2f);
        font.draw(batch, "Walking Enemies kill points: 2" , 20, 600);
        font.getData().setScale(1f);
        font.getData().setScale(2f);
        font.draw(batch, "Press Q to go left / Press D to go right" , 700, 700);
        font.getData().setScale(1f);
        font.getData().setScale(2f);
        font.draw(batch, "SPACE to Jump" , 700, 650);
        font.getData().setScale(1f);
        font.getData().setScale(2f);
        font.draw(batch, "Mouse to aim / MOUSE LEFT CLick to Shoot" , 700, 600);
        font.getData().setScale(1f);
        font.getData().setScale(3f);
        font.draw(batch, "Click on your hero (red box)!" , 450, 500);
        font.getData().setScale(1f);
        font.setColor(1, 1, 1, 1);

        font.getData().setScale(2f);
        font.draw(batch, "Classic Mage! " , 50, 400);
        font.getData().setScale(1f);

        font.draw(batch, "Attack: 50 " , 100, 350);

        font.draw(batch, "Hp: 35 " , 100, 300);
        font.getData().setScale(2f);
        font.draw(batch, "Gamer Guy! " , 500, 400);
        font.getData().setScale(1f);
        font.draw(batch, "Attack: 40" , 500, 350);

        font.draw(batch, "Hp: 42 " , 500, 300);
        font.getData().setScale(2f);
        font.draw(batch, "Big Fucking Space Marine Guy! " , 800, 400);
        font.getData().setScale(1f);
        font.draw(batch, "Attack: 40 " , 900, 350);

        font.draw(batch, "Hp: 50 " , 900, 300);


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


