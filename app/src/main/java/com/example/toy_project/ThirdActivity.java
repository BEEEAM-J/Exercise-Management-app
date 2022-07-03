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

public class ThirdActivity extends AppCompatActivity {

    myDBHelper1 myHelper;
    TextView tv3;
    EditText edtExTime, edtExDistance, edtExWalk;
    Button btnSave;
    SQLiteDatabase sqlDB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView tvSession2 = findViewById(R.id.tvSession2);
        edtExTime = findViewById(R.id.edtExTime);
        edtExDistance = findViewById(R.id.edtExDistance);
        edtExWalk = findViewById(R.id.edtExWalk);
        btnSave = findViewById(R.id.btnSave);
        myHelper = new myDBHelper1(this);

        Intent inIntent2 = getIntent();
        Intent intent3 = new Intent(getApplicationContext(), FourthActivity.class);

        int val2 = inIntent2.getIntExtra("Session", 0);
        int versionID2 = inIntent2.getIntExtra("Version", 0);

//        if(versionID2 == 1){
//            tv3.setVisibility(View.VISIBLE);
//            edtExWalk.setVisibility(View.VISIBLE);
//        }

        tvSession2.setText("목표 기간: " + String.valueOf(val2) + "일");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB1 = myHelper.getWritableDatabase();
                sqlDB1.execSQL("INSERT INTO exerciseTBL VALUES ( " + edtExTime.getText().toString() + " , " + edtExDistance.getText().toString() + " , " + edtExWalk.getText().toString() + ");");
                sqlDB1.close();
                intent3.putExtra("Session", val2);
                intent3.putExtra("Version", versionID2);
                startActivityForResult(intent3, 0);
            }
        });
    }

    public class myDBHelper1 extends SQLiteOpenHelper {

        public myDBHelper1(Context context){
            super(context, "ExerciseDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE exerciseTBL ( exTime INTEGER, exDistance INTEGER, exWalk INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS exerciseTBL");
            onCreate(db);
        }
    }
}