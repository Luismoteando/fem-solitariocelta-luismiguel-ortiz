package es.upm.miw.solitarioCelta;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import es.upm.miw.solitarioCelta.databinding.ItemResultadosBinding;
import es.upm.miw.solitarioCelta.models.Resultado;

public class ResultadosAdapter extends ArrayAdapter {

    private Context context;

    private int idLayout;

    private List<Resultado> resultados;

    private static LayoutInflater layoutInflater;

    public ResultadosAdapter(@NonNull Context context, int idLayout, @NonNull List<Resultado> resultados) {
        super(context, idLayout, resultados);
        this.context = context;
        this.idLayout = idLayout;
        this.resultados = resultados;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (null == layoutInflater) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        ItemResultadosBinding itemResultadosBinding = DataBindingUtil.getBinding(convertView);
        if (itemResultadosBinding == null) {
            itemResultadosBinding = DataBindingUtil.inflate(layoutInflater, idLayout, parent, false);
        }
        itemResultadosBinding.setResultado(resultados.get(position));
        itemResultadosBinding.executePendingBindings();
        return itemResultadosBinding.getRoot();
    }
}
