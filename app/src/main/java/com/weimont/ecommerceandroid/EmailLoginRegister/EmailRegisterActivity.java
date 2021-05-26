package com.weimont.ecommerceandroid.EmailLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.weimont.ecommerceandroid.PhoneLoginRegister.PhoneRegisterActivity;
import com.weimont.ecommerceandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRegisterActivity extends AppCompatActivity {

    private EditText name, email, password;
    private Button regBtn;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

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

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        regBtn = (Button) findViewById(R.id.button2);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar() {


        String user_name = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();

        //String user_name = "joel";
        //String user_email = "joel@gonhi";
        //String user_password = "joel";

        if(TextUtils.isEmpty(user_name)){
            name.setError("name is required");
        }else if(TextUtils.isEmpty(user_email)){
            email.setText("email is required");
        }else if(TextUtils.isEmpty(user_password)){
            password.setText("password is required");
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

            Call<Users> call = apiInterface.performEmailRegistration(user_name, user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    if(!response.isSuccessful()){

                        Log.e("callService.onResponse", "Error" + response.code());

                    } else {

                        if(response.body().getResponse().equals("ok")){
                            Toast.makeText(EmailRegisterActivity.this, "Cuenta creada satisfactoraimente", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else if(response.body().getResponse().equals("failed")){
                            Toast.makeText(EmailRegisterActivity.this, "Algo salio mal. Intentalo de nuevo", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else if(response.body().getResponse().equals("already")){
                            Toast.makeText(EmailRegisterActivity.this, "Este email ya existe. Ingrese otro", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }



                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Registro fallo", Toast.LENGTH_LONG).show();
                    Log.e("CallService.onfailure", t.getLocalizedMessage());

                }
            });
        }
    }

    public void goToLogin(View view) {

        Intent intent = new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }

    public void backToMainPage(View view) {

        Intent intent = new Intent(EmailRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }
}