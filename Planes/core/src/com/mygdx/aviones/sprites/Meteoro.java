package com.mygdx.aviones.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.aviones.Aviones;
import java.util.Random;

public class Meteoro {
    public static final int METEOR_WIDTH = 30;
    private static final int MAX = (Aviones.HEIGHT / 2) - METEOR_WIDTH;
    private Rectangle bounds;
    private Texture meteoro;
    private Vector2 posicion;
    private Random rand;

    public Meteoro(float x) {
        meteoro = new Texture("meteoro.png");
        rand = new Random();
        posicion = new Vector2(x, rand.nextInt(MAX));
        bounds = new Rectangle(posicion.x, posicion.y, meteoro.getWidth(), meteoro.getHeight());
    }

    public boolean collision(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void reposition(float x) {
        posicion.set(x, rand.nextInt(MAX));
        bounds.setPosition(posicion.x, posicion.y);
    }

    public void move(Vector2 moveement) {
        moveement.scl(100f);
        posicion.add(moveement);
    }

    public void update(float dt) {
        move(new Vector2(0, -dt));
    }


    public Vector2 getPosicion() {
        return posicion;
    }

    public Texture getMeteoro() {
        return meteoro;
    }
}
