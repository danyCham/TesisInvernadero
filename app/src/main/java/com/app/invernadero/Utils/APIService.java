package com.app.invernadero.Utils;

import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Model.UsuarioPost;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @FormUrlEncoded
    @POST("usuario/registrarUsuario")
    Call<Usuario> registraUsuario(@Field("p_Id_Usuario") int id_usuario,
                                 @Field("p_Id_Registra") int id_usuario_reg,
                                 @Field("p_Nombre") String nombres,
                                 @Field("p_Apellido") String apellidos,
                                 @Field("p_Email") String email,
                                 @Field("p_Direccion") String direccion,
                                 @Field("p_telefono") String telefono,
                                 @Field("p_Fecha_nacimiento") String fecha_nacimiento,
                                 @Field("p_Clave") String clave,
                                 @Field("p_Id_rol") int rol,
                                 @Field("p_Estado") String estado,
                                 @Field("p_Opcion") int opcion);

    @POST("usuario/registrarUsuario")
    Call<Usuario> registrarUsuario(@Body UsuarioPost usuario);

    @GET("usuario/{p_Clave}/{p_Email}/{p_Opcion}")
    Call<Usuario> obtenerUser(@Path("p_Clave") String clave, @Path("p_Email") String email, @Path("p_Opcion") int opcion);
}
