package com.appmoviles.proyecto.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.appmoviles.proyecto.modelo.Usuario;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler instance = null;

    public static final String DB_NAME = "Bitsafe";
    public static final int DB_VERSION = 2;

    // ---------------------------------------------------------------------------------------------
    //TABLA USUARIOS

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String USUARIO_ID = "userid";
    public static final String USUARIO_NOMBRE = "nombre";
    public static final String USUARIO_FECHA = "fecha";
    public static final String USUARIO_GENERO = "genero";
    public static final String USUARIO_TELEFONO = "telefono";
    public static final String USUARIO_ESTADO_ID = "estadoid";
    public static final String USUARIO_LOGIN_ID = "loginid";
    public static final String USUARIO_CONFIGURACION_ID = "configuracionid";
    public static final String CREATE_TABLA_USUARIOS = "CREATE TABLE " +
            "" + TABLA_USUARIOS + " (" + USUARIO_ID + " TEXT PRIMARY KEY, " +
            "" + USUARIO_NOMBRE + " TEXT, " + USUARIO_FECHA + " TEXT, " +
            "" + USUARIO_GENERO + " TEXT, " + USUARIO_TELEFONO + " TEXT, " +
            "" + USUARIO_ESTADO_ID + " TEXT, " + USUARIO_LOGIN_ID + "TEXT, " +
            "" + USUARIO_CONFIGURACION_ID + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA ACTIVIDADES

    public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA CATEGORIA

    public static final String TABLA_CATEGORIAS = "actividades";
    public static final String CATEGORIA_ID = "categoriaid";
    public static final String CATEGORIA_NOMBRE = "nombre";
    public static final String ID_CATEGORIA = "idcategoria";
    public static final String CREATE_TABLA_CATEGORIA = "CREATE TABLE " +
            "" + TABLA_CATEGORIAS + " (" + CATEGORIA_ID + " TEXT PRIMARY KEY, " +
            "" + CATEGORIA_NOMBRE + " TEXT, " + ID_CATEGORIA + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA BANCOS

    public static final String TABLA_BANCOS = "bancos";
    public static final String BANCOS_ID = "bancoid";
    public static final String BANCOS_NOMBRE = "nombre";
    public static final String ID_BANCO = "idbanco";
    public static final String CREATE_TABLA_BANCOS = "CREATE TABLE " +
            "" + TABLA_BANCOS + " (" + BANCOS_ID + " TEXT PRIMARY KEY, " +
            "" + BANCOS_NOMBRE + " TEXT, " + ID_BANCO + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA CONFIGURACIONES

    public static final String TABLA_CONFIGURACION = "configuracion";
    public static final String CONFIGURACION_ID = "configuracionid";
    public static final String CONFIGURACION_PIN = "pin";
    public static final String CONFIGURACION_HUELLA = "huella";
    public static final String CONFIGURACION_USUARIO_ID = "usuarioid";
    public static final String ID_CONFIGURACION = "idconfiguracion";
    public static final String CREATE_TABLA_CONFIGURACION = "CREATE TABLE " +
            "" + TABLA_CONFIGURACION + " (" + CONFIGURACION_ID + " TEXT PRIMARY KEY, " +
            "" + CONFIGURACION_PIN + " TEXT, " + CONFIGURACION_HUELLA + "TEXT, " +
            "" + CONFIGURACION_USUARIO_ID + "TEXT, " + ID_CONFIGURACION + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA CUENTAS

    public static final String TABLA_CUENTAS = "cuentas";
    public static final String CUENTA_ID = "userid";
    public static final String CUENTA_NOMBRE = "nombre";
    public static final String CUENTA_USUARIO_ID = "usuarioid";
    public static final String CUENTA_TIPO_CUENTA_ID = "tipocuentaid";
    public static final String CUENTA_TIPO_CUENTA_NOMBRE = "tipocuentanombre";
    public static final String ID_CUENTA = "idcuenta";
    public static final String CREATE_TABLA_CUENTAS = "CREATE TABLE " +
            "" + TABLA_CUENTAS + " (" + CUENTA_ID + " TEXT PRIMARY KEY, " +
            "" + CUENTA_NOMBRE + " TEXT, " + CUENTA_USUARIO_ID + " TEXT, " +
            "" + CUENTA_TIPO_CUENTA_ID + " TEXT, " + CUENTA_TIPO_CUENTA_NOMBRE + " TEXT, " +
            "" + ID_CUENTA + " TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA ESTADO_PLANES_AHORRO

    public static final String TABLA_ESTADO_PLANES_AHORRO = "estadoplanesahorro";
    public static final String ESTADO_PLANES_ID = "estadoplanesid";
    public static final String ESTADO_PLANES_NOMBRE = "estadonombre";
    public static final String ESTADO_PLANES_ESTADO = "estadoestado";
    public static final String CREATE_TABLA_ESTADO_PLANES = "CREATE TABLE " +
            "" + TABLA_ESTADO_PLANES_AHORRO + " (" + ESTADO_PLANES_ID + " TEXT PRIMARY KEY, " +
            "" + ESTADO_PLANES_NOMBRE + " TEXT, " + ESTADO_PLANES_ESTADO + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA ESTADO_USUARIOS

    public static final String TABLA_ESTADO_USUARIO = "estadousuario";
    public static final String ESTADO_ID = "estadoid";
    public static final String ESTADO_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ESTADO = "CREATE TABLE " +
            "" + TABLA_ESTADO_USUARIO + " (" + ESTADO_ID + " TEXT PRIMARY KEY, " +
            "" + ESTADO_NOMBRE + " TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA LOGIN

    public static final String TABLA_LOGIN = "login";
    public static final String LOGIN_ID = "loginid";
    public static final String LOGIN_CORREO = "logincorreo";
    public static final String LOGIN_CONTRASENIA = "logincontrasenia";
    public static final String LOGIN_USUARIO_ID = "loginusuarioid";
    public static final String CREATE_TABLA_LOGIN = "CREATE TABLE " +
            "" + TABLA_LOGIN + " (" + LOGIN_ID + " TEXT PRIMARY KEY, " +
            "" + LOGIN_CORREO + " TEXT, " + LOGIN_CONTRASENIA + "TEXT, " +
            "" + LOGIN_USUARIO_ID + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA PLAN_AHORRO

    public static final String TABLA_PLAN_AHORRO = "planahorro";
    public static final String PLAN_AHORRO_ID = "planAhorroid";
    public static final String PLAN_AHORRO_USUARIO_ID = "usuarioahorro";
    public static final String PLAN_AHORRO_FECHA_FINAL = "fechafinal";
    public static final String PLAN_AHORRO_FECHA_INICIO = "fechainicio";
    public static final String PLAN_AHORRO_CUOTAS = "cuotas";
    public static final String PLAN_AHORRO_MONTO = "monto";
    public static final String PLAN_AHORRO_PERIODO = "periodo";
    public static final String PLAN_AHORRO_DESCRIPCION = "descripcion";
    public static final String PLAN_AHORRO_ESTADO = "estadoplan";
    public static final String CREATE_TABLA_PLAN_AHORRO = "CREATE TABLE " +
            "" + TABLA_PLAN_AHORRO + " (" + PLAN_AHORRO_ID + " TEXT PRIMARY KEY, " +
            "" + PLAN_AHORRO_USUARIO_ID + " TEXT, " + PLAN_AHORRO_FECHA_FINAL + " TEXT, " +
            "" + PLAN_AHORRO_FECHA_INICIO + " TEXT, " + PLAN_AHORRO_CUOTAS + " TEXT, " +
            "" + PLAN_AHORRO_MONTO + " TEXT, " + PLAN_AHORRO_PERIODO + "TEXT, " +
            "" + PLAN_AHORRO_DESCRIPCION + "TEXT, " + PLAN_AHORRO_ESTADO + "TEXT)";

    // ---------------------------------------------------------------------------------------------
    //TABLA ROLES

    /*public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";*/

    // ---------------------------------------------------------------------------------------------
    //TABLA ROL_USUARIO

     /*public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";*/

    // ---------------------------------------------------------------------------------------------
    //TABLA ROL_ACTIVIDAD

     /*public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";*/

    // ---------------------------------------------------------------------------------------------
    //TABLA TIPO_CUENTAS

     /*public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";*/

    // ---------------------------------------------------------------------------------------------
    //TABLA TIPO_FLUJO_CUENTAS

     /*public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";*/

    // ---------------------------------------------------------------------------------------------
    //TABLA TIPO_TRANSACCIONES

    /*public static final String TABLA_ACTIVIDADES = "actividades";
    public static final String ACTIVIDAD_ID = "actividadid";
    public static final String ACTIVIDAD_NOMBRE = "nombre";
    public static final String CREATE_TABLA_ACTIVIDAD = "CREATE TABLE " +
            "" + TABLA_ACTIVIDADES + " (" + ACTIVIDAD_ID + " TEXT PRIMARY KEY, " +
            "" + ACTIVIDAD_NOMBRE + " TEXT)";*/

    // ---------------------------------------------------------------------------------------------
    //TRANSACCION

    public static final String TABLA_TRANSACCION = "transaccion";
    public static final String TRANSACCION_ID = "transaccionid";
    public static final String TRANSACCION_CUENTA_ORIGEN_ID = "cuentaorigenid";
    public static final String TRANSACCION_CUENTA_DESTINO_ID = "cuentadestinoid";
    public static final String TRANSACCION_TIPO_TRANSACCION_ID = "tipotransaccionid";
    public static final String TRANSACCION_CATEGORIA_ID = "categoriaid";
    public static final String TRANSACCION_DESCRIPCION = "descripcion";
    public static final String TRANSACCION_MONTO_TRANSACCION = "montotransaccion";
    public static final String TRANSACCION_FECHA_TRANSACCION = "fechatransaccion";
    public static final String CREATE_TABLA_TRANSACCION = "CREATE TABLE " +
            "" + TABLA_TRANSACCION + " (" + TRANSACCION_ID + " TEXT PRIMARY KEY, " +
            "" + TRANSACCION_CUENTA_ORIGEN_ID + " TEXT, " + TRANSACCION_CUENTA_DESTINO_ID + " TEXT, " +
            "" + TRANSACCION_TIPO_TRANSACCION_ID + " TEXT, " + TRANSACCION_CATEGORIA_ID + " TEXT, " +
            "" + TRANSACCION_DESCRIPCION + " TEXT, " + TRANSACCION_MONTO_TRANSACCION + "TEXT, " +
            "" + TRANSACCION_FECHA_TRANSACCION + "TEXT)";


    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        return instance;
    }

    private DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }

    // ------------------------------------------------------------------------------------------------------------------------
    //CRUD USUARIO

    //CREATE
    public void createUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLA_USUARIOS + " (" + USUARIO_ID + ", " + USUARIO_NOMBRE + ", " + USUARIO_FECHA + ", " +
                "" + USUARIO_GENERO + ", " + USUARIO_TELEFONO + ", " + USUARIO_ESTADO_ID + ", " + USUARIO_LOGIN_ID + ", " +
                "" + USUARIO_CONFIGURACION_ID + ") VALUES ('" +
                "" + usuario.getUsuarioID() + "','" +
                "" + usuario.getNombre() + "','" +
                "" + usuario.getFecha_nacimiento() + "','" +
                "" + usuario.getGenero() + "','" +
                "" + usuario.getTelefono() + "', '" +
                "" + usuario.getEstadoUsuarioID() + "', '" +
                "" + usuario.getLoginID() + "', '" +
                "" + usuario.getConfiguracionID() + "')");
        db.close();
    }

    //READ
    public ArrayList<Usuario> getAllUsuariosOfUser(String uid) {
        ArrayList<Usuario> respuesta = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_USUARIOS + " WHERE " + USUARIO_ID + "='" + uid + "'", null);
        Log.e(">>>", "Amigos");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setUsuarioID(cursor.getString(cursor.getColumnIndex(USUARIO_ID)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndex(USUARIO_NOMBRE)));
                usuario.setFecha_nacimiento(cursor.getString(cursor.getColumnIndex(USUARIO_FECHA)));
                usuario.setGenero(cursor.getString(cursor.getColumnIndex(USUARIO_GENERO)));
                usuario.setTelefono(cursor.getString(cursor.getColumnIndex(USUARIO_TELEFONO)));
                usuario.setEstadoUsuarioID(cursor.getString(cursor.getColumnIndex(USUARIO_ESTADO_ID)));
                usuario.setLoginID(cursor.getString(cursor.getColumnIndex(USUARIO_LOGIN_ID)));
                usuario.setConfiguracionID(cursor.getString(cursor.getColumnIndex(USUARIO_CONFIGURACION_ID)));
                respuesta.add(usuario);
                Log.e(">>>", usuario.getNombre());

            } while (cursor.moveToNext());
        }
        db.close();

        return respuesta;

    }

    //DELETE
    public void deleteUsuariosOfUser(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLA_USUARIOS + " WHERE " + USUARIO_ID + "='" + uid + "'");
        db.close();
    }
}
