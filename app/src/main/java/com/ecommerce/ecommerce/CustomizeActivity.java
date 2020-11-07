package com.ecommerce.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import nl.dionsegijn.steppertouch.StepperTouch;

public class CustomizeActivity extends AppCompatActivity {

    Toolbar toolbar;
    StepperTouch stepperTouch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("Customize Item");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
        stepperTouch = findViewById(R.id.stepperTouch);

        stepperTouch.setMinValue(0);
        stepperTouch.setMaxValue(99);
        stepperTouch.setSideTapEnabled(false);
        stepperTouch.setCount(1);

    }
}