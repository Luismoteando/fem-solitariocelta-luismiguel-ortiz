package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.SolitarioCelta.models.Resultado;

public class ResultadosAdapter extends ArrayAdapter {

    private Context context;

    private int idLayout;

    private List<Resultado> resultados;

    public ResultadosAdapter(@NonNull Context context, int idLayout, @NonNull List<Resultado> resultados) {
        super(context, idLayout, resultados);
        this.context = context;
        this.idLayout = idLayout;
        this.resultados = resultados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout linearLayout;
        if (null != convertView) {
            linearLayout = (LinearLayout) convertView;
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            linearLayout = (LinearLayout) layoutInflater.inflate(idLayout, parent, false);
        }
        TextView tvItem1 = linearLayout.findViewById(R.id.tvJugadorResultado);
        tvItem1.setText(
                getContext().getResources().getString(
                        R.string.default_jugador_resultado,
                        resultados.get(position).getJugador()));
        TextView tvItem2 = linearLayout.findViewById(R.id.tvFechaResultado);
        tvItem2.setText(
                getContext().getResources().getString(
                        R.string.default_fecha_resultado, resultados.get(position).getFecha()));
        TextView tvItem3 = linearLayout.findViewById(R.id.tvFichasResultado);
        tvItem3.setText(
                getContext().getResources().getString(
                        R.string.default_fichas_resultado,
                        resultados.get(position).getFichas()));
        return linearLayout;
    }
}
