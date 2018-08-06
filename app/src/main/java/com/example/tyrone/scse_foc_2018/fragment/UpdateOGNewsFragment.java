package com.example.tyrone.scse_foc_2018.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UpdateOGNewsFragment extends Fragment {
    private FirebaseAuth mAuth;

    private DatabaseReference database;

    MemberController memberController;
    Button UpdateButton;
    TextView TitleTextView;
    TextView ContentTextView;

    private View.OnClickListener UpdateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BroadcastMessage();
        }
    };
    public UpdateOGNewsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberController = new MemberController();
        memberController.retrieveMemberRecord();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_update_ognews, container, false);
        return inflater.inflate(R.layout.fragment_update_news, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UpdateButton = getActivity().findViewById(R.id.HandOverButton);
        UpdateButton.setOnClickListener(UpdateButtonOnClickListener);

        ContentTextView = getActivity().findViewById(R.id.contentTextView);
        TitleTextView = getActivity().findViewById(R.id.TitleTextView);
    }
    public void BroadcastMessage()
    {

        String author = memberController.currentMember.getName();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        //TODO make the author and time correctly
        News news = new News(TitleTextView.getText().toString(), author, date, "12pm", ContentTextView.getText().toString());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference();
            database.child("OGnews").child(memberController.currentMember.getGroup()).push().setValue(news);
        }
        else {
            Log.d("ASD", "boardcastmessage:failure");
        }
    }
}
