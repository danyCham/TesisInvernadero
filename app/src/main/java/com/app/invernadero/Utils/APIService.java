package com.app.invernadero.Utils;

import com.app.invernadero.Model.Detector;
import com.app.invernadero.Model.Sensor;
import com.app.invernadero.Model.Usuario;
import com.app.invernadero.Model.UsuarioPost;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @POST("usuario/registrarUsuario")
    Call<Usuario> registrarUsuario(@Body UsuarioPost usuario);

    @GET("usuario/{p_Clave}/{p_Email}/{p_Opcion}")
    Call<Usuario> obtenerUser(@Path("p_Clave") String clave, @Path("p_Email") String email, @Path("p_Opcion") int opcion);

    @POST("sensor/")
    Call<Detector> registrarSensor(@Body Sensor sensor);

    @GET("sensor/{p_Sensor}/{p_Fecha_ini}/{p_Fecha_fin}/{p_Opcion}")
    Call<Detector> obtenerSensor(@Path("p_Sensor") String sensor, @Path("p_Fecha_ini") String fechaIni, @Path("p_Fecha_fin") String fechafin, @Path("p_Opcion") int opcion);
}
