package com.mygdx.aviones.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.aviones.Aviones;

import java.util.Random;

public class Enemigo {
    public static final int ENEMIGO_WIDTH = 30;
    private static final int MAX = (Aviones.HEIGHT / 2) - ENEMIGO_WIDTH;
    private Rectangle bounds;
    private Texture enemigo;
    private Vector2 posicion;
    private Random rand;

    public Enemigo(float x) {
        enemigo = new Texture("enemigo.png");
        rand = new Random();
        posicion = new Vector2(x, rand.nextInt(MAX));
        bounds = new Rectangle(posicion.x, posicion.y, enemigo.getWidth(), enemigo.getHeight());
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

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Texture getEnemigo() {
        return enemigo;
    }

}
