package com.example.tyrone.scse_foc_2018.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Fragment;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.GroupScore;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewScoreFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;


    TextView ApusTextView;
    TextView CorvusTextView;
    TextView LeoTextView;
    TextView LyraTextView;
    TextView OrionTextView;
    TextView ScorpiusTextView;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* do what you need to do */
            Refresh();
            /* and here comes the "trick" */
            handler.postDelayed(this, 1000);
        }
    };

    public ViewScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Refresh();

        handler.postDelayed(runnable, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_score, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApusTextView = getActivity().findViewById(R.id.ApusTextView);
        CorvusTextView = getActivity().findViewById(R.id.CorvusTextView);
        LeoTextView = getActivity().findViewById(R.id.LeoTextView);
        LyraTextView = getActivity().findViewById(R.id.LyraTextView);
        OrionTextView = getActivity().findViewById(R.id.OrionTextView);
        ScorpiusTextView = getActivity().findViewById(R.id.ScorpiusTextView);
    }

    void Refresh ()
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

        ArrayList<GroupScore> g = new ArrayList<>();

        for ( DataSnapshot score :  data.getChildren() ) {
            //News news = new News();
            //g.add(score.getValue(GroupScore.class));
            GroupScore ascore = score.getValue(GroupScore.class);
            String thescore = ascore.getScore();

            switch(score.getKey())
            {
                case "apus" :
                    ApusTextView.setText("" + thescore);
                    break;
                case "corvus" :
                    CorvusTextView.setText("" + thescore);
                    break;
                case "leo" :
                    LeoTextView.setText("" + thescore);
                    break;
                case "lyra" :
                    LyraTextView.setText("" + thescore);
                    break;
                case "orion" :
                    OrionTextView.setText("" + thescore);
                    break;
                case "scorpius" :
                    ScorpiusTextView.setText("" + thescore);
                    break;
            }
        }
    }
}

