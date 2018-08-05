package com.example.tyrone.scse_foc_2018.controller;

/**
 * Created by Tyrone on 14/2/2018.
 */

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.tyrone.scse_foc_2018.activity.LoginActivity;
import com.example.tyrone.scse_foc_2018.fragment.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public Member currentMember;

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

    public boolean updateMemberRecord(String newPassword){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference();

            //Log.d(TAG, "updateMember:password: " + member.getPassword());

            //user updates their mobile and email in EditAccountFragment
            /*user.updateEmail(member.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "User email address updated.");
                    }
                }
            });*/

            //user updates password in ChangePassword fragment
            //database.child("member").child(user.getUid()).child("password").setValue(member.getPassword());
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "User password updated.");
                    }
                }
            });

            //user updates avatar in CameraviewFragment
            //database.child("member").child(user.getUid()).child("avatar").setValue(member.getAvatar());

            result = true;
            Log.d(TAG, "updateMember:updateRecord:success");

        }
        else {
            Log.d(TAG, "updateMember:updateRecord:failure");
        }
        return result;
    }

    public void retrieveMemberRecord() {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            database = FirebaseDatabase.getInstance().getReference("member");
            database.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //if(fragment instanceof AccountFragment)
                    //    currentMember = ((AccountFragment) fragment).onGetDataSuccess(dataSnapshot);
                    currentMember = dataSnapshot.getValue(Member.class);
                    Log.i("Role:",currentMember.getRole());
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
