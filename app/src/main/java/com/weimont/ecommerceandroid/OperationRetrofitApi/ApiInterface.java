package com.weimont.ecommerceandroid.OperationRetrofitApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    /////////////// Email registration //////////////////

    @GET("email_registration_oficial4.php")
    Call<Users> performEmailRegistration(
        @Query("user_name") String user_name,
        @Query("user_email") String user_email,
        @Query("user_password") String user_password
    );

    /////////////// Email login //////////////////
    @GET("email_login.php")
    Call<Users> performEmailLogin(
            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );

    /////////////// phone registration //////////////////
    @GET("phone_registration.php")
    Call<Users> performPhoneRegistration(
            @Query("user_phone") String user_phone
    );

    /////////////// phone login //////////////////
    @GET("phone_login.php")
    Call<Users> performPhoneLogin(
            @Query("user_phone") String user_phone
    );
}
