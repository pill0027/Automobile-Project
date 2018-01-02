package com.example.krishnaveni.automobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishnaveni.automobile.Automobile;

import java.util.ArrayList;
import java.util.List;

public class AutomobileAdapter extends ArrayAdapter implements View.OnClickListener {
    public static final String ACTIVITY_NAME="Automobile Activity";
    List list=new ArrayList();
    Context ctx;
    SQLiteDatabase db;
    int tag ;
    Automobile automobile;
    View parentView;

    AutomobileDatabaseHelper automobileDatabaseHelper;
    ;

    public AutomobileAdapter(Context context, int resource) {
        super(context, resource);
        ctx=context;
        automobileDatabaseHelper=new AutomobileDatabaseHelper(context);
        db=automobileDatabaseHelper.getWritableDatabase();
    }


    public void add(Automobile object) {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        AutomobileHolder automobileHolder;
        if(row==null){
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.automobile_record,parent,false);
            automobileHolder=new AutomobileHolder();
            automobileHolder.tx_dbid=row.findViewById(R.id.dbid);
            automobileHolder.tx_liters=row.findViewById(R.id.ltrs);
            automobileHolder.tx_price=row.findViewById(R.id.price);
            automobileHolder.tx_km=row.findViewById(R.id.km);
            automobileHolder.tx_timestamp=row.findViewById(R.id.timestamp);
            automobileHolder.img_edit=row.findViewById(R.id.editImage);
            automobileHolder.img_delete=row.findViewById(R.id.deleteImage);
            row.setTag(automobileHolder);

        }
        else{
            automobileHolder=(AutomobileHolder)row.getTag();
        }
        Automobile automobile=(Automobile)getItem(position);
        automobileHolder.tx_dbid.setText(""+automobile.getId());
        automobileHolder.tx_liters.setText(""+automobile.getLiters());
        automobileHolder.tx_price.setText(""+automobile.getPrice());
        automobileHolder.tx_km.setText(""+automobile.getKilometer());
        automobileHolder.tx_timestamp.setText(""+automobile.getTimestamp());
        automobileHolder.img_delete.setTag(position);
        automobileHolder.img_delete.setOnClickListener(this);
        automobileHolder.img_edit.setTag(position);
        automobileHolder.img_edit.setOnClickListener(this);

        return row;
    }

    @Override
    public void onClick(final View view) {
        tag = (Integer) view.getTag();
        automobile=(Automobile)list.get(tag);
        parentView=view;
        switch (view.getId()) {
            case R.id.deleteImage:
                Log.i(ACTIVITY_NAME, "User clicked to add Gasoline details");

                AlertDialog.Builder custombuilder = new AlertDialog.Builder(view.getContext());
                // Get the layout inflater
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialogView= inflater.inflate(R.layout.dialog_option3, null);
                custombuilder.setTitle(getContext().getString(R.string.delete_msg));
                custombuilder.setMessage(getContext().getString(R.string.r_sure));
                custombuilder.setView(dialogView)
                        // Add action buttons
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                automobileDatabaseHelper.delete(db,automobile.getId());
                                list.remove(tag);
                                Snackbar snackbar = Snackbar.make(view, getContext().getString(R.string.delete_msg), Snackbar.LENGTH_LONG);
                                snackbar.show();
                                notifyDataSetChanged();


                            }
                        })
                        .setNegativeButton(R.string.cancle , new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog customdialog = custombuilder.create();
                customdialog.show();
                break;

            case R.id.editImage:
                Log.i(ACTIVITY_NAME, "User clicked to edit Gasoline details");
                Intent intent = new Intent(ctx,AddGasoline.class);
                intent.putExtra("AutomobileRecord",automobile);
                ctx.startActivity(intent);

                break;
            default:
                break;
        }
        this.notifyDataSetChanged();



    }

    static class AutomobileHolder{
        TextView tx_dbid,tx_liters, tx_price, tx_km,tx_timestamp;
        ImageView img_edit,img_delete;
    }
}