package com.example.tyrone.scse_foc_2018.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FeedbackController {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference database;

    private final String TAG = "FEEDBACK_CONTROLLER";

    public FeedbackController () {}

    public boolean sendFeedback(String focDate, String gameRating, String welfareRating, String hosRating, String others) {
        boolean result = false;

        return result;
    }
}
