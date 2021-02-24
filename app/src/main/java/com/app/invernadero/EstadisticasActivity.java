package com.app.invernadero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.invernadero.Model.Detector;
import com.app.invernadero.Model.Sensor;
import com.app.invernadero.Utils.ApiAdaptar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstadisticasActivity extends AppCompatActivity {

    private LineChart mChart;
    private String sensor="Humedad";
    private int dia,mes,anio;
    private String fecha = "";
    public static final String OPCION_SENSOR ="opcion_sensor";

    TextView tv_mensaje;
    EditText edt_Fecha;
    Button bn_buscar;
    Spinner listaSensor;
    String[] datosSensor = {"Humedad", "Temperatura", "Luz"};
    SharedPreferences preferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        mChart = findViewById(R.id.lineChart);
        edt_Fecha = findViewById(R.id.edt_Fecha);
        listaSensor = findViewById(R.id.listaSensor);
        tv_mensaje = findViewById(R.id.txt_mensaje_estadistico);
        bn_buscar  = findViewById(R.id.bn_buscar_estadistica);

        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        ArrayAdapter<String> adapterListSensor = new ArrayAdapter<>(EstadisticasActivity.this,android.R.layout.simple_list_item_activated_1,datosSensor);
        listaSensor.setAdapter(adapterListSensor);

        Call<Detector> call = ApiAdaptar.getApiService().obtenerSensor(sensor,getFechaConsulta(),"2021-01-02",6);
        call.enqueue(new SensorCallback());

        listaSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencia.edit();
                editor.putString(OPCION_SENSOR,datosSensor[position]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencia = getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);
                sensor = preferencia.getString(OPCION_SENSOR,"");
                String fechaconsulta = edt_Fecha.getText().toString().trim();
                Log.i("error", "Valor del sensor: "+sensor);
                Log.i("error", "fecha: "+fechaconsulta);
                if (!fechaconsulta.equalsIgnoreCase("")) {
                    if(!sensor.equalsIgnoreCase("")) {
                        Call<Detector> call = ApiAdaptar.getApiService().obtenerSensor(sensor, fechaconsulta, "2021-01-02", 6);
                        call.enqueue(new SensorCallback());
                    } else {
                        Toast.makeText(EstadisticasActivity.this, "No ha seleccionado el sensor a consultar", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EstadisticasActivity.this, "No ha ingresado la fecha", Toast.LENGTH_LONG).show();
                }
            }
        });

        edt_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EstadisticasActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear < 9 && monthOfYear >= 0 && dayOfMonth <= 9 && dayOfMonth >0){
                            fecha = year + "-" + "0"+(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                        } else if (monthOfYear < 9 && monthOfYear >= 0){
                            fecha = year + "-" + "0"+(monthOfYear + 1) + "-" +dayOfMonth;

                        } else if (dayOfMonth <= 9 && dayOfMonth >0){
                            fecha = year + "-" +(monthOfYear + 1) + "-" + "0"+dayOfMonth;

                        } else {
                            fecha = year + "-" +(monthOfYear + 1) + "-" + dayOfMonth;

                        }
                        edt_Fecha.setText(fecha);
                    }
                },
                        anio,
                        mes,
                        dia);
                datePickerDialog.show();
            }
        });
    }

    private String getFechaConsulta(){
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);

        String fecha = new String();
        if (mes < 9 && mes >= 0 && dia <= 9 && dia >0){
            fecha = anio + "-" + "0"+(mes + 1) + "-" + "0"+dia;

        } else if (mes < 9 && mes >= 0){
            fecha = anio + "-" + "0"+(mes + 1) + "-" +dia;

        } else if (dia <= 9 && dia >0){
            fecha = anio + "-" +(mes + 1) + "-" + "0"+dia;

        } else {
            fecha = anio + "-" +(mes + 1) + "-" + dia;

        }
        Log.i("error", fecha);
        return fecha;
    }

    public void renderData(ArrayList<Entry> values, float isMayorX, float isMayorY, float rangoMax, float rangoMin) {

        Log.i("error", "Valores: "+values.toString());

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        /*Valores de la X*/
        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMaximum(isMayorX);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(rangoMax, "Rango Maximo");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(rangoMin, "Rango minimo");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        /*Valores de la Y*/
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(isMayorY);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        mChart.getAxisRight().setEnabled(false);
        setData(values);
    }

    private void setData(ArrayList<Entry> values) {

        LineDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Datos del Sensor");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }

    public float getValorMayor(ArrayList<Integer> listanumeros){
        int iNumeroMayor = 0 ;
        for (int x=0; x<listanumeros.size(); x++){
            if (listanumeros.get(x)>iNumeroMayor) {
                iNumeroMayor = listanumeros.get(x);
            }
        }
        return iNumeroMayor;
    }

    class SensorCallback implements Callback<Detector> {
        @Override
        public void onResponse(Call<Detector> call, Response<Detector> response) {
            ArrayList<Sensor> sensorList = (ArrayList<Sensor>) response.body().getSensor();
            Sensor sensorResp = response.body().getSensor().get(0);

            if (sensorResp.getCodigoError().equalsIgnoreCase("0000")) {
                ArrayList<Entry> values = new ArrayList<>();
                ArrayList<Integer> listaNumerosX = new ArrayList<>();
                ArrayList<Integer> listaNumerosY = new ArrayList<>();
                for (int i = 0; i < sensorList.size(); i++) {
                    int valorx = Integer.parseInt(response.body().getSensor().get(i).getFecha().substring(11, 13));
                    int valory = response.body().getSensor().get(i).getValor();
                    listaNumerosX.add(valorx);
                    listaNumerosY.add(valory);
                    values.add(new Entry(valorx, valory));
                    Log.i("error", "Valor x: " + valorx);
                    Log.i("error", "Valor y: " + valory);
                }
                float rangoMin = 0;
                float rangoMax = 0;
                if (sensor.equalsIgnoreCase("Humedad")){
                    rangoMin = 70;
                    rangoMax = 65;
                } else if (sensor.equalsIgnoreCase("Temperatura")){
                    rangoMin = 20;
                    rangoMax = 24;
                } else {
                    rangoMin = 60;
                    rangoMax = 50;
                }
                float isMayorX = getValorMayor(listaNumerosX);
                float isMayorY = getValorMayor(listaNumerosY);
                renderData(values, isMayorX+5, isMayorY+5, rangoMax, rangoMin);
                tv_mensaje.setText("");
            } else {
                tv_mensaje.setText(sensorResp.getMensajeError().trim()+" "+sensor);
                tv_mensaje.bringToFront();
                //Toast.makeText(EstadisticasActivity.this, sensorResp.getMensajeError().trim() + " " + sensor, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Detector> call, Throwable t) {
            Log.i("error", "error " + t.getLocalizedMessage());
            Toast.makeText(EstadisticasActivity.this, "Fallo la conexion " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}