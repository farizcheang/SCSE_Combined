package com.example.tyrone.scse_foc_2018.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.tyrone.scse_foc_2018.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends BaseAuthActivity {


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_email = (EditText)findViewById(R.id.etEmail);
        et_password = (EditText)findViewById(R.id.etPassword);
        btn_login = (Button) findViewById(R.id.bLogin);
        btn_register = (Button) findViewById(R.id.bRegister);

        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v ) {
                //hideProgressDialog();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }

        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick( View v ) {
                //hideProgressDialog();
                //Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                //startActivity(intent);
                //finish();
                attemptLogin();
            }

        });

    }

    public void initToolBar() {
        /*toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.titlebar_news);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(LoginActivity.this,"clicking Toolbar");
                    }
                }
        );*/
    }


    public void onClick(View view) {
    }
        /*setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_skip = findViewById(R.id.btn_skip);

        MemberController memberController = new MemberController();
        if (memberController.isLoggedIn()) {
            hideProgressDialog();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideProgressDialog();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (validateLoginForm()) {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgressDialog();

            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d((getString(R.string.REGISTERED_TAG)), "login:success");
                                if (mAuth.getCurrentUser() != null) {
                                    hideProgressDialog();
                                    Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                hideProgressDialog();
                                // If sign in fails, display a message to the user.
                                Log.w((getString(R.string.REGISTERED_TAG)), "login:failure", task.getException());
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Incorrect Email/Password.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}
