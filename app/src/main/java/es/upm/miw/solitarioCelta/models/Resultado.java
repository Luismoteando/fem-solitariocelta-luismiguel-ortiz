package es.upm.miw.solitarioCelta.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Resultado implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Resultado> CREATOR = new Parcelable.Creator<Resultado>() {
        @Override
        public Resultado createFromParcel(Parcel in) {
            return new Resultado(in);
        }

        @Override
        public Resultado[] newArray(int size) {
            return new Resultado[size];
        }
    };
    private int id;
    private String jugador;
    private String fecha;
    private int fichas;
    private String chronoTime;

    public Resultado(String jugador, String fecha, int fichas, String chronoTime) {
        this.jugador = jugador;
        this.fecha = fecha;
        this.fichas = fichas;
        this.chronoTime = chronoTime;
    }

    protected Resultado(Parcel in) {
        id = in.readInt();
        jugador = in.readString();
        fecha = in.readString();
        fichas = in.readInt();
        chronoTime = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getJugador() {
        return jugador;
    }

    public String getFecha() {
        return fecha;
    }

    public int getFichas() {
        return fichas;
    }

    public String getChronoTime() {
        return chronoTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(jugador);
        dest.writeString(fecha);
        dest.writeInt(fichas);
        dest.writeString(chronoTime);
    }
}