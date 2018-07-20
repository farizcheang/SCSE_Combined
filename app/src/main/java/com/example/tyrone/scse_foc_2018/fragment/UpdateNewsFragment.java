package com.example.tyrone.scse_foc_2018.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.app.Fragment;
import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UpdateNewsFragment extends Fragment {

    private FirebaseAuth mAuth;

    private DatabaseReference database;

    Button UpdateButton;
    TextView TitleTextView;
    TextView ContentTextView;

    private View.OnClickListener UpdateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BroadcastMessage();
        }
    };

    public UpdateNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_news, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UpdateButton = getActivity().findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(UpdateButtonOnClickListener);

        ContentTextView = getActivity().findViewById(R.id.contentTextView);
        TitleTextView = getActivity().findViewById(R.id.TitleTextView);
    }
    public void BroadcastMessage()
    {

        String author = "tyrone";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        News news = new News(TitleTextView.getText().toString(), author, date, "12pm", ContentTextView.getText().toString());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference();
            database.child("news").push().setValue(news);
        }
        else {
            Log.d("ASD", "createNews:createRecord:failure");
        }
    }


}
