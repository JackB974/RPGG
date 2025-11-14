package io.github.some_example_name;

public class BasicEnnemies extends Ennemies{

    public boolean movingLeft = true;

    public BasicEnnemies() {
        super("BasicEnnemies.png", 1100, 25, 64, 64, 100, 10, "BasicEnnemies");
    }

    int speed = 162;
    public void basicMvtRight(){
        setVelocity(speed, this.velocityY);
    }
    public void basicMvtLeft(){
        setVelocity(-speed, this.velocityY);
    }

    @Override
    public void update(float delta) {
        if(movingLeft){
            basicMvtLeft();
        }
        else{
            basicMvtRight();
        }
        super.update(delta);
    }

}
