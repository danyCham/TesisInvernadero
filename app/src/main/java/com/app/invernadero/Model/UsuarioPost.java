package com.app.invernadero.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UsuarioPost {

    @SerializedName("p_Id_Usuario")
    @Expose
    private Integer pIdUsuario;
    @SerializedName("p_Id_Registra")
    @Expose
    private Integer pIdRegistra;
    @SerializedName("p_Nombre")
    @Expose
    private String pNombre;
    @SerializedName("p_Apellido")
    @Expose
    private String pApellido;
    @SerializedName("p_Email")
    @Expose
    private String pEmail;
    @SerializedName("p_Direccion")
    @Expose
    private String pDireccion;
    @SerializedName("p_telefono")
    @Expose
    private String pTelefono;
    @SerializedName("p_Fecha_nacimiento")
    @Expose
    private String pFechaNacimiento;
    @SerializedName("p_Clave")
    @Expose
    private String pClave;
    @SerializedName("p_Id_rol")
    @Expose
    private Integer pIdRol;
    @SerializedName("p_Estado")
    @Expose
    private String pEstado;
    @SerializedName("p_Opcion")
    @Expose
    private Integer pOpcion;

    /**
     * No args constructor for use in serialization
     */
    public UsuarioPost() {
    }

    /**
     * @param pIdRegistra
     * @param pFechaNacimiento
     * @param pEmail
     * @param pEstado
     * @param pClave
     * @param pIdRol
     * @param pTelefono
     * @param pOpcion
     * @param pNombre
     * @param pApellido
     * @param pIdUsuario
     * @param pDireccion
     */
    public UsuarioPost(Integer pIdUsuario, Integer pIdRegistra, String pNombre, String pApellido, String pEmail, String pDireccion, String pTelefono, String pFechaNacimiento, String pClave, Integer pIdRol, String pEstado, Integer pOpcion) {
        super();
        this.pIdUsuario = pIdUsuario;
        this.pIdRegistra = pIdRegistra;
        this.pNombre = pNombre;
        this.pApellido = pApellido;
        this.pEmail = pEmail;
        this.pDireccion = pDireccion;
        this.pTelefono = pTelefono;
        this.pFechaNacimiento = pFechaNacimiento;
        this.pClave = pClave;
        this.pIdRol = pIdRol;
        this.pEstado = pEstado;
        this.pOpcion = pOpcion;
    }

    public Integer getPIdUsuario() {
        return pIdUsuario;
    }

    public void setPIdUsuario(Integer pIdUsuario) {
        this.pIdUsuario = pIdUsuario;
    }

    public Integer getPIdRegistra() {
        return pIdRegistra;
    }

    public void setPIdRegistra(Integer pIdRegistra) {
        this.pIdRegistra = pIdRegistra;
    }

    public String getPNombre() {
        return pNombre;
    }

    public void setPNombre(String pNombre) {
        this.pNombre = pNombre;
    }

    public String getPApellido() {
        return pApellido;
    }

    public void setPApellido(String pApellido) {
        this.pApellido = pApellido;
    }

    public String getPEmail() {
        return pEmail;
    }

    public void setPEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getPDireccion() {
        return pDireccion;
    }

    public void setPDireccion(String pDireccion) {
        this.pDireccion = pDireccion;
    }

    public String getPTelefono() {
        return pTelefono;
    }

    public void setPTelefono(String pTelefono) {
        this.pTelefono = pTelefono;
    }

    public String getPFechaNacimiento() {
        return pFechaNacimiento;
    }

    public void setPFechaNacimiento(String pFechaNacimiento) {
        this.pFechaNacimiento = pFechaNacimiento;
    }

    public String getPClave() {
        return pClave;
    }

    public void setPClave(String pClave) {
        this.pClave = pClave;
    }

    public Integer getPIdRol() {
        return pIdRol;
    }

    public void setPIdRol(Integer pIdRol) {
        this.pIdRol = pIdRol;
    }

    public String getPEstado() {
        return pEstado;
    }

    public void setPEstado(String pEstado) {
        this.pEstado = pEstado;
    }

    public Integer getPOpcion() {
        return pOpcion;
    }

    public void setPOpcion(Integer pOpcion) {
        this.pOpcion = pOpcion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("pIdUsuario", pIdUsuario).append("pIdRegistra", pIdRegistra).append("pNombre", pNombre).append("pApellido", pApellido).append("pEmail", pEmail).append("pDireccion", pDireccion).append("pTelefono", pTelefono).append("pFechaNacimiento", pFechaNacimiento).append("pClave", pClave).append("pIdRol", pIdRol).append("pEstado", pEstado).append("pOpcion", pOpcion).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pIdRegistra).append(pIdUsuario).append(pDireccion).append(pFechaNacimiento).append(pEmail).append(pEstado).append(pClave).append(pIdRol).append(pTelefono).append(pOpcion).append(pNombre).append(pApellido).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Usuario) == false) {
            return false;
        }
        UsuarioPost rhs = ((UsuarioPost) other);
        return new EqualsBuilder().append(pIdRegistra, rhs.pIdRegistra).append(pIdUsuario, rhs.pIdUsuario).append(pDireccion, rhs.pDireccion).append(pFechaNacimiento, rhs.pFechaNacimiento).append(pEmail, rhs.pEmail).append(pEstado, rhs.pEstado).append(pClave, rhs.pClave).append(pIdRol, rhs.pIdRol).append(pTelefono, rhs.pTelefono).append(pOpcion, rhs.pOpcion).append(pNombre, rhs.pNombre).append(pApellido, rhs.pApellido).isEquals();
    }
}
