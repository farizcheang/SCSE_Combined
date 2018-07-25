package com.example.tyrone.scse_foc_2018.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
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
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.GroupScore;
import com.example.tyrone.scse_foc_2018.entity.TutorialReport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class TrReportFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;
    FirebaseStorage storage;

    static final int REQUEST_BEFORE_IMAGE = 0;
    static final int REQUEST_AFTER_IMAGE = 1;

    static final int STATUS_EMPTY = 0;
    static final int STATUS_HALFWAY = 1;
    static final int STATUS_FINISH = 2;

    String mCurrentPhotoPath = "";

    SharedPreferences sharedPref;

    TextView InfoMessage;

    Button UpdateButton;
    Spinner TRSpinner;
    Spinner TimingSpinner;

    String[] TutorialRooms;
    String[] TutorialTimings;

    String CurrentRoom;
    String CurrentTiming;

    ImageView BeforeImage;
    ImageView AfterImage;

    String BeforeImageEncoded = "";
    String AfterImageEncoded = "";

    private View.OnClickListener BeforeImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if the encoded image is NOT = "", means got image, THEN do this view image Fragment
            if(!BeforeImageEncoded.equals("")) {
                ViewImageFragment viewImageFragment = ViewImageFragment.newInstance(BeforeImageEncoded);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fl_contents, viewImageFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    };
    private View.OnClickListener AfterImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if the encoded image is NOT = "", means got image, THEN do this view image Fragment
            if(!AfterImageEncoded.equals("")) {

                ViewImageFragment viewImageFragment = ViewImageFragment.newInstance(AfterImageEncoded);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fl_contents, viewImageFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    };
    private View.OnClickListener UpdateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //uploadFile();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tr_report, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        InfoMessage = getActivity().findViewById(R.id.InfoMessage);

        BeforeImage = getActivity().findViewById(R.id.BeforeImage);
        BeforeImage.setOnClickListener(BeforeImageOnClickListener);

        AfterImage = getActivity().findViewById(R.id.AfterImage);
        AfterImage.setOnClickListener(AfterImageOnClickListener);

        UpdateButton = getActivity().findViewById(R.id.UpdateButton);
        UpdateButton.setEnabled(false);

        TRSpinner = getActivity().findViewById(R.id.TRspinner);
        TimingSpinner = getActivity().findViewById(R.id.TimeSpinner);

        TutorialRooms = getResources().getStringArray((R.array.tutorialRooms));
        TutorialTimings = getResources().getStringArray((R.array.tutorialTimings));

        ArrayAdapter<String> RoomAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,TutorialRooms);
        ArrayAdapter<String> TimingAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,TutorialTimings);

        RoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TimingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TRSpinner.setAdapter(RoomAdapter);
        TimingSpinner.setAdapter(TimingAdapter);

        CurrentRoom = TRSpinner.getSelectedItem().toString();
        CurrentTiming = TimingSpinner.getSelectedItem().toString();

        setUpListeners();

    }
    private void setUpListeners()
    {
        //TRSpinner.setOnItemSelectedListener();
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                CurrentRoom = TRSpinner.getSelectedItem().toString();

                if(!CurrentRoom.equals("Select TR")) {
                    checkForAvailability();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        };

        AdapterView.OnItemSelectedListener listener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                CurrentTiming = TimingSpinner.getSelectedItem().toString();
                if(!CurrentRoom.equals("Select Time"))
                {
                    checkForAvailability();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        };

        TRSpinner.setOnItemSelectedListener(listener);
        TimingSpinner.setOnItemSelectedListener(listener2);
    }
    private void uploadFile(String encoded, int BeforeOrAfter) {
        //TODO change date to appropriate one
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String date = "2018-07-21";
        if(BeforeOrAfter == REQUEST_BEFORE_IMAGE)//before
            FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).child("beforeimage").setValue(encoded);
        else//after
            FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).child("afterimage").setValue(encoded);

    }
    private void checkForAvailability()
    {

        //if both the things havent been selected yet den dont search
        if( CurrentRoom.equals("Select TR") || CurrentTiming.equals("Select Time"))
        {
            UpdateButton.setEnabled(false);
            return;
        }
        else {
            //check the DB for the currently selected room
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            if ( user != null ) {
                //TODO change date to appropriate one
                //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String date = "2018-07-21";
                database = FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).getRef();

                database.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        TutorialReport report = dataSnapshot.getValue(TutorialReport.class);
                        SetPicturesAccordingly(report);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //String inStatus = database.child("status").;

            }
        }
    }
    public void SetPicturesAccordingly(TutorialReport report)
    {
        int status = Integer.parseInt(report.getStatus());
        //3 statuses : STATUS_EMPTY, STATUS_HALFWAY, STATUS_FINISH

        //StorageReference storageRef = storage.getReference();

        String key = CurrentRoom + ":" + CurrentTiming;

        if (status == STATUS_EMPTY) {
            InfoMessage.setText("No record submitted");

            //set button onclick to take a before picture and create a new form
            setButtonClickToTakeBefore();
            UpdateButton.setEnabled(true);
            UpdateButton.setText("Submit New Record");
            //update both the before and after images

            BeforeImage.setImageResource(R.mipmap.ic_launcher);
            AfterImage.setImageResource(R.mipmap.ic_launcher);

            BeforeImageEncoded = "";
            AfterImageEncoded = "";
        }
        else if (status == STATUS_HALFWAY)
        {
            InfoMessage.setText("A Before photo was submitted, please submit the After photo when you are done");

            //set button onclick to take after picture and complete the form
            setButtonClickToTakeAfter();
            UpdateButton.setEnabled(true);
            UpdateButton.setText("Submit After photo");

            //update both the before and after images

            byte[] picByteArr = Base64.decode(report.getBeforeimage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(picByteArr, 0, picByteArr.length);

            BeforeImage.setImageBitmap(bitmap);
            AfterImage.setImageResource(R.mipmap.ic_launcher);

            BeforeImageEncoded = report.getBeforeimage();
            AfterImageEncoded = "";

        }
        else if(status == STATUS_FINISH)
        {
            InfoMessage.setText("Record has been submitted");
            UpdateButton.setEnabled(false);
            UpdateButton.setText("Record submitted");

            //update both the before and after images
            byte[] BeforePicByteArr = Base64.decode(report.getBeforeimage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(BeforePicByteArr, 0, BeforePicByteArr.length);

            BeforeImage.setImageBitmap(bitmap);

            byte[] AfterPicByteArr = Base64.decode(report.getAfterimage(), Base64.DEFAULT);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(AfterPicByteArr, 0, AfterPicByteArr.length);

            AfterImage.setImageBitmap(bitmap2);

            BeforeImageEncoded = report.getBeforeimage();
            AfterImageEncoded = report.getAfterimage();
        }
    }
    private void setButtonClickToTakeBefore()
    {
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(UpdateButton, REQUEST_BEFORE_IMAGE);
            }
        });
    }
    private void setButtonClickToTakeAfter()
    {
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(UpdateButton, REQUEST_AFTER_IMAGE);
                //setPic(UpdateButton);
            }
        });
    }

    public void dispatchTakePictureIntent(View view, int request) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
           startActivityForResult(takePictureIntent, request);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_BEFORE_IMAGE || requestCode == REQUEST_AFTER_IMAGE) && resultCode == RESULT_OK) {

            //load the image from the camera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //convert it to a byte array followed by String to store in DB
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] picByteArr = baos.toByteArray();
            final String encodedImage = Base64.encodeToString(picByteArr, Base64.DEFAULT);

            uploadFile(encodedImage, requestCode);

            if(requestCode == REQUEST_BEFORE_IMAGE) {
                SetReportStatus(STATUS_HALFWAY);
                BeforeImage.setImageBitmap(imageBitmap);

            }
            else if(requestCode == REQUEST_AFTER_IMAGE) {
                SetReportStatus(STATUS_FINISH);
                AfterImage.setImageBitmap(imageBitmap);

            }
            else
                Log.i("huge error", "image wasnt set properly");
            //update the page and picture accordingly
            checkForAvailability();

        }
    }
    private void SetReportStatus(int status)
    {
        //TODO change date to appropriate one
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String date = "2018-07-21";

        FirebaseDatabase.getInstance().getReference("tutorialreport").child(date)
                .child(CurrentRoom).child(CurrentTiming).child("status").setValue("" + status);

    }
}
