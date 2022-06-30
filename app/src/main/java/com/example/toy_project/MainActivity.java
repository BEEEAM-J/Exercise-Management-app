package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    RadioButton oneDay, oneWeek, oneMonth;
    Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneDay = findViewById(R.id.oneDay);
        oneWeek = findViewById(R.id.oneWeek);
        oneMonth = findViewById(R.id.oneMonth);
        btnCheck = findViewById(R.id.btnCheck);

        Intent intent1 = new Intent(getApplicationContext(), SecondActivity.class);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oneDay.isChecked() == true){
                    intent1.putExtra("Session", 1);
                    startActivityForResult(intent1, 0);
                }
                if(oneWeek.isChecked() == true){
                    intent1.putExtra("Session", 7);
                    startActivityForResult(intent1, 0);
                }
                if(oneMonth.isChecked() == true){
                    intent1.putExtra("Session", 30);
                    startActivityForResult(intent1, 0);
                }
            }
        });
    }
}