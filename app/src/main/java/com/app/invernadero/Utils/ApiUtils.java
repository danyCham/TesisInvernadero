package com.app.invernadero.Utils;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.100.139:3000/src/v1.0/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
