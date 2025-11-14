package io.github.some_example_name;

public class Boss extends Ennemies{
    public Boss(String texturePath, int posX, int posY, int width, int height, int hp, int dmg, String name) {
        super("Boss.png", posX, posY, width, height, 300, 35, "Boss");
    }
}
