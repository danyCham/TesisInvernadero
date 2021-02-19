package com.app.invernadero;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.invernadero.Model.Detector;
import com.app.invernadero.Model.Result;
import com.app.invernadero.Model.Sensor;
import com.app.invernadero.Utils.ApiAdaptar;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalerySensor extends AppCompatActivity {
    private RecyclerView mRecyclerSensor;
    private SensorAdaptador mAdapter;
    TextView tv_mensaje;
    EditText tv_fecha_ini, tv_fecha_fin;
    Button bn_buscar;
    SharedPreferences preferencia;

    public static final String SENSOR ="sensor";
    public static final String FECHAINI ="fecha_ini";
    public static final String FECHAFIN ="fecha_fin";

    private int dia,mes,anio, opcion;
    private String fechaIni = "", fechaFin = "", sensor= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_sensor);

        mRecyclerSensor = findViewById(R.id.recycle_view_sensor);
        tv_fecha_ini = findViewById(R.id.tv_buscarIni);
        tv_fecha_fin = findViewById(R.id.tv_buscarFin);
        tv_mensaje = findViewById(R.id.txt_mensaje_sensor);
        bn_buscar  = findViewById(R.id.bn_buscar_sensor);

        mRecyclerSensor.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(GalerySensor.this, 1);
        mRecyclerSensor.setLayoutManager(layoutManager);

        mAdapter = new SensorAdaptador();
        mRecyclerSensor.setAdapter(mAdapter);

        preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
        sensor = preferencia.getString(SENSOR,"");

        Log.d("error","Valor del sensor "+sensor);

        Call<Detector> call = ApiAdaptar.getApiService().obtenerSensor(sensor,"2021-01-01","2021-01-02",3);
        call.enqueue(new SensorCallback());

        tv_fecha_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(GalerySensor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 9 && monthOfYear >= 0 && dayOfMonth <= 9 && dayOfMonth >0){
                            fechaIni = year + "-" + "0"+(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                        } else if (monthOfYear < 9 && monthOfYear >= 0){
                            fechaIni = year + "-" + "0"+(monthOfYear + 1) + "-" +dayOfMonth;

                        } else if (dayOfMonth <= 9 && dayOfMonth >0){
                            fechaIni = year + "-" +(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                        } else {
                            fechaIni = year + "-" +(monthOfYear + 1) + "-" + dayOfMonth;

                        }
                        tv_fecha_ini.setText(fechaIni);
                    }
                },
                        anio,
                        mes,
                        dia);
                datePickerDialog.show();
            }
        });
        tv_fecha_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(GalerySensor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 9 && monthOfYear >= 0 && dayOfMonth <= 9 && dayOfMonth >0){
                            fechaFin = year + "-" + "0"+(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                        } else if (monthOfYear < 9 && monthOfYear >= 0){
                            fechaFin = year + "-" + "0"+(monthOfYear + 1) + "-" +dayOfMonth;

                        } else if (dayOfMonth <= 9 && dayOfMonth >0){
                            fechaFin = year + "-" +(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                        } else {
                            fechaFin = year + "-" +(monthOfYear + 1) + "-" + dayOfMonth;

                        }
                        tv_fecha_fin.setText(fechaFin);
                    }
                },
                        anio,
                        mes,
                        dia);
                datePickerDialog.show();
            }
        });
        bn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                sensor = preferencia.getString(SENSOR,"");
                fechaIni = tv_fecha_ini.getText().toString().trim();
                fechaFin = tv_fecha_fin.getText().toString().trim();
                Log.i("sensor", "nombre del sensor: "+sensor);
                Log.i("sensor", "Fecha inicio "+fechaIni);
                Log.i("sensor", "Fecha fin "+fechaFin);

                if (!sensor.equalsIgnoreCase("")
                        && !fechaIni.equalsIgnoreCase("")
                        && !fechaFin.equalsIgnoreCase("")) {
                    Call<Detector> call = ApiAdaptar.getApiService().obtenerSensor(sensor, fechaIni, fechaFin, 2);
                    call.enqueue(new SensorCallback());
                } else if (fechaIni.equalsIgnoreCase("")){
                    Toast.makeText(GalerySensor.this, "Ingrese la fecha de inicio",Toast.LENGTH_SHORT).show();
                } else if (fechaFin.equalsIgnoreCase("")){
                    Toast.makeText(GalerySensor.this, "Ingrese la fecha fin para la consulta", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    class SensorCallback implements Callback<Detector> {
        @Override
        public void onResponse(Call<Detector> call, Response<Detector> response) {
            Log.d("onResponse Sensor", "Size de registro de sensores" + response.body().getSensor().toString());
            Log.d("onResponse Sensor", "Size de registro de sensores" + response.body().getSensor().size());
            ArrayList<Sensor> sensorList = (ArrayList<Sensor>) response.body().getSensor();
            Sensor sensorResp = response.body().getSensor().get(0);
            if(sensorResp.getCodigoError().equalsIgnoreCase("0000") ) {
                mAdapter.setDataSet(sensorList);
                tv_mensaje.setText("");
                Toast.makeText(GalerySensor.this, "Cantidad de registrar son: " + sensorList.size(), Toast.LENGTH_LONG).show();
                Log.d("onResponse Sensor", "Size de registro de sensores" + sensorList.size());
            } else {
                mAdapter = new SensorAdaptador();
                mRecyclerSensor.setAdapter(mAdapter);
                tv_mensaje.setText(sensorResp.getMensajeError().trim()+" "+sensor);
            }
        }

        @Override
        public void onFailure(Call<Detector> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(GalerySensor.this, "Fallo la conexion "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
