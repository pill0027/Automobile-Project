package com.example.krishnaveni.automobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class AddGasoline extends Activity implements View.OnClickListener{
    private TextView literText;
    private  TextView priceText;
    private  TextView kilometerText;
    private Button okButton=null;
    private Button cancelButton=null;
    private  AutomobileDatabaseHelper automobileDatabaseHelper=new AutomobileDatabaseHelper(this);
    ;
    private  SQLiteDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasoline);
        literText=findViewById(R.id.literText);
        priceText=findViewById(R.id.priceText);
        kilometerText=findViewById(R.id.kilometersText);
        okButton=(Button)findViewById(R.id.okButton);
        cancelButton=findViewById(R.id.cancle);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        double liters=0.0d;
        double kilometers=0.0d;
        double price=0.0d;
        if(view.getId()==R.id.okButton){

            liters=Double.parseDouble(""+literText.getText());
            kilometers=Double.parseDouble(""+kilometerText.getText());
            price=Double.parseDouble(""+priceText.getText());
            db = automobileDatabaseHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(automobileDatabaseHelper.KEY_LITER,liters);
            values.put(automobileDatabaseHelper.KEY_PRICE,price);
            values.put(automobileDatabaseHelper.KEY_KILOMETER,kilometers);
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(automobileDatabaseHelper.TABLE_NAME, null, values);
         }
        if(view.getId()==R.id.cancle){
            this.finish();}
        }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "In onDestroy: ");
        automobileDatabaseHelper.close();
        super.onDestroy();
    }
}
