package com.weimont.ecommerceandroid.PhoneLoginRegister;

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
import com.weimont.ecommerceandroid.EmailLoginRegister.EmailLoginActivity;
import com.weimont.ecommerceandroid.EmailLoginRegister.EmailRegisterActivity;
import com.weimont.ecommerceandroid.MainActivity;
import com.weimont.ecommerceandroid.OperationRetrofitApi.ApiClient;
import com.weimont.ecommerceandroid.OperationRetrofitApi.ApiInterface;
import com.weimont.ecommerceandroid.OperationRetrofitApi.Users;
import com.weimont.ecommerceandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneRegisterActivity extends AppCompatActivity {

    private EditText phone;
    private Button btnReg;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

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

        phone = (EditText) findViewById(R.id.phone);
        btnReg = (Button) findViewById(R.id.button2);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String user_phone = phone.getText().toString().trim();
        if(TextUtils.isEmpty(user_phone)){
            phone.setError("phone is required");

        }else{
            //////////////// inicio barraa de progreso ///////////////
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
            //////////////// fin barraa de progreso ///////////////

            Call<Users> call = apiInterface.performPhoneRegistration(user_phone);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    if(response.body().getResponse().equals("ok")){
                        Toast.makeText(PhoneRegisterActivity.this, "Cuenta creada satisfactoraimente", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else if(response.body().getResponse().equals("failed")){
                        Toast.makeText(PhoneRegisterActivity.this, "Algo salio mal. Intentalo de nuevo", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else if(response.body().getResponse().equals("already")){
                        Toast.makeText(PhoneRegisterActivity.this, "Este telefono ya existe. Ingrese otro", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Fallo el registro", Toast.LENGTH_LONG).show();
                    Log.e("CallService.onfailure", t.getLocalizedMessage());

                }
            });
        }
    }

    public void goToLogin(View view) {

        Intent intent = new Intent(PhoneRegisterActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }

    public void backToMainPage(View view) {

        Intent intent = new Intent(PhoneRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }
}