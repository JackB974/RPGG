package io.github.some_example_name;

import com.badlogic.gdx.Gdx;

public class FlyingEnnemies extends Ennemies {

    public boolean movingDown = true;
    public boolean movingLeft = true;

    public FlyingEnnemies() {
        super("FlyingEnnemies.png", 0, 0, 80, 80, 550, 32, 32, 5, "FlyingEnnemies");
    }

    int speed = 225;
    public void firstMvtDown(){
        setVelocity(-150, -speed);
    }
    public void firstMvtUp(){
        setVelocity(150, speed);
    }
    public void secondMvtdown(){setVelocity(200, -speed);}
    public void secondMvtUp(){setVelocity(-200, -speed);}

    @Override
    public void update(float delta) {
        if(movingDown){
            firstMvtDown();
        }
        else{
            firstMvtUp();
        }

        super.update(delta);

        if(this.posY <= 0){
            movingDown = false;
        }
        else if(this.posY + this.height >= 720){
            movingDown = true;
        }

        if(this.posX <= 0){
            movingLeft = false  ;
        }


        else if(this.posX + this.width >= 1280){
            movingLeft = true  ;
        }
        if (movingLeft) {
            this.velocityX = -Math.abs(this.velocityX);
        } else {
            this.velocityX = Math.abs(this.velocityX);
        }
    }

}
