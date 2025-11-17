package io.github.some_example_name;

public abstract class Player extends Characters {

    int speed = 300;

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
        if(posY <= this.floorLevelY) {
            setVelocity(this.velocityX, 900);
        }
        else{
            this.fall(delta);
        }
    }
   public void fall(float delta){
        if(this.posY <= this.floorLevelY) {
            setVelocity(this.velocityX, 0);
        }
        else{
            //to do use delta
            setVelocity(this.velocityX, this.velocityY -= 10 );
        }
   }



    public Player(String texturePath, int posX, int posY, int width, int height, int hitBoxWidth, int hitBoxHeight, int maxHp, int dmg, String name) {
        super(texturePath, posX, posY, width, height, hitBoxWidth, hitBoxHeight, maxHp, dmg, name);
    }


}
