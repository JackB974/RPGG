package io.github.some_example_name;


import com.badlogic.gdx.math.Rectangle;

public class Collision {
    public static boolean checkCollision(GameObject a, GameObject b) {
        return a.getBounds().overlaps(b.getBounds());
    }

    public static void resolveCollision(GameObject a, GameObject b) {
        // maybe adjust positions or apply physics here
    }
}

