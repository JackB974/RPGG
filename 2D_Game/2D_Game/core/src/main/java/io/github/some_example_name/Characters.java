package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public abstract class Characters extends GameObject{
    protected int maxHp;
    protected float hp;
    protected float dmg ;
    protected String name;

    public Characters(String texturePath, int posX, int posY, int width, int height, int hitBoxWidth, int hitBoxHeight, int maxHp, float dmg, String name) {
        super(texturePath, posX, posY, width, height, hitBoxWidth, hitBoxHeight);
        this.maxHp = maxHp;
        this.dmg = dmg;
        this.name = name;
        this.hp = maxHp;
    }

    public int getMaxHp(){return maxHp;}
    public float getHp(){return hp;}
    public String getName(){return name;}
    public void takeDmg(float dmgAmount){
        this.hp -= dmgAmount;
        if (this.hp < 0){
            this.hp = 0;
        }
    }
    public float dealDmg(){
        return this.dmg;
    }


}
