package com.mygdx.aviones.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.aviones.Aviones;
import com.mygdx.aviones.sprites.Bala;
import com.mygdx.aviones.sprites.Enemigo;
import com.mygdx.aviones.sprites.Meteoro;
import com.mygdx.aviones.sprites.Nave;

public class PlayState extends State {
    private static final int distancia = 200;
    private static final int distancia2 = 700;
    private static final int distancia3 = 300;
    private static final int met_count = 7;
    public int puntuacion;
    public int vida;
    private Array<Meteoro> array;
    private Array<Enemigo> enemigos;
    private Array<Bala> balas;
    private Nave nave;
    private Texture bg;
    private Sound explosion;
    BitmapFont font = new BitmapFont();

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        nave = new Nave(0, 0);
        puntuacion = 0;
        vida = 100;
        camera.setToOrtho(false, Aviones.WIDHT / 2, Aviones.HEIGHT / 2);
        bg = new Texture("estrellas.png");
        explosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        array = new Array<Meteoro>();
        enemigos = new Array<Enemigo>();
        balas = new Array<Bala>();
        for (int i = 1; i <= met_count; i++) {
            array.add(new Meteoro(i * (distancia + Meteoro.METEOR_WIDTH)));
            enemigos.add(new Enemigo(i * (distancia2 + Enemigo.ENEMIGO_WIDTH)));
            balas.add(new Bala(i * (distancia3 + Bala.BALA_WIDTH), 20 + enemigos.get(i - 1).getPosicion().y));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            nave.mover();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        nave.update(dt);
        camera.position.x = nave.getPosition().x + 80;
        for (Meteoro meteoro : array) {
            if (camera.position.x - (camera.viewportWidth / 2) > meteoro.getPosicion().x + meteoro.getMeteoro().getWidth()) {
                meteoro.reposition(meteoro.getPosicion().x + ((Meteoro.METEOR_WIDTH + distancia) * met_count));
                if (puntuacion <= 0) {
                    puntuacion -= 1;
                }
            }
            if (meteoro.collision(nave.getBounds())) {
                explosion.play(1.0f);
                gsm.set(new PlayState(gsm));
            }
        }
        for (Enemigo enemigo : enemigos) {
            if (camera.position.x - (camera.viewportWidth / 2) > enemigo.getPosicion().x + enemigo.getEnemigo().getWidth()) {
                enemigo.reposition(enemigo.getPosicion().x + ((Meteoro.METEOR_WIDTH + distancia2) * met_count));
                if (puntuacion <= 0) {
                    puntuacion -= 1;
                }
            }
            if (enemigo.collision(nave.getBounds())) {
                explosion.play(1.0f);
                gsm.set(new PlayState(gsm));
            }
        }
        for (Bala bala : balas) {
            if (camera.position.x - (camera.viewportWidth / 2) > bala.getPosicion().x + bala.getBala().getWidth()) {
                bala.reposition(bala.getPosicion().x + ((Meteoro.METEOR_WIDTH + distancia2) * met_count), bala.getPosicion().y);
            }
            if (bala.collision(nave.getBounds())) {
                if (vida < 15) {
                    System.out.println("Vida " + vida);
                } else {
                    vida = vida - 15;
                }
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        spriteBatch.draw(nave.getNave(), nave.getPosition().x, nave.getPosition().y);
        for (Meteoro meteoro : array) {
            spriteBatch.draw(meteoro.getMeteoro(), meteoro.getPosicion().x, meteoro.getPosicion().y);
        }
        for (Enemigo enemigo : enemigos) {
            spriteBatch.draw(enemigo.getEnemigo(), enemigo.getPosicion().x, enemigo.getPosicion().y);
        }
        for (Bala bala : balas) {
            spriteBatch.draw(bala.getBala(), bala.getPosicion().x, bala.getPosicion().y);
        }
        font.draw(spriteBatch, "PUNTUACION= " + puntuacion, 100, 100);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
