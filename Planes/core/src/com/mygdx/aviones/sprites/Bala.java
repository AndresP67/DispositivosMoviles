package com.mygdx.aviones.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bala {

    public static final int BALA_WIDTH = 30;
    private Rectangle bounds;
    private Texture bala;
    private Vector2 posicion;
    private Vector2 velocity;


    public Bala(float x, float y) {
        bala = new Texture("bala.png");
        posicion = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(posicion.x, posicion.y, bala.getWidth(), bala.getHeight());
    }

    public boolean collision(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void reposition(float x, float y) {
        posicion.set(x, y);
        bounds.setPosition(posicion.x, posicion.y);
    }

    public void move(Vector2 moveement) {
        moveement.scl(10f);
        posicion.add(moveement);
    }

    public void update(float dt) {
        move(new Vector2(0, dt));
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public Texture getBala() {
        return bala;
    }

    public void dispose() {
        bala.dispose();
    }
}
