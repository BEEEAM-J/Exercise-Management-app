package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    Button btnCheckWalk, btnCheckRun, btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckWalk = findViewById(R.id.btnCheckWalk);
        btnCheckRun = findViewById(R.id.btnCheckRun);
        btnRecord = findViewById(R.id.btnRecord);

        Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), RecordActivity.class);

        btnCheckWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.putExtra("versionID", 1);
                startActivityForResult(intent1, 0);
            }
        });

        btnCheckRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.putExtra("versionID", 0);
                startActivityForResult(intent1, 0);
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2.putExtra("Version", 2);
                startActivityForResult(intent2, 0);
            }
        });

    }
}