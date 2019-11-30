package com.mygdx.Juego;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/*Clase que implementa la interfaz ApplicationListener, que será lanzada
al crear la aplicación. Todos los métodos que sobrescribe, se implementan en el
hilo de OpenGL para manipular gráficos con total seguridad.*/
public class Juego implements ApplicationListener {

    /*Se declaran las variables para mostrar los diferentes mapas de bits (Sprites)
    y Texturas para las imágenes del juego.*/
    private SpriteBatch batch;//Clase que nos permite dibujar rectángulos 2D y referenciarlos con texturas definidas.
    private Texture textura_jugador, textura_enemigo, textura_vida, textura_fondo;
    private Sprite sprite_fondo;
    private OrthographicCamera camera;
    private BitmapFont contador_puntos, contador_vidas, rotulo_puntuacion, game_over, nivel_pantalla;
    private int velocidad_nave = 200;
    private int altura;
    private Rectangle rectangulo_jugador, rectangulo_cpu_enemigo, rectangulo_vida;
    private long numero_enemigos, numero_vidas;
    private float velocidad_enemigo = 300.0f;
    private float velocidad_vida = 200.0f;
    public Music coger_vida, golpe_enemigo, fondo_escenario, vidaExtra;
    int vidas = 3;
    int puntos = 0;
    int nivel = 0;
    float tamanio_fuente_puntuacion = 3.0f;
    float tamanio_fuente_over = 4.0f;

    public Juego() {
		/*Crea la cámara y define la zona de visión del juego,
		indicando la resolución del dispositivo.*/
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 1280, 800);
    }

    /*Método que será invocado una sola vez, al principio de lanzar la 	aplicación.*/
    @Override
    public void create() {

		/*Se crean los objetos de la clase Rectangle, indicando el ancho y alto de la figura geométrica rectángulo,
		además de asignar las coordenadas X e Y del objeto.*/
        rectangulo_jugador = new Rectangle();
        rectangulo_jugador.width = 128;
        rectangulo_jugador.height = 64;
        rectangulo_jugador.x = 150;
        rectangulo_jugador.y = 600;

		/*Tanto el objeto cpu_enemigo, como el objeto vida_jugador, se posicionan fuera del ancho de
		la pantalla (coordenada X) al iniciar la partida, y se establece una posición aleatoria en la coordenada Y,
		siempre dentro del rango máximo de altura de la pantalla, es decir, no superior a 800 px - alto del rectángulo
		para que no se oculte el objeto.*/
        rectangulo_cpu_enemigo = new Rectangle();
        rectangulo_cpu_enemigo.width = 128;
        rectangulo_cpu_enemigo.height = 64;
        rectangulo_cpu_enemigo.x = 1280;
        rectangulo_cpu_enemigo.y = MathUtils.random(0, 800 - 64);

        rectangulo_vida = new Rectangle();
        rectangulo_vida.width = 128;
        rectangulo_vida.height = 64;
        rectangulo_vida.x = 1280;
        rectangulo_vida.y = MathUtils.random(0, 800 - 64);

        altura = Gdx.graphics.getHeight();
        contador_puntos = new BitmapFont();
        contador_vidas = new BitmapFont();
        rotulo_puntuacion = new BitmapFont();
        nivel_pantalla = new BitmapFont();
        game_over = new BitmapFont();
        batch = new SpriteBatch();

		/*Se cargan las texturas con los recursos añadidos al proyecto, dentro de la carpeta "assets/data"
		, del proyecto a compilar.*/
        textura_jugador = new Texture(Gdx.files.internal("data/nave.png"));
        textura_vida = new Texture(Gdx.files.internal("data/vida.png"));
        textura_enemigo = new Texture(Gdx.files.internal("data/enemigo.png"));
        textura_fondo = new Texture(Gdx.files.internal("data/fondo.png"));

		/* Se inicializan e instancian los objetos de la clase Music, estableciendo los recursos añadidos
		con la extensión .wav.*/
        coger_vida = Gdx.audio.newMusic(Gdx.files.internal("data/vida.mp3"));
        golpe_enemigo = Gdx.audio.newMusic(Gdx.files.internal("data/golpe_enemigo.mp3"));
        fondo_escenario = Gdx.audio.newMusic(Gdx.files.internal("data/fondo_escenario.mp3"));
        vidaExtra = Gdx.audio.newMusic(Gdx.files.internal("data/extra.mp3"));

		/*Se debe establecer el método setLooping() en true para que cuando
		finalice la pista vuelva a reproducirse. Será la música que nos
		acompañe durante la partida.*/
        fondo_escenario.setLooping(true);
        /*Se invoca el método play() para que comience a reproducirse al iniciar la aplicación.*/
        fondo_escenario.play();
        int w;
        int h;
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
		/*Inicializamos el Sprite para el fondo de la pantalla, indicando el recurso, las
		coordenadas de la esquina inferior izquierda, y el tamaño de la
		imagen, que obligatoriamente debe ser potencia de 2.*/
        sprite_fondo = new Sprite(textura_fondo, 0, 0, w, h);

        /*Establecemos la posición inicial para el fondo de pantalla.*/
        sprite_fondo.setPosition(0, 0);
    }

    /*Método que será invocado al destruir la aplicación. En él se invocan aquellos
    objetos que deben destruirse para que el recolector de basura lo elimine
    de la memoria.*/
    @Override
    public void dispose() {
        batch.dispose();
        textura_jugador.dispose();
        textura_enemigo.dispose();
        textura_vida.dispose();
        textura_fondo.dispose();
    }


    /*Método que será invocado por cada frame que se dibuje en pantalla,
    mostrando los elementos definidos.*/
    @Override
    public void render() {
        pintarJuego();
        controlesJugador();
        controlColisionesYPuntuacion();

        if (TimeUtils.nanoTime() - numero_enemigos > 10000000) {
            crearEnemigos();
        }

        if (TimeUtils.nanoTime() - numero_vidas > 10000000) {
            crearVida();
        }
    }

    /*Método que será invocado desde render(), encargado de controlar las diferentes colisiones que se produzcan
    en el videojuego, estableciendo la lógica de puntuaciones, niveles, vidas restantes y velocidad de los elementos.*/
    private void controlColisionesYPuntuacion() {
        /*Si se colisiona con la nave enemiga.*/
        if (rectangulo_jugador.overlaps(rectangulo_cpu_enemigo)) {
            vidas = vidas - 1;
            golpe_enemigo.play();
            rectangulo_cpu_enemigo.x = 1280;
            rectangulo_cpu_enemigo.y = MathUtils.random(0, 800 - 64);
        }

        /*Si se colisiona con el elemento de puntuación.*/
        if (rectangulo_jugador.overlaps(rectangulo_vida)) {
			/*Por cada 10 puntos, incrementaremos una vida, subiremos un nivel y aumentará la velocidad
			de la nave enemiga y del objeto de puntuación.*/
            if ((puntos == 8)) {
                vidas = vidas + 1;
                nivel = nivel + 1;
                velocidad_enemigo = 500.0f;
                velocidad_vida = 300.0f;
                vidaExtra.play();

            } else if ((puntos == 18)) {
                vidas = vidas + 1;
                nivel = nivel + 1;
                velocidad_enemigo = 800.0f;
                velocidad_vida = 400.0f;
                vidaExtra.play();
            } else if ((puntos == 28)) {
                vidas = vidas + 1;
                nivel = nivel + 1;
                velocidad_enemigo = 1000.0f;
                velocidad_vida = 500.0f;
                vidaExtra.play();
            } else if ((puntos == 38)) {
                vidas = vidas + 1;
                nivel = nivel + 1;
                velocidad_enemigo = 1200.0f;
                velocidad_vida = 600.0f;
                vidaExtra.play();
            } else if ((puntos == 48)) {
                vidas = vidas + 1;
                nivel = nivel + 1;
                velocidad_enemigo = 1400.0f;
                velocidad_vida = 700.0f;
            }
            puntos = puntos + 2;
            coger_vida.play();
            rectangulo_vida.x = 1280;
            rectangulo_vida.y = MathUtils.random(0, 800);
        }
    }

    /*Método que permitirá colocar fuera de la pantalla al elemento enemigo, en una coordenada Y aleatoria,
    cada vez que se destruya (desaparezca por el lado izquierdo de la pantalla o eje de coordenadas -X)
    ,para simular nuevos enemigos.*/
    private void crearEnemigos() {
        float tiempo = Gdx.graphics.getDeltaTime();
        rectangulo_cpu_enemigo.x = rectangulo_cpu_enemigo.x - velocidad_enemigo * tiempo;
        if (rectangulo_cpu_enemigo.x < -128) {
            rectangulo_cpu_enemigo.x = 1280;
            rectangulo_cpu_enemigo.y = MathUtils.random(64, 800);
            numero_enemigos = TimeUtils.nanoTime();
        }
        if (rectangulo_cpu_enemigo.y > 800 - 64) {
            rectangulo_cpu_enemigo.y = 800 - 64;
        }

        if (rectangulo_cpu_enemigo.y < 0) {
            rectangulo_cpu_enemigo.y = 0;
        }
    }

    /*
    Método que permitirá colocar fuera de la pantalla al elemento que permite puntuar al jugador,
    en una coordenada Y aleatoria, cada vez que la nave del jugador consigue colisionar con dicho elemento.
    Por lo tanto, cada vez que desaparezca por el lado izquierdo de la pantalla o eje de coordenadas -X
    , aparecerá un nuevo objeto de puntuación por el lado derecho.*/
    private void crearVida() {
        float tiempo = Gdx.graphics.getDeltaTime();
        rectangulo_vida.x = rectangulo_vida.x - velocidad_vida * tiempo;
        if (rectangulo_vida.x < -128) {
            rectangulo_vida.x = 1280;
            rectangulo_vida.y = MathUtils.random(64, 800 - 64);
            numero_enemigos = TimeUtils.nanoTime();
        }

        if (rectangulo_vida.y > 800 - 64) {
            rectangulo_vida.y = 800 - 64;
        }

        if (rectangulo_vida.y < 0) {
            rectangulo_vida.y = 0;
        }
    }


    /*Método que permite controlar la nave del jugador con pulsaciones en
    la pantalla del dispositivo. Además se implementa la lógica de movimiento que
    no permita salir la nave del jugador fuera de la pantalla.*/
    private void controlesJugador() {

        boolean tocar_pantalla = Gdx.input.isTouched();

        float eje_x_nave = rectangulo_jugador.getX();
        float eje_y_nave = rectangulo_jugador.getY();

        float tiempo = Gdx.graphics.getDeltaTime();

        if (tocar_pantalla) {
            /*Comprobamos que la nave del jugador no salga por la parte superior de la pantalla.*/
            if (rectangulo_jugador.y > 800 - 64) {
                eje_y_nave = 800 - 64;
            } else {
                eje_y_nave = eje_y_nave + velocidad_nave * tiempo;
            }
        } else {
            /*Comprobamos que la nave del jugador no salga por la parte inferior de la pantalla.*/
            if (rectangulo_jugador.y < 0) {
                eje_y_nave = 0;
            } else {
                eje_y_nave = eje_y_nave - velocidad_nave * tiempo;
            }
        }
        rectangulo_jugador.set(eje_x_nave, eje_y_nave, 256, 64);
    }

    /*Método encargado de dibujar los elementos en pantalla. Mediante
    el objeto batch, referenciamos las texturas cargadas con los objetos de la clase Rectangle definidos.*/
    private void pintarJuego() {
        //Borra la pantalla y la dibuja de gris.
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite_fondo.draw(batch);

        if (vidas >= 0 && puntos < 60) {
            batch.draw(textura_jugador, rectangulo_jugador.x, rectangulo_jugador.y);
            batch.draw(textura_enemigo, rectangulo_cpu_enemigo.x, rectangulo_cpu_enemigo.y);
            batch.draw(textura_vida, rectangulo_vida.x, rectangulo_vida.y);
            nivel_pantalla.setColor(Color.GREEN);
            nivel_pantalla.getData().setScale(tamanio_fuente_puntuacion);
            nivel_pantalla.draw(batch, "Nivel: " + nivel, 900, altura - 140);
            contador_vidas.setColor(Color.RED);
            contador_vidas.getData().setScale(tamanio_fuente_puntuacion);
            contador_vidas.draw(batch, "Vidas restantes: " + vidas, 900, altura - 80);
            contador_puntos.setColor(Color.BLUE);
            contador_puntos.getData().setScale(tamanio_fuente_puntuacion);
            contador_puntos.draw(batch, "Puntuación: " + puntos, 900, altura - 20);

        } else if (puntos == 60) {
            batch.draw(textura_enemigo, rectangulo_cpu_enemigo.x, rectangulo_cpu_enemigo.y);
            batch.draw(textura_vida, rectangulo_vida.x, rectangulo_vida.y);
            game_over.setColor(Color.RED);
            game_over.getData().setScale(tamanio_fuente_over);
            game_over.draw(batch, "Juego finalizado!!!", 300, 700);
            rotulo_puntuacion.setColor(Color.BLUE);
            rotulo_puntuacion.getData().setScale(tamanio_fuente_over);
            rotulo_puntuacion.draw(batch, "Puntuación final: " + ((vidas * 10) + puntos) + " ptos", 300, 600);
        } else {
            batch.draw(textura_enemigo, rectangulo_cpu_enemigo.x, rectangulo_cpu_enemigo.y);
            batch.draw(textura_vida, rectangulo_vida.x, rectangulo_vida.y);
            game_over.setColor(Color.RED);
            game_over.getData().setScale(tamanio_fuente_over);
            game_over.draw(batch, "Juego finalizado!!!", 300, 700);
            rotulo_puntuacion.setColor(Color.BLUE);
            rotulo_puntuacion.getData().setScale(tamanio_fuente_over);
            rotulo_puntuacion.draw(batch, "Puntuación final: " + puntos + " ptos", 300, 600);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }
}