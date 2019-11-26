package com.mygdx.aviones.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    // Señalizar lo que queremos ver en el momento.
    protected OrthographicCamera camera;
    // Donde se da clic
    protected Vector3 mouse;
    //Administra todos los estados del juego
    protected GameStateManager gsm;

    public State(GameStateManager gameStateManager) {
        this.gsm = gameStateManager;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    // Detectar entradas en el estado.
    protected abstract void handleInput();

    // Actualiza el juego en tiempo real.
    public abstract void update(float dt);

    // Cargar los elementos necesarios del juego.
    public abstract void render(SpriteBatch spriteBatch);

    public abstract void dispose();
}
