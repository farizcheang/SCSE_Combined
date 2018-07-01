package com.example.tyrone.scse_foc_2018.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.entity.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Tyrone on 14/2/2018.
 */

public class RegisterActivity extends BaseAuthActivity {


    Button btn_submit, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        et_confirmPassword = (EditText)findViewById(R.id.et_confirmPassword);
        et_mobileNo = (EditText)findViewById(R.id.et_mobileNo);

        btn_submit = (Button) findViewById(R.id.bSubmit);
        btn_back = (Button) findViewById(R.id.bBack);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                //startActivity(intent);
                //finish();
                createMember(et_name.getText().toString(),
                        et_email.getText().toString(),
                        et_password.getText().toString(),
                        Integer.parseInt(et_mobileNo.getText().toString()));
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void createMember(String name, String email, String password, int mobileNo) {
        if (!validateRegisterForm()) {
            return;
        }

        showProgressDialog();

        final Member member = new Member(name, email, "Comm_Prog", mobileNo, null);
        final MemberController memberController = new MemberController();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d((getString(R.string.REGISTERED_TAG)), "createMember:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                if (memberController.createMemberRecord(member)) {
                                    Log.d((getString(R.string.REGISTERED_TAG)), "createMemberRecord:success");
                                }
                            }
                            else {
                                Log.d((getString(R.string.REGISTERED_TAG)), "createMemberRecord:failure");
                            }
                        } else {
                            Log.w((getString(R.string.REGISTERED_TAG)), "createMember:failure", task.getException());
                        }
                    }
                });

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d((getString(R.string.REGISTERED_TAG)), "login:success");
                            if (mAuth.getCurrentUser() != null) {
                                hideProgressDialog();
                                Intent intent = new Intent(RegisterActivity.this, NewsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            hideProgressDialog();
                            // If sign in fails, display a message to the user.
                            Log.w((getString(R.string.REGISTERED_TAG)), "login:failure", task.getException());
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
