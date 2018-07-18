package com.example.tyrone.scse_foc_2018.controller;

/**
 * Created by Tyrone on 14/2/2018.
 */

import android.app.Fragment;
import android.util.Log;

import com.example.tyrone.scse_foc_2018.fragment.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.tyrone.scse_foc_2018.entity.Member;
import com.google.firebase.database.ValueEventListener;


public class MemberController {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference database;
    private Member member;

    private boolean result;

    private final String TAG = "MEMBER_CONTROLLER";

    //  Constructor
    public MemberController () {};

    //  Create Member Record
    public boolean createMemberRecord ( Member member ) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference();
            database.child("member").child(user.getUid()).setValue(member);
        }
        else {
            Log.d(TAG, "createMember:createRecord:failure");
        }
        return result;
    }

    public void retrieveMemberRecord(final Fragment fragment) {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference("member");
            database.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //if(fragment instanceof AccountFragment)
                        //((AccountFragment) fragment).onGetDataSuccess(dataSnapshot);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //if(fragment instanceof AccountFragment)
                      //  ((AccountFragment) fragment).onGetDataFailed(databaseError);

                }
            });
        }
    }

}
