package com.example.krishnaveni.automobile;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddGasoline extends Activity implements View.OnClickListener{
    private TextView literText;
    private  TextView priceText;
    private  TextView kilometerText;
    private Button okButton;
    private Button cancelButton;
    private  AutomobileDatabaseHelper automobileDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        literText=findViewById(R.id.literText);
        priceText=findViewById(R.id.priceText);
        kilometerText=findViewById(R.id.kilometersText);
        okButton=findViewById(R.id.okButton);
        cancelButton=findViewById(R.id.cancle);
        automobileDatabaseHelper=new AutomobileDatabaseHelper(this);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        setContentView(R.layout.activity_add_gasoline);
    }

    @Override
    public void onClick(View view) {
        double liters=0.0d;
        double kilometers=0.0d;
        double price=0.0d;
        if(view.getId()==R.id.okButton){
            liters=Double.parseDouble((String) literText.getText());
            kilometers=Double.parseDouble((String)kilometerText.getText());
            price=Double.parseDouble((String) priceText.getText());
            SQLiteDatabase db = automobileDatabaseHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(automobileDatabaseHelper.KEY_LITER,liters);
            values.put(automobileDatabaseHelper.KEY_PRICE,price);
            values.put(automobileDatabaseHelper.KEY_KILOMETER,kilometers);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(automobileDatabaseHelper.TABLE_NAME, null, values);

        }
        if(view.getId()==R.id.cancle){
            this.finish();
        }

        }
}
