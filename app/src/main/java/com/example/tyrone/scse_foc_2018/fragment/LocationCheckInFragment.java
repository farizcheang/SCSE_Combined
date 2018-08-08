package com.example.tyrone.scse_foc_2018.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.adapter.LocationAdapter;
import com.example.tyrone.scse_foc_2018.entity.GroupLocation;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class LocationCheckInFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    //variables for the spinner for OG(temp)
    Spinner OGSelectSpinner;
    String[] OGs;

    //variables for the location listview
    //LocationAdapter locationAdapter;
    ListView listView;
    ArrayAdapter<String> locationAdapter;
    String CurrentLocation = "";
    String[] items;

    Button CheckInButton;
    private View.OnClickListener CheckInOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckIn();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_check_in, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init the check in button stuff
        CheckInButton = getActivity().findViewById(R.id.CheckInButton);
        CheckInButton.setOnClickListener(CheckInOnClickListener);


        //init the og spinner
        OGSelectSpinner = getActivity().findViewById(R.id.OGSelectSpinner);
        OGs = getResources().getStringArray((R.array.OGs));
        ArrayAdapter<String> OGAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,OGs);
        OGSelectSpinner.setAdapter(OGAdapter);

        //init the listview for the locations
        listView = (ListView) view.findViewById(R.id.LocationListView);

        locationAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray((R.array.locations)));


        listView.setAdapter(locationAdapter);
        CurrentLocation = locationAdapter.getItem(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CurrentLocation = locationAdapter.getItem(i);
                view.setSelected(true);

                for(int a = 0; a < adapterView.getChildCount(); a++)
                {
                    adapterView.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                }
                //view.setBackgroundColor(Color.argb(1.0f,0.5f,0.5f,1.0f));
            }
        });

    }
    public void CheckIn()
    {
        //store the current location in the db before replacing it
        StoreCurrentLocation();

        String OG = OGSelectSpinner.getSelectedItem().toString();
        //String Location = "SCSE";
        Date currentDate = Calendar.getInstance().getTime();

        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        String sTime = localDateFormat.format(currentDate);

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        FirebaseDatabase.getInstance().getReference("oglocation").child(OG).child("location").setValue(CurrentLocation);
        FirebaseDatabase.getInstance().getReference("oglocation").child(OG).child("time").setValue(sTime);
        FirebaseDatabase.getInstance().getReference("oglocation").child(OG).child("date").setValue(date);

    }
    private void StoreCurrentLocation()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String OG = OGSelectSpinner.getSelectedItem().toString();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("oglocation");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot loc : dataSnapshot.getChildren()) {

                        if(loc.getKey().equals(OGSelectSpinner.getSelectedItem().toString())) {
                            GroupLocation location = loc.getValue(GroupLocation.class);
                            database = FirebaseDatabase.getInstance().getReference();
                            database.child("oglocationrecord").child(OGSelectSpinner.getSelectedItem().toString()).push().setValue(location);
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
