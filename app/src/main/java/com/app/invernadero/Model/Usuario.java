package com.app.invernadero.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Usuario {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Usuario() {
    }

    /**
     *
     * @param result
     */
    public Usuario(List<Result> result) {
        super();
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("result", result).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(result).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Usuario) == false) {
            return false;
        }
        Usuario rhs = ((Usuario) other);
        return new EqualsBuilder().append(result, rhs.result).isEquals();
    }

}