package com.example.tyrone.scse_foc_2018.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.tyrone.scse_foc_2018.R;

public class FeedbackFragment extends Fragment {

    private Button submitButton;
    private RatingBar gameRating;
    private Spinner date_Spinner;

    String[] focDates;

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
        submitButton = (Button)v.findViewById(R.id.submitFeedback);
        focDates = getResources().getStringArray((R.array.foc_dates));

        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,focDates);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_Spinner.setAdapter(datesAdapter);

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
        });

        return v;
    }

    public FeedbackFragment() {}
}
