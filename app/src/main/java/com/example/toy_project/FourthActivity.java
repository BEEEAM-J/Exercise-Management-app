package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class FourthActivity extends AppCompatActivity {

    TextView tvSession3, tvOTime, tvExTime, tvCheckTime, tvODistance, tvExDistance, tvCheckDistance, tvOWalk, tvExWalk, tvCheckWalk;
    SQLiteDatabase sqlDB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        tvSession3 = findViewById(R.id.tvSession3);
        tvOTime = findViewById(R.id.tvOTime);
        tvExTime = findViewById(R.id.tvExTime);
        tvCheckTime = findViewById(R.id.tvCheckTime);
        tvODistance = findViewById(R.id.tvODistance);
        tvExDistance = findViewById(R.id.tvExDistance);
        tvCheckDistance = findViewById(R.id.tvCheckDistance);
        tvOWalk = findViewById(R.id.tvOWalk);
        tvExWalk = findViewById(R.id.tvExWalk);
        tvCheckWalk = findViewById(R.id.tvCheckWalk);

        Intent inIntent3 = getIntent();

        int val3 = inIntent3.getIntExtra("Session", 0);

        tvSession3.setText("목표 기간: " + String.valueOf(val3) + "일");

    }
}