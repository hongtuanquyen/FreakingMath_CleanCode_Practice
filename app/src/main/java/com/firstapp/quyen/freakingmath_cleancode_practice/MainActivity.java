package com.firstapp.quyen.freakingmath_cleancode_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        setViewOnClickListerner();
    }

    public void setViewOnClickListerner(){
        btnStart.setOnClickListener(this);
    }

    public void initializeView(){
        btnStart = findViewById(R.id.button_Start);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Start:
                Intent intent = new Intent(MainActivity.this, GameplayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
