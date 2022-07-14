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
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    myDBHelper myHelper;
    TextView tvRes, tv3;
    NumberPicker NpMin, Npsec, NpDistance, NpWalk;
    Button btnCheck2;
    SQLiteDatabase sqlDB;
    RelativeLayout WalkLayout;
    int versionID, obTime;
    String[] WalkValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv3 = findViewById(R.id.tv3);
        WalkLayout = findViewById(R.id.WalkLayout);
        btnCheck2 = findViewById(R.id.btnCheck2);
        NpMin = findViewById(R.id.NpMin);
        Npsec = findViewById(R.id.Npsec);
        NpDistance = findViewById(R.id.NpDistance);
        NpWalk = findViewById(R.id.NpWalk);
        myHelper = new myDBHelper(this);

        Intent inIntent = getIntent();
        Intent intent2 = new Intent(getApplicationContext(), ThirdActivity.class);

        int versionID = inIntent.getIntExtra("versionID", 0);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current = format.format(currentTime);



        if(versionID == 1){
            tv3.setVisibility(View.VISIBLE);
            WalkLayout.setVisibility(View.VISIBLE);
        }
        if(versionID == 0){
            tv3.setVisibility(View.INVISIBLE);
            WalkLayout.setVisibility(View.INVISIBLE);
        }

        NpMin.setMinValue(0);
        NpMin.setMaxValue(90);
        Npsec.setMinValue(0);
        Npsec.setMaxValue(60);
        NpDistance.setMinValue(0);
        NpDistance.setMaxValue(10);
        NpWalk.setMinValue(0);
        NpWalk.setMaxValue(60);
        WalkValues = getArrayWithSteps(0, 30000, 500);
        NpWalk.setDisplayedValues(WalkValues);

        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                obTime = (NpMin.getValue() * 60) + Npsec.getValue();

                try{
                    if(versionID == 1){
                        sqlDB = myHelper.getReadableDatabase();
                        sqlDB.execSQL("DELETE FROM objectTBL_W WHERE date = ('" + current + "') ");
                        sqlDB.close();
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO objectTBL_W VALUES ( " + obTime + " , " + NpDistance.getValue() + " , " + WalkValues[NpWalk.getValue()] + " , '" + current + "' );");
                        sqlDB.close();
                    }
                    else if(versionID == 0){
                        sqlDB = myHelper.getReadableDatabase();
                        sqlDB.execSQL("DELETE FROM objectTBL_R WHERE date = ('" + current + "') ");
                        sqlDB.close();
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO objectTBL_R VALUES ( " + obTime + " , " + NpDistance.getValue() + " , '" + current + "' );");
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

    public String[] getArrayWithSteps(int min, int max, int step) {

        int number_of_array = (max - min) / step + 1;

        String[] result = new String[number_of_array];

        for (int i = 0; i < number_of_array; i++) {
            result[i] = String.valueOf(min + step * i);
        }
        return result;
    }

}