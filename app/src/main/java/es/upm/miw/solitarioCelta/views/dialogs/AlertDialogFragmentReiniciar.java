package es.upm.miw.solitarioCelta.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;

import es.upm.miw.solitarioCelta.MainActivity;
import es.upm.miw.solitarioCelta.R;

public class AlertDialogFragmentReiniciar extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoReiniciarTitulo)
                .setMessage(R.string.txtDialogoReiniciarPregunta)
                .setPositiveButton(
                        getString(R.string.txtDialogoReiniciarAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.miJuego.reiniciar();
                                main.mostrarTablero();
                                main.getChronometer().setBase(SystemClock.elapsedRealtime());
                                main.getChronometer().start();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtDialogoReiniciarNegativo), null);

        return builder.create();
    }
}
