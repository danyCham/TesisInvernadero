package com.app.invernadero.Model;

import java.sql.Date;

public class Sensor {
    private int id_registro_sensor;
    private String sensor;
    private int valor;
    private String unidad_medida;
    private double latitud;
    private double longitud;
    private Date fecha;
    private int semaforo;
    private int usuario_mod;
    private Date fecha_mod;

    public int getId_registro_sensor() {
        return id_registro_sensor;
    }

    public void setId_registro_sensor(int id_registro_sensor) {
        this.id_registro_sensor = id_registro_sensor;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(int semaforo) {
        this.semaforo = semaforo;
    }

    public int getUsuario_mod() {
        return usuario_mod;
    }

    public void setUsuario_mod(int usuario_mod) {
        this.usuario_mod = usuario_mod;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }
}
