package com.appmoviles.proyecto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.appmoviles.proyecto.util.Constantes.CHILD_BANCOS;
import static com.appmoviles.proyecto.util.Constantes.CUENTA_TIPO_CREDITO;
import static com.appmoviles.proyecto.util.Constantes.CUENTA_TIPO_DEBIDO;

public class VincularCuentaFragment extends Fragment implements Serializable {

    private Spinner spinner_cuenta_tipo;
    private Spinner spinner_banco_cuenta;
    private EditText tv_numero_cuenta;
    private LinearLayout ly_agregar_fecha_cuenta;
    private TextView et_date_cuenta;
    private LinearLayout ly_continuar_cuenta;

    FirebaseDatabase database;
    FirebaseAuth auth;
    private int mYear, mMonth, mDay;
    private String fechita;

    public VincularCuentaFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_vincular_cuenta, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        spinner_cuenta_tipo = v.findViewById(R.id.spinner_cuenta_tipo);



        spinner_banco_cuenta=v.findViewById(R.id.spinner_banco_cuenta);

        final ArrayList<CharSequence> bancos = new ArrayList<>();
        database.getReference().child(CHILD_BANCOS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Banco bancoTmp;
                for (DataSnapshot hijo: dataSnapshot.getChildren()){
                    bancoTmp=hijo.getValue(Banco.class);
                    bancos.add(bancoTmp.getNombreBanco());

                    ArrayAdapter<CharSequence> bancosLista = new ArrayAdapter<>(getContext(), R.layout.spinner_item_banco,bancos);
                    spinner_banco_cuenta.setAdapter(bancosLista);
                    //ahora
                    String seleccionBanco = spinner_banco_cuenta.getSelectedItem().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tv_numero_cuenta = v.findViewById(R.id.tv_numero_cuenta);



        /**
        tv_numero_cuenta.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
            **/





        ly_agregar_fecha_cuenta = v.findViewById(R.id.ly_agregar_fecha_cuenta);
        et_date_cuenta =v.findViewById(R.id.et_date_cuenta);
        ly_agregar_fecha_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    et_date_cuenta.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                            + "-" + String.valueOf(year));
                    fechita=et_date_cuenta.getText().toString();
                }
                };
        });


        ly_continuar_cuenta = v.findViewById(R.id.ly_continuar_cuenta);
        ly_continuar_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifico que los campos esten llenos
                final String numeroDeCuenta = tv_numero_cuenta.getText().toString();
                if(numeroDeCuenta.equals("") || fechita.equals("")){
                    mostrarMensaje("Debe llenar todos lo campos de cuenta válido");
                }else{
                    String cuentaid=(UUID.randomUUID().toString());
                    final String idUsuario = auth.getCurrentUser().getUid();
                    String tipoCuenta = spinner_cuenta_tipo.getSelectedItem().toString();
                    String tipoCuentaId="";
                    if(tipoCuenta.equals("Cuenta Credito")){
                        tipoCuentaId=CUENTA_TIPO_CREDITO;
                    }else {
                        tipoCuentaId=CUENTA_TIPO_DEBIDO;
                    }

                    String tipoBanco=spinner_banco_cuenta.getSelectedItem().toString();
                    String tipoBancoId="";
                    if (tipoBanco.equals("Davivienda")){
                        tipoBancoId="01";
                    }else if(tipoBanco.equals("Bogota")){
                        tipoBancoId="02";
                    }else if(tipoBanco.equals("Bancolombia")){
                        tipoBancoId="03";
                    }else{
                        tipoBancoId="04";
                    }
                    String saldo = "50000";

                    final Cuenta cuenta = new Cuenta(cuentaid,numeroDeCuenta,idUsuario,tipoCuentaId,tipoCuenta,tipoBancoId,saldo,fechita);
                    database.getReference().child("cuentas").push().setValue(cuenta).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),"Tu cuenta ha sido agregada exitosamente",Toast.LENGTH_LONG);
                        }
                    });

                    database.getReference().child("usuarios_id").child(idUsuario).child("listaCuentas").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long hijo=dataSnapshot.getChildrenCount();
                            database.getReference().child("usuarios_id").child(idUsuario).child("listaCuentas").child(hijo+"").setValue(cuenta);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                getFragmentManager().beginTransaction().replace(R.id.vincular, new CuentasFragment()).addToBackStack(null).commit();
            }
        });

        return v;
    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }




}
