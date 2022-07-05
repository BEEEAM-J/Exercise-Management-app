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

        tvRes = findViewById(R.id.tvSession);
        tv3 = findViewById(R.id.tv3);
        edtTime = findViewById(R.id.edtTime);
        edtDistance = findViewById(R.id.edtDistance);
        edtWalk = findViewById(R.id.edtWalk);
        btnCheck2 = findViewById(R.id.btnCheck2);
        rBtnWalk = findViewById(R.id.rBtnWalk);
        rBtnRunning = findViewById(R.id.rBtnRunning);
        rGroup2 = findViewById(R.id.rGroup2);
        myHelper = new myDBHelper(this);

        Intent inIntent = getIntent();
        Intent intent2 = new Intent(getApplicationContext(), ThirdActivity.class);

        int val = inIntent.getIntExtra("Session", 0);
        tvRes.setText("목표 기간: " + String.valueOf(val) + "일");

        rBtnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.setVisibility(View.VISIBLE);
                edtWalk.setVisibility(View.VISIBLE);
                versionID = 1;
            }
        });
        rBtnRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.setVisibility(View.INVISIBLE);
                edtWalk.setVisibility(View.INVISIBLE);
                versionID = 0;
            }
        });

        btnCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase();
                sqlDB.execSQL("DELETE FROM objectTBL");
                sqlDB.close();
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO objectTBL VALUES ( " + edtTime.getText().toString() + " , " + edtDistance.getText().toString() + " , " + edtWalk.getText().toString() + ");");
                sqlDB.close();
                intent2.putExtra("Session", val);
                intent2.putExtra("Version", versionID);
                startActivityForResult(intent2, 0);
            }
        });

    }

    public class myDBHelper extends SQLiteOpenHelper{

        public myDBHelper(Context context){
            super(context, "ObjectDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE objectTBL ( oTime INTEGER, oDistance INTEGER, oWalk INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS objectTBL");
            onCreate(db);
        }
    }

}