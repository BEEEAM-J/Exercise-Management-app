package com.example.toy_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    Cursor excursor;
    String exItem = " ";
    String exTime;
    int h ,m, s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        myHelper = new myDBHelper(this);

        Intent inIntent = getIntent();
        int versionID3 = inIntent.getIntExtra("Version", 0);

        sqlDB = myHelper.getReadableDatabase();

        if (versionID3 == 1){                           // 걷기 모드
            walkMode();
        }
        else if(versionID3 == 0){                       // 달리기 모드
            runningMode();
        }
        else if(versionID3 == 2){
            totalMode();
        }


    }

    void walkMode(){
        excursor = sqlDB.rawQuery("SELECT * FROM exerciseTBL_W;", null);

        ArrayList<String> list = new ArrayList<>();

        while(excursor.moveToNext()){

            if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                h = Integer.parseInt(excursor.getString(0)) / 3600;
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = h + "시간" + m + "분" + s + "초";
            }
            else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = m + "분" + s + "초";
            }
            else{                                                                   // 운동시간이 1분 미만
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = s + "초";
            }

            exItem += "걷기\r\n" + excursor.getString(3) + "\r\n 운동시간: " +
                    exTime + "\r\n 운동 거리: " + excursor.getString(1) + "Km\r\n 걸음수: " + excursor.getString(2) + "\r\n";
            list.add(String.format(exItem));
            exItem = " ";
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        recyclerAdapter adapter = new recyclerAdapter(list) ;
        recyclerView.setAdapter(adapter) ;

        sqlDB.close();
        excursor.close();
    }

    void runningMode(){
        excursor = sqlDB.rawQuery("SELECT * FROM exerciseTBL_R;", null);

        ArrayList<String> list = new ArrayList<>();

        while(excursor.moveToNext()){

            if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                h = Integer.parseInt(excursor.getString(0)) / 3600;
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = h + "시간" + m + "분" + s + "초";
            }
            else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = m + "분" + s + "초";
            }
            else{                                                                   // 운동시간이 1분 미만
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = s + "초";
            }

            exItem += "달리기\r\n" + excursor.getString(2) + "\r\n 운동시간: "
                    + exTime + "\r\n 운동 거리: " + excursor.getString(1) + "Km\r\n";
            list.add(String.format(exItem));
            exItem = " ";
        }


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        recyclerAdapter adapter = new recyclerAdapter(list) ;
        recyclerView.setAdapter(adapter) ;

        sqlDB.close();
        excursor.close();
    }

    void totalMode(){
        excursor = sqlDB.rawQuery("SELECT * FROM exerciseTBL_W;", null);

        ArrayList<String> list = new ArrayList<>();

        while(excursor.moveToNext()){

            if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                h = Integer.parseInt(excursor.getString(0)) / 3600;
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = h + "시간" + m + "분" + s + "초";
            }
            else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = m + "분" + s + "초";
            }
            else{                                                                   // 운동시간이 1분 미만
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = s + "초";
            }

            exItem += "걷기\r\n" + excursor.getString(3) + "\r\n 운동시간: " +
                    exTime + "\r\n 운동 거리: " + excursor.getString(1) + "Km\r\n 걸음수: " + excursor.getString(2) + "\r\n";
            list.add(String.format(exItem));
            exItem = " ";
        }

        excursor = sqlDB.rawQuery("SELECT * FROM exerciseTBL_R;", null);

        while(excursor.moveToNext()){

            if(Integer.parseInt(excursor.getString(0)) >= 3600){              // 운동시간이 1시간 이상
                h = Integer.parseInt(excursor.getString(0)) / 3600;
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = h + "시간" + m + "분" + s + "초";
            }
            else if(Integer.parseInt(excursor.getString(0)) >= 60){              // 운동시간이 1분 이상
                m = (Integer.parseInt(excursor.getString(0)) % 3600) / 60;
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = m + "분" + s + "초";
            }
            else{                                                                   // 운동시간이 1분 미만
                s = (Integer.parseInt(excursor.getString(0)) % 3600) % 60;
                exTime = s + "초";
            }

            exItem += "달리기\r\n" + excursor.getString(2) + "\r\n 운동시간: "
                    + exTime + "\r\n 운동 거리: " + excursor.getString(1) + "Km\r\n";
            list.add(String.format(exItem));
            exItem = " ";
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        recyclerAdapter adapter = new recyclerAdapter(list) ;
        recyclerView.setAdapter(adapter) ;

        sqlDB.close();
        excursor.close();
    }

}