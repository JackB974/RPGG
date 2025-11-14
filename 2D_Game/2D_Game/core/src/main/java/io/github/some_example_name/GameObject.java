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

    public GameObject(String texturePath, int posX, int posY, int width, int height) {
        this.texture = new Texture(texturePath);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void update(float delta) {
        posX += (int) (velocityX * delta);
        posY += (int) (velocityY * delta);
        //Clamping
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        //right side
        if (posX + width > screenWidth){
            posX = screenWidth - width;
        }
        //left side ==>2D physic left is always 0
        if (posX < 0) posX = 0;


    }

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

    public void setVelocity(int vx, int vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }

    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

}
