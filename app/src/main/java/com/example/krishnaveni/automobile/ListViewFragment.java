package com.example.krishnaveni.automobile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by KRISHNAVENI on 2017-12-17.
 */

public class ListViewFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{
    private static final int RESULT_CODE = 200;
    private static final String ACTIVITY_NAME = "ListViewFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_listview, container, false);
        ImageButton addButton = (ImageButton) v.findViewById(R.id.add);
        ListView listView=(ListView)v.findViewById(R.id.listrecords);
        listView.setOnItemClickListener(this);
        addButton.setOnClickListener(this);
        ReadDataAsyncTask readDataAsyncTask=new ReadDataAsyncTask(getActivity());
        readDataAsyncTask.execute("get_data");

        return v;
    }


    @Override
    public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked to add Gasoline details");
                Intent intent = new Intent(getActivity(),AddGasoline.class);
                startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        ReadDataAsyncTask readDataAsyncTask=new ReadDataAsyncTask(getActivity());
        readDataAsyncTask.execute("get_data");

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
