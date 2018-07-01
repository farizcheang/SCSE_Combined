package com.example.tyrone.scse_foc_2018.activity;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tyrone.scse_foc_2018.R;
import com.google.firebase.auth.FirebaseAuth;

public class BaseAuthActivity extends AppCompatActivity {
    protected EditText et_email;
    protected EditText et_name;
    protected EditText et_mobileNo;
    protected EditText et_password;
    protected EditText et_confirmPassword;
    protected Button btn_login;
    protected Button btn_register;

    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected boolean validateLoginForm(){

        boolean valid = true;

        // Reset errors.
        et_email.setError(null);
        et_password.setError(null);

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            valid = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            valid = false;
        } else if (!isEmailValid(email)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
            valid = false;
        }

        if (!valid) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return valid;
    }


    protected boolean validateRegisterForm() {
        boolean valid = true;

        String email = et_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_email.setError("Required.");
            valid = false;
        } else {
            et_email.setError(null);
        }

        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_password.setError("Required.");
            valid = false;
        } else {
            et_password.setError(null);
        }

        String confirmPassword = et_password.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            et_confirmPassword.setError("Required.");
            valid = false;
        }
        else if (!TextUtils.equals(password, confirmPassword)) {
            et_password.setError("Password does not match.");
            et_confirmPassword.setError("Password does not match.");
            valid = false;
        }
        else {
            et_confirmPassword.setError(null);
        }

        return valid;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
