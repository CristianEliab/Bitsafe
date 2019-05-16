package com.appmoviles.proyecto.util;

import android.util.JsonReader;

import com.appmoviles.proyecto.modelo.Actividad;
import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Categoria;
import com.appmoviles.proyecto.modelo.ConfiguracionSeguridad;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.EstadoPlanAhorro;
import com.appmoviles.proyecto.modelo.EstadoUsuario;
import com.appmoviles.proyecto.modelo.Login;
import com.appmoviles.proyecto.modelo.PlanAhorro;
import com.appmoviles.proyecto.modelo.Rol;
import com.appmoviles.proyecto.modelo.RolActividad;
import com.appmoviles.proyecto.modelo.RolUsuario;
import com.appmoviles.proyecto.modelo.TipoCuenta;
import com.appmoviles.proyecto.modelo.TipoFlujoDinero;
import com.appmoviles.proyecto.modelo.TipoTransaccion;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.appmoviles.proyecto.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonParse {

    private ArrayList<Actividad> actividadArrayList;
    private ArrayList<Banco> bancoArrayList;
    private ArrayList<Categoria> categoriaArrayList;
    private ArrayList<ConfiguracionSeguridad> configuracionSeguridadArrayList;
    private ArrayList<Cuenta> cuentaArrayList;
    private ArrayList<EstadoPlanAhorro> estadoPlanAhorroArrayList;
    private ArrayList<EstadoUsuario> estadoUsuarioArrayList;
    private ArrayList<Login> loginArrayList;
    private ArrayList<PlanAhorro> planAhorroArrayList;
    private ArrayList<Rol> rolArrayList;
    private ArrayList<RolActividad> rolActividadArrayList;
    private ArrayList<RolUsuario> rolUsuarioArrayList;
    private ArrayList<TipoCuenta> tipoCuentaArrayList;
    private ArrayList<TipoFlujoDinero> tipoFlujoDineroArrayList;
    private ArrayList<TipoTransaccion> tipoTransaccionArrayList;
    private ArrayList<Transaccion> transaccionArrayList;
    private ArrayList<Usuario> usuarioArrayList;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    public JsonParse() {

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        this.actividadArrayList = new ArrayList<>();
        this.bancoArrayList = new ArrayList<>();
        this.categoriaArrayList = new ArrayList<>();
        this.configuracionSeguridadArrayList = new ArrayList<>();
        this.cuentaArrayList = new ArrayList<>();
        this.estadoPlanAhorroArrayList = new ArrayList<>();
        this.estadoUsuarioArrayList = new ArrayList<>();
        this.loginArrayList = new ArrayList<>();
        this.planAhorroArrayList = new ArrayList<>();
        this.rolArrayList = new ArrayList<>();
        this.rolActividadArrayList = new ArrayList<>();
        this.rolUsuarioArrayList = new ArrayList<>();
        this.tipoCuentaArrayList = new ArrayList<>();
        this.tipoFlujoDineroArrayList = new ArrayList<>();
        this.tipoTransaccionArrayList = new ArrayList<>();
        this.transaccionArrayList = new ArrayList<>();
        this.usuarioArrayList = new ArrayList<>();
    }

    public void readJsonStream(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "usuarios":
                        leerArrayUsuario(reader);
                        reader.endArray();
                        break;
                    case "actividad":
                        leerArrayActividades(reader);
                        reader.endArray();
                        break;
                    case "bancos":
                        leerArrayBancos(reader);
                        reader.endArray();
                        break;
                    case "categoria":
                        leerArrayCategorias(reader);
                        reader.endArray();
                        break;
                    case "configuracionseguridad":
                        leerArrayConfiguracionSeguridad(reader);
                        reader.endArray();
                        break;
                    case "cuentas":
                        leerArrayCuentas(reader);
                        reader.endArray();
                        break;
                    case "estadousuario":
                        leerArrayEstadoUsuario(reader);
                        reader.endArray();
                        break;
                    case "login":
                        leerArrayLogin(reader);
                        reader.endArray();
                        break;
                    case "planesahorro":
                        leerArrayPlanesAhorro(reader);
                        reader.endArray();
                        break;
                    case "roles":
                        leerArrayRoles(reader);
                        reader.endArray();
                        break;
                    case "rolactividad":
                        leerArrayRolActividad(reader);
                        reader.endArray();
                        break;
                    case "rolusuario":
                        leerArrayRolUsuario(reader);
                        reader.endArray();
                        break;
                    case "tipocuentas":
                        leerArrayTipoCuenta(reader);
                        reader.endArray();
                        break;
                    case "tipoflujodinero":
                        leerArrayTipoFlujo(reader);
                        reader.endArray();
                        break;
                    case "tipotransacciones":
                        leerArrayTipoTransaccion(reader);
                        reader.endArray();
                        break;
                    case "transacciones":
                        leerArrayTransaccion(reader);
                        reader.endArray();
                        break;
                }
            }
        } finally {
            reader.close();
        }

    }

    public void saveDataBase() {
        for (Usuario usuario : usuarioArrayList) {
            rtdb.getReference().child("usuarios").push().setValue(usuario);
        }
        for (Actividad value : actividadArrayList) {
            rtdb.getReference().child("actividades").push().setValue(value);
        }
        for (Banco value : bancoArrayList) {
            rtdb.getReference().child("bancos").push().setValue(value);
        }
        for (Categoria value : categoriaArrayList) {
            rtdb.getReference().child("categorias").push().setValue(value);
        }
        for (ConfiguracionSeguridad value : configuracionSeguridadArrayList) {
            rtdb.getReference().child("configuracionesseguridad").push().setValue(value);
        }
        for (Cuenta value : cuentaArrayList) {
            rtdb.getReference().child("cuentas").push().setValue(value);
        }
        for (EstadoUsuario value : estadoUsuarioArrayList) {
            rtdb.getReference().child("estadousuario").push().setValue(value);
        }
        for (Login value : loginArrayList) {
            rtdb.getReference().child("login").push().setValue(value);
        }
        for (PlanAhorro value : planAhorroArrayList) {
            rtdb.getReference().child("planesahorro").push().setValue(value);
        }
        for (Rol value : rolArrayList) {
            rtdb.getReference().child("roles").push().setValue(value);
        }
        for (RolActividad value : rolActividadArrayList) {
            rtdb.getReference().child("rolactividad").push().setValue(value);
        }
        for (RolUsuario value : rolUsuarioArrayList) {
            rtdb.getReference().child("rolusuario").push().setValue(value);
        }
        for (TipoCuenta value : tipoCuentaArrayList) {
            rtdb.getReference().child("tipocuentas").push().setValue(value);
        }
        for (TipoFlujoDinero value : tipoFlujoDineroArrayList) {
            rtdb.getReference().child("tipoflujodinero").push().setValue(value);
        }
        for (TipoTransaccion value : tipoTransaccionArrayList) {
            rtdb.getReference().child("tipotransacciones").push().setValue(value);
        }
        for (Transaccion value : transaccionArrayList) {
            rtdb.getReference().child("transacciones").push().setValue(value);
        }
    }

    private void leerArrayUsuario(JsonReader reader) throws IOException {
        String usuarioID = null;
        String nombre = null;
        String fecha_nacimiento = null;
        String telefono = null;
        String genero = null;
        String estadoUsuarioID = null;
        String loginID = null;
        String configuracionID = null;
        String cedula = null;
        String ubicacion = null;
        String correo = null;
        reader.beginArray();
        // Inicia el arreglo
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "usuarioID":
                        usuarioID = reader.nextString();
                        break;
                    case "nombre":
                        nombre = reader.nextString();
                        break;
                    case "fecha_nacimiento":
                        fecha_nacimiento = reader.nextString();
                        break;
                    case "telefono":
                        telefono = reader.nextString();
                        break;
                    case "genero":
                        genero = reader.nextString();
                        break;
                    case "estadoUsuarioID":
                        estadoUsuarioID = reader.nextString();
                        break;
                    case "loginID":
                        loginID = reader.nextString();
                        break;
                    case "configuracionID":
                        configuracionID = reader.nextString();
                        break;
                    case "cedula":
                        cedula = reader.nextString();
                        break;
                    case "ubicacion":
                        ubicacion = reader.nextString();
                        break;
                    case "correo":
                        correo = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.usuarioArrayList.add(new Usuario(usuarioID, nombre, fecha_nacimiento, telefono, genero, estadoUsuarioID, loginID, configuracionID, cedula, ubicacion, correo));
            reader.endObject();
        }
    }

    private void leerArrayTransaccion(JsonReader reader) throws IOException {
        String transaccionID = null;
        String cuentaOrigenID = null;
        String cuentaDestinoID = null;
        String tipoTransaccionID = null;
        String categoriaID = null;
        String descripcion = null;
        String montoTransaccion = null;
        String fechaTransaccion = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "transaccionID":
                        transaccionID = reader.nextString();
                        break;
                    case "cuentaOrigenID":
                        cuentaOrigenID = reader.nextString();
                        break;
                    case "cuentaDestinoID":
                        cuentaDestinoID = reader.nextString();
                        break;
                    case "tipoTransaccionID":
                        tipoTransaccionID = reader.nextString();
                        break;
                    case "categoriaID":
                        categoriaID = reader.nextString();
                        break;
                    case "descripcion":
                        descripcion = reader.nextString();
                        break;
                    case "montoTransaccion":
                        montoTransaccion = reader.nextString();
                        break;
                    case "fechaTransaccion":
                        fechaTransaccion = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.transaccionArrayList.add(new Transaccion(transaccionID, cuentaOrigenID, cuentaDestinoID, tipoTransaccionID, categoriaID, descripcion, montoTransaccion, fechaTransaccion));
            reader.endObject();
        }
    }

    private void leerArrayTipoTransaccion(JsonReader reader) throws IOException {
        String tipoTransaccionID = null;
        String nombre = null;
        String tipoFlujoDinero = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "tipoTransaccionID":
                        tipoTransaccionID = reader.nextString();
                        break;
                    case "nombre":
                        nombre = reader.nextString();
                        break;
                    case "tipoFlujoDinero":
                        tipoFlujoDinero = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.tipoTransaccionArrayList.add(new TipoTransaccion(tipoTransaccionID, nombre, tipoFlujoDinero));
            reader.endObject();
        }

    }

    private void leerArrayTipoFlujo(JsonReader reader) throws IOException {
        String tipoFlujoeDineroID = null;
        String flujoDinero = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "tipoFlujoeDineroID":
                        tipoFlujoeDineroID = reader.nextString();
                        break;
                    case "rolID":
                        flujoDinero = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.tipoFlujoDineroArrayList.add(new TipoFlujoDinero(tipoFlujoeDineroID, flujoDinero));
            reader.endObject();
        }
    }

    private void leerArrayRolUsuario(JsonReader reader) throws IOException {
        String usuarioID = null;
        String rolID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "usuarioID":
                        usuarioID = reader.nextString();
                        break;
                    case "rolID":
                        rolID = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.rolUsuarioArrayList.add(new RolUsuario(usuarioID, rolID));
            reader.endObject();
        }

    }

    private void leerArrayRolActividad(JsonReader reader) throws IOException {
        String rolID = null;
        String actividadID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "rolID":
                        rolID = reader.nextString();
                        break;
                    case "actividadID":
                        actividadID = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.rolActividadArrayList.add(new RolActividad(rolID, actividadID));
            reader.endObject();
        }
    }

    private void leerArrayRoles(JsonReader reader) throws IOException {
        String rolID = null;
        String nombreRol = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "rolID":
                        rolID = reader.nextString();
                        break;
                    case "nombreRol":
                        nombreRol = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            this.rolArrayList.add(new Rol(rolID, nombreRol));
        }
    }

    private void leerArrayPlanesAhorro(JsonReader reader) throws IOException {
        String planAhorroID = null;
        String usuarioID = null;
        String fechaFinal = null;
        String fechaInicio = null;
        String cuotas = null;
        String monto = null;
        String periodo = null;
        String descripcion = null;
        String estadoPlanAhorroID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "planAhorroID":
                        planAhorroID = reader.nextString();
                        break;
                    case "usuarioID":
                        usuarioID = reader.nextString();
                        break;
                    case "fechaFinal":
                        fechaFinal = reader.nextString();
                        break;
                    case "fechaInicio":
                        fechaInicio = reader.nextString();
                        break;
                    case "cuotas":
                        cuotas = reader.nextString();
                        break;
                    case "monto":
                        monto = reader.nextString();
                        break;
                    case "periodo":
                        periodo = reader.nextString();
                        break;
                    case "descripcion":
                        descripcion = reader.nextString();
                        break;
                    case "estadoPlanAhorroID":
                        estadoPlanAhorroID = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.planAhorroArrayList.add(new PlanAhorro(planAhorroID, usuarioID, fechaFinal, fechaInicio, cuotas, monto, periodo, descripcion, estadoPlanAhorroID));
            reader.endObject();
        }
    }

    private void leerArrayLogin(JsonReader reader) throws IOException {
        String loginID = null;
        String correo = null;
        String contrasenia = null;
        String usuarioID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "loginID":
                        loginID = reader.nextString();
                        break;
                    case "correo":
                        correo = reader.nextString();
                        break;
                    case "contrasenia":
                        contrasenia = reader.nextString();
                        break;
                    case "usuarioID":
                        usuarioID = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.loginArrayList.add(new Login(loginID, correo, contrasenia, usuarioID));
            reader.endObject();
        }
    }

    private void leerArrayEstadoUsuario(JsonReader reader) throws IOException {
        String estadoID = null;
        String nombre = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "estadoID":
                        estadoID = reader.nextString();
                        break;
                    case "nombre":
                        nombre = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.estadoUsuarioArrayList.add(new EstadoUsuario(estadoID, nombre));
            reader.endObject();
        }
    }

    private void leerArrayEstadoPlanes(JsonReader reader) throws IOException {
    }

    private void leerArrayCuentas(JsonReader reader) throws IOException {
        String cuentaID = null;
        String numeroCuenta = null;
        String usuarioID = null;
        String tipoCuentaID = null;
        String tipoCuentaNombre = null;
        String bancoID = null;
        String saldo = null;
        String fechaVinculacion = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "cuentaID":
                        cuentaID = reader.nextString();
                        break;
                    case "numeroCuenta":
                        numeroCuenta = reader.nextString();
                        break;
                    case "usuarioID":
                        usuarioID = reader.nextString();
                        break;
                    case "tipoCuentaID":
                        tipoCuentaID = reader.nextString();
                        break;
                    case "tipoCuentaNombre":
                        tipoCuentaNombre = reader.nextString();
                        break;
                    case "bancoID":
                        bancoID = reader.nextString();
                        break;
                    case "saldo":
                        saldo = reader.nextString();
                        break;
                    case "fechaVinculacion":
                        fechaVinculacion = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.cuentaArrayList.add(new Cuenta(cuentaID, numeroCuenta, usuarioID, tipoCuentaID, tipoCuentaNombre, bancoID, saldo, fechaVinculacion));
            reader.endObject();
        }

    }

    private void leerArrayCategorias(JsonReader reader) throws IOException {
        String categoriaID = null;
        String nombre = null;
        Categoria subCategoriaID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "categoriaID":
                        categoriaID = reader.nextString();
                        break;
                    case "nombre":
                        nombre = reader.nextString();
                        break;
                    case "subCategorias":
                        subCategoriaID = leerSubcategorias(reader);
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.categoriaArrayList.add(new Categoria(categoriaID, nombre, subCategoriaID));
            reader.endObject();
        }
    }

    private Categoria leerSubcategorias(JsonReader reader) throws IOException {
        Categoria value = new Categoria();
        String categoriaID = null;
        String nombre = null;
        String subCategoriaID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "categoriaID":
                        categoriaID = reader.nextString();
                        break;
                    case "nombre":
                        nombre = reader.nextString();
                        break;
                    case "subCategoriaID":
                        subCategoriaID = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            value.setCategoriaID(categoriaID);
            value.setNombre(nombre);
            value.setSubCategoria(null);
        }
        reader.endArray();
        return value;
    }

    private void leerArrayConfiguracionSeguridad(JsonReader reader) throws IOException {
        String configuracionSeguridadID = null;
        String pin = null;
        String huella = null;
        String usuarioID = null;
        String configuracionID = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "configuracionSeguridadID":
                        configuracionSeguridadID = reader.nextString();
                        break;
                    case "pin":
                        pin = reader.nextString();
                        break;
                    case "huella":
                        huella = reader.nextString();
                        break;
                    case "usuarioID":
                        usuarioID = reader.nextString();
                        break;
                    case "configuracionID":
                        configuracionID = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.configuracionSeguridadArrayList.add(new ConfiguracionSeguridad(configuracionSeguridadID, pin, huella, usuarioID, configuracionID));
            reader.endObject();
        }
    }

    private void leerArrayTipoCuenta(JsonReader reader) throws IOException {
        String tipoCuentaID = null;
        String nombre = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "tipoCuentaID":
                        tipoCuentaID = reader.nextString();
                        break;
                    case "nombre":
                        nombre = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.tipoCuentaArrayList.add(new TipoCuenta(tipoCuentaID, nombre));
            reader.endObject();
        }
    }

    private void leerArrayBancos(JsonReader reader) throws IOException {
        String bancoID = null;
        String nombreBanco = null;
        String icono = null;
        String saldo = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "bancoID":
                        bancoID = reader.nextString();
                        break;
                    case "nombreBanco":
                        nombreBanco = reader.nextString();
                        break;
                    case "icono":
                        icono = reader.nextString();
                        break;
                    case "saldo":
                        saldo = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.bancoArrayList.add(new Banco(bancoID, nombreBanco, icono, saldo));
            reader.endObject();
        }
    }

    private void leerArrayActividades(JsonReader reader) throws IOException {
        String actividadID = null;
        String nombreActividad = null;
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "actividadID":
                        actividadID = reader.nextString();
                        break;
                    case "nombreActividad":
                        nombreActividad = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            this.actividadArrayList.add(new Actividad(actividadID, nombreActividad));
            reader.endObject();
        }
    }

}
