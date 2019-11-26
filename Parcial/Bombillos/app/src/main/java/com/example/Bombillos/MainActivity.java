package com.example.Bombillos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.Bombillos.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageButton ibBano;
    ImageButton ibCocina;
    ImageButton ibHab1;
    ImageButton ibHab2;
    ImageButton ibHab3;
    ImageButton ibHab4;
    Switch swBano;
    Switch swCocina;
    Switch swHab1;
    Switch swHab2;
    Switch swHab3;
    Switch swHab4;
    boolean bano;
    boolean cocina;
    boolean hab1;
    boolean hab2;
    boolean hab3;
    boolean hab4;
    public static final String URL = "http://192.168.0.6:3000/aptos/AptoStatus/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibBano = findViewById(R.id.ibBano);
        swBano = findViewById(R.id.swBano);
        ibCocina = findViewById(R.id.ibCocina);
        swCocina = findViewById(R.id.swCocina);
        ibHab1 = findViewById(R.id.ibHab1);
        swHab1 = findViewById(R.id.swHab1);
        ibHab2 = findViewById(R.id.ibHab2);
        swHab2 = findViewById(R.id.swHab2);
        ibHab3 = findViewById(R.id.ibHab3);
        swHab3 = findViewById(R.id.swHab3);
        ibHab4 = findViewById(R.id.ibHab4);
        swHab4 = findViewById(R.id.swHab4);
        makeCall(new View(this));
    }

    public void makeCall(View v) {
        callWebService("");
    }

    public void callWebService(String serviceEndPoint) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlService = new URL(URL);
                    HttpURLConnection connection = (HttpURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200) {
                        // Success
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject();
                        leerJson(jsonReader);
                        jsonReader.endObject();
                        llenar();
                    } else {
                        // Error handling code goes here
                        Log.v("ERROR", "ERROR");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    } // end callWebService()

    public void sendData(View v) {
        callPostService();
    }

    public void callPostService() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlService = new URL("http://10.10.0.40:3000/aptos/changeAptoStatusBody");
                    HttpURLConnection connection = (HttpURLConnection) urlService.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes("{\"luzBano\":false,\"luzCocina\":true,\"luzHabitacion1\":false,\"luzHabitacion2\":false,\"luzHabitacion3\":true,\"luzHabitacion4\":true}");
                    wr.close();
                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200) {
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void leerJson(JsonReader reader) throws IOException {
        while (reader.hasNext()) {
            String key = reader.nextName();
            switch (key) {
                case "luzBano":
                    bano = reader.nextBoolean();
                    if (bano == true) {
                        ibBano.setBackgroundResource(R.drawable.encendido);
                        bano = true;
                    } else {
                        ibBano.setBackgroundResource(R.drawable.apagado);
                        bano = false;
                    }
                    break;
                case "luzCocina":
                    cocina = reader.nextBoolean();
                    if (cocina == true) {
                        ibCocina.setBackgroundResource(R.drawable.encendido);
                        cocina = true;
                    } else {
                        ibCocina.setBackgroundResource(R.drawable.apagado);
                        cocina = false;
                    }
                    break;
                case "luzHabitacion1":
                    hab1 = reader.nextBoolean();
                    if (hab1 == true) {
                        ibHab1.setBackgroundResource(R.drawable.encendido);
                        hab1 = true;
                    } else {
                        ibHab1.setBackgroundResource(R.drawable.apagado);
                        hab1 = false;
                    }
                    break;
                case "luzHabitacion2":
                    hab2 = reader.nextBoolean();
                    if (hab2 == true) {
                        ibHab2.setBackgroundResource(R.drawable.encendido);
                        hab2 = true;
                    } else {
                        ibHab2.setBackgroundResource(R.drawable.apagado);
                        hab2 = false;
                    }
                    break;
                case "luzHabitacion3":
                    hab3 = reader.nextBoolean();
                    if (hab3 == true) {
                        ibHab3.setBackgroundResource(R.drawable.encendido);
                        hab3 = true;
                    } else {
                        ibHab3.setBackgroundResource(R.drawable.apagado);
                        hab3 = false;
                    }
                    break;
                case "luzHabitacion4":
                    hab4 = reader.nextBoolean();
                    if (hab4 == true) {
                        ibHab4.setBackgroundResource(R.drawable.encendido);
                        hab4 = true;
                    } else {
                        ibHab4.setBackgroundResource(R.drawable.apagado);
                        hab4 = false;
                    }
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
    }

    public void on_of(View view) {
        if (view.getId() == swBano.getId()) {
            if (swBano.isChecked()) {
                ibBano.setBackgroundResource(R.drawable.encendido);
            } else {
                ibBano.setBackgroundResource(R.drawable.apagado);
            }
        } else if (view.getId() == swCocina.getId()) {
            if (swCocina.isChecked()) {
                ibCocina.setBackgroundResource(R.drawable.encendido);
            } else {
                ibCocina.setBackgroundResource(R.drawable.apagado);
            }
        } else if (view.getId() == swHab1.getId()) {
            if (swHab1.isChecked()) {
                ibHab1.setBackgroundResource(R.drawable.encendido);
            } else {
                ibHab1.setBackgroundResource(R.drawable.apagado);
            }
        } else if (view.getId() == swHab2.getId()) {
            if (swHab2.isChecked()) {
                ibHab2.setBackgroundResource(R.drawable.encendido);
            } else {
                ibHab2.setBackgroundResource(R.drawable.apagado);
            }
        } else if (view.getId() == swHab3.getId()) {
            if (swHab3.isChecked()) {
                ibHab3.setBackgroundResource(R.drawable.encendido);
            } else {
                ibHab3.setBackgroundResource(R.drawable.apagado);
            }
        } else if (view.getId() == swHab4.getId()) {
            if (swHab4.isChecked()) {
                ibHab4.setBackgroundResource(R.drawable.encendido);
            } else {
                ibHab4.setBackgroundResource(R.drawable.apagado);
            }
        }
    }

    void llenar() {
        if (bano == true) {
            swBano.setChecked(true);
        } else {
            swBano.setChecked(false);
        }
        if (cocina == true) {
            swCocina.setChecked(true);
        } else {
            swCocina.setChecked(false);
        }
        if (hab1 == true) {
            swHab1.setChecked(true);
        } else {
            swHab1.setChecked(false);
        }
        if (hab2 == true) {
            swHab2.setChecked(true);
        } else {
            swHab2.setChecked(false);
        }
        if (hab3 == true) {
            swHab3.setChecked(true);
        } else {
            swHab3.setChecked(false);
        }
        if (hab4 == true) {
            swHab4.setChecked(true);
        } else {
            swHab4.setChecked(false);
        }
    }
}