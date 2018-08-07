package com.example.tyrone.scse_foc_2018.fragment;

import android.app.Fragment;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.Feedback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {

    private Button submitButton;
    private RatingBar venueRating;
    private RatingBar serviceRating;
    private RatingBar receptionRating;
    private RatingBar activitiesRating;
    private RatingBar durationRating;
    private RatingBar overallRating;
    private EditText feedbackText;

    private Spinner date_Spinner;

    private FirebaseAuth mAuth;

    private DatabaseReference database;

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

        date_Spinner = (Spinner)v.findViewById(R.id.dateSpinner);
        venueRating = (RatingBar)v.findViewById(R.id.venueRatingBar);
        serviceRating = (RatingBar)v.findViewById(R.id.serviceRatingBar);
        receptionRating = (RatingBar)v.findViewById(R.id.refreshmentRatingBar);
        activitiesRating = (RatingBar)v.findViewById(R.id.activitiesRatingBar);
        durationRating = (RatingBar)v.findViewById(R.id.durationRatingBar);
        overallRating = (RatingBar)v.findViewById(R.id.overallRatingBar);
        feedbackText = (EditText)v.findViewById(R.id.feedbackEdit);



        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,focDates);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_Spinner.setAdapter(datesAdapter);

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                Feedback feedback = new Feedback(user.getUid(),date_Spinner.getSelectedItem().toString(),String.valueOf(venueRating.getRating()),String.valueOf(serviceRating.getRating()),
                                                String.valueOf(receptionRating.getRating()),String.valueOf(activitiesRating.getRating()),String.valueOf(durationRating.getRating()),String.valueOf(overallRating.getRating()),feedbackText.getText().toString());

                if ( user != null ) {
                    database = FirebaseDatabase.getInstance().getReference();
                    database.child("feedback").push().setValue(feedback);

                }
                else {
                    Log.d("ASD", "boardcastmessage:failure");
                }
            }
        });

        return v;
    }

    public FeedbackFragment() {}
}
