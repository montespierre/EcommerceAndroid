package com.weimont.ecommerceandroid.EmailLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.weimont.ecommerceandroid.MainActivity;
import com.weimont.ecommerceandroid.OperationRetrofitApi.ApiClient;
import com.weimont.ecommerceandroid.OperationRetrofitApi.ApiInterface;
import com.weimont.ecommerceandroid.OperationRetrofitApi.Users;
import com.weimont.ecommerceandroid.R;

import java.util.concurrent.RunnableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        //////////// Status bar hide start //////////////////////////////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        //////////// Status bar hide end //////////////////////////////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        init();




    }

    private void init() {

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.button2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {

        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();

        if(TextUtils.isEmpty((user_email))){
            email.setError("email requerido");
        }else if(TextUtils.isEmpty(user_password)){
            password.setError("Password es requerida");
        }else{

            ///// inicio progress bar //////
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Registrando...");
            dialog.setMessage("Espere escribimos tus credenciales");
            dialog.setMax(100);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCanceledOnTouchOutside(false);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        while(dialog.getProgress()<=dialog.getMax()){
                            Thread.sleep(200);
                            dialog.incrementProgressBy(10);
                            if(dialog.getProgress()==dialog.getMax()){
                                dialog.dismiss();

                            }
                        }
                    }catch(Exception e){

                    }
                }
            }).start();
            dialog.show();
            ///// fin progress bar //////

            Call<Users> call = apiInterface.performEmailLogin(user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    String user_id = response.body().getUserId();

                    if(response.body().getResponse().equals("ok")){
                        Toast.makeText(EmailLoginActivity.this, user_id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(EmailLoginActivity.this, "Loggin satisfactorio", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }else if(response.body().getResponse().equals("no_account")){
                        Toast.makeText(EmailLoginActivity.this, "email o contrasenha incorrecta", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }


    public void goToRegister(View view) {

        Intent intent = new Intent(EmailLoginActivity.this, EmailRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }

    public void backToMainPage(View view) {

        Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }
}