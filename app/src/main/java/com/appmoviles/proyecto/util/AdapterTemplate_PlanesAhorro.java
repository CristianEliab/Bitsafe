package com.appmoviles.proyecto.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.PlanAhorro;

import java.util.ArrayList;

public class AdapterTemplate_PlanesAhorro extends RecyclerView.Adapter<AdapterTemplate_PlanesAhorro.CustomViewHolder> {

    ArrayList<PlanAhorro> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }


    public AdapterTemplate_PlanesAhorro() {
        data = new ArrayList<>();
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_ahorro_item, parent, false);
        AdapterTemplate_PlanesAhorro.CustomViewHolder vh = new AdapterTemplate_PlanesAhorro.CustomViewHolder(v);
        return vh;
    }

    private int dpToPx(int dp, Context contexto) {
        float density = contexto.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTemplate_PlanesAhorro.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_titulo)).setText(data.get(position).getDescripcion());
        ((TextView) holder.root.findViewById(R.id.tv_fecha)).setText("Finaliza en "+data.get(position).getFechaFinal());
        ((TextView) holder.root.findViewById(R.id.tv_monto_acumulado)).setText(data.get(position).getMonto());
        ((TextView) holder.root.findViewById(R.id.tv_monto_final)).setText(data.get(position).getMeta());


        int monto = Integer.parseInt(data.get(position).getMonto());
        int meta = Integer.parseInt(data.get(position).getMeta().replace("0.0","0"));


        int r = (monto*371)/meta;

        Log.e("PLAN", " monto: " + monto);
        Log.e("PLAN", " meta: " + meta);
        Log.e("PLAN", " r: " + r);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((ImageView) holder.root.findViewById(R.id.iv_barra_completado)).getLayoutParams();
        params.width = (int) dpToPx(r, holder.root.getContext());
        params.height = dpToPx(6, holder.root.getContext());


        ((ImageView) holder.root.findViewById(R.id.iv_barra_completado)).setLayoutParams(params);
        ((ImageView) holder.root.findViewById(R.id.iv_barra_completado)).requestLayout();

        holder.root.findViewById(R.id.ll_plan_ahorro).setOnClickListener(new View.OnClickListener() {
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

    public boolean contains(PlanAhorro planAhorro) {
        return data.contains(planAhorro);
    }

    public void agregarPlanAhorro(PlanAhorro planAhorro) {
        data.add(planAhorro);
        notifyDataSetChanged();
    }

    //Patrón observer para identifcar cual plan de ahorro se seleccionó

    public interface OnItemClickListener {
        void onItemClick(PlanAhorro planAhorro);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
