package com.mygdx.aviones;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.aviones.states.GameStateManager;
import com.mygdx.aviones.states.MenuState;

public class Aviones extends ApplicationAdapter {
    // Tamaño de pantalla.
    public static final int WIDHT = 720;
    public static final int HEIGHT = 480;
    // Titulo de la aplicacion.
    public static final String TITLE = "Aviones";
    // Controla los estados del juego
    private GameStateManager gsm;
    private SpriteBatch batch;
    private Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(0, 1, 0, 1);
        gsm.push(new MenuState(gsm));
        setUpMusic();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        music.dispose();
    }

    private void setUpMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }
}
