package io.github.some_example_name;

public class Bullets extends GameObject{
    float directionX;
    float directionY;
    float speed;
    float dmg;
    boolean bulletAlive = true;


    public Bullets(String texturePath, int posX, int posY, int width, int height, int hitboxWidth, int hitboxHeight) {
        super(texturePath, posX, posY, width, height, hitboxWidth, hitboxHeight);
    }

    public void setDirection(float dx, float dy){
        this.directionX = dx;
        this.directionY = dy;
   }
    public void setDamage(float dmg){
        this.dmg = dmg;
   }
    public void setSpeed(float speed){
        this.speed = speed;
   }
   @Override
    public void update(float delta) {
        float newX = this.posX+ (this.directionX * this.speed * delta);
        float newY = this.posY + (this.directionY * this.speed * delta);
        this.posX = (int) newX;
        this.posY = (int) newY;


        if (this.posX > 1280 || this.posX + this.width < 0 || this.posY > 720 || this.posY + this.height < 0)
        {
            bulletAlive = false;
        }
    }

}
