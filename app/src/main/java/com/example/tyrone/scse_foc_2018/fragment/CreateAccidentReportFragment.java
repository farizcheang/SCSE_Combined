package com.example.tyrone.scse_foc_2018.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.AccidentReport;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CreateAccidentReportFragment extends Fragment {

    private final int REQUEST_TAKE_PICTURE = 0;

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    Spinner AccidentSpinner;
    Spinner StudentSpinner;

    TextView studentNameTextView;

    String CurrentAccident;
    String CurrentStudent;

    android.widget.TextView Description;
    Button SendButton;
    Button UploadImageButton;
    Button ImageCancelButton;

    ImageView AccidentImageView;
    String encodedImage = "";

    private View.OnClickListener SendButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SendReport();
        }
    };
    private View.OnClickListener UploadImageButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UploadImage();
        }
    };
    private View.OnClickListener ImageCancelButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageCancel();
        }
    };
    private View.OnClickListener ImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if the encoded image is NOT = "", means got image, THEN do this view image Fragment
            if(!encodedImage.equals("")) {
                ViewImageFragment viewImageFragment = ViewImageFragment.newInstance(encodedImage);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fl_contents, viewImageFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    };
    AdapterView.OnItemSelectedListener AdapterListener = new AdapterView.OnItemSelectedListener() {
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

        AccidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AccidentSpinner.setAdapter(AccidentAdapter);

        CurrentAccident = AccidentSpinner.getSelectedItem().toString();

        AccidentSpinner.setOnItemSelectedListener(AdapterListener);

        //text
        Description = (TextView) getActivity().findViewById(R.id.DescriptionText);
        studentNameTextView = getActivity().findViewById(R.id.studentNameTextView);

        InfomationTextView = getActivity().findViewById(R.id.InfoTextView);

        //image views
        AccidentImageView = getActivity().findViewById(R.id.AccidentImageView);
        AccidentImageView.setOnClickListener(ImageViewOnClickListener);

        //buttons
        UploadImageButton = getActivity().findViewById(R.id.UploadImageButton);
        UploadImageButton.setOnClickListener(UploadImageButtonOnClickListener);

        SendButton = getActivity().findViewById(R.id.SendButton);
        SendButton.setEnabled(false);
        SendButton.setOnClickListener(SendButtonOnClickListener);


        ImageCancelButton = getActivity().findViewById(R.id.ImageCancelButton);
        ImageCancelButton.setOnClickListener(ImageCancelButtonOnClickListener);
        ImageCancelButton.setClickable(false);
        ImageCancelButton.setVisibility(View.INVISIBLE);

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


            AccidentReport accident = new AccidentReport(studentNameTextView.getText().toString(), Description.getText().toString(), CurrentAccident, date, encodedImage);
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

            //get the user out of the activity
            getActivity().onBackPressed();
        }
    }
    public void ImageCancel()
    {
        ImageCancelButton.setClickable(false);
        ImageCancelButton.setVisibility(View.INVISIBLE);
        AccidentImageView.setImageResource(R.mipmap.ic_edit);
        encodedImage = "";
    }
    public void UploadImage()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_TAKE_PICTURE) && resultCode == RESULT_OK) {

            //load the image from the camera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //convert it to a byte array followed by String to store in DB
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] picByteArr = baos.toByteArray();
            encodedImage = Base64.encodeToString(picByteArr, Base64.DEFAULT);

            AccidentImageView.setImageBitmap(imageBitmap);
            ImageCancelButton.setClickable(true);
            ImageCancelButton.setVisibility(View.VISIBLE);
        }
    }
}
