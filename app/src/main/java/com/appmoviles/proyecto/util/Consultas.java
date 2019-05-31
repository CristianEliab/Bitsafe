package com.appmoviles.proyecto.util;

import android.support.annotation.NonNull;

import com.appmoviles.proyecto.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Consultas {

    private static Consultas instance = null;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    ArrayList<Usuario> listaUsuario;
    HashMap<String, EstadisticasMes> diccionario;
    HashMap<String, EstadisticasGenero> diccionarioGenero;

    public static synchronized Consultas getInstance() {
        if (instance == null) {
            instance = new Consultas();
        }
        return instance;
    }

    public Consultas() {
        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();
        listaUsuario = new ArrayList<>();
        diccionario = new HashMap<>();
        diccionarioGenero = new HashMap<>();
    }

    public void addUsuarios(Usuario usuario) {
        listaUsuario.add(usuario);
    }

    public void crearBase() {

        diccionario.put("01", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("02", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("03", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("04", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("05", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("06", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("07", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("08", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("09", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("10", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("11", new EstadisticasMes(0, new ArrayList<String>()));
        diccionario.put("12", new EstadisticasMes(0, new ArrayList<String>()));

        diccionarioGenero.put(Constantes.FEMENINO, new EstadisticasGenero(0, 0, new ArrayList<String>()));
        diccionarioGenero.put(Constantes.MASCULINO, new EstadisticasGenero(0, 0, new ArrayList<String>()));

      /*  rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ENERO).setValue(diccionario.get("01"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_FEBRERO).setValue(diccionario.get("02"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_MARZO).setValue(diccionario.get("03"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ABRIL).setValue(diccionario.get("04"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_MAYO).setValue(diccionario.get("05"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_JUNIO).setValue(diccionario.get("06"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_JULIO).setValue(diccionario.get("07"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_AGOSTO).setValue(diccionario.get("08"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_SEPTIEMBRE).setValue(diccionario.get("09"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_OCTUBRE).setValue(diccionario.get("10"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_NOVIEMBRE).setValue(diccionario.get("11"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_DICIEMBRE).setValue(diccionario.get("12"));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_MASCULINO).setValue(diccionarioGenero.get(Constantes.MASCULINO));
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_FEMENINO).setValue(diccionarioGenero.get(Constantes.FEMENINO));*/
    }


    public void calcularGeneros() {
        for (final Usuario usuario : listaUsuario) {
            if (usuario.getGenero() != null) {
                if (usuario.getGenero().equals(Constantes.REGISTRO_FEMENINO)) {
                    EstadisticasGenero estadisticasGenero = diccionarioGenero.get(Constantes.FEMENINO);
                    ArrayList<String> listaId = estadisticasGenero.getListaID();
                    if (!listaId.contains(usuario.getUsuarioID())) {
                        estadisticasGenero.setNumero_femeninos(estadisticasGenero.getNumero_femeninos() + 1);
                        diccionarioGenero.put(Constantes.FEMENINO, estadisticasGenero);
                        listaId.add(usuario.getUsuarioID());
                    }
                }
                if (usuario.getGenero().equals(Constantes.REGISTRO_MASCULINO)) {
                    EstadisticasGenero estadisticasGenero = diccionarioGenero.get(Constantes.MASCULINO);
                    ArrayList<String> listaId = estadisticasGenero.getListaID();
                    if (!listaId.contains(usuario.getUsuarioID())) {
                        estadisticasGenero.setNumero_masculinos(estadisticasGenero.getNumero_masculinos() + 1);
                        diccionarioGenero.put(Constantes.MASCULINO, estadisticasGenero);
                        listaId.add(usuario.getUsuarioID());
                    }
                }
            }
        }
    }

    private void guardarGeneros() {
        String[] genero = new String[2];
        genero[0] = Constantes.MASCULINO;
        genero[1] = Constantes.FEMENINO;
        for (String value : genero) {
            switch (value) {
                case Constantes.MASCULINO:
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_MASCULINO).setValue(diccionarioGenero.get(value));
                    break;
                case Constantes.FEMENINO:
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_FEMENINO).setValue(diccionarioGenero.get(value));
                    break;
            }
        }
    }


    public void calcularRegistrosFecha() {
        for (final Usuario usuario : listaUsuario) {
            if (usuario.getFechaCreacion() != 0l) {
                long val = usuario.getFechaCreacion();
                Date date = new Date(val);
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                String dateText = df2.format(date);
                String[] datos = dateText.split("/");
                String mes = datos[1];
                EstadisticasMes estadisticasMes;
                ArrayList<String> listaId;
                switch (mes) {
                    case "01":
                        estadisticasMes = diccionario.get("01");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("01", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "02":
                        estadisticasMes = diccionario.get("02");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("02", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "03":
                        estadisticasMes = diccionario.get("03");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("03", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "04":
                        estadisticasMes = diccionario.get("04");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("04", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "05":
                        estadisticasMes = diccionario.get("05");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("05", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "06":
                        estadisticasMes = diccionario.get("06");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("06", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "07":
                        estadisticasMes = diccionario.get("07");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("07", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "08":
                        estadisticasMes = diccionario.get("08");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("08", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "09":
                        estadisticasMes = diccionario.get("09");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("09", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "10":
                        estadisticasMes = diccionario.get("10");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("10", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "11":
                        estadisticasMes = diccionario.get("11");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("11", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                    case "12":
                        estadisticasMes = diccionario.get("12");
                        listaId = estadisticasMes.getListaUsuariosID();
                        if (!listaId.contains(usuario.getUsuarioID())) {
                            estadisticasMes.setNumeroClientes(estadisticasMes.getNumeroClientes() + 1);
                            diccionario.put("12", estadisticasMes);
                            listaId.add(usuario.getUsuarioID());
                        }
                        break;
                }
            }
        }
    }


    public void guardarEstadisticas() {
        String[] meses = new String[12];
        meses[0] = "01";
        meses[1] = "02";
        meses[2] = "03";
        meses[3] = "04";
        meses[4] = "05";
        meses[5] = "06";
        meses[6] = "07";
        meses[7] = "08";
        meses[8] = "09";
        meses[9] = "10";
        meses[10] = "11";
        meses[11] = "12";
        for (String mes : meses) {
            switch (mes) {
                case "01":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ENERO).setValue(diccionario.get(mes));
                    break;
                case "02":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_FEBRERO).setValue(diccionario.get(mes));
                    break;
                case "03":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_MARZO).setValue(diccionario.get(mes));
                    break;
                case "04":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ABRIL).setValue(diccionario.get(mes));
                    break;
                case "05":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_MAYO).setValue(diccionario.get(mes));
                    break;
                case "06":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_JUNIO).setValue(diccionario.get(mes));
                    break;
                case "07":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_JULIO).setValue(diccionario.get(mes));
                    break;
                case "08":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_AGOSTO).setValue(diccionario.get(mes));
                    break;
                case "09":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_SEPTIEMBRE).setValue(diccionario.get(mes));
                    break;
                case "10":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_OCTUBRE).setValue(diccionario.get(mes));
                    break;
                case "11":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_NOVIEMBRE).setValue(diccionario.get(mes));
                    break;
                case "12":
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_DICIEMBRE).setValue(diccionario.get(mes));
                    break;
            }
        }

    }


    public HashMap<String, EstadisticasMes> getDiccionario() {
        return diccionario;
    }

    public void setDiccionario(HashMap<String, EstadisticasMes> diccionario) {
        this.diccionario = diccionario;
    }


    public HashMap<String, EstadisticasGenero> getDiccionaioGenero() {
        return diccionarioGenero;
    }

    public void setDiccionaioGenero(HashMap<String, EstadisticasGenero> diccionaioGenero) {
        this.diccionarioGenero = diccionaioGenero;
    }

    public ArrayList<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(ArrayList<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
}
