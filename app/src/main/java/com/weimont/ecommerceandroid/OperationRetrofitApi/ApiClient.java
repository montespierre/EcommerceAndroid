package com.weimont.ecommerceandroid.OperationRetrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //public static final String BASE_URL = "http://localhost/libfio/users/";
    // public static final String BASE_URL = "http://127.0.0.1:80/libfio/users/";
    public static final String BASE_URL = "http://" + "192.168.43.40" + "/libfio/users/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }

}
