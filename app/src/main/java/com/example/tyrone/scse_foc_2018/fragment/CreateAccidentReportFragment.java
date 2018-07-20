package com.example.tyrone.scse_foc_2018.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.AccidentReport;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAccidentReportFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    Spinner AccidentSpinner;
    Spinner StudentSpinner;

    TextView studentNameTextView;

    String CurrentAccident;
    String CurrentStudent;

    android.widget.TextView Description;
    Button SendButton;

    private View.OnClickListener SendButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SendReport();
        }
    };

    TextView InfomationTextView;

    public CreateAccidentReportFragment() {
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
        return inflater.inflate(R.layout.fragment_create_accident_report, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AccidentSpinner = getActivity().findViewById(R.id.AccidentSpinner);
        StudentSpinner = getActivity().findViewById(R.id.StudentSpinner);

        ArrayAdapter<String> AccidentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray((R.array.accidentTypes)));
        //ArrayAdapter<String> StudentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray((R.array.studentNames)));

        AccidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AccidentSpinner.setAdapter(AccidentAdapter);
        //StudentSpinner.setAdapter(StudentAdapter);

        CurrentAccident = AccidentSpinner.getSelectedItem().toString();
        //CurrentStudent = StudentSpinner.getSelectedItem().toString();

        setUpListeners();

        Description = (TextView) getActivity().findViewById(R.id.DescriptionText);
        studentNameTextView = getActivity().findViewById(R.id.studentNameTextView);

        SendButton = getActivity().findViewById(R.id.SendButton);
        SendButton.setEnabled(false);
        SendButton.setOnClickListener(SendButtonOnClickListener);

        InfomationTextView = getActivity().findViewById(R.id.InfoTextView);
    }

    private void setUpListeners()
    {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                CurrentAccident = AccidentSpinner.getSelectedItem().toString();

                if(CurrentAccident.equals("Select Accident")) {
                    //send a prompt to indicate that cannot send
                    SendButton.setEnabled(false);
                }
                else SendButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        };

        /*AdapterView.OnItemSelectedListener listener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                CurrentStudent = StudentSpinner.getSelectedItem().toString();
                if(CurrentAccident.equals("Select Accident") || CurrentStudent.equals("Select Student")) {
                    //send a prompt to indicate that cannot send
                    SendButton.setEnabled(false);
                }
                else SendButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

        };*/

        AccidentSpinner.setOnItemSelectedListener(listener);
        //StudentSpinner.setOnItemSelectedListener(listener2);
    }

    public void SendReport()
    {
        //first check if the items have values
        CurrentAccident = AccidentSpinner.getSelectedItem().toString();
        //CurrentStudent = StudentSpinner.getSelectedItem().toString();

        //if no accident is being selected then dont do anything
        if(CurrentAccident.equals("Select Accident")) {
            //send a prompt to indicate that cannot send
            SendButton.setEnabled(false);
            InfomationTextView.setText("Please select the accident");
            return;
        }
        else
        {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            AccidentReport accident = new AccidentReport(studentNameTextView.getText().toString(), Description.getText().toString(), CurrentAccident, date);
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            if ( user != null ) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("accidentreport").push().setValue(accident);
            }
            else {
                Log.d("ASD", "create Accident report: SendReport : failure");
            }


            InfomationTextView.setText("Case has been sent");

        }
    }
}
