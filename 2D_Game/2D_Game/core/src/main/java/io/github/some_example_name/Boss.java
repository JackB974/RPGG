package io.github.some_example_name;

public class Boss extends Ennemies{

    float VelocityX = 200;
    float VelocityY = 150;
    float shootTimer = 0;
    float shootCooldown = 1f;

    public Boss() {
        super("Boss.png", 0, 0, 200, 160, 2500, 200, 160, 250, "Boss");
    }
    @Override
    public void update(float delta) {
        posX += VelocityX * delta;
        posY += VelocityY * delta;

        if (posX < 0 || posX + width > 1280) {
            VelocityX = -VelocityX;
        }

        if (posY < 0 || posY + height > 720) {
            VelocityY = -VelocityY;
        }
    }
    public boolean canShoot(float delta) {
        shootTimer += delta;
        if (shootTimer >= shootCooldown) {
            shootTimer = 0;
            return true;
        }
        return false;
    }

}
