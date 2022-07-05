package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class FourthActivity extends AppCompatActivity {

    myDBHelper myHelper;
    TextView tvSession3, tvOTime, tvExTime, tvCheckTime, tvODistance, tvExDistance, tvCheckDistance, tvOWalk, tvExWalk, tvCheckWalk;
    SQLiteDatabase sqlDB2;
    Cursor ocursor, excursor;
    int objectTime, objectDistance, objectWalk, exerciseTime, exerciseDistance, exerciseWalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        myHelper = new myDBHelper(this);
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

        sqlDB2 = myHelper.getReadableDatabase();
        ocursor = sqlDB2.rawQuery(" SELECT * FROM objectTBL; ", null);
        excursor = sqlDB2.rawQuery("SELECT * FROM ExerciseTBL;", null);

        while(ocursor.moveToNext()){
            tvOTime.setText("목표 시간: " + ocursor.getString(0) + "분");
            objectTime = Integer.parseInt(ocursor.getString(0));

            tvODistance.setText("목표 거리: " + ocursor.getString(1) + "Km");
            objectDistance = Integer.parseInt(ocursor.getString(1));

            tvOWalk.setText("목표 걸음수: " + ocursor.getString(2));
            objectWalk = Integer.parseInt(ocursor.getString(2));
        }

        while(excursor.moveToNext()){
            tvExTime.setText("내 운동 시간: " + excursor.getString(0) + "분");
            exerciseTime = Integer.parseInt(excursor.getString(0));

            tvExDistance.setText("내 운동 거리: " + excursor.getString(1) + "Km");
            exerciseDistance = Integer.parseInt(excursor.getString(1));

            tvExWalk.setText("내 걸음수: " + excursor.getString(2));
            exerciseWalk = Integer.parseInt(excursor.getString(2));
        }

        if(exerciseTime >= objectTime){
            tvCheckTime.setText("목표 완료/실패: 완료");
        }
        else{
            tvCheckTime.setText("목표 완료/실패: 실패");
        }

        if(exerciseDistance >= objectDistance){
            tvCheckDistance.setText("목표 완료/실패: 완료");
        }
        else{
            tvCheckDistance.setText("목표 완료/실패: 실패");
        }

        if(exerciseWalk >= objectWalk){
            tvCheckWalk.setText("목표 완료/실패: 완료");
        }
        else{
            tvCheckWalk.setText("목표 완료/실패: 실패");
        }

        ocursor.close();
        excursor.close();
        sqlDB2.close();
    }
}