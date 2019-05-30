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

        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ENERO).setValue(diccionario.get("01"));
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
        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_FEMENINO).setValue(diccionarioGenero.get(Constantes.FEMENINO));
    }


    public void calcularGeneros() {
        for (final Usuario usuario : listaUsuario) {
            guardarGeneros();
            if (usuario.getGenero() != null) {
                if (usuario.getGenero().equals(Constantes.REGISTRO_FEMENINO)) {
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_FEMENINO).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            EstadisticasGenero estadisticasGenero = dataSnapshot.getValue(EstadisticasGenero.class);
                            if (estadisticasGenero.getNumero_femeninos() == 0l) {
                                estadisticasGenero.setNumero_femeninos(1);
                            } else {
                                estadisticasGenero.setNumero_femeninos(estadisticasGenero.getNumero_femeninos() + 1);
                            }

                            diccionarioGenero.put(Constantes.FEMENINO, estadisticasGenero);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if (usuario.getGenero().equals(Constantes.REGISTRO_MASCULINO)) {
                    rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_GENERO_ID).child(Constantes.CHILD_ESTADISTICAS_MASCULINO).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            EstadisticasGenero estadisticasGenero = dataSnapshot.getValue(EstadisticasGenero.class);
                            if (estadisticasGenero.getNumero_masculinos() == 0l) {
                                estadisticasGenero.setNumero_masculinos(1);
                            } else {
                                estadisticasGenero.setNumero_masculinos(estadisticasGenero.getNumero_masculinos() + 1);
                            }

                            diccionarioGenero.put(Constantes.MASCULINO, estadisticasGenero);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
            guardarEstadisticas();
            if (usuario.getFechaCreacion() != 0l) {
                long val = usuario.getFechaCreacion();
                Date date = new Date(val);
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                String dateText = df2.format(date);
                String[] datos = dateText.split("/");
                String mes = datos[1];
                switch (mes) {
                    case "01":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ENERO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("01", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "02":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_FEBRERO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("02", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "03":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_MARZO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("03", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "04":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_ABRIL).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("04", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "05":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_MAYO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("05", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "06":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_JUNIO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("06", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "07":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_JULIO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("07", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "08":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_AGOSTO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("08", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "09":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_SEPTIEMBRE).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("09", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "10":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_OCTUBRE).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("10", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "11":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_NOVIEMBRE).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("11", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "12":
                        rtdb.getReference().child(Constantes.CHILD_ESTADISTICAS_ID).child(Constantes.CHILD_ESTADISTICAS_DICIEMBRE).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                EstadisticasMes estadisticasMes = dataSnapshot.getValue(EstadisticasMes.class);
                                ArrayList<String> listaID = estadisticasMes.getListaUsuariosID();
                                float numero = 0;
                                if (listaID == null) {
                                    listaID = new ArrayList<>();
                                    listaID.add(usuario.getUsuarioID());
                                    numero = 1;
                                } else {
                                    for (String id : listaID) {
                                        if (!id.equals(usuario.getUsuarioID())) {
                                            numero = estadisticasMes.getNumeroClientes() + 1;
                                            listaID.add(usuario.getUsuarioID());
                                        }
                                    }
                                }

                                estadisticasMes.setListaUsuariosID(listaID);
                                estadisticasMes.setNumeroClientes(numero);
                                diccionario.put("12", estadisticasMes);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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
}
