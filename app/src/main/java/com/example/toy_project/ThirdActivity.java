package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView tvSession2 = findViewById(R.id.tvSession2);

        Intent inIntent2 = getIntent();

        int val2 = inIntent2.getIntExtra("Session", 0);
        tvSession2.setText("목표 기간: " + String.valueOf(val2) + "일");
    }
}