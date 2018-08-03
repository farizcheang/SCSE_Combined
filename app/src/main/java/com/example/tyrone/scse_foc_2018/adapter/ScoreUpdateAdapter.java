package com.example.tyrone.scse_foc_2018.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.ScoreUpdateRequest;
import com.example.tyrone.scse_foc_2018.fragment.ApproveScoreFragment;

import java.util.ArrayList;

public class ScoreUpdateAdapter extends ArrayAdapter<ScoreUpdateRequest> {

    private String title, author, datetime, content;

    ArrayList<ScoreUpdateRequest> RequestArray;
    ArrayList<String> KeyArray = new ArrayList<String>();

    private final Activity context;

    ApproveScoreFragment approveScoreFragment;

    public ScoreUpdateAdapter(Activity inContext, ArrayList<ScoreUpdateRequest> requests, ApproveScoreFragment inApproveScoreFragment) {
        super(inContext, R.layout.score_approve_listview_detail, requests);
        context = inContext;
        RequestArray = requests;

        approveScoreFragment = inApproveScoreFragment;

    }

    @Override
    public int getCount() {
        return RequestArray.size();
    }

    @Override
    public ScoreUpdateRequest getItem(int position) {
        return RequestArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void add(ScoreUpdateRequest request) {
        RequestArray.add(request);
    }
    public void addKey(String key) {
        KeyArray.add(0,key);
    }
    public void delete(int position)
    {
        RequestArray.remove(position);
        KeyArray.remove(position);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        ScoreUpdateRequest request = getItem(position);

        if (view == null) {
            view = inflater.inflate(R.layout.score_approve_listview_detail, null);
        }

        TextView RequesterTextView = (TextView) view.findViewById(R.id.requesterTextView);
        TextView ScoreTextView = (TextView) view.findViewById(R.id.scoreTextView);
        TextView DescriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView GroupTextView  = (TextView) view.findViewById(R.id.groupTextView);

        RequesterTextView.setText(request.getRequester());
        ScoreTextView.setText(request.getScore());
        DescriptionTextView.setText(request.getDescription());
        GroupTextView.setText(request.getGroup());

        //handles approve button on click
        view.findViewById(R.id.approveButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                approveScoreFragment.approve(position, KeyArray.get(position));
                Log.i("HELLOOOOOOO", "I AM HEARD at position " + position);
            }
        });

        //handles reject button on click
        view.findViewById(R.id.rejectButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                approveScoreFragment.reject(position, KeyArray.get(position));

                Log.i("HELLOOOOOOO", "I AM HEARD at position " + position);
            }
        });
        return view;
    }
}