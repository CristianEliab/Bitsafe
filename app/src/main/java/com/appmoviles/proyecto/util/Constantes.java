package com.appmoviles.proyecto.util;

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

    public final static String BUNDLE_TIPO_I_O = "BUNDLE_TIPO_I_O";

    public final static String BUNDLE_TIPO_INGRESOS = "Ingresos";
    public final static String BUNDLE_TIPO_GASTOS = "Gastos";

    //PARA LA BASE DE DATOS --> getReference().child(constante)
    public final static String CHILD_USUARIOS = "usuarios";
    public final static String CHILD_BANCOS = "bancos";
    public final static String CHILD_CUENTAS = "cuentas";
    public final static String CHILD_TRANSACCIONES = "transacciones";

    //PARA LAS IMÁGENES
    public final static String ICON_BANCO_DAVIVIENDA = "banco_davivienda";
    public final static String ICON_BANCO_BOGOTA = "banco_bogota";
    public final static String ICON_BANCO_BANCOLOMBIA = "banco_bancolombia";
    public final static String ICON_BANCO_ITAU = "banco_itau";

}
