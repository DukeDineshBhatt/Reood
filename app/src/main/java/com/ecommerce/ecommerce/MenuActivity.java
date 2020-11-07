package com.ecommerce.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ecommerce.ecommerce.Adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    ImageView image;
    Toolbar toolbar;
    String name;
    TextView toolbarTextView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ProgressBar progress;
    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        image = findViewById(R.id.image);
        toolbar = findViewById(R.id.toolbar);

        name = getIntent().getStringExtra("name");
        toolbarTextView = findViewById(R.id.toolbar_title);

        progress = findViewById(R.id.progressBar2);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        toolbarTextView.setText(name);
        fragments = new ArrayList<>();

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nest_scrollview);
        scrollView.setFillViewport(true);

        fragments.add(new ProductList());
        fragments.add(new ProductList());
        fragments.add(new ProductList());


        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext(), fragments);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Cheese Crust Pizzas");
        tabLayout.getTabAt(1).setText("Party Meals");
        tabLayout.getTabAt(2).setText("Individual Pizzas");
    }

}
