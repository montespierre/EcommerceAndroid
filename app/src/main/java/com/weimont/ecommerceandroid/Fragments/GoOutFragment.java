package com.weimont.ecommerceandroid.Fragments;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.weimont.ecommerceandroid.R;


public class GoOutFragment extends Fragment implements View.OnClickListener{



    public GoOutFragment() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private View view;
    private RelativeLayout loginSignUp, bookmarks, fiorellaGold;
    private TextView your_orders, online_ordering_help, address_book, favorite_orders, send_feedback, report_safety_emergency, rate_playstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_go_out, container, false);
        onSetNavigationDrawerEvents();
        return view;
    }

    private void onSetNavigationDrawerEvents() {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        loginSignUp = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        bookmarks = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        fiorellaGold = (RelativeLayout) view.findViewById(R.id.relativeLayout4);

        your_orders = (TextView) view.findViewById(R.id.your_orders);
        online_ordering_help = (TextView) view.findViewById(R.id.online_ordering_help);
        address_book = (TextView) view.findViewById(R.id.address_book);
        favorite_orders = (TextView) view.findViewById(R.id.favorite_orders);
        send_feedback = (TextView) view.findViewById(R.id.send_feedback);
        report_safety_emergency = (TextView) view.findViewById(R.id.report_safety_emergency);
        rate_playstore = (TextView) view.findViewById(R.id.rate_playstore);

        navigationBar.setOnClickListener(this);
        loginSignUp.setOnClickListener(this);
        bookmarks.setOnClickListener(this);

        bookmarks.setOnClickListener(this);
        fiorellaGold.setOnClickListener(this);
        your_orders.setOnClickListener(this);
        online_ordering_help.setOnClickListener(this);
        address_book.setOnClickListener(this);
        favorite_orders.setOnClickListener(this);
        send_feedback.setOnClickListener(this);
        report_safety_emergency.setOnClickListener(this);
        rate_playstore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;
            case R.id.relativeLayout2:
                Toast.makeText(getContext(),"loginSignUp",Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout3:
                Toast.makeText(getContext(),"bookmarks",Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout4:
                Toast.makeText(getContext(),"fiorellaGold",Toast.LENGTH_SHORT).show();
                break;
            case R.id.your_orders:
                Toast.makeText(getContext(),"your_orders",Toast.LENGTH_SHORT).show();
                break;
            case R.id.online_ordering_help:
                Toast.makeText(getContext(),"online_ordering_help",Toast.LENGTH_SHORT).show();
                break;
            case R.id.address_book:
                Toast.makeText(getContext(),"address_book",Toast.LENGTH_SHORT).show();
                break;
            case R.id.favorite_orders:
                Toast.makeText(getContext(),"favorite_orders",Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_feedback:
                Toast.makeText(getContext(),"send_feedback",Toast.LENGTH_SHORT).show();
                break;
            case R.id.report_safety_emergency:
                Toast.makeText(getContext(),"report_safety_emergency",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rate_playstore:
                Toast.makeText(getContext(),"rate_playstore",Toast.LENGTH_SHORT).show();
                break;

        }
    }




}