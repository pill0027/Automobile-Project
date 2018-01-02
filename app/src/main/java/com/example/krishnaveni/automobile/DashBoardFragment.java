package com.example.krishnaveni.automobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by KRISHNAVENI on 2017-12-17.
 */

public class DashBoardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private  AutomobileDatabaseHelper automobileDatabaseHelper;
    private String year,month;
    private SQLiteDatabase db;
    private float gasPurChasedPerMonth;
    private  TextView gasPerMonth;
    private String[] monthName = {"Jan", "Feb",
            "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov",
            "Dec"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_dashboard, container, false);
        automobileDatabaseHelper=new AutomobileDatabaseHelper(getActivity());
        db=automobileDatabaseHelper.getReadableDatabase();
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        year = Integer.toString(cal.get(Calendar.YEAR));
        month = Integer.toString(cal.get(Calendar.MONTH)+1);
        FloatingActionButton helpButton = (FloatingActionButton)v.findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder custombuilder = new AlertDialog.Builder(view.getContext());

                final View dialogView= inflater.inflate(R.layout.help_dialog_layout, null);
                custombuilder.setView(dialogView)

                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog customdialog = custombuilder.create();
                customdialog.show();
            }
        });
     float avgPrice=automobileDatabaseHelper.getAverageGasPrice(db,year,month);
     gasPurChasedPerMonth=automobileDatabaseHelper.getGasPurchasedPerMonth(db,year,month);
     TextView avgGasPrice = (TextView) v.findViewById(R.id.gasprice);
     TextView totalGasPurchased = (TextView) v.findViewById(R.id.gasliters);
     gasPerMonth=(TextView)v.findViewById(R.id.gaspermonth);
     avgGasPrice.setText(""+avgPrice+" $");
     totalGasPurchased.setText(""+gasPurChasedPerMonth+" $");
     Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        //List<String> months = monthName;
        ArrayAdapter adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_spinner_item,monthName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(Calendar.getInstance().get(Calendar.MONTH));
     return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        //idText = (TextView) view.findViewById(R.id.dbIdText);
        //msgText= (TextView) view.findViewById(R.id.msgText);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int monthSeleced=i+1;
        Calendar cal=Calendar.getInstance();
        String currentYear = Integer.toString(cal.get(Calendar.YEAR));
        gasPurChasedPerMonth=automobileDatabaseHelper.getGasPurchasedPerMonth(db,currentYear,"0"+Integer.toString(monthSeleced));
        gasPerMonth.setText(""+gasPurChasedPerMonth+" $");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
