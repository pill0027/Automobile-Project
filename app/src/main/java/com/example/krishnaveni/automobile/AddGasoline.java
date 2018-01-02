package com.example.krishnaveni.automobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class AddGasoline extends AppCompatActivity implements View.OnClickListener{
    private TextView literText;
    private  TextView priceText;
    private  TextView kilometerText;
    private Button okButton=null;
    private Button cancelButton=null;
    private  AutomobileDatabaseHelper automobileDatabaseHelper=new AutomobileDatabaseHelper(this);
    private Automobile automobile;
    private  SQLiteDatabase db=null;
    View parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasoline);
        setTheme(R.style.Theme_AppCompat_Light);
        literText=findViewById(R.id.literText);
        priceText=findViewById(R.id.priceText);
        kilometerText=findViewById(R.id.kilometersText);
        okButton=(Button)findViewById(R.id.okButton);
        cancelButton=findViewById(R.id.cancle);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        Intent intent = getIntent();
        automobile= (Automobile)intent.getSerializableExtra("AutomobileRecord");
        if(automobile!=null)
        {
            literText.setText(""+automobile.getLiters());
            priceText.setText(""+automobile.getPrice());
            kilometerText.setText(""+automobile.getKilometer());

        }
        //testPopulateData();
    }
    @Override
    public void onClick(View view) {
        float liters=0.0f;
        float kilometers=0.0f;
        float price=0.0f;
        if(view.getId()==R.id.okButton){

            liters=Float.parseFloat(""+literText.getText());
            kilometers=Float.parseFloat(""+kilometerText.getText());
            price=Float.parseFloat(""+priceText.getText());
            db = automobileDatabaseHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(automobileDatabaseHelper.KEY_LITER,liters);
            values.put(automobileDatabaseHelper.KEY_PRICE,price);
            values.put(automobileDatabaseHelper.KEY_KILOMETER,kilometers);
           if(automobile!=null)
           {
               db.update(automobileDatabaseHelper.TABLE_NAME, values,automobileDatabaseHelper.KEY_ID + " = ?",
                       new String[]{Long.toString(automobile.getId())} );
               Toast toast = Toast.makeText(this,getResources().getString(R.string.item_edited), Toast.LENGTH_LONG);
               toast.show();

               this.finish();
           }
           else {
               // Insert the new row, returning the primary key value of the new row
               long newRowId = db.insert(automobileDatabaseHelper.TABLE_NAME, null, values);
                 this.finish();
         }


        if(view.getId()==R.id.cancle){
            this.finish();}

    }}
    public void testPopulateData() {
        ArrayList values = new ArrayList();
        String year="2017";
        String prevMonth="12";
        String startDay="1";
        Random r=new Random();

        for( int i=1;i<=31;i++){
            values.clear();
            float ltrs = 10F + new Random().nextFloat() * (50F - 10F);
            float prc =  1F + new Random().nextFloat() * (2F - 1F);
            float km = 5F + new Random().nextFloat() * (250F - 5F);
            String timestamp=year+"-"+prevMonth+"-"+i+"T00:00";
            values.add(ltrs);
            values.add(prc);
            values.add(km);
            values.add(timestamp);
            automobileDatabaseHelper.populateDB(values);
        }

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "In onDestroy: ");
        automobileDatabaseHelper.close();
        super.onDestroy();
    }
}
