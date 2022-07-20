package com.example.toy_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThirdActivity extends AppCompatActivity implements SensorEventListener, LocationListener{

    myDBHelper myHelper;
    TextView tv3, edtExWalk, edtExDistance;
    Chronometer chronoExTime;
    Button btnExEnd;
    SQLiteDatabase sqlDB1;
    SensorManager sensorManager;
    Sensor stepCountSensor;
    LinearLayout layout_walk;
    int timeSec;
    private Sensor stepDetectorSensor;
    private int mStepDetector = 0;
    private boolean isGPSEnable = false;

    private LocationManager locationManager;
    private Location lastKnownLocation = null;
    private Location nowLastlocation = null;

    double totaldistance = 0;

    // 현재 걸음 수
    int currentSteps = 0;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tv3 = findViewById(R.id.tv3);
        chronoExTime = findViewById(R.id.chronoExTime);
        edtExDistance = findViewById(R.id.edtExDistance);
        edtExWalk = findViewById(R.id.edtExWalk);
        btnExEnd = findViewById(R.id.btnExEnd);
        layout_walk = findViewById(R.id.layout_walk);
        myHelper = new myDBHelper(this);

        Intent inIntent2 = getIntent();
        Intent intent3 = new Intent(getApplicationContext(), FourthActivity.class);

        int versionID2 = inIntent2.getIntExtra("Version", 0);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current = format.format(currentTime);

        chronoExTime.setBase(SystemClock.elapsedRealtime());
        chronoExTime.start();

        if (versionID2 == 1) {
            layout_walk.setVisibility(View.VISIBLE);
        } else if (versionID2 == 0) {
            layout_walk.setVisibility(View.INVISIBLE);
        }

        btnExEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (versionID2 == 1) {
                        chronoExTime.stop();
                        sqlDB1 = myHelper.getReadableDatabase();
                        sqlDB1.execSQL("DELETE FROM exerciseTBL_W WHERE date = ('" + current + "') ");
                        sqlDB1.close();
                        sqlDB1 = myHelper.getWritableDatabase();
                        sqlDB1.execSQL("INSERT INTO exerciseTBL_W VALUES ( " + timeSec + " , " + edtExDistance.getText().toString() + " , " + String.valueOf(currentSteps) + " , '" + current + "' );");
                        sqlDB1.close();
                    } else if (versionID2 == 0) {
                        chronoExTime.stop();
                        sqlDB1 = myHelper.getReadableDatabase();
                        sqlDB1.execSQL("DELETE FROM exerciseTBL_R WHERE date = ('" + current + "') ");
                        sqlDB1.close();
                        sqlDB1 = myHelper.getWritableDatabase();
                        sqlDB1.execSQL("INSERT INTO exerciseTBL_R VALUES ( " + timeSec + " , " + edtExDistance.getText().toString() + " , '" + current + "' );");
                        sqlDB1.close();
                    }
                    intent3.putExtra("Version", versionID2);
                    startActivityForResult(intent3, 0);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "값을 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chronoExTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int)(time / 3600000);
                int m = (int)(time - h * 3600000) / 60000;
                int s = (int)(time - h * 3600000 - m * 60000) / 1000 ;
                timeSec = s + (m * 60) + (h * 3600);
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                cArg.setText(hh+":"+mm+":"+ss);
            }
        });

        // 활동 퍼미션 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // GPS 사용 가능 여부 확인
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        //step -----
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepDetectorSensor == null) {
            Toast.makeText(this, "No Step Detect Sensor", Toast.LENGTH_SHORT).show();

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                return;
            }
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
                double longWork = getGPSLocation();
            }
        }
    }

    public double getGPSLocation() {
        double deltaTime = 0.0;
        double deltaDist = 0.0;
        //GPS Start
        if(isGPSEnable) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return 0.0;
            }
//            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastKnownLocation == null ) {
                lastKnownLocation = nowLastlocation;
            }

            if (lastKnownLocation != null && nowLastlocation != null) {
                double lat1 = lastKnownLocation.getLatitude();
                double lng1 = lastKnownLocation.getLongitude();
                double lat2 = nowLastlocation.getLatitude();
                double lng2 = nowLastlocation.getLongitude();

                deltaTime = (nowLastlocation.getTime() - lastKnownLocation.getTime()) / 1000.0;  //시간 간격


                deltaDist = distance(lat1,  lng1,  lat2,  lng2);
                if(deltaDist > 0.05) {
                    totaldistance += Double.parseDouble(String.format("%f",deltaDist));
                    edtExDistance.setText(String.format("%.3f", totaldistance / 1000));
                    lastKnownLocation = nowLastlocation;
                    return deltaDist;
                }
            }
        }
        return 0.0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location) {
        nowLastlocation = location;
    }

    @Override
    public void onProviderEnabled(String provider) {
        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 위치정보 업데이트
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI);

        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 위치정보 업데이트
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        // 위치정보 가져오기 제거
        locationManager.removeUpdates(this);
    }

    // 거리계산
    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344 * 1000; //미터 단위

        return dist;
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}