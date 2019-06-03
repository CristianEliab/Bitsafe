package com.appmoviles.proyecto;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;

import java.util.ArrayList;

import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BANCOLOMBIA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BOGOTA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_DAVIVIENDA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_ITAU;

public class AdapterTemplate_CuentasVinculadas extends RecyclerView.Adapter<AdapterTemplate_CuentasVinculadas.CustomViewHolder> {

    ArrayList<Cuenta> data_c;
    ArrayList<Banco> data_b;



    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_CuentasVinculadas(){

        data_c = new ArrayList<>();
        data_b = new ArrayList<>();

    }

    @Override
    public AdapterTemplate_CuentasVinculadas.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.renglon_cuentas_bancarias, viewGroup, false);
        AdapterTemplate_CuentasVinculadas.CustomViewHolder vh = new AdapterTemplate_CuentasVinculadas.CustomViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTemplate_CuentasVinculadas.CustomViewHolder holder, int i) {

        ((TextView) holder.root.findViewById(R.id.tv_tipo_cuenta)).setText(data_c.get(i).getTipoCuentaNombre());
        ((TextView) holder.root.findViewById(R.id.tv_fecha_vinculacion_cuentasbancarias)).setText(data_c.get(i).getFechaVinculacion());
        ((TextView) holder.root.findViewById(R.id.tv_monto_cuentabancaria)).setText(data_c.get(i).getSaldo());

        if (data_b.get(i).getIcono().equals(ICON_BANCO_DAVIVIENDA)) {
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_cuentasvinculadas_imagen)).setBackgroundResource(R.drawable.banco_davivienda);
        } else if (data_b.get(i).getIcono().equals(ICON_BANCO_BOGOTA)) {
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_cuentasvinculadas_imagen)).setBackgroundResource(R.drawable.banco_bogota);
        } else if (data_b.get(i).getIcono().equals(ICON_BANCO_BANCOLOMBIA)) {
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_cuentasvinculadas_imagen)).setBackgroundResource(R.drawable.banco_bancolombia);
        } else if (data_b.get(i).getIcono().equals(ICON_BANCO_ITAU)) {
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_cuentasvinculadas_imagen)).setBackgroundResource(R.drawable.banco_itau);
        }



    }

    @Override
    public int getItemCount(){
        return data_c.size();
}

    public void agregar(Cuenta cuenta, Banco banco){
        data_c.add(cuenta);
        data_b.add(banco);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Cuenta cuenta);
    }

    private AdapterTemplate_CuentasVinculadas.OnItemClickListener listener;

    public void setListener(AdapterTemplate_CuentasVinculadas.OnItemClickListener listener){
        this.listener = listener;
    }
}
