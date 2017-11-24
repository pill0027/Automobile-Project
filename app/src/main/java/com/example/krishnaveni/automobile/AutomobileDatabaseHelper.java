package com.example.krishnaveni.automobile;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by KRISHNAVENI on 2017-10-11.
 */

public class AutomobileDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="Automobile.db";
    private static int VERSION_NUM=1;
    public final static String KEY_ID ="_ID";
    public final static String KEY_LITER="LITERS";
    public final static String KEY_PRICE="PRICE";
    public final static String KEY_KILOMETER="KILOMETER";
    public static String TABLE_NAME="GasolineRecord";

    public AutomobileDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME  + "( _ID INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_LITER +" LONG,"+ KEY_PRICE + " LONG,"+KEY_KILOMETER+" LONG)";
        Log.i("AutoMobileDBHelper", "Calling onCreate");
        db.execSQL(sql);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

}

