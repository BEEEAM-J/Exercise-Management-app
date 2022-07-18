package com.example.toy_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {

    public myDBHelper(Context context){
        super(context, "ObjectDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE objectTBL_W ( oTime INTEGER, oDistance INTEGER, oWalk INTEGER, date STRING);");
        db.execSQL("CREATE TABLE objectTBL_R ( oTime INTEGER, oDistance INTEGER , date STRING);");
        db.execSQL("CREATE TABLE exerciseTBL_W ( exTime INTEGER, exDistance DOUBLE, exWalk INTEGER, date STRING);");
        db.execSQL("CREATE TABLE exerciseTBL_R ( exTime INTEGER, exDistance DOUBLE, date STRING);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS objectTBL");
        db.execSQL("DROP TABLE IF EXISTS exerciseTBL");
        onCreate(db);
    }
}
