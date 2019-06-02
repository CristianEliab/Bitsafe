package com.appmoviles.proyecto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;

public class VincularCuentaFragment extends Fragment implements Serializable {

    private Spinner spinner_cuenta_tipo;
    private Spinner spinner_banco_cuenta;
    private EditText tv_numero_cuenta;
    private LinearLayout ly_agregar_fecha_cuenta;
    private TextView et_date_cuenta;

    private int mYear, mMonth, mDay;

    public VincularCuentaFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_vincular_cuenta, container, false);

        spinner_cuenta_tipo = v.findViewById(R.id.spinner_cuenta_tipo);
        spinner_banco_cuenta=v.findViewById(R.id.spinner_banco_cuenta);
        tv_numero_cuenta = v.findViewById(R.id.tv_numero_cuenta);
        ly_agregar_fecha_cuenta = v.findViewById(R.id.ly_agregar_fecha_cuenta);
        et_date_cuenta =v.findViewById(R.id.et_date_cuenta);


        ly_agregar_fecha_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
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

            et_date_cuenta.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };


}
