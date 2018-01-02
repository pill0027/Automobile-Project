package com.example.krishnaveni.automobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by KRISHNAVENI on 2017-10-11.
 */

public class AutomobileDatabaseHelper extends SQLiteOpenHelper {

    public static final String SQLTIME = "sqltime";
    private static String DATABASE_NAME="Automobile.db";
    private static int VERSION_NUM=7;
    public final static String KEY_ID ="_ID";
    public final static String KEY_LITER="LITERS";
    public final static String KEY_PRICE="PRICE";
    public final static String KEY_KILOMETER="KILOMETER";
    public static String TABLE_NAME="GasolineRecord";
    private SQLiteDatabase dbTest=null;
    public AutomobileDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME  + "( _ID INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_LITER +" FLOAT,"+ KEY_PRICE +
                " FLOAT,"+KEY_KILOMETER+ " FLOAT, " + SQLTIME + " DATETIME NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M', 'now', 'localtime')))";
        Log.i("AutoMobileDBHelper", "Calling onCreate");
        db.execSQL(sql);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public Cursor listRecords(SQLiteDatabase db)
    {
        String[] coloumns={KEY_ID,KEY_LITER,KEY_PRICE,KEY_KILOMETER,SQLTIME};
        Cursor cursor=db.query(TABLE_NAME,coloumns,null,null,null,null,null);
        return cursor;
    }

    public void delete(SQLiteDatabase db,Long id)
    {
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{Long.toString(id)} );

    }


    public void update(SQLiteDatabase db,Automobile automobile)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_LITER,automobile.getLiters());
        cv.put(KEY_PRICE,automobile.getPrice());
        cv.put(KEY_KILOMETER,automobile.getKilometer());
        db.update(TABLE_NAME, cv, "where"+KEY_ID+" = "+automobile.getId(), null);
    }

    public void populateDB(ArrayList values)
    {
        if(dbTest==null) {
            dbTest = this.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(KEY_LITER,(Float)values.get(0));
        cv.put(KEY_PRICE,(Float)values.get(1));
        cv.put(KEY_KILOMETER,(Float)values.get(2));
        cv.put(SQLTIME,(String)values.get(3));
        dbTest.insert(TABLE_NAME, null, cv);
    }

    public float getAverageGasPrice(SQLiteDatabase db,String year, String month)
    {

        float avgPrice=0.0f;
        String query="SELECT  AVG("+KEY_PRICE+") AS avg, "+SQLTIME +
                " FROM "+TABLE_NAME+" WHERE STRFTIME('%Y', "+SQLTIME+")=? " +
                "AND STRFTIME('%m', "+SQLTIME+")=? GROUP BY STRFTIME('%Y', "+SQLTIME+"), STRFTIME('%m',  "+SQLTIME+")";

        Cursor cursor=db.rawQuery(query,new String[]{year,month});
        while(cursor.moveToNext() ) {
            avgPrice=cursor.getFloat(cursor.getColumnIndex("avg"));

        }

        return  avgPrice;
    }


    public float getGasPurchasedPerMonth(SQLiteDatabase db,String year, String month)
    {

        float gasPurchased=0.0f;
        String query="SELECT  SUM("+KEY_PRICE+") AS liters, "+SQLTIME +
                " FROM "+TABLE_NAME+" WHERE STRFTIME('%Y', "+SQLTIME+")=? " +
                "AND STRFTIME('%m', "+SQLTIME+")=? GROUP BY STRFTIME('%Y', "+SQLTIME+"), STRFTIME('%m',  "+SQLTIME+")";

        Cursor cursor=db.rawQuery(query,new String[]{year,month});
        while(cursor.moveToNext() ) {
            gasPurchased=cursor.getFloat(cursor.getColumnIndex("liters"));

        }

        return  gasPurchased;
    }

}


