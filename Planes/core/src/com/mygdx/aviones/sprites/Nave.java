package com.mygdx.aviones.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Nave {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;
    private Rectangle bounds;
    private Vector3 position;
    private Vector3 velocity;
    private Texture nave;
    private Sound disparo;

    public Nave(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        nave = new Texture("nave.png");
        bounds = new Rectangle(x, y, nave.getWidth(), nave.getHeight());
        disparo = Gdx.audio.newSound(Gdx.files.internal("disparo.ogg"));
    }

    public void update(float dt) {
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if (position.y < 0) {
            position.y = 0;
        }
        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getNave() {
        return nave;
    }

    public void mover() {
        velocity.y = 250;
        disparo.play(0.4f);
    }

    public void dispose() {
        nave.dispose();
        disparo.dispose();
    }
}
