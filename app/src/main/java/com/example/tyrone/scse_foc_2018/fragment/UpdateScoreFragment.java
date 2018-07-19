package com.example.tyrone.scse_foc_2018.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.GroupScore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateScoreFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    Spinner GroupSelectSpinner;
    Button PlusButton;
    Button MinusButton;
    Button UpdateButton;
    TextView Score;

    private View.OnClickListener MinusButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MinusScore();
        }
    };
    private View.OnClickListener PlusButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PlusScore();
        }
    };

    private View.OnClickListener UpdateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UpdateScore();
        }
    };
    public UpdateScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_score, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GroupSelectSpinner = getActivity().findViewById(R.id.GroupSelectSpinner);
        PlusButton = getActivity().findViewById(R.id.PlusButton);
        PlusButton.setOnClickListener(PlusButtonOnClickListener);

        MinusButton = getActivity().findViewById(R.id.MinusButton);
        MinusButton.setOnClickListener(MinusButtonOnClickListener);

        UpdateButton = getActivity().findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(UpdateButtonOnClickListener);

        Score = getActivity().findViewById(R.id.ScoreTextView);


        String[] OG_Groups = getResources().getStringArray(R.array.OGs);
        ArrayAdapter<String> GroupAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,OG_Groups);
        GroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        GroupSelectSpinner.setAdapter(GroupAdapter);

    }

    public void MinusScore()
    {
        int score = Integer.parseInt(Score.getText().toString());
        score -= 5;
        Score.setText("" + score);
    }

    public void PlusScore()
    {
        int score = Integer.parseInt( Score.getText().toString());
        score += 5;
        Score.setText("" + score);
    }
    public void UpdateScore()
    {
       /* int CurrentScore = sharedPref.getInt("ApusScore", 0);
        int AddScore = Integer.parseInt(Score.getText().toString());
        String OGname = GroupSelectSpinner.getSelectedItem().toString();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(OGname + "Score", CurrentScore + AddScore);
        editor.commit();

        GetScore()*/
    }
    void GetScore ()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("groups");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //if (fragment instanceof NewsFragment)
                    onGetDataSuccess(dataSnapshot);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    public void onGetDataSuccess (DataSnapshot data) {
        for ( DataSnapshot score :  data.getChildren() ) {



            //if its the group that i want to edit, then do
            /*if(score.getKey().equals(GroupSelectSpinner.getSelectedItem().toString()))
            {
                GroupScore ascore = score.getValue(GroupScore.class);
                String thescore = ascore.getScore();
                int AddScore = Integer.parseInt(Score.getText().toString());

                //GroupScore newScore = new GroupScore("" + thescore + AddScore, "");
                data.
            }*/


        }

    }
}
