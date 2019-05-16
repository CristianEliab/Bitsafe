package com.appmoviles.proyecto.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import java.util.ArrayList;

public class AdapterTemplate_Clientes extends RecyclerView.Adapter<AdapterTemplate_Clientes.CustomViewHolder> {

    //Los datos que vamos a mostrar (View)
    private ArrayList<Usuario> data;
    private LinearLayoutManager manage;
    private AdaptadorIconsBancos adaptadorIconsBancos;

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
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setHasFixedSize(true);
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setLayoutManager(this.manage);
        adaptadorIconsBancos = new AdaptadorIconsBancos();
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setAdapter(adaptadorIconsBancos);

        final ArrayList<String> listaIdBancos = new ArrayList<>();

        rtdb.getReference().child("cuentas")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Cuenta cuenta = hijo.getValue(Cuenta.class);
                            Usuario usuarionItem = data.get(position);
                            if(cuenta.getUsuarioID().equals(usuarionItem.getUsuarioID())){
                                String bancoID = cuenta.getBancoID();
                                listaIdBancos.add(bancoID);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        rtdb.getReference().child("bancos")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Banco banco = hijo.getValue(Banco.class);
                            for (String idBanco: listaIdBancos ) {
                                if(banco.getBancoID().equals(idBanco)){
                                    adaptadorIconsBancos.agregarBanco(banco);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        listaIdBancos.clear();

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
