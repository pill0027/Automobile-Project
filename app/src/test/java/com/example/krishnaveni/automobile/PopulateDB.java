package com.example.krishnaveni.automobile;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PopulateDB extends AndroidTestCase{
    AutomobileDatabaseHelper automobileDatabaseHelper;
    @Override
    public void setUp() throws Exception {
        //super.setUp();
        AutomobileDatabaseHelper automobileDatabaseHelper=new AutomobileDatabaseHelper(mContext);
    }

    public void testPopulateData() throws Exception {

        AutomobileDatabaseHelper automobileDatabaseHelper=new AutomobileDatabaseHelper(mContext);
        ArrayList values = new ArrayList();
        String year="2017";
        String prevMonth="Nov";
        String startDay="1";
        Random r=new Random();

        for( int i=1;i<3;i++){

            double litres = 10 + (50 - 10) * r.nextDouble();
            double price = 1 + (2 - 1) * r.nextDouble();
            double km = 5 + (250 - 5) * r.nextDouble();
            String timestamp=year+"-"+prevMonth+"-"+i+"T00:00:00";
            values.add(litres);
            values.add(price);
            values.add(km);
            values.add(timestamp);
            automobileDatabaseHelper.populateDB(values);

        }

    }
}