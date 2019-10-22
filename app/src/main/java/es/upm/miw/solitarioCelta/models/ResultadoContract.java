package es.upm.miw.solitarioCelta.models;

import android.provider.BaseColumns;

public final class ResultadoContract {

    private ResultadoContract() {
    }

    public static abstract class TablaResultado implements BaseColumns {
        static final String TABLE_NAME = "resultados";
        static final String COL_NAME_ID = _ID;
        static final String COL_NAME_JUGADOR = "jugador";
        static final String COL_NAME_FECHA = "fecha";
        static final String COL_NAME_FICHAS = "fichas";
        static final String COL_NAME_CHRONOTIME = "chronotime";
    }
}
