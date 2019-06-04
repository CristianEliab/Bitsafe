package com.appmoviles.proyecto.util;

import com.appmoviles.proyecto.Splash;

import java.io.Serializable;

public class Constantes {

    public final static String MONTO_KEY = "MONTO_KEY";
    public final static String MONTO_DEFAULT = "Agregar Monto";
    public final static String CLIENTE_ORIGEN_KEY_NOMBRE = "CLIENTE_ORIGEN_KEY_NOMBRE";
    public final static String CLIENTE_ORIGEN_DEFAULT_NOMBRE = "Cliente Origen";
    public final static String CLIENTE_ORIGEN_KEY_ID = "CLIENTE_ORIGEN_KEY_ID";
    public final static String CLIENTE_DESTINO_KEY = "CLIENTE_DESTINO_KEY";
    public final static String FECHA_KEY = "FECHA_KEY";
    public final static String FECHA_DEFAULT = "Agregar una fecha";
    public final static String CLIENTE_DESTINO_KEY_NOMBRE = "CLIENTE_DESTINO_KEY_NOMBRE";
    public final static String CLIENTE_DESTINO_DEFAULT_NOMBRE = "Cliente Destino";
    public final static String PUNTO = ",";
    public final static String CLIENTE_CUENTA = "Cuenta: ";

    //Muriel - Para pasar de un fragment a otro objetos tipo banco, cuenta, etc
    public final static String BUNDLE_BANCO = "BUNDLE_BANCO";
    public final static String BUNDLE_CUENTA = "BUNDLE_CUENTA";
    public final static String BUNDLE_TRANSACCION = "BUNDLE_TRANSACCION";
    public final static String BUNDLE_PLANES_AHORRO = "BUNDLE_PLANES_AHORRO";

    public final static String BUNDLE_TIPO_I_O = "BUNDLE_TIPO_I_O";

    public final static String BUNDLE_TIPO_INGRESOS = "Ingresos";
    public final static String BUNDLE_TIPO_GASTOS = "Gastos";

    //PARA LA BASE DE DATOS --> getReference().child(constante)
    public final static String CHILD_USUARIOS = "usuarios";
    public final static String CHILD_BANCOS = "bancos";
    public final static String CHILD_CUENTAS = "cuentas";
    public final static String CHILD_TRANSACCIONES = "transacciones";
    public final static String CHILD_PLANES_AHORRO = "planesahorro";

    public final static String GO_TO_PERFIL = "GO_TO_PERFIL";
    public final static String CLIENTES = "CLIENTE";
    public final static String ESTADISTICAS = "ESTADISTICAS";
    public final static String CARGAR = "CARGAR";
    public final static String TRANSACCIONES = "TRANSACCIONES";
    public final static String FRAGMENT = "FRAGMENT";

    //PARA LAS IMÁGENES
    public final static String ICON_BANCO_DAVIVIENDA = "banco_davivienda";
    public final static String ICON_BANCO_BOGOTA = "banco_bogota";
    public final static String ICON_BANCO_BANCOLOMBIA = "banco_bancolombia";
    public final static String ICON_BANCO_ITAU = "banco_itau";


    //Para mostrar Datos
    public final static String USUARIO = "USUARIO";
    public final static String FINANZAS = "FINANZAS";
    public final static String DATOS_FRAGMENT = "DATOS_FRAGMENT";
    public final static String TRANSACCIONES_DATOS = "TRANSACCIONES_DATOS";
    public final static String DONDE_VIENE = "DONDE_VIENE";
    public final static String FRAGMENT_CLIENTE = "FRAGMENT_CLIENTE";
    public final static String FRAGMENT_ESTADISTICAS = "FRAGMENT_ESTADISTICAS";
    public final static String FRAGMENT_TRANSACCION = "FRAGMENT_TRANSACCION";
    public final static String FRAGMENT_FINANZAS = "FRAGMENT_FINANZAS";
    public final static String FRAGMENT_CARGAR = "FRAGMENT_CARGAR";
    public final static String FRAGMENT_PLANES = "FRAGMENT_PLANES";
    public final static String FRAGMENT_CUENTAS = "FRAGMENT_CUENTAS";
    public final static String USUARIO_DESTINO = "USUARIO_DESTINO";
    public final static String USUARIO_ORIGEN = "USUARIO_ORIGEN";
    public final static String USUARIO_SERIALIZABLE = "USUARIO_SERIALIZABLE";

    //Para Planes de Ahorro

}
