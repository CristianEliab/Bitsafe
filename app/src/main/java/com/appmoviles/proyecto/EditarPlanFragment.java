package com.appmoviles.proyecto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.PlanAhorro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_PLANES_AHORRO;

public class EditarPlanFragment extends Fragment {

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

    private PlanAhorro planAhorro;

    public EditarPlanFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_plan, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Bundle bundle = getArguments();
        planAhorro= (PlanAhorro) bundle.getSerializable(BUNDLE_PLANES_AHORRO);

        ly_agregar_fecha = (LinearLayout) v.findViewById(R.id.ly_agregar_fecha);
        ly_agregar_frecuencia_pago = (LinearLayout) v.findViewById(R.id.ly_agregar_frecuencia_pago);
        tvDate = (TextView) v.findViewById(R.id.et_date);
        tvTime = (TextView) v.findViewById(R.id.tv_payment_frequency);
        tvCuota = (TextView) v.findViewById(R.id.tv_cuota);
        etName = (EditText) v.findViewById(R.id.et_name);
        etMoney = (EditText) v.findViewById(R.id.et_money);
        btn_guardar = (Button) v.findViewById(R.id.btn_guardar);
        btn_cancelar = (Button) v.findViewById(R.id.btn_cancelar);

        setValues();

        return v;
    }

    private void setValues(){
        tvDate.setText(planAhorro.getFechaFinal());
        tvTime.setText(planAhorro.getPeriodo());
        etName.setText(planAhorro.getDescripcion());
        etMoney.setText(planAhorro.getMeta());

        frequencyPayment = planAhorro.getPeriodo();
        date = planAhorro.getFechaFinal();
        money = Long.parseLong(planAhorro.getMeta());
        name = planAhorro.getDescripcion();

        updateCuota();
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
                    dateInit = sdf.format(currentTime.getTime());

                    long diff = dateSelected.getTime() - currentTime.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    Log.d("************ Days: ", String.valueOf(days));
                    setTvCuota(days, fp, fpData[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
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
