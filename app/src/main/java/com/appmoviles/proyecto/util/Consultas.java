package com.appmoviles.proyecto.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Consultas {

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    public Consultas() {
        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();
    }


}
