package com.example.tyrone.scse_foc_2018.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.entity.ScoreUpdateRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateScoreFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private MemberController memberController;

    Button PlusButton;
    Button MinusButton;
    Button UpdateButton;
    TextView Score;
    TextView DescriptionTextView;

    ImageView Selector;
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

    private View.OnClickListener OGImagenOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //adjust the position to "highlight" the current group
            float adjustX = v.getX() - (Selector.getWidth()/2 - v.getWidth()/2);
            float adjustY = v.getY() - (Selector.getHeight()/2 - v.getHeight()/2);
            Selector.setX(adjustX);
            Selector.setY(adjustY);

            Selector.setVisibility(View.VISIBLE);

            //update the tag with the OG name
            Selector.setTag(v.getTag());
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

        memberController = new MemberController();
        memberController.retrieveMemberRecord();

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



        //temp imageview to set the selector
        ImageView apusImage = getActivity().findViewById(R.id.apusImageView);
        getActivity().findViewById(R.id.apusImageView).setOnClickListener(OGImagenOnClickListener);
        getActivity().findViewById(R.id.apusImageView).setTag("apus");

        getActivity().findViewById(R.id.leoImageView).setOnClickListener(OGImagenOnClickListener);
        getActivity().findViewById(R.id.leoImageView).setTag("leo");

        getActivity().findViewById(R.id.corvusImageView).setOnClickListener(OGImagenOnClickListener);
        getActivity().findViewById(R.id.corvusImageView).setTag("corvus");

        getActivity().findViewById(R.id.scorpiusImageView).setOnClickListener(OGImagenOnClickListener);
        getActivity().findViewById(R.id.scorpiusImageView).setTag("scorpius");

        getActivity().findViewById(R.id.lyraImageView).setOnClickListener(OGImagenOnClickListener);
        getActivity().findViewById(R.id.lyraImageView).setTag("lyra");

        getActivity().findViewById(R.id.orionImageView).setOnClickListener(OGImagenOnClickListener);
        getActivity().findViewById(R.id.orionImageView).setTag("orion");

        //init the selector to the first image which is apus
        Selector = getActivity().findViewById(R.id.Selector);
        Selector.setVisibility(View.INVISIBLE);

        //update the tag with the OG name
        Selector.setTag(getActivity().findViewById(R.id.apusImageView).getTag());

        PlusButton = getActivity().findViewById(R.id.PlusButton);
        PlusButton.setOnClickListener(PlusButtonOnClickListener);

        MinusButton = getActivity().findViewById(R.id.MinusButton);
        MinusButton.setOnClickListener(MinusButtonOnClickListener);

        UpdateButton = getActivity().findViewById(R.id.HandOverButton);
        UpdateButton.setOnClickListener(UpdateButtonOnClickListener);

        Score = getActivity().findViewById(R.id.ScoreTextView);
        DescriptionTextView  = getActivity().findViewById(R.id.descriptionTextView);

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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        //TODO make the requester correctly
        String requester = memberController.currentMember.getName();

        String group = Selector.getTag().toString();
        ScoreUpdateRequest updateReq = new ScoreUpdateRequest(DescriptionTextView.getText().toString(), requester, Score.getText().toString(),group);

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference();
            database.child("scoreupdaterequest").push().setValue(updateReq);
        }

        /*if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("groups");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for ( DataSnapshot group :  dataSnapshot.getChildren() ) {

                        //if its the group that i want to edit, then do
                        if(group.getKey().equals(GroupSelectSpinner.getSelectedItem().toString()))
                        {
                            GroupScore ascore = group.getValue(GroupScore.class);
                            String thescore = ascore.getScore();
                            int AddScore = Integer.parseInt(Score.getText().toString());

                            database.child(group.getKey()).child("score").setValue("" + (Integer.parseInt(thescore) + AddScore));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }
            );
        }*/
    }


}

