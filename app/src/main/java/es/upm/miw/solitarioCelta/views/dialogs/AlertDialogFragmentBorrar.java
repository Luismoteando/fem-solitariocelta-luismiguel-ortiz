package es.upm.miw.solitarioCelta.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import es.upm.miw.solitarioCelta.R;
import es.upm.miw.solitarioCelta.views.SCeltaResultados;

public class AlertDialogFragmentBorrar extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final SCeltaResultados resultados = (SCeltaResultados) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(resultados);
        builder
                .setTitle(R.string.txtDialogoBorrarTitulo)
                .setMessage(R.string.txtDialogoBorrarPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoBorrarAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resultados.repositorioResultados.deleteAll();
                                resultados.resultadosAdapter.clear();
                                resultados.lvResultados.setAdapter(resultados.resultadosAdapter);
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoBorrarNegativo), null);

        return builder.create();
    }
}
