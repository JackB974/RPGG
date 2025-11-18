package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {

    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int velocityX;
    protected int velocityY;
    protected Texture texture;
    protected int floorLevelY = 25;
    protected int hitboxWidth;
    protected int hitboxHeight;
    protected int hitboxOffsetX;
    protected int hitboxOffsetY;
    protected boolean hasEnteredScreen = false;




    public GameObject(String texturePath, int posX, int posY, int width, int height, int hitboxWidth, int hitboxHeight) {
        this.texture = new Texture(texturePath);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.hitboxOffsetX = (width - hitboxWidth) / 2;
        this.hitboxOffsetY = (height - hitboxHeight) / 2;
    }

    public void update(float delta) {
        posX += (int) (velocityX * delta);
        posY += (int) (velocityY * delta);
        //Clamping
        int screenWidth = 1280;
        int screenHeight = 720;
        //right side
        if (posX + width < screenWidth && posX > 0) {
            hasEnteredScreen = true;
        }

        // Only apply clamping/bounce AFTER they entered the screen
        if (hasEnteredScreen) {
            if (posX < 0) posX = 0;
            if (posX + width > screenWidth) posX = screenWidth - width;
        }
    }

    //check this?
    public void render(SpriteBatch batch) {
        batch.draw(texture, posX, posY, width, height);
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }

    public int getX() { return posX; }
    public int getY() { return posY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Texture getTexture() { return texture; }

    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }


    public void setVelocity(int vx, int vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }

    public Rectangle getBounds() {
        return new Rectangle(posX + hitboxOffsetX, posY + hitboxOffsetY, hitboxWidth, hitboxHeight);
    }

}
