package com.weimont.ecommerceandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.weimont.ecommerceandroid.Adapters.PlateAdapter;
import com.weimont.ecommerceandroid.EmailLoginRegister.EmailLoginActivity;
import com.weimont.ecommerceandroid.EmailLoginRegister.EmailRegisterActivity;
import com.weimont.ecommerceandroid.Model.PlateModel;
import com.weimont.ecommerceandroid.PhoneLoginRegister.PhoneLoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //segunda actividad a mostrar

    private RecyclerView recyclerView;
    private List<PlateModel> plateModelList;
    private PlateAdapter plateAdapter;
    private LinearLayout emailContinue, phoneContinue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////// Status bar hide start //////////////////////////////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        //////////// Status bar hide end //////////////////////////////


        emailContinue = findViewById(R.id.linear2);
        phoneContinue = findViewById(R.id.linear1);


        //////////// Inicio Imagen en pasarela //////////////////////////////
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        plateModelList = new ArrayList<>();
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));
        plateModelList.add(new PlateModel(R.drawable.one));

        plateAdapter = new PlateAdapter(plateModelList, this);

        recyclerView.setAdapter(plateAdapter);;
        plateAdapter.notifyDataSetChanged();

        //////////  inicio imagen en Pasarella //////////////
        autoScroll();
        /////////// fin imagen en Pasarella //////////////

        /////////////// inicio continuar con email ////////////////////////////////////
        emailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmailLoginActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(MainActivity.this);
            }
        });
        /////////////// fin continuar con email ////////////////////////////////////

        /////////////// inicio continuar con phone ////////////////////////////////////
        phoneContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(MainActivity.this);
            }
        });
        /////////////// fin continuar con phone ////////////////////////////////////

    }

    //////////// inicio Imagen en pasarela //////////////////////////////
    public void autoScroll(){
        // default speedScroll is 0
        final int speedScroll = 4;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == plateAdapter.getItemCount())
                    count = 0;
                if(count < plateAdapter.getItemCount()){
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    //////////// Fin Imagen en pasarela //////////////////////////////
}