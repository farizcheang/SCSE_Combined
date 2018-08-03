package com.example.tyrone.scse_foc_2018.fragment;

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
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.GroupLocation;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewOGLocationFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    TextView ApusLocationTextView;
    TextView CorvusLocationTextView;
    TextView LeoLocationTextView;
    TextView LyraLocationTextView;
    TextView OrionLocationTextView;
    TextView ScorpiusLocationTextView;

    TextView ApusTimeTextView;
    TextView CorvusTimeTextView;
    TextView LeoTimeTextView;
    TextView LyraTimeTextView;
    TextView OrionTimeTextView;
    TextView ScorpiusTimeTextView;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* do what you need to do */
            updateInfo();
            /* and here comes the "trick" */
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_oglocation, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("ASD", "COMING INTO ONVIEWCREATED IN VIEWOGLOCATIONFRAGMENT.JAVA");
        ApusLocationTextView = getActivity().findViewById(R.id.apusLocationTextView);
        CorvusLocationTextView = getActivity().findViewById(R.id.corvusLocationTextView);
        LeoLocationTextView = getActivity().findViewById(R.id.leoLocationTextView);
        LyraLocationTextView = getActivity().findViewById(R.id.lyraLocationTextView);
        OrionLocationTextView = getActivity().findViewById(R.id.orionLocationTextView);
        ScorpiusLocationTextView = getActivity().findViewById(R.id.scorpiusLocationTextView);

        ApusTimeTextView = getActivity().findViewById(R.id.apusTimeTextView);
        CorvusTimeTextView = getActivity().findViewById(R.id.corvusTimeTextView);
        LeoTimeTextView = getActivity().findViewById(R.id.leoTimeTextView);
        LyraTimeTextView = getActivity().findViewById(R.id.lyraTimeTextView);
        OrionTimeTextView = getActivity().findViewById(R.id.orionTimeTextView);
        ScorpiusTimeTextView = getActivity().findViewById(R.id.scorpiusTimeTextView);

        updateInfo();
        Log.i("ASD", "GOING AWAU FRO<M ONVIEWCREATED IN VIEWOGLOCATIONFRAGMENT.JAVA");

        int c = 1;
        c =+ 2;

    }
    private void updateInfo()
    {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("oglocation");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for ( DataSnapshot loc :  dataSnapshot.getChildren() ) {

                        GroupLocation location = loc.getValue(GroupLocation.class);

                        switch (loc.getKey())
                        {
                            case "apus":
                                ApusLocationTextView.setText(location.getLocation());
                                ApusTimeTextView.setText(location.getTime());
                                break;
                            case "corvus":
                                CorvusLocationTextView.setText(location.getLocation());
                                CorvusTimeTextView.setText(location.getTime());
                                break;
                            case "leo":
                                LeoLocationTextView.setText(location.getLocation());
                                LeoTimeTextView.setText(location.getTime());
                                break;
                            case "lyra":
                                LyraLocationTextView.setText(location.getLocation());
                                LyraTimeTextView.setText(location.getTime());
                                break;
                            case "orion":
                                OrionLocationTextView.setText(location.getLocation());
                                OrionTimeTextView.setText(location.getTime());
                                break;
                            case "scorpius":
                                ScorpiusLocationTextView.setText(location.getLocation());
                                ScorpiusTimeTextView.setText(location.getTime());
                                break;

                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



    }
}
