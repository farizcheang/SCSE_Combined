package com.example.tyrone.scse_foc_2018.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.tyrone.scse_foc_2018.R;

public class FeedbackFragment extends Fragment {

    private Button submitButton;
    private RatingBar gameRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        //  Get
        submitButton = (Button)v.findViewById(R.id.btn_feedbackSubmit);

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
        });

        return v;
    }

    public FeedbackFragment() {}
}
