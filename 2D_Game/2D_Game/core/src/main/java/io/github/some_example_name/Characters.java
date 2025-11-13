package io.github.some_example_name;

public abstract class Characters extends GameObject{
    int hp = 0;
    int dmg = 0;
    String name = "";

    public Characters(String texturePath, int posX, int posY, int width, int height) {
        super(texturePath, posX, posY, width, height);
    }
}
