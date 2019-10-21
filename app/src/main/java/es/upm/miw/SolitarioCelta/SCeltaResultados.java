package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import es.upm.miw.SolitarioCelta.models.RepositorioResultados;

public class SCeltaResultados extends AppCompatActivity {

    private ListView lvResultados;

    private RepositorioResultados repositorioResultados;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        repositorioResultados = new RepositorioResultados(this);
        lvResultados = findViewById(R.id.lvResultados);
        lvResultados.setAdapter(new ResultadosAdapter(this, R.layout.item_resultados, repositorioResultados.readAll()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_resultados, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcBorrarClasificacion:
                repositorioResultados.deleteAll();
                lvResultados.setAdapter(new ResultadosAdapter(this, R.layout.item_resultados, repositorioResultados.readAll()));
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
}