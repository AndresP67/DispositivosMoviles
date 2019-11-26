package co.devbeerloper.StarWars2;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    // Link pagina
    public static final String URL_SWAPI = "https://swapi.co/api/";
    // Control de elementos de la aplicacion
    private Button btn_planets;
    private Button btn_people;
    private Button btn_mostrar;
    RecyclerView recycler;
    // Arreglo para guardar los elementos del json
    public ArrayList<datos> lista;
    // Lleva control del elemento actual
    public int actual = 0;
    // Variable para llevar control de la categoria: 0 Planetas 1 Personas
    public int tipo = -1;
    // Variable para controlar la pagina actual
    public int pagina = 0;
    // Variables para sonido
    MediaPlayer cancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        btn_planets = findViewById(R.id.btn_planetas);
        btn_people = findViewById(R.id.btn_personas);
        btn_mostrar = findViewById(R.id.btn_mostrar);
        lista = new ArrayList();
        cancion = MediaPlayer.create(this, R.raw.starwars);
        recycler = findViewById(R.id.rv_info);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
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
                        urlService = new URL(URL_SWAPI + "planets");
                    } else if (tipo == 1) {
                        urlService = new URL(URL_SWAPI + "people");
                    }
                    HttpsURLConnection connection = (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200) {
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject(); // Start processing the JSON object
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
        datos plantilla = null;
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
        plantilla = new datos(v1, v2, v3, v4, v5, v6, v7, v8);
        lista.add(plantilla);
    }

    public void mostrar(View view) {
        if (view.getId() == btn_planets.getId()) {
            tipo = 0;
            //tv_info.setBackgroundColor(Color.parseColor("#000000"));
            cancion.start();
            btn_planets.setVisibility(view.GONE);
            btn_people.setVisibility(view.GONE);
            btn_mostrar.setVisibility(view.VISIBLE);
        } else if (view.getId() == btn_people.getId()) {
            tipo = 1;
            cancion.start();
            btn_planets.setVisibility(view.GONE);
            btn_people.setVisibility(view.GONE);
            btn_mostrar.setVisibility(view.VISIBLE);
        }
        makeCall(view);
        AdapterList adapter = new AdapterList(lista);
        recycler.setAdapter(adapter);
    }
}