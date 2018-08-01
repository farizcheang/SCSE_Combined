package com.example.tyrone.scse_foc_2018.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.News;

import java.util.ArrayList;

public class LocationAdapter  extends ArrayAdapter<String> {

    private String title, author, datetime, content;

    ArrayList<String> LocationArray = new ArrayList<String>();

    private final Activity context;


    public LocationAdapter(Activity inContext, ArrayList<String> location)
    {
        super(inContext,R.layout.location_listview_detail, location);
        context = inContext;
        LocationArray = location;

    }
    @Override
    public int getCount() {
        Log.i("getCount size", String.valueOf(LocationArray.size()));
        return LocationArray.size();
    }

    @Override
    public String getItem(int position) {
        Log.i("getItem position", String.valueOf(position));
        return LocationArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i("getItem id", String.valueOf(position));
        return position;
    }


    public void add(String location) {
        LocationArray.add(location);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        String location = getItem(position);

        if ( view == null ) {
            view = inflater.inflate(R.layout.location_listview_detail, null);
        }

        TextView LocationTextView = (TextView) view.findViewById(R.id.LocationTextView);

        LocationTextView.setText(location);
        return view;
    }
}
