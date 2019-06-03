package com.appmoviles.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class CrearPlanFragment extends Fragment implements FrecuenciaPago.OnAddFrequencyPaymentListener {

    private LinearLayout ly_agregar_fecha;
    private LinearLayout ly_agregar_frecuencia_pago;
    private EditText etName, etMoney;

    FrecuenciaPago frecuanciaPagoDialog;

    private TextView tvDate, tvTime, tvCuota;
    private int mYear, mMonth, mDay;
    private String name, frequencyPayment, date;
    private float money;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_plan, container, false);

        ly_agregar_fecha = (LinearLayout) v.findViewById(R.id.ly_agregar_fecha);
        ly_agregar_frecuencia_pago = (LinearLayout) v.findViewById(R.id.ly_agregar_frecuencia_pago);
        tvDate = (TextView) v.findViewById(R.id.et_date);
        tvTime = (TextView) v.findViewById(R.id.tv_payment_frequency);
        tvCuota = (TextView) v.findViewById(R.id.tv_cuota);
        etName = (EditText) v.findViewById(R.id.et_name);
        etMoney = (EditText) v.findViewById(R.id.et_money);

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

            }

            @Override
            public void afterTextChanged(Editable s) {
                money = Float.parseFloat(etMoney.getText().toString());
                updateCuota();
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = etName.getText().toString();
                updateCuota();
            }
        });

        return v;
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

            if (!frequencyPayment.equals(null) && !date.equals(null) && money != 0 && !name.equals(null)) {

                String[] fpData = frequencyPayment.split("/");
                String fp = fpData[0];
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateSelected = sdf.parse(date);
                    long diff = dateSelected.getTime() - currentTime.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    Log.d("************ Days: ", String.valueOf(days));
                    //tvCuota.setText();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }
}
