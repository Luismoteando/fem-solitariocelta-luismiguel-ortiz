package es.upm.miw.SolitarioCelta.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static es.upm.miw.SolitarioCelta.models.ResultadoContract.TablaResultado;

public class RepositorioResultados extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = TablaResultado.TABLE_NAME + ".db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TablaResultado.TABLE_NAME + " (" +
                    TablaResultado._ID + " INTEGER PRIMARY KEY," +
                    TablaResultado.COL_NAME_JUGADOR + " TEXT," +
                    TablaResultado.COL_NAME_FECHA + " TEXT," +
                    TablaResultado.COL_NAME_FICHAS + " INT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TablaResultado.TABLE_NAME;

    public RepositorioResultados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TablaResultado.TABLE_NAME);
    }

    public long add(String jugador, String fecha, int fichas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TablaResultado.COL_NAME_JUGADOR, jugador);
        values.put(TablaResultado.COL_NAME_FECHA, fecha);
        values.put(TablaResultado.COL_NAME_FICHAS, fichas);

        return db.insert(TablaResultado.TABLE_NAME, null, values);
    }

    public List<Resultado> readAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Resultado> resultados = new ArrayList<>();

        Cursor cursor = db.query(TablaResultado.TABLE_NAME, null, null, null, null, null, TablaResultado.COL_NAME_FICHAS);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            resultados.add(new Resultado(
                    cursor.getString(cursor.getColumnIndex(TablaResultado.COL_NAME_JUGADOR)),
                    cursor.getString(cursor.getColumnIndex(TablaResultado.COL_NAME_FECHA)),
                    cursor.getInt(cursor.getColumnIndex(TablaResultado.COL_NAME_FICHAS))
            ));
        }
        cursor.close();
        db.close();

        return resultados;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TablaResultado.TABLE_NAME, null, null);
        db.close();
    }
}
