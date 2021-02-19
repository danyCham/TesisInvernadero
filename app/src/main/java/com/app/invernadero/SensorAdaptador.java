package com.app.invernadero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.invernadero.Model.Sensor;

import java.util.ArrayList;

public class SensorAdaptador extends RecyclerView.Adapter<SensorAdaptador.ViewHolder>{
    private ArrayList<Sensor> mDataSet;

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombre;
        public TextView tvValor;
        public TextView tvMedida;
        public TextView tvLatitud;
        public TextView tvLongitud;
        public TextView tvFecha;
        public TextView tvSemaforo;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvNombre = itemView.findViewById(R.id.tvSensorUser);
            this.tvValor = itemView.findViewById(R.id.tvValorSensor);
            this.tvMedida = itemView.findViewById(R.id.tvMedida);
            this.tvLatitud = itemView.findViewById(R.id.tvLatitud);
            this.tvLongitud = itemView.findViewById(R.id.tvLongitud);
            this.tvFecha = itemView.findViewById(R.id.tvFechaReg);
            this.tvSemaforo = itemView.findViewById(R.id.tvSemaforo);
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public SensorAdaptador() {
        mDataSet = new ArrayList<>();
    }

    public void setDataSet(ArrayList<Sensor> sensorList) {
        mDataSet = sensorList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Creamos una nueva vista
        View tv = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_views, parent, false);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        // - reemplazamos el contenido de los views según tales datos
        Sensor sensor = mDataSet.get(position);

        holder.tvNombre.setText(sensor.getSensor());
        holder.tvValor.setText(sensor.getValor().toString());
        holder.tvMedida.setText(sensor.getUnidadMedida());
        holder.tvLatitud.setText(sensor.getLatitud().toString());
        holder.tvLongitud.setText(sensor.getLongitud().toString());
        holder.tvFecha.setText(sensor.getFecha().substring(0,10));
        //holder.tvSemaforo.setText(sensor.getSemaforo().toString());

    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
