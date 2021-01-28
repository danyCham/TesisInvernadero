package com.app.invernadero.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Result {

    @SerializedName("id_usuario")
    @Expose
    private Integer idUsuario;
    @SerializedName("id_usuario_reg")
    @Expose
    private Object idUsuarioReg;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("clave")
    @Expose
    private String clave;
    @SerializedName("rol")
    @Expose
    private String rol;
    @SerializedName("estado")
    @Expose
    private String estado;
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
    public Result() {
    }

    /**
     *
     * @param estado
     * @param codigoError
     * @param fechaNacimiento
     * @param mensajeError
     * @param idUsuario
     * @param apellido
     * @param direccion
     * @param telefono
     * @param idUsuarioReg
     * @param nombre
     * @param email
     * @param rol
     */
    public Result(Integer idUsuario, Object idUsuarioReg, String nombre, String apellido, String email, String direccion, String fechaNacimiento, String telefono, String clave, String rol, String estado, String codigoError, String mensajeError) {
        super();
        this.idUsuario = idUsuario;
        this.idUsuarioReg = idUsuarioReg;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.clave = clave;
        this.rol = rol;
        this.estado = estado;
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Object getIdUsuarioReg() {
        return idUsuarioReg;
    }

    public void setIdUsuarioReg(Object idUsuarioReg) {
        this.idUsuarioReg = idUsuarioReg;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        return new ToStringBuilder(this).append("idUsuario", idUsuario).append("idUsuarioReg", idUsuarioReg).append("nombre", nombre).append("apellido", apellido).append("email", email).append("direccion", direccion).append("fechaNacimiento", fechaNacimiento).append("telefono", telefono).append("clave", clave).append("rol", rol).append("estado", estado).append("codigoError", codigoError).append("mensajeError", mensajeError).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(estado).append(codigoError).append(fechaNacimiento).append(mensajeError).append(idUsuario).append(direccion).append(nombre).append(clave).append(rol).append(apellido).append(telefono).append(idUsuarioReg).append(email).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Result) == false) {
            return false;
        }
        Result rhs = ((Result) other);
        return new EqualsBuilder().append(estado, rhs.estado).append(codigoError, rhs.codigoError).append(fechaNacimiento, rhs.fechaNacimiento).append(mensajeError, rhs.mensajeError).append(idUsuario, rhs.idUsuario).append(direccion, rhs.direccion).append(nombre, rhs.nombre).append(rol, rhs.rol).append(apellido, rhs.apellido).append(telefono, rhs.telefono).append(idUsuarioReg, rhs.idUsuarioReg).append(email, rhs.email).isEquals();
    }

}