package com.appmoviles.proyecto.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.CHILD_BANCOS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;

public class AdapterTemplate_Clientes extends RecyclerView.Adapter<AdapterTemplate_Clientes.CustomViewHolder> implements Serializable {

    //Los datos que vamos a mostrar (View)
    private ArrayList<Usuario> data;
    private LinearLayoutManager manage;
    private AdaptadorIconsBancos adaptadorIconsBancos;
    private List<Cuenta> listaCuentas;
    int index = -1;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Clientes() {
        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        data = new ArrayList<>();
        listaCuentas = new ArrayList<>();

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cliente_registro, parent, false);
        manage = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_cliente_registro_nombre)).setText(data.get(position).getNombre());
        adaptadorIconsBancos = new AdaptadorIconsBancos();
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setHasFixedSize(true);
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setAdapter(adaptadorIconsBancos);

        holder.root.findViewById(R.id.ll_fragment_clientes_registro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                listener.onItemUsuario(data.get(position));
                notifyDataSetChanged();
            }
        });

        /*cargarCuentas(position);
        cargarBancos();*/

        Banco banco = new Banco();
        banco.setIcono(Constantes.ICON_BANCO_DAVIVIENDA);
        adaptadorIconsBancos.agregarBanco(banco);

    }

    public void cargarBancos() {
        rtdb.getReference().child(CHILD_BANCOS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Banco> bancos = new ArrayList<>();
                for (int i = 0; i < listaCuentas.size(); i++) {
                    String bancoID = listaCuentas.get(i).getBancoID();
                    Banco bancoTmp;
                    for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                        bancoTmp = hijo.getValue(Banco.class);
                        //Solo los bancos que tienen cuentas del usuario registrado
                        if (bancoTmp.getBancoID().equals(bancoID) && !bancos.contains(bancoTmp)) {
                            bancos.add(bancoTmp);
                        }
                    }

                    for (Banco banco: bancos) {
                        adaptadorIconsBancos.agregarBanco(banco);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarCuentas(final int position) {
        listaCuentas = new ArrayList<Cuenta>();
        rtdb.getReference().child(CHILD_CUENTAS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuenta cuentaTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    cuentaTmp = hijo.getValue(Cuenta.class);
                    //Solo las cuentas que pertenezcan al usuario logueado
                    if (cuentaTmp.getUsuarioID().equals(data.get(position).getUsuarioID())) {
                        listaCuentas.add(cuentaTmp);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //OBSERVER
    public interface OnItemClickUsuario {
        void onItemUsuario(Usuario usuario);
    }

    private OnItemClickUsuario listener;

    public void setListener(OnItemClickUsuario listener) {
        this.listener = listener;
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarUsuario(Usuario usuario) {
        data.add(usuario);
        notifyDataSetChanged();
    }
}
