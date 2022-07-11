package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    Button btnCheckWalk, btnCheckRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckWalk = findViewById(R.id.btnCheckWalk);
        btnCheckRun = findViewById(R.id.btnCheckRun);

        Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);

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

//        btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(oneDay.isChecked() == true){
//                    intent1.putExtra("Session", 1);
//                    startActivityForResult(intent1, 0);
//                }
//                if(oneWeek.isChecked() == true){
//                    intent1.putExtra("Session", 7);
//                    startActivityForResult(intent1, 0);
//                }
//                if(oneMonth.isChecked() == true){
//                    intent1.putExtra("Session", 30);
//                    startActivityForResult(intent1, 0);
//                }
//            }
//        });
    }
}