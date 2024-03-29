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
    public final static String CHILD_CATEGORIAS = "categorias";
    public final static String CHILD_ROL_USUARIO = "rolusuario";

    public final static String CHILD_USUARIOS_ID = "usuarios_id";
    public final static String CHILD_BANCOS_ID = "bancos_id";
    public final static String CHILD_CUENTAS_ID = "cuentas_id";
    public final static String CHILD_TRANSACCIONES_ID = "transacciones_id";
    public final static String CHILD_ROL_USUARIO_ID = "rolusuario_id";
    public final static String CHILD_ESTADISTICAS_ID = "estadisticas";


    public final static String CHILD_ESTADISTICAS_ENERO = "ENERO";
    public final static String CHILD_ESTADISTICAS_FEBRERO = "FEBRERO";
    public final static String CHILD_ESTADISTICAS_MARZO = "MARZO";
    public final static String CHILD_ESTADISTICAS_ABRIL = "ABRIL";
    public final static String CHILD_ESTADISTICAS_MAYO = "MAYO";
    public final static String CHILD_ESTADISTICAS_JUNIO = "JUNIO";
    public final static String CHILD_ESTADISTICAS_JULIO = "JULIO";
    public final static String CHILD_ESTADISTICAS_AGOSTO = "AGOSTO";
    public final static String CHILD_ESTADISTICAS_SEPTIEMBRE = "SEPTIEMBRE";
    public final static String CHILD_ESTADISTICAS_OCTUBRE = "OCTUBRE";
    public final static String CHILD_ESTADISTICAS_NOVIEMBRE = "NOVIEMBRE";
    public final static String CHILD_ESTADISTICAS_DICIEMBRE = "DICIEMBRE";

    public final static String CHILD_ESTADISTICAS_GENERO_ID = "estadisticasgenero";
    public final static String CHILD_ESTADISTICAS_MASCULINO = "masculino";
    public final static String CHILD_ESTADISTICAS_FEMENINO = "femenino";

    public final static String MASCULINO = "M";
    public final static String FEMENINO = "F";


    public final static String REGISTRO_MASCULINO = "Masculino";
    public final static String REGISTRO_FEMENINO = "Femenino";
    public final static String CHILD_PLANES_AHORRO = "planesahorro";

    public final static String GO_TO_PERFIL = "GO_TO_PERFIL";
    public final static String CLIENTES = "CLIENTE";
    public final static String ESTADISTICAS = "ESTADISTICAS";
    public final static String CARGAR = "CARGAR";
    public final static String TRANSACCIONES = "TRANSACCIONES";
    public final static String FRAGMENT = "FRAGMENT";

    public final static String CREAR_TRANSACCION = "CREAR_TRANSACCION";

    //PARA LAS IMÁGENES
    public final static String ICON_BANCO_DAVIVIENDA = "banco_davivienda";
    public final static String ICON_BANCO_BOGOTA = "banco_bogota";
    public final static String ICON_BANCO_BANCOLOMBIA = "banco_bancolombia";
    public final static String ICON_BANCO_ITAU = "banco_itau";

    public final static String BANCO_DAVIVIENDA = "Banco Davivienda";
    public final static String BANCO_BOGOTA = "Banco Bogota";
    public final static String BANCO_BANCOLOMBIA = "Banco Bancolombia";
    public final static String BANCO_ITAU = "Banco Itau";

    //PARA LAS GRÄFICAS
    public final static String TIPO_PIE = "tipopie";
    public final static String TIPO_BAR = "tipobar";

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


    //Para crear transacciones, ID de Cuentas predeterminadas
    //Sale dinero de una cuenta predeterminada a mi cuenta --> INGRESO
    public final static String CUENTA_ORIGEN_ID = "999";
    //Sale dinero de mi cuenta a una cuenta predeterminada --> GASTO
    public final static String CUENTA_DESTINO_ID = "888";


    //TPOS DE CUENTAS - PARA PONER IMAGEN
    public final static String CUENTA_TIPO_CREDITO = "01";
    public final static String CUENTA_TIPO_DEBIDO = "02";


    // Almacenar Imagenes
    public final static String CHILD_IMAGENES_PERFIL = "fotos_clientes";

    public final static String CATEGORIA_ENVIO_ID = "20";
    public final static String CATEGORIA_RECEPCION_ID = "30";

}
