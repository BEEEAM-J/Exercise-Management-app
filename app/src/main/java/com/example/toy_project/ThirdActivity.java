package com.example.toy_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThirdActivity extends AppCompatActivity implements SensorEventListener {

    myDBHelper myHelper;
    TextView tv3, edtExWalk;
    EditText edtExTime, edtExDistance;
    Button btnSave;
    SQLiteDatabase sqlDB1;
    SensorManager sensorManager;
    Sensor stepCountSensor;

    // 현재 걸음 수
    int currentSteps = 0;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tv3 = findViewById(R.id.tv3);
        edtExTime = findViewById(R.id.edtExTime);
        edtExDistance = findViewById(R.id.edtExDistance);
        edtExWalk = findViewById(R.id.edtExWalk);
        btnSave = findViewById(R.id.btnSave);
        myHelper = new myDBHelper(this);

        Intent inIntent2 = getIntent();
        Intent intent3 = new Intent(getApplicationContext(), FourthActivity.class);

        int versionID2 = inIntent2.getIntExtra("Version", 0);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current = format.format(currentTime);

        if (versionID2 == 1) {
            tv3.setVisibility(View.VISIBLE);
            edtExWalk.setVisibility(View.VISIBLE);
        } else if (versionID2 == 0) {
            tv3.setVisibility(View.INVISIBLE);
            edtExWalk.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (versionID2 == 1) {
                        sqlDB1 = myHelper.getReadableDatabase();
                        sqlDB1.execSQL("DELETE FROM exerciseTBL_W WHERE date = ('" + current + "') ");
                        sqlDB1.close();
                        sqlDB1 = myHelper.getWritableDatabase();
                        sqlDB1.execSQL("INSERT INTO exerciseTBL_W VALUES ( " + edtExTime.getText().toString() + " , " + edtExDistance.getText().toString() + " , " + String.valueOf(currentSteps) + " , '" + current + "' );");
                        sqlDB1.close();
                    } else if (versionID2 == 0) {
                        sqlDB1 = myHelper.getReadableDatabase();
                        sqlDB1.execSQL("DELETE FROM exerciseTBL_R WHERE date = ('" + current + "') ");
                        sqlDB1.close();
                        sqlDB1 = myHelper.getWritableDatabase();
                        sqlDB1.execSQL("INSERT INTO exerciseTBL_R VALUES ( " + edtExTime.getText().toString() + " , " + edtExDistance.getText().toString() + " , '" + current + "' );");
                        sqlDB1.close();
                    }
                    intent3.putExtra("Version", versionID2);
                    startActivityForResult(intent3, 0);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

    }


    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;
//                stepCountView.setText(String.valueOf(currentSteps));
                edtExWalk.setText(String.valueOf(currentSteps));

            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}