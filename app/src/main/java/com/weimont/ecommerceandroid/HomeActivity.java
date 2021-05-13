package com.weimont.ecommerceandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.weimont.ecommerceandroid.Fragments.GoOutFragment;
import com.weimont.ecommerceandroid.Fragments.GoldFragment;
import com.weimont.ecommerceandroid.Fragments.OrdersFragment;
import com.weimont.ecommerceandroid.Fragments.VideosFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ////////////////// Inicio Dejar solo la Status Bar //////////////////////////////////////////
        Window window = getWindow();
        View decorView = window.getDecorView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController wic = decorView.getWindowInsetsController();
            wic.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // set any light background color to the status bar. e.g. - white or light blue
        window.setStatusBarColor(Color.rgb(255,20,147));
        ////////////////// Fin Dejar solo la Status Bar //////////////////////////////////////////




        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigation);



    }

    //////////////////////////// Select Fragment /////////////////////////////
    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.orders:
                            selectedFragment = new OrdersFragment();
                            break;

                        case R.id.goout:
                            selectedFragment = new GoOutFragment();
                            break;

                        case R.id.gold:
                            selectedFragment = new GoldFragment();
                            break;

                        case R.id.videos:
                            selectedFragment = new VideosFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

                    return true;

                }
            };
}