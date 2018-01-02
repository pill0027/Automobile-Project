package com.example.krishnaveni.automobile;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by KRISHNAVENI on 2017-12-17.
 */

public class ReadDataAsyncTask extends AsyncTask<String,Automobile, String> {
    Context ctx;
    AutomobileAdapter automobileAdapter;
    Activity activity;
    ListView listView;
    private  AutomobileDatabaseHelper automobileDatabaseHelper;
    public ReadDataAsyncTask(Context ctx)
    {
        this.ctx=ctx;
        activity=(Activity)ctx;
    }
    @Override
    protected String doInBackground(String... operation) {
        ProgressBar simpleProgressBar = (ProgressBar)activity.findViewById(R.id.progressBar2);
        String oper=operation[0];
        if(oper.equalsIgnoreCase("get_data"))
        {
            automobileDatabaseHelper=new AutomobileDatabaseHelper(ctx);
            listView=(ListView)activity.findViewById(R.id.listrecords);

            SQLiteDatabase db=automobileDatabaseHelper.getReadableDatabase();
            Cursor cursor=automobileDatabaseHelper.listRecords(db);
            automobileAdapter=new AutomobileAdapter(ctx,R.layout.fragment_listview);
            int id;
            float liters,price,kilometer;
            String timestamp;
            int length = cursor.getCount();
            int counter=1;
            while(cursor.moveToNext() ) {

                if(simpleProgressBar!=null)
                simpleProgressBar.setProgress((counter/length)*100);
                id=cursor.getInt(cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_ID));
                liters=cursor.getFloat(cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_LITER));
                price=cursor.getFloat(cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_PRICE));
                kilometer=cursor.getFloat(cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_KILOMETER));
                timestamp=cursor.getString(cursor.getColumnIndex(AutomobileDatabaseHelper.SQLTIME));
                Automobile automobile=new Automobile(id,liters,price,kilometer,timestamp);
                publishProgress(automobile);
                counter++;
            }
            return "get_info";

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Automobile... values) {
        automobileAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equalsIgnoreCase("get_info")){
            listView.setAdapter(automobileAdapter);
        }
        else{
            Toast.makeText(ctx,"ADDED",Toast.LENGTH_LONG).show();
        }
    }
}

