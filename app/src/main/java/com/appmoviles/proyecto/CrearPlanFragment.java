package com.appmoviles.proyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.PlanAhorro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_PLANES_AHORRO;


public class CrearPlanFragment extends Fragment implements FrecuenciaPago.OnAddFrequencyPaymentListener {

    private LinearLayout ly_agregar_fecha;
    private LinearLayout ly_agregar_frecuencia_pago;
    private EditText etName, etMoney;
    private Button btn_guardar, btn_cancelar;

    FrecuenciaPago frecuanciaPagoDialog;

    private TextView tvDate, tvTime, tvCuota;
    private int mYear, mMonth, mDay;
    private String name, frequencyPayment, date, dateInit;
    private float money;
    private boolean isReady;

    private List<Cuenta> listaCuentas;

    FirebaseDatabase database;
    FirebaseAuth auth;

    String userId;
    long cantidad;
    float cuotaValue;

    public CrearPlanFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_plan, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ly_agregar_fecha = (LinearLayout) v.findViewById(R.id.ly_agregar_fecha);
        ly_agregar_frecuencia_pago = (LinearLayout) v.findViewById(R.id.ly_agregar_frecuencia_pago);
        tvDate = (TextView) v.findViewById(R.id.et_date);
        tvTime = (TextView) v.findViewById(R.id.tv_payment_frequency);
        tvCuota = (TextView) v.findViewById(R.id.tv_cuota);
        etName = (EditText) v.findViewById(R.id.et_name);
        etMoney = (EditText) v.findViewById(R.id.et_money);
        btn_guardar = (Button) v.findViewById(R.id.btn_guardar);
        btn_cancelar = (Button) v.findViewById(R.id.btn_cancelar);

        cargarUserID();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReady) {
                    sendData();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle("¡Tu ahorro " + name + " ha sido guardado!")
                            .setMessage("Ánimate a seguir ahorrando con Bitsafe. Puedes crear todos los ahorros que desees de manera segura con nuestro servico.")
                            .setCancelable(true);

                    builder1.setPositiveButton(
                            "Continuar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                    PlanesFragment planesFragment = new PlanesFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.contenido_cliente, planesFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle("No es el ahorro que buscas")
                            .setMessage("Primero debes de llenar el formulario correctamente para guardar tu plan de ahorro.")
                            .setCancelable(true);

                    builder1.setPositiveButton(
                            "Entendido",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main, new PlanesFragment()).addToBackStack(null).commit();
            }
        });

        ly_agregar_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        ly_agregar_frecuencia_pago.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showPaymentFrequencyDialog();
            }
        });


        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!etMoney.getText().toString().equals("")) {
                    money = Float.parseFloat(etMoney.getText().toString());
                }else if(etMoney.getText().toString().equals("")){
                    money = 0;
                }
                updateCuota();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!etName.getText().toString().equals("")) {
                    name = etName.getText().toString();
                }else if(etName.getText().toString().equals("")){
                    name = null;
                }
                updateCuota();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        return v;
    }

    private void sendData() {
        PlanAhorro planAhorro = new PlanAhorro(UUID.randomUUID().toString(), userId, date, dateInit, String.valueOf(cuotaValue), String.valueOf(cantidad), "0", String.valueOf(money), frequencyPayment, name, "01");
        database.getReference().child(CHILD_PLANES_AHORRO).push().setValue(planAhorro);
        Log.d("sendData:", "////////// COMPLETO LA SUBIDA DEL OBJ AHORRO /////////////////");
    }

    public void cargarUserID() {

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }

    }

    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }


    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            date = (String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                    + "/" + String.valueOf(year));
            tvDate.setText(date);
            updateCuota();
        }
    };

    private void showPaymentFrequencyDialog() {

        FragmentManager fm = getFragmentManager();
        frecuanciaPagoDialog = new FrecuenciaPago();
        frecuanciaPagoDialog.setTargetFragment(this, 0);
        frecuanciaPagoDialog.show(fm, "fragment_payment_frequency");
    }

    @Override
    public void OnAddFrequencyPaymentSubmit(String fpSelected) {
        Log.e("///// Entrooo a close", fpSelected);
        frequencyPayment = fpSelected;
        String[] fpData = frequencyPayment.split("/");
        if (!fpData[0].equals(fpData[1])) {
            //if(!fpSelected.equals("Diario") && !fpSelected.equals("Semanal") && !fpSelected.equals("Cada 2 Semanas") && !fpSelected.equals("Mensual")) {
            tvTime.setText(fpSelected);
            frecuanciaPagoDialog.getDialog().dismiss();
            updateCuota();
        }
    }

    private void updateCuota() {

        Log.d("///////////////////////", "Entro a UpdateCuenta /////////////////////////////");

        try {
            Log.d("*** frequencyPayment: ", frequencyPayment);
            Log.d("*** date: ", date);
            Log.d("*** money: ", String.valueOf(money));
            Log.d("*** name: ", name);

            if (!frequencyPayment.equals(null) && !date.equals(null) && money > 0 && !name.equals(null)) {

                String[] fpData = frequencyPayment.split("/");
                String fp = fpData[0];
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateSelected = sdf.parse(date);
                    dateInit = sdf.format(currentTime.getTime());

                    long diff = dateSelected.getTime() - currentTime.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    Log.d("************ Days: ", String.valueOf(days));
                    setTvCuota(days, fp, fpData[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                tvCuota.setText("Completa todos los campos para calcular tus cuotas.");
                tvCuota.setTextColor(Color.parseColor("#828282"));
                isReady = false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            tvCuota.setText("Completa todos los campos para calcular tus cuotas.");
            tvCuota.setTextColor(Color.parseColor("#828282"));
            isReady = false;
        }
    }

    private void setTvCuota(long days, String fp, String fps) {
        String msg = "";
        int divDays = 1;
        if (fp.equals("Diario")) {
            divDays = 1;
            msg = "a Diario en la " + fps + ".";
        } else if (fp.equals("Semanal")) {
            divDays = 7;
            msg = "cada Semana en los días " + fps + ".";
        } else if (fp.equals("Cada 2 Semanas")) {
            divDays = 14;
            msg = "cada 2 Semanas en los días " + fps + ".";
        } else if (fp.equals("Mensual")) {
            divDays = 30;
            msg = "cada Mes en las fechas número " + fps + ".";
        }
        cantidad = days / divDays;
        if (cantidad <= 1) {
            tvCuota.setText("Se recomienda modificar el formulario para crear una cantidad de cuotas rezonables para tu ahorro.");
            tvCuota.setTextColor(Color.parseColor("#828282"));
        } else {
            cuotaValue = money / cantidad;
            tvCuota.setText("Para cumplir con tu ahorro, deberás de completar un total de " + cantidad + " cuotas por un valor de " + cuotaValue + " pesos, " + msg);
            tvCuota.setTextColor(Color.parseColor("#4F4F4F"));
            isReady = true;
        }
    }
}
