package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public abstract class Player extends Characters {

    int speed = 300;
    float fireRate = 0.1f;
    float shotCooldown = 0f;
    boolean isGrounded = true;
    //boolean isDead = false;

    public void moveRight(){
        setVelocity(speed, this.velocityY);
    }

    public void moveLeft(){
        setVelocity(-speed, this.velocityY);
    }

    public void stopMoving(){
        setVelocity(0, this.velocityY);
    }

    public void jump(float delta) {
        if(isGrounded) {
            setVelocity(this.velocityX, 900);
            isGrounded = false;
        }


    }
   public void fall(float delta){
        if(this.posY <= this.floorLevelY) {
            setVelocity(this.velocityX, 0);
            isGrounded = true;
        }
        else{
            isGrounded = true;
            //to do use delta
            setVelocity(this.velocityX, this.velocityY -= 10 );
        }
   }

    public Player(String texturePath, int posX, int posY, int width, int height, int hitBoxWidth, int hitBoxHeight, int maxHp, int dmg, String name) {
        super(texturePath, posX, posY, width, height, hitBoxWidth, hitBoxHeight, maxHp, dmg, name);

    }

    public boolean shouldShoot(float delta){
        shotCooldown -= delta;
        if(shotCooldown <= 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            shotCooldown = fireRate;
            return true;

        }
        return false;
    }
}
