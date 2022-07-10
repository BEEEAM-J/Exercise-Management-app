package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThirdActivity extends AppCompatActivity {

    myDBHelper myHelper;
    TextView tv3;
    EditText edtExTime, edtExDistance, edtExWalk;
    Button btnSave;
    SQLiteDatabase sqlDB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView tvSession2 = findViewById(R.id.tvSession2);
        tv3 = findViewById(R.id.tv3);
        edtExTime = findViewById(R.id.edtExTime);
        edtExDistance = findViewById(R.id.edtExDistance);
        edtExWalk = findViewById(R.id.edtExWalk);
        btnSave = findViewById(R.id.btnSave);
        myHelper = new myDBHelper(this);

        Intent inIntent2 = getIntent();
        Intent intent3 = new Intent(getApplicationContext(), FourthActivity.class);

        int val2 = inIntent2.getIntExtra("Session", 0);
        int versionID2 = inIntent2.getIntExtra("Version", 0);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current = format.format(currentTime);

        tvSession2.setText("목표 기간: " + String.valueOf(val2) + "일");

        if(versionID2 == 1){
            tv3.setVisibility(View.VISIBLE);
            edtExWalk.setVisibility(View.VISIBLE);
        }
        else if(versionID2 == 0){
            tv3.setVisibility(View.INVISIBLE);
            edtExWalk.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(versionID2 == 1){
                    sqlDB1 = myHelper.getReadableDatabase();
                    sqlDB1.execSQL("DELETE FROM exerciseTBL_W WHERE date = ('" + current + "') ");
                    sqlDB1.close();
                    sqlDB1 = myHelper.getWritableDatabase();
                    sqlDB1.execSQL("INSERT INTO exerciseTBL_W VALUES ( " + edtExTime.getText().toString() + " , " + edtExDistance.getText().toString() + " , " + edtExWalk.getText().toString() + " , '" + current + "' );");
                    sqlDB1.close();
                }
                else if(versionID2 == 0){
                    sqlDB1 = myHelper.getReadableDatabase();
                    sqlDB1.execSQL("DELETE FROM exerciseTBL_R WHERE date = ('" + current + "') ");
                    sqlDB1.close();
                    sqlDB1 = myHelper.getWritableDatabase();
                    sqlDB1.execSQL("INSERT INTO exerciseTBL_R VALUES ( " + edtExTime.getText().toString() + " , " + edtExDistance.getText().toString() + " , '" + current + "' );");
                    sqlDB1.close();
                }
                intent3.putExtra("Session", val2);
                intent3.putExtra("Version", versionID2);
                startActivityForResult(intent3, 0);
            }
        });
    }

}