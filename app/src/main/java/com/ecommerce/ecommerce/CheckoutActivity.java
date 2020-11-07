package com.ecommerce.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ecommerce.ecommerce.ui.home.HomePickUpFragment;
import com.google.android.material.tabs.TabLayout;

public class CheckoutActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView pickup, delivery;
    FrameLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout2);

        toolbar = findViewById(R.id.toolbar);
        placeholder = findViewById(R.id.your_placeholder);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("Checkout");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        pickup = findViewById(R.id.pickup);
        delivery = findViewById(R.id.delivery);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, new checkoutFrgament());

        ft.commit();

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = CheckoutActivity.this
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.your_placeholder, new checkoutFrgament());
                fragmentTransaction.commit();

                pickup.setTextColor(getResources().getColor(R.color.color_white));
                pickup.setBackgroundResource(R.drawable.black_box);
                delivery.setBackgroundResource(0);
                delivery.setTextColor(getResources().getColor(R.color.default_color));

            }
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = CheckoutActivity.this
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.your_placeholder, new checkoutFrgament());
                fragmentTransaction.commit();

                delivery.setTextColor(getResources().getColor(R.color.color_white));
                delivery.setBackgroundResource(R.drawable.black_box);
                pickup.setBackgroundResource(0);
                pickup.setTextColor(getResources().getColor(R.color.default_color));

            }
        });


    }


}