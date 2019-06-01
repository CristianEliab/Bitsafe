package com.appmoviles.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


public class CrearPlanFragment extends Fragment implements FrecuenciaPago.OnAddFrequencyPaymentListener {

    private LinearLayout ly_agregar_fecha;
    private LinearLayout ly_agregar_frecuencia_pago;

    FrecuenciaPago frecuanciaPagoDialog;

    private TextView txtDate, txtTime;
    private int mYear, mMonth, mDay;

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
        txtDate=(TextView) v.findViewById(R.id.et_date);
        txtTime = (TextView) v.findViewById(R.id.tv_payment_frequency);

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
            txtDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };

    private void showPaymentFrequencyDialog(){

        FragmentManager fm = getFragmentManager();
        frecuanciaPagoDialog = new FrecuenciaPago();
        frecuanciaPagoDialog.setTargetFragment(this, 0);
        frecuanciaPagoDialog.show(fm, "fragment_payment_frequency");
    }

    @Override
    public void OnAddFrequencyPaymentSubmit(String fpSelected) {
        Log.e("///// Entrooo a close", fpSelected);
        if(!fpSelected.equals("Diario") && !fpSelected.equals("Semanal") && !fpSelected.equals("Cada 2 Semanas") && !fpSelected.equals("Mensual")) {
            txtTime.setText(fpSelected);
            frecuanciaPagoDialog.getDialog().dismiss();
        }
    }
}
