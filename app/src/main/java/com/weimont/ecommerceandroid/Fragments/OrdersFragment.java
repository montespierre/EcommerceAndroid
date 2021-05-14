package com.weimont.ecommerceandroid.Fragments;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.weimont.ecommerceandroid.R;


public class OrdersFragment extends Fragment implements View.OnClickListener{



    public OrdersFragment() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private View view;
    private TextView One, Two;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        onSetNavigationDrawerEvents();
        return view;
    }

    private void onSetNavigationDrawerEvents() {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);
        One = (TextView) view.findViewById(R.id.textView3);
        Two = (TextView) view.findViewById(R.id.textView4);

        navigationBar.setOnClickListener(this);
        One.setOnClickListener(this);
        Two.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;
            case R.id.textView3:
                Toast.makeText(getContext(),"One",Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView4:
                Toast.makeText(getContext(),"Two",Toast.LENGTH_SHORT).show();
                break;

        }
    }




}