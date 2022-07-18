package com.example.toy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FourthActivity extends AppCompatActivity {

    myDBHelper myHelper;
    TextView tvOTime, tvExTime, tvCheckTime, tvODistance, tvExDistance, tvCheckDistance, tvOWalk, tvExWalk, tvCheckWalk, tvRecord;
    SQLiteDatabase sqlDB2;
    CalendarView calView2;
    Cursor ocursor, excursor;
    LinearLayout tvLayout;
    int objectTime, objectDistance, objectWalk, exerciseTime, exerciseWalk, h ,m, s;
    double exerciseDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        myHelper = new myDBHelper(this);
        calView2 = findViewById(R.id.calView2);
        tvOTime = findViewById(R.id.tvOTime);
        tvExTime = findViewById(R.id.tvExTime);
        tvCheckTime = findViewById(R.id.tvCheckTime);
        tvODistance = findViewById(R.id.tvODistance);
        tvExDistance = findViewById(R.id.tvExDistance);
        tvCheckDistance = findViewById(R.id.tvCheckDistance);
        tvOWalk = findViewById(R.id.tvOWalk);
        tvExWalk = findViewById(R.id.tvExWalk);
        tvCheckWalk = findViewById(R.id.tvCheckWalk);
        tvRecord = findViewById(R.id.tvRecord);
        tvLayout = findViewById(R.id.tvLayout);

        Intent inIntent3 = getIntent();

        int versionID3 = inIntent3.getIntExtra("Version", 0);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current = format.format(currentTime);

        sqlDB2 = myHelper.getReadableDatabase();
        if(versionID3 == 1){                                                        // 걷기 모드
            ocursor = sqlDB2.rawQuery("SELECT * FROM objectTBL_W WHERE date = '" + current + "'; ", null);
            excursor = sqlDB2.rawQuery("SELECT * FROM exerciseTBL_W WHERE date = '" + current + "';", null);

            while(ocursor.moveToNext()){
//                tvOTime.setText("목표 시간: " + ocursor.getString(0) + "분");
//                objectTime = Integer.parseInt(ocursor.getString(0));

                if(Integer.parseInt(ocursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                    h = Integer.parseInt(ocursor.getString(0)) / 3600;
                    m = (Integer.parseInt(ocursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(ocursor.getString(0)) % 3600) % 60;
                    tvOTime.setText("목표 시간: " + h + "시간" + m + "분" + s + "초");
                }
                else if(Integer.parseInt(ocursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                    m = (Integer.parseInt(ocursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(ocursor.getString(0)) % 3600) % 60;
                    tvOTime.setText("목표 시간: " + m + "분" + s + "초");
                }
                else{                                                                   // 운동시간이 1분 미만
                    s = (Integer.parseInt(ocursor.getString(0)) % 3600) % 60;
                    tvOTime.setText("목표 시간: " + s + "초");
                }

                objectTime = Integer.parseInt(ocursor.getString(0));

                tvODistance.setText("목표 거리: " + ocursor.getString(1) + "Km");
                objectDistance = Integer.parseInt(ocursor.getString(1));

                tvOWalk.setText("목표 걸음수: " + ocursor.getString(2));
                objectWalk = Integer.parseInt(ocursor.getString(2));
            }

            while(excursor.moveToNext()){
//                tvExTime.setText("내 운동 시간: " + excursor.getString(0) + "분");
//                exerciseTime = Integer.parseInt(excursor.getString(0));

                if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                    h = Integer.parseInt(excursor.getString(0)) / 3600;
                    m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                    tvExTime.setText("내 운동 시간: " + h + "시간" + m + "분" + s + "초");
                }
                else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                    m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                    tvExTime.setText("내 운동 시간: " + m + "분" + s + "초");
                }
                else{                                                                   // 운동시간이 1분 미만
                    s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                    tvExTime.setText("내 운동 시간: " + s + "초");
                }

                exerciseTime = Integer.parseInt(excursor.getString(0));

                tvExDistance.setText("내 운동 거리: " + excursor.getString(1) + "Km");
                exerciseDistance = Double.parseDouble(excursor.getString(1));

                tvExWalk.setText("내 걸음수: " + excursor.getString(2));
                exerciseWalk = Integer.parseInt(excursor.getString(2));
            }

            if(exerciseTime >= objectTime){
                tvCheckTime.setText("목표 달성");
            }
            else{
                tvCheckTime.setText("실패");
            }

            if(exerciseDistance >= objectDistance){
                tvCheckDistance.setText("목표 달성");
            }
            else{
                tvCheckDistance.setText("실패");
            }

            if(exerciseWalk >= objectWalk){
                tvCheckWalk.setText("목표 달성");
            }
            else{
                tvCheckWalk.setText("실패");
            }
        }
        else if(versionID3 == 0){                               // 달리기 모드

            tvOWalk.setVisibility(View.INVISIBLE);
            tvExWalk.setVisibility(View.INVISIBLE);
            tvCheckWalk.setVisibility(View.INVISIBLE);

            ocursor = sqlDB2.rawQuery(" SELECT * FROM objectTBL_R WHERE date = '" + current + "'; ", null);
            excursor = sqlDB2.rawQuery("SELECT * FROM exerciseTBL_R WHERE date = '" + current + "';", null);

            while(ocursor.moveToNext()){
//                tvOTime.setText("목표 시간: " + ocursor.getString(0) + "분");
//                objectTime = Integer.parseInt(ocursor.getString(0));

                if(Integer.parseInt(ocursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                    h = Integer.parseInt(ocursor.getString(0)) / 3600;
                    m = (Integer.parseInt(ocursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(ocursor.getString(0)) % 3600) % 60;
                    tvOTime.setText("목표 시간: " + h + "시간" + m + "분" + s + "초");
                }
                else if(Integer.parseInt(ocursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                    m = (Integer.parseInt(ocursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(ocursor.getString(0)) % 3600) % 60;
                    tvOTime.setText("목표 시간: " + m + "분" + s + "초");
                }
                else{                                                                   // 운동시간이 1분 미만
                    s = (Integer.parseInt(ocursor.getString(0)) % 3600) % 60;
                    tvOTime.setText("목표 시간: " + s + "초");
                }

                objectTime = Integer.parseInt(ocursor.getString(0));

                tvODistance.setText("목표 거리: " + ocursor.getString(1) + "Km");
                objectDistance = Integer.parseInt(ocursor.getString(1));
            }

            while(excursor.moveToNext()){
//                tvExTime.setText("내 운동 시간: " + excursor.getString(0) + "분");
//                exerciseTime = Integer.parseInt(excursor.getString(0));

                if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                    h = Integer.parseInt(excursor.getString(0)) / 3600;
                    m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                    tvExTime.setText("내 운동 시간: " + h + "시간" + m + "분" + s + "초");
                }
                else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                    m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                    s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                    tvExTime.setText("내 운동 시간: " + m + "분" + s + "초");
                }
                else{                                                                   // 운동시간이 1분 미만
                    s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                    tvExTime.setText("내 운동 시간: " + s + "초");
                }

                exerciseTime = Integer.parseInt(excursor.getString(0));

                tvExDistance.setText("내 운동 거리: " + excursor.getString(1) + "Km");
                exerciseDistance = Double.parseDouble(excursor.getString(1));

                if(exerciseTime >= objectTime){
                    tvCheckTime.setText("목표 달성");
                }
                else{
                    tvCheckTime.setText("실패");
                }

                if(exerciseDistance >= objectDistance){
                    tvCheckDistance.setText("목표 달성");
                }
                else{
                    tvCheckDistance.setText("실패");
                }
            }
        }

        ocursor.close();
        excursor.close();
        sqlDB2.close();

        calView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() // 날짜 선택 이벤트
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                String strMonth = String.valueOf(month + 1);
                String strDayOfMonth = String.valueOf(dayOfMonth);
                if(month < 9){
                    strMonth = "0" + String.valueOf(month + 1);
                }
                if(dayOfMonth < 10){
                    strDayOfMonth = "0" + String.valueOf(dayOfMonth);
                }
                String date = year + "-" + strMonth + "-" + strDayOfMonth;
                tvRecord.setText(date); // 선택한 날짜로 설정
                sqlDB2 = myHelper.getReadableDatabase();
                tvLayout.setVisibility(View.INVISIBLE);

                if(versionID3 == 1){
                    String strRecord = date + "\r\n" + "운동 종류: 걷기" + "\r\n";
                    excursor = sqlDB2.rawQuery("SELECT * FROM exerciseTBL_W WHERE date = '" + date + "';", null);
                    while(excursor.moveToNext()){
                        if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                            h = Integer.parseInt(excursor.getString(0)) / 3600;
                            m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                            s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                            strRecord += "내 운동 시간: " + h + "시간" + m + "분" + s + "초" + "\r\n";
                        }
                        else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                            m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                            s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                            strRecord += "내 운동 시간: " + m + "분" + s + "초" + "\r\n";
                        }
                        else{                                                                   // 운동시간이 1분 미만
                            s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                            tvExTime.setText("내 운동 시간: " + s + "초");
                            strRecord += "내 운동 시간: " + s + "초" + "\r\n";
                        }
                        strRecord += "내 운동 거리: " + excursor.getString(1) + "Km" + "\r\n" +
                                "내 걸음수: " + excursor.getString(2);
                    }
                    tvRecord.setText(strRecord);
                }
                else if(versionID3 == 0){
                    String strRecord = date + "\r\n" + "운동 종류: 달리기" + "\r\n";
                    excursor = sqlDB2.rawQuery("SELECT * FROM exerciseTBL_R WHERE date = '" + date + "';", null);
                    while(excursor.moveToNext()){
                        if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                            h = Integer.parseInt(excursor.getString(0)) / 3600;
                            m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                            s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                            strRecord += "내 운동 시간: " + h + "시간" + m + "분" + s + "초" + "\r\n";
                        }
                        else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                            m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                            s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                            strRecord += "내 운동 시간: " + m + "분" + s + "초" + "\r\n";
                        }
                        else{                                                                   // 운동시간이 1분 미만
                            s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                            tvExTime.setText("내 운동 시간: " + s + "초");
                            strRecord += "내 운동 시간: " + s + "초" + "\r\n";
                        }
                        strRecord += "내 운동 거리: " + excursor.getString(1) + "Km" + "\r\n" ;
                    }
                    tvRecord.setText(strRecord);
                }
                excursor.close();
                sqlDB2.close();
            }
        });

    }
}