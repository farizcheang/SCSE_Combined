package com.example.tyrone.scse_foc_2018.controller;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.fragment.NewsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsController {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference database;

    private final String TAG = "NEWS_CONTROLLER";

    public NewsController () {}



    //  Retrieve News
    public void retrieveNews(final Fragment fragment) {

        Log.d(TAG, "retrieve");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("news");
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (fragment instanceof NewsFragment)
                        ((NewsFragment)fragment).onGetDataSuccess(dataSnapshot);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
