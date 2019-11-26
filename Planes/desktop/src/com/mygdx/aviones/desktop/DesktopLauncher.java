package com.mygdx.aviones.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.aviones.Aviones;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Aviones.WIDHT;
        config.height = Aviones.HEIGHT;
        config.title = Aviones.TITLE;
        new LwjglApplication(new Aviones(), config);
    }
}
