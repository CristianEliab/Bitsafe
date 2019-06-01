package com.appmoviles.proyecto;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;
    private int year, month, day;
    // Envio de información
    private OnFragmentInteractionListener listener;

    public DatePickerFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");

        String fecha = day + "-" + month + "-" + year;
        if (listener != null) {
            listener.onAction(this, fecha);
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
/*
        return new DatePickerDialog(getActivity(), R.style.DialogTheme, ondateSet, year, month, day);
*/
    }

    public void setInteractionListener(OnFragmentInteractionListener listener) {
        this.listener = listener;
    }
}