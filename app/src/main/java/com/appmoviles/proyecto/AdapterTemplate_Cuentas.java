package com.appmoviles.proyecto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Cuenta;

import java.util.ArrayList;

import static com.appmoviles.proyecto.util.Constantes.CUENTA_TIPO_CREDITO;
import static com.appmoviles.proyecto.util.Constantes.CUENTA_TIPO_DEBIDO;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BANCOLOMBIA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BOGOTA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_DAVIVIENDA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_ITAU;

public class AdapterTemplate_Cuentas  extends RecyclerView.Adapter<AdapterTemplate_Cuentas.CustomViewHolder> {

    ArrayList<Cuenta> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Cuentas(){
        data = new ArrayList<>();
    }

    @Override
    public AdapterTemplate_Cuentas.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_cuenta, parent, false);
        AdapterTemplate_Cuentas.CustomViewHolder vh = new AdapterTemplate_Cuentas.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterTemplate_Cuentas.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_renglon_cuenta_numero_cuenta)).setText(data.get(position).getNumeroCuenta());
        ((TextView) holder.root.findViewById(R.id.tv_renglon_cuenta_fecha_vinculacion)).setText(data.get(position).getFechaVinculacion());
        ((TextView) holder.root.findViewById(R.id.tv_renglon_cuenta_saldo)).setText(data.get(position).getSaldo());

        if(data.get(position).getTipoCuentaID().equals(CUENTA_TIPO_DEBIDO)){
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_cuenta_imagen_banco)).setBackgroundResource(R.drawable.ic_debit_card);
        }
        else if(data.get(position).getTipoCuentaID().equals(CUENTA_TIPO_CREDITO)){
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_cuenta_imagen_banco)).setBackgroundResource(R.drawable.ic_credit_card);
        }


        holder.root.findViewById(R.id.rl_renglon_relative_marco_cuenta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarCuenta(Cuenta cuenta){
        data.add(cuenta);
        notifyDataSetChanged();
    }

    //Patrón observer para identifcar cual cuenta se seleccionó

    public interface OnItemClickListener{
        void onItemClick(Cuenta cuenta);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //Hasta aquí patrón observer
}
