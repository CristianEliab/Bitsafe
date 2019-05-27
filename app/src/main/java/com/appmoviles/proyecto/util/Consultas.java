package com.appmoviles.proyecto.util;

import android.support.annotation.NonNull;

import com.appmoviles.proyecto.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Consultas {

    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    ArrayList<Usuario> listaUsuario = new ArrayList<>();

    public Consultas() {
        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();
    }

    public void listaUsuario() {
        rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Usuario usuario = hijo.getValue(Usuario.class);
                            listaUsuario.add(usuario);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    public void calcularRegistrosFecha() {
        for (Usuario usuario : listaUsuario) {
           /* if (usuario.getFechaCreacion()){

            }*/
            rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ENERO);
        }
    }


}
