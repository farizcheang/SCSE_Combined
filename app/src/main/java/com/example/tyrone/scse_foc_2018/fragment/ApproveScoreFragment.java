package com.example.tyrone.scse_foc_2018.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.adapter.AccidentReportAdapter;
import com.example.tyrone.scse_foc_2018.adapter.ScoreUpdateAdapter;
import com.example.tyrone.scse_foc_2018.entity.AccidentReport;
import com.example.tyrone.scse_foc_2018.entity.GroupScore;
import com.example.tyrone.scse_foc_2018.entity.ScoreUpdateRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApproveScoreFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    private ListView scoreApproveListView;

    private ArrayList<ScoreUpdateRequest> requestArrayList = new ArrayList<ScoreUpdateRequest>();

    private ScoreUpdateAdapter scoreUpdateAdapter;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* do what you need to do */
            UpdateRequests();
            /* and here comes the "trick" */
            handler.postDelayed(this, 1000);
        }
    };
    public ApproveScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approve_score,container,false);

        scoreApproveListView = (ListView) view.findViewById(R.id.scoreApproveListView);

        scoreUpdateAdapter = new ScoreUpdateAdapter(getActivity(),requestArrayList, this);

        scoreApproveListView.setAdapter(scoreUpdateAdapter);

        scoreApproveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        scoreApproveListView = getActivity().findViewById(R.id.scoreApproveListView);

    }
    private void UpdateRequests()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("scoreupdaterequest");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    onGetDataSuccess(dataSnapshot);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void onGetDataSuccess (DataSnapshot data) {

        scoreUpdateAdapter.clear();
        for ( DataSnapshot request :  data.getChildren() ) {

            requestArrayList.add(0, request.getValue(ScoreUpdateRequest.class));

            //add the key into the adapter to keep track of which ones to delete later on
            scoreUpdateAdapter.addKey(request.getKey());

        }

        scoreUpdateAdapter.notifyDataSetChanged();

    }
    public void approve(int position, String key)
    {
        //add score to the og thru DB
        addScore(Integer.parseInt(requestArrayList.get(position).getScore()), key, requestArrayList.get(position).getGroup());

        //delete the item from the listview
        scoreUpdateAdapter.delete(position);
        scoreUpdateAdapter.notifyDataSetChanged();

        //delete the request from the DB
        database = FirebaseDatabase.getInstance().getReference("scoreupdaterequest");
        database.child(key).removeValue();
    }
    public void reject(int position, String key)
    {
        //delete the item from the listview
        scoreUpdateAdapter.delete(position);
        scoreUpdateAdapter.notifyDataSetChanged();

        //delete the request from the DB
        database = FirebaseDatabase.getInstance().getReference("scoreupdaterequest");
        database.child(key).removeValue();
    }
    private void addScore(final int score, String key, final String GroupToAddScore)
    {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("groups");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for ( DataSnapshot group :  dataSnapshot.getChildren() ) {

                        database = FirebaseDatabase.getInstance().getReference("groups");

                        //if its the group that i want to edit, then do
                        if(group.getKey().equals(GroupToAddScore))
                        {
                            GroupScore ascore = group.getValue(GroupScore.class);
                            String currentScore = ascore.getScore();

                            database.child(group.getKey()).child("score").setValue("" + (Integer.parseInt(currentScore) + score));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }
            );
        }
    }
}
