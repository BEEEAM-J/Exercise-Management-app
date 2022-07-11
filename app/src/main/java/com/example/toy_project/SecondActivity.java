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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    myDBHelper myHelper;
    TextView tvRes, tv3;
    EditText edtTime, edtDistance, edtWalk;
    RadioButton rBtnWalk, rBtnRunning;
    RadioGroup rGroup2;
    Button btnCheck2;
    SQLiteDatabase sqlDB;
    int versionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv3 = findViewById(R.id.tv3);
        edtTime = findViewById(R.id.edtTime);
        edtDistance = findViewById(R.id.edtDistance);
        edtWalk = findViewById(R.id.edtWalk);
        btnCheck2 = findViewById(R.id.btnCheck2);
        myHelper = new myDBHelper(this);

        Intent inIntent = getIntent();
        Intent intent2 = new Intent(getApplicationContext(), ThirdActivity.class);

        int versionID = inIntent.getIntExtra("versionID", 0);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current = format.format(currentTime);

        if(versionID == 1){
            tv3.setVisibility(View.VISIBLE);
            edtWalk.setVisibility(View.VISIBLE);
        }
        if(versionID == 0){
            tv3.setVisibility(View.INVISIBLE);
            edtWalk.setVisibility(View.INVISIBLE);
        }

        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(versionID == 1){
                        sqlDB = myHelper.getReadableDatabase();
                        sqlDB.execSQL("DELETE FROM objectTBL_W WHERE date = ('" + current + "') ");
                        sqlDB.close();
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO objectTBL_W VALUES ( " + edtTime.getText().toString() + " , " + edtDistance.getText().toString() + " , " + edtWalk.getText().toString() + " , '" + current + "' );");
                        sqlDB.close();
                    }
                    else if(versionID == 0){
                        sqlDB = myHelper.getReadableDatabase();
                        sqlDB.execSQL("DELETE FROM objectTBL_R WHERE date = ('" + current + "') ");
                        sqlDB.close();
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO objectTBL_R VALUES ( " + edtTime.getText().toString() + " , " + edtDistance.getText().toString() + " , '" + current + "' );");
                        sqlDB.close();
                    }
                    intent2.putExtra("Version", versionID);
                    startActivityForResult(intent2, 0);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}