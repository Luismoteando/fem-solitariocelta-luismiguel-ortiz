package es.upm.miw.solitarioCelta.views;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import es.upm.miw.solitarioCelta.R;
import es.upm.miw.solitarioCelta.models.RepositorioResultados;
import es.upm.miw.solitarioCelta.views.dialogs.AlertDialogFragmentBorrar;

public class SCeltaResultados extends AppCompatActivity {

    public ListView lvResultados;

    public ResultadosAdapter resultadosAdapter;

    public RepositorioResultados repositorioResultados;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        repositorioResultados = new RepositorioResultados(this);
        lvResultados = findViewById(R.id.lvResultados);
        resultadosAdapter = new ResultadosAdapter(this, R.layout.item_resultados, repositorioResultados.readAll());
        lvResultados.setAdapter(resultadosAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_resultados, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBorrarClasificacion) {
            new AlertDialogFragmentBorrar().show(getFragmentManager(), "ALERT_DIALOG");
            return true;
        } else {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtSinImplementar),
                    Snackbar.LENGTH_LONG
            ).show();
        }
        return true;
    }
}