package io.github.some_example_name;

import com.badlogic.gdx.Input;

public abstract class Player extends Characters {


    public Player(String texturePath, int posX, int posY, int width, int height, int hp, int dmg, String name) {
        super(texturePath, posX, posY, width, height, hp, dmg, name);
    }

    // Key event movement
    //key event attack
}
