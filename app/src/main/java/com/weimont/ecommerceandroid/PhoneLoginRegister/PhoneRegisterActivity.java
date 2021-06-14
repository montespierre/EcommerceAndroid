package com.weimont.ecommerceandroid.PhoneLoginRegister;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.weimont.ecommerceandroid.EmailLoginRegister.EmailLoginActivity;
import com.weimont.ecommerceandroid.EmailLoginRegister.EmailRegisterActivity;
import com.weimont.ecommerceandroid.MainActivity;
import com.weimont.ecommerceandroid.OperationRetrofitApi.ApiClient;
import com.weimont.ecommerceandroid.OperationRetrofitApi.ApiInterface;
import com.weimont.ecommerceandroid.OperationRetrofitApi.Users;
import com.weimont.ecommerceandroid.R;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneRegisterActivity extends AppCompatActivity {

    private EditText phone, otp;
    private Button btnReg, btnOtp;
    public static ApiInterface apiInterface;
    String user_id;
    ProgressDialog dialog;

    ////////////////////// Phone otp ////////////////////////////////
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

        //////////// Status bar hide start /////////////////////////////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        //////////// Status bar hide end //////////////////////////////

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mAuth = FirebaseAuth.getInstance();





        init();
    }

    private void init() {

        phone = (EditText) findViewById(R.id.phone);
        otp = (EditText) findViewById(R.id.otp);
        btnOtp = (Button) findViewById(R.id.button3);
        btnReg = (Button) findViewById(R.id.button2);

        //////////////// inicio barraa de progreso ///////////////
        dialog = new ProgressDialog(this);
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
        //////////////// fin barraa de progreso ///////////////

        ////////////// phone otp callback start ////////////////////////
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                dialog.dismiss();
                otp.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                btnOtp.setVisibility(View.GONE);
                btnReg.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneRegisterActivity.this, "NUmero invalido" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // Guardar verificacion ID y enviando token, de esa manera podemos usarlo luego
                mVerificationId = verificationId;
                mResendToken = token;

                Toast.makeText(PhoneRegisterActivity.this, "EL codigo fue enviado, revise y verifique", Toast.LENGTH_LONG).show();
                otp.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                btnOtp.setVisibility(View.VISIBLE);
                btnReg.setVisibility(View.GONE);
                dialog.dismiss();
            }
        };
        ////////////// phone otp callback end ////////////////////////

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_phone = phone.getText().toString().trim();
                if(TextUtils.isEmpty(user_phone)){
                    phone.setError("phone is required");

                }else{
                    dialog.show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+51" + user_phone, // Telefono a verificar
                            60, // duracion de timeout
                            TimeUnit.SECONDS, // Unidad de timeout
                            PhoneRegisterActivity.this, // Actividad (para callback binding)
                            callbacks); // OnVerificationStateChangedCallbacks

                }

            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString().equals("")){
                    Toast.makeText(PhoneRegisterActivity.this, "Ingrese sus 6 digitos OTP", Toast.LENGTH_SHORT).show();

                }else{

                }
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            register();
                        }else{
                            Toast.makeText(PhoneRegisterActivity.this, "Algo salio mal", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void register() {


            Call<Users> call = apiInterface.performPhoneRegistration(phone.getText().toString());
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    if(response.body().getResponse().equals("ok")){

                        user_id = response.body().getUserId();

                        // Toast.makeText(PhoneRegisterActivity.this, user_id, Toast.LENGTH_SHORT).show();
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