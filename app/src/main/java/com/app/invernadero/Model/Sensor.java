package com.app.invernadero.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Sensor {

    @SerializedName("id_registro_sensor")
    @Expose
    private Integer idRegistroSensor;
    @SerializedName("sensor")
    @Expose
    private String sensor;
    @SerializedName("valor")
    @Expose
    private Integer valor;
    @SerializedName("unidad_medida")
    @Expose
    private String unidadMedida;
    @SerializedName("latitud")
    @Expose
    private Double latitud;
    @SerializedName("longitud")
    @Expose
    private Double longitud;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("semaforo")
    @Expose
    private String semaforo;
    @SerializedName("CodigoError")
    @Expose
    private String codigoError;
    @SerializedName("MensajeError")
    @Expose
    private String mensajeError;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sensor() {
    }

    /**
     *
     * @param fecha
     * @param latitud
     * @param longitud
     * @param codigoError
     * @param idRegistroSensor
     * @param unidadMedida
     * @param mensajeError
     * @param valor
     * @param sensor
     * @param semaforo
     */
    public Sensor(Integer idRegistroSensor, String sensor, Integer valor, String unidadMedida, Double latitud, Double longitud, String fecha, String semaforo, String codigoError, String mensajeError) {
        super();
        this.idRegistroSensor = idRegistroSensor;
        this.sensor = sensor;
        this.valor = valor;
        this.unidadMedida = unidadMedida;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
        this.semaforo = semaforo;
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
    }

    public Integer getIdRegistroSensor() {
        return idRegistroSensor;
    }

    public void setIdRegistroSensor(Integer idRegistroSensor) {
        this.idRegistroSensor = idRegistroSensor;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(String semaforo) {
        this.semaforo = semaforo;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("idRegistroSensor", idRegistroSensor).append("sensor", sensor).append("valor", valor).append("unidadMedida", unidadMedida).append("latitud", latitud).append("longitud", longitud).append("fecha", fecha).append("semaforo", semaforo).append("codigoError", codigoError).append("mensajeError", mensajeError).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(fecha).append(latitud).append(longitud).append(codigoError).append(idRegistroSensor).append(unidadMedida).append(mensajeError).append(valor).append(sensor).append(semaforo).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Sensor) == false) {
            return false;
        }
        Sensor rhs = ((Sensor) other);
        return new EqualsBuilder().append(fecha, rhs.fecha).append(latitud, rhs.latitud).append(longitud, rhs.longitud).append(codigoError, rhs.codigoError).append(idRegistroSensor, rhs.idRegistroSensor).append(unidadMedida, rhs.unidadMedida).append(mensajeError, rhs.mensajeError).append(valor, rhs.valor).append(sensor, rhs.sensor).append(semaforo, rhs.semaforo).isEquals();
    }

}