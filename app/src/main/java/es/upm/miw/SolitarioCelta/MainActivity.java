package es.upm.miw.SolitarioCelta;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import es.upm.miw.SolitarioCelta.models.RepositorioResultados;

public class MainActivity extends AppCompatActivity {

    public static final String PARTIDA_GUARDADA = "partida.txt";
    public final String LOG_KEY = "MiW";
    SCeltaViewModel miJuego;
    private FileOutputStream fileOutputStream;

    private BufferedReader bufferedReader;

    private StringBuffer sb;

    private TextView barraEstado;

    private RepositorioResultados repositorioResultados;

    private SharedPreferences settings;

    private Chronometer chronometer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);
        barraEstado = findViewById(R.id.barraEstado);
        repositorioResultados = new RepositorioResultados(this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        chronometer = findViewById(R.id.chronometer);
        if (savedInstanceState != null) {
            chronometer.setBase(savedInstanceState.getLong("chronoTime"));
        }
        chronometer.start();
        mostrarTablero();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("chronoTime", chronometer.getBase());
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fichaPulsada(@NotNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        Log.i(LOG_KEY, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuego.jugar(i, j);
        Log.i(LOG_KEY, "#fichas=" + miJuego.numeroFichas());

        mostrarTablero();
        if (miJuego.juegoTerminado()) {
            chronometer.stop();
            new AlertDialogFragmentTerminar().show(getFragmentManager(), "ALERT_DIALOG");
            repositorioResultados.add(
                    getJugador(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    miJuego.numeroFichas(),
                    new SimpleDateFormat("mm:ss", Locale.US)
                            .format(new Date(SystemClock.elapsedRealtime() - chronometer.getBase()))
            );
        }
    }


    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    button.setChecked(miJuego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
        barraEstado.setText(getString(R.string.default_barra_estado, miJuego.numeroFichas()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    private String getJugador() {
        return settings.getString(getString(R.string.key_jugador_setting), getString(R.string.default_jugador_setting));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcReiniciarPartida:
                new AlertDialogFragmentReiniciar().show(getFragmentManager(), "ALERT_DIALOG");
                return true;
            case R.id.opcGuardarPartida:
                guardarPartida();
                return true;
            case R.id.opcRecuperarPartida:
                recuperarPartida();
                return true;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, SCeltaResultados.class));
                return true;
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    private void guardarPartida() {
        try {
            fileOutputStream = openFileOutput(PARTIDA_GUARDADA, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.write(miJuego.serializaTablero().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recuperarPartida() {
        this.sb = new StringBuffer();
        String line;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(PARTIDA_GUARDADA)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            line = bufferedReader.readLine();
            while (line != null) {
                this.sb.append(line);
                line = bufferedReader.readLine();
            }
            if (!this.sb.toString().equals(miJuego.serializaTablero())) {
                new AlertDialogFragmentRecuperar().show(getFragmentManager(), "ALERT_DIALOG");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringBuffer getSb() {
        return sb;
    }

    public Chronometer getChronometer() {
        return chronometer;
    }
}