package co.devbeerloper.StarWars;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import co.devbeerloper.StarWars.R;

public class MainActivity extends AppCompatActivity {
    // Link pagina
    public static final String URL_SWAPI = "https://swapi.co/api/";
    // Control de elementos de la aplicacion
    private TextView tv_info;
    private Button btn_anterior;
    private Button btn_sig;
    private Button btn_planets;
    private Button btn_people;
    // Variable para la respuesta
    String nameResponse = "";
    // Arreglo para guardar los elementos del json
    public ArrayList lista;
    // Lleva control del elemento actual
    public int actual = 0;
    // Variable para llevar control de la categoria: 0 Planetas 1 Personas
    public int tipo = -1;
    // Variable para controlar la pagina actual
    public int pagina = 0;
    // Variable para tipo de letra
    private Typeface fuente;
    // Variables para sonido
    MediaPlayer cancion, tono;

    Runnable stopPlayerTask = new Runnable() {
        @Override
        public void run() {
            cancion.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        tv_info = findViewById(R.id.tv_info);
        btn_anterior = findViewById(R.id.btn_anterior);
        btn_sig = findViewById(R.id.btn_siguiente);
        btn_planets = findViewById(R.id.btn_planetas);
        btn_people = findViewById(R.id.btn_personas);
        lista = new ArrayList();
        String ruta = "fuente/Starjedi.ttf";
        this.fuente = Typeface.createFromAsset(getAssets(), ruta);
        tv_info.setTypeface(fuente);
        cancion = MediaPlayer.create(this, R.raw.starwars);
        tono = MediaPlayer.create(this, R.raw.darthvader);
    }

    public void makeCall(View v) {
        callWebService("");
    }

    public void callWebService(String serviceEndPoint) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlService = null;
                    if (tipo == 0) {
                        urlService = new URL(URL_SWAPI + "planets/?page=" + pagina);
                    } else if (tipo == 1) {
                        urlService = new URL(URL_SWAPI + "people/?page=" + pagina);
                    } else {
                        urlService = new URL(URL_SWAPI + "people/1/");
                    }
                    HttpsURLConnection connection = (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200) {
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String i = jsonReader.nextName();
                            if (i.equals("results")) {
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    jsonReader.beginObject();
                                    leerJson(jsonReader);
                                    jsonReader.endObject();
                                }
                                jsonReader.endArray();
                            } else {
                                jsonReader.skipValue();
                            }
                        }
                        nameResponse = (String) lista.get(0);
                        tv_info.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_info.setText(nameResponse);
                            }
                        });
                    } else {
                        Log.v("ERROR", "ERROR");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void leerJson(JsonReader reader) throws IOException {
        String plantilla;
        String v1 = null;
        String v2 = null;
        String v3 = null;
        String v4 = null;
        String v5 = null;
        String v6 = null;
        String v7 = null;
        String v8 = null;
        while (reader.hasNext()) {
            String key = reader.nextName();
            switch (key) {
                case "name":
                    v1 = reader.nextString();
                    break;
                case "height":
                case "orbital_period":
                    v2 = reader.nextString();
                    break;
                case "mass":
                case "diameter":
                    v3 = reader.nextString();
                    break;
                case "hair_color":
                case "climate":
                    v4 = reader.nextString();
                    break;
                case "skin_color":
                case "gravity":
                    v5 = reader.nextString();
                    break;
                case "eye_color":
                case "terrain":
                    v6 = reader.nextString();
                    break;
                case "birth_year":
                case "surface_water":
                    v7 = reader.nextString();
                    break;
                case "gender":
                case "population":
                    v8 = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        if (tipo == 0) {
            plantilla = "Nombre: " + v1 + "\n" + "Periodo Orbital: " + v2 + "\n" + "Diametro: " + v3 + "\n" + "Clima: " + v4 + "\n" + "Gravedad: " + v5 + "\n" + "Terreno: " + v6 + "\n" + "Superficie del agua: " + v7 + "\n" + "Poblacion: " + v8;
            lista.add(plantilla);
        } else {
            plantilla = "Nombre: " + v1 + "\n" + "Altura: " + v2 + "\n" + "Peso: " + v3 + "\n" + "Color de Pelo: " + v4 + "\n" + "Color de Piel: " + v5 + "\n" + "Color de Ojos: " + v6 + "\n" + "Fecha de Nacimiento: " + v7 + "\n" + "Genero: " + v8;
            lista.add(plantilla);
        }
    }

    public void ocultar_mostrar(View view) {
        if (view.getId() == btn_planets.getId()) {
            tipo = 0;
            tv_info.setBackgroundColor(Color.parseColor("#000000"));
            btn_planets.setVisibility(view.GONE);
            btn_people.setVisibility(view.GONE);
            btn_sig.setVisibility(view.VISIBLE);
            btn_anterior.setVisibility(view.VISIBLE);
            cancion.start();
        } else if (view.getId() == btn_people.getId()) {
            tipo = 1;
            tv_info.setBackgroundColor(Color.parseColor("#000000"));
            btn_planets.setVisibility(view.GONE);
            btn_people.setVisibility(view.GONE);
            btn_sig.setVisibility(view.VISIBLE);
            btn_anterior.setVisibility(view.VISIBLE);
            cancion.start();
        }
        makeCall(view);
    }

    public void mostrar(View view) {
        if (view.getId() == btn_anterior.getId()) {
            if (actual > 0) {
                actual--;
                nameResponse = (String) lista.get(actual);
                tv_info.setText(nameResponse);
            } else if (actual == 0) {
                cancion.pause();
                tono.start();
                Handler handler = new Handler();
                handler.postDelayed(stopPlayerTask, 4500);
                Toast toast = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.lytLayout));
                TextView txtMsg = layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("No hay datos anteriores");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        } else if (view.getId() == btn_sig.getId()) {
            if (actual < lista.size() - 1) {
                actual++;
                nameResponse = (String) lista.get(actual);
                tv_info.setText(nameResponse);
            } else {
                cancion.pause();
                tono.start();
                Handler handler = new Handler();
                handler.postDelayed(stopPlayerTask, 4500);
                Toast toast2 = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.lytLayout));
                TextView txtMsg = layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("No hay mas datos");
                toast2.setDuration(Toast.LENGTH_LONG);
                toast2.setView(layout);
                toast2.show();
            }
        }
    }
}