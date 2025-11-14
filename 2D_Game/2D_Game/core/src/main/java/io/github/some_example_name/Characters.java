package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public abstract class Characters extends GameObject{
    protected int hp;
    protected int dmg ;
    protected String name;

    public Characters(String texturePath, int posX, int posY, int width, int height, int hp, int dmg, String name) {
        super(texturePath, posX, posY, width, height);
        this.hp = hp;
        this.dmg = dmg;
        this.name = name;
    }

    public int getHp(){return hp;}
    public int getDmg(){return dmg;}
    public String getName(){return name;}
    public void takeDmg(int dmgAmount){
        this.hp -= dmgAmount;
        if (this.hp < 0){
            this.hp = 0;
        }
    }
    public int dealDmg(){
        return this.dmg;
    }


}
