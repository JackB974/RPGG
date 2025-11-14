package io.github.some_example_name;

import com.badlogic.gdx.Input;

public abstract class Player extends Characters {

    int speed = 200;

    public void moveRight(){
        setVelocity(speed, this.velocityY);
    }

    public void moveLeft(){
        setVelocity(-speed, this.velocityY);
    }

    public void stopMoving(){
        setVelocity(0, this.velocityY);
    }

    //public void jump(){
    //    setVelocity(this.velocityX, 200);
    //} ==> need Gravity


    public Player(String texturePath, int posX, int posY, int width, int height, int hp, int dmg, String name) {
        super(texturePath, posX, posY, width, height, hp, dmg, name);
    }

    // Key event movement
    //key event attack
}
