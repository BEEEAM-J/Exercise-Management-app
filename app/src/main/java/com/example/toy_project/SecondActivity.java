package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tvRes, tv3;
    EditText edtTime, edtDistance, edtWalk;
    RadioButton rBtnWalk, rBtnRunning;
    RadioGroup rGroup2;
    Button btnCheck2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvRes = findViewById(R.id.tvSession);
        tv3 = findViewById(R.id.tv3);
        edtTime = findViewById(R.id.edtTime);
        edtDistance = findViewById(R.id.edtDistance);
        edtWalk = findViewById(R.id.edtWalk);
        btnCheck2 = findViewById(R.id.btnCheck2);
        rBtnWalk = findViewById(R.id.rBtnWalk);
        rBtnRunning = findViewById(R.id.rBtnRunning);
        rGroup2 = findViewById(R.id.rGroup2);

        Intent inIntent = getIntent();
        Intent intent2 = new Intent(getApplicationContext(), ThirdActivity.class);

        int val = inIntent.getIntExtra("Session", 0);
        tvRes.setText("목표 기간: " + String.valueOf(val) + "일");

        rBtnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.setVisibility(View.VISIBLE);
                edtWalk.setVisibility(View.VISIBLE);
            }
        });
        rBtnRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.setVisibility(View.INVISIBLE);
                edtWalk.setVisibility(View.INVISIBLE);
            }
        });

        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2.putExtra("Session", val);
                startActivityForResult(intent2, 0);
            }
        });
    }
}