package com.app.invernadero.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Detector {

    @SerializedName("sensor")
    @Expose
    private List<Sensor> sensor = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Detector() {
    }

    /**
     *
     * @param sensor
     */
    public Detector(List<Sensor> sensor) {
        super();
        this.sensor = sensor;
    }

    public List<Sensor> getSensor() {
        return sensor;
    }

    public void setSensor(List<Sensor> sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sensor", sensor).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sensor).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Detector) == false) {
            return false;
        }
        Detector rhs = ((Detector) other);
        return new EqualsBuilder().append(sensor, rhs.sensor).isEquals();
    }

}