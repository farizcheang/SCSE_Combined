package com.example.tyrone.scse_foc_2018.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.AccidentReport;

import java.util.ArrayList;

public class AccidentReportAdapter extends ArrayAdapter<AccidentReport> {

    ArrayList<AccidentReport> reportArray;


    private final Activity context;

    public AccidentReportAdapter(Activity inContext, ArrayList<AccidentReport> report)
    {
        super(inContext,R.layout.accidentreport_listview_detail, report);
        context = inContext;
        reportArray = report;

    }
    @Override
    public int getCount() {
        return reportArray.size();
    }

    @Override
    public AccidentReport getItem(int position) {
        return reportArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void add(AccidentReport report) {
        reportArray.add(report);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("getView", "test");
        LayoutInflater inflater = context.getLayoutInflater();

        AccidentReport report = getItem(position);

        if ( view == null ) {
            view = inflater.inflate(R.layout.accidentreport_listview_detail, null);
        }

        TextView studentTextView = (TextView) view.findViewById(R.id.studentTextView);
        TextView accidentTextView = (TextView) view.findViewById(R.id.accidentTextView);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);

        studentTextView.setText(report.getStudent());
        accidentTextView.setText(report.getAccident());
        descriptionTextView.setText(report.getDescription());
        dateTextView.setText(report.getDate());

        //if the image is not "", means there is an image, then display the icon
        if(!report.getImage().equals(""))
            view.findViewById(R.id.pictureImageView).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.pictureImageView).setVisibility(View.INVISIBLE);

        return view;
    }

}
