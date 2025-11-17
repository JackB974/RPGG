package io.github.some_example_name;

public abstract class Ennemies extends Characters{


    public Ennemies(String texturePath, int posX, int posY, int width, int height, int maxHp, int hitBoxWidth, int hitBoxHeight, float dmg, String name) {
        super(texturePath, posX, posY, width, height, hitBoxWidth, hitBoxHeight, maxHp, dmg, name);
    }


}
