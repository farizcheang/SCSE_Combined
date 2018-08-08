package com.example.tyrone.scse_foc_2018.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.StrictMode;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.entity.TutorialReport;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class TrReportFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private MemberController memberController;


    private String mCurrentPhotoPath = "";

    static final int STATUS_EMPTY = 0;
    static final int STATUS_HALFWAY = 1;
    static final int STATUS_FINISH = 2;

    int CurrentTRStatus = STATUS_EMPTY;

    TextView InfoMessage;
    TextView TakeOverName;
    TextView TakeOverTime;
    TextView HandOverName;
    TextView HandOverTime;

    Button TakeOverButton;
    Button HandOverButton;

    Button UpdateButton;
    Spinner TRSpinner;
    Spinner TimingSpinner;

    String[] TutorialRooms;
    String[] TutorialTimings;

    String CurrentRoom;
    String CurrentTiming;

    int NumImages = 7;
    ImageView Images[];
    ImageView AfterImages[];

    String BeforeImageEncoded = "";
    String AfterImageEncoded = "";

    private View.OnClickListener BeforeImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView view = (ImageView) v;

            //if the iamge is equal to the default, means must take picture
            if(((view.getDrawable().getConstantState() == getResources().getDrawable(R.mipmap.ic_launcher_round).getConstantState())))
            {
                dispatchTakePictureIntent(Integer.parseInt(v.getTag().toString()));
            }
            else if((view.getDrawable().getConstantState() != getResources().getDrawable(R.mipmap.ic_launcher_round).getConstantState()))
            {
                Bitmap bm=((BitmapDrawable)view.getDrawable()).getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] picByteArr = baos.toByteArray();
                final String encodedImage = Base64.encodeToString(picByteArr, Base64.DEFAULT);


                ViewImageFragment viewImageFragment = ViewImageFragment.newInstance(encodedImage);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.fl_contents, viewImageFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    };

    private View.OnClickListener TakeOverButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SubmitTakeOverRequest();
        }
    };
    private View.OnClickListener HandOverButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SubmitHandOverRequest();
        }
    };
    AdapterView.OnItemSelectedListener TRSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            CurrentRoom = TRSpinner.getSelectedItem().toString();

            if(!CurrentRoom.equals("Select TR")) {
                UpdateOnCurrentTrStatus();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    };

    AdapterView.OnItemSelectedListener TimingSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            CurrentTiming = TimingSpinner.getSelectedItem().toString();
            if(!CurrentRoom.equals("Select Time"))
            {
                UpdateOnCurrentTrStatus();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    };
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
        return inflater.inflate(R.layout.fragment_tr_report, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //BeforeImages = new ImageView[NumImages];
        Images = new ImageView[NumImages*2];

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout BeforeImagesGallery = getActivity().findViewById(R.id.BeforeImagesGallery);
        LinearLayout AfterImagesGallery = getActivity().findViewById(R.id.AfterImagesGallery);


        for(int i = 0; i < NumImages; i++)
        {

            View BeforeItemView = inflater.inflate(R.layout.tr_report_item, BeforeImagesGallery, false);
            View AfterItemView = inflater.inflate(R.layout.tr_report_item, AfterImagesGallery, false);

            ImageView BeforeImageView = BeforeItemView.findViewById((R.id.trReportItemImageView));
            BeforeImageView.setImageResource(R.mipmap.ic_launcher_round);
            BeforeImageView.setOnClickListener(BeforeImageOnClickListener);
            BeforeImageView.setTag(i);
            BeforeImageView.setEnabled(false);

            BeforeImagesGallery.addView(BeforeItemView);
            Images[i] = BeforeImageView;


            ImageView AfterImageView = AfterItemView.findViewById((R.id.trReportItemImageView));
            AfterImageView.setImageResource(R.mipmap.ic_launcher_round);
            AfterImageView.setOnClickListener(BeforeImageOnClickListener);
            AfterImageView.setTag(NumImages + i);
            AfterImageView.setEnabled(false);

            AfterImagesGallery.addView(AfterItemView);
            Images[NumImages + i] = AfterImageView;

        }

        InfoMessage = getActivity().findViewById(R.id.infoMessage);

        TakeOverName = getActivity().findViewById(R.id.takeOverNameTextView);
        TakeOverTime = getActivity().findViewById(R.id.takeOverTimeTextView);
        HandOverName = getActivity().findViewById(R.id.handOverNameTextView);
        HandOverTime = getActivity().findViewById(R.id.handOverTimeTextView);

        TakeOverButton = getActivity().findViewById(R.id.TakeOverButton);
        TakeOverButton.setEnabled(false);
        TakeOverButton.setOnClickListener(TakeOverButtonOnClickListener);

        HandOverButton = getActivity().findViewById(R.id.HandOverButton);
        HandOverButton.setEnabled(false);
        HandOverButton.setOnClickListener(HandOverButtonOnClickListener);

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

        TRSpinner.setOnItemSelectedListener(TRSpinnerListener);
        TimingSpinner.setOnItemSelectedListener(TimingSpinnerListener);

        CurrentRoom = TRSpinner.getSelectedItem().toString();
        CurrentTiming = TimingSpinner.getSelectedItem().toString();


    }


    private void UpdateOnCurrentTrStatus()
    {
        //if both the things havent been selected yet den dont search
        if( CurrentRoom.equals("Select TR") || CurrentTiming.equals("Select Time"))
        {
            HandOverButton.setEnabled(false);
            TakeOverButton.setEnabled(false);

        }
        else {
            //check the DB for the currently selected room
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            if ( user != null ) {

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
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

        if(report==null)
            CurrentTRStatus = STATUS_EMPTY;
        else
            CurrentTRStatus = Integer.parseInt(report.getStatus());

        //3 statuses : STATUS_EMPTY, STATUS_HALFWAY, STATUS_FINISH

        if (CurrentTRStatus == STATUS_EMPTY) {
            InfoMessage.setText("No record submitted , take photos by pressing the buttons below");

            //set button onclick to take a before picture and create a new form
            //setButtonClickToTakeBefore();
            TakeOverButton.setEnabled(false);
            HandOverButton.setEnabled(false);

            TakeOverName.setText("Name :");
            TakeOverTime.setText("Time :");
            HandOverName.setText("Name :");
            HandOverTime.setText("Time :");

            setImagesEnableStatus(CurrentTRStatus);
            //update both the before and after images

            for(int i = 0; i < NumImages*2; i++)
                Images[i].setImageResource(R.mipmap.ic_launcher_round);

        }
        else if (CurrentTRStatus == STATUS_HALFWAY)
        {
            InfoMessage.setText("A Take Over report was submitted for this TR and timeslot, please submit the Hand Over report when you are done");

            TakeOverButton.setEnabled(false);
            HandOverButton.setEnabled(false);
            TakeOverName.setText("Name : " + report.getTakeOverName());
            TakeOverTime.setText("Time : " + report.getTakeOverTime());
            HandOverName.setText("Name : ");
            HandOverTime.setText("Time : ");

            setImagesEnableStatus(CurrentTRStatus);

            //update both the before and after images

            byte[] picByteArr;
            Bitmap bitmap;

            report.setEncodedImages();
            for(int i = 0; i < NumImages; i++) {
                picByteArr = Base64.decode(report.getImage(i), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(picByteArr, 0, picByteArr.length);
                Images[i].setImageBitmap(bitmap);

                Images[i + NumImages].setImageResource(R.mipmap.ic_launcher_round);
            }

        }
        else if(CurrentTRStatus == STATUS_FINISH)
        {
            InfoMessage.setText("Report has been submitted for this TR and timeslot");
            TakeOverButton.setEnabled(false);
            HandOverButton.setEnabled(false);
            TakeOverName.setText("Name : " + report.getTakeOverName());
            TakeOverTime.setText("Time : " + report.getTakeOverTime());
            HandOverName.setText("Name : " + report.getHandOverName());
            HandOverTime.setText("Time : " + report.getHandOverTime());

            setImagesEnableStatus(CurrentTRStatus);

            //update both the before and after images
            byte[] picByteArr;
            Bitmap bitmap;

            report.setEncodedImages();
            for(int i = 0; i < NumImages*2; i++) {
                picByteArr = Base64.decode(report.getImage(i), Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(picByteArr, 0, picByteArr.length);
                Images[i].setImageBitmap(bitmap);

            }
        }
    }
    private void setImagesEnableStatus(int TRStatus)
    {
        //enable the takeover ones
        if(TRStatus == STATUS_EMPTY)
        {
            for(int i = 0; i < NumImages; i++) {
                Images[i].setEnabled(true);
            }
            for(int i = NumImages; i < NumImages*2; i++) {
                Images[i].setEnabled(false);
            }
        }
        //enable only the handover ones
        else if(TRStatus == STATUS_HALFWAY || TRStatus == STATUS_FINISH)
        {
            for(int i = 0; i < NumImages*2; i++) {
                Images[i].setEnabled(true);
            }
        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "POSTPHOTO";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent(int imageRequestCode) {
       /* Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        File f = createImageFile();
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        //imageToUploadUri = Uri.fromFile(f);
        String auth = "com.example.tyrone.scse_foc_2018";
        imageToUploadUri = FileProvider.getUriForFile(getActivity(), auth, f);


        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            startActivityForResult(takePictureIntent, imageRequestCode);
        }*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.tyrone.scse_foc_2018",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, imageRequestCode);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            //load the image from the camera
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");

            galleryAddPic();
            setPic(Images[requestCode]);

            if (CurrentTRStatus == STATUS_EMPTY)
                checkIfReadyForTakeOverSubmit();
            else if (CurrentTRStatus == STATUS_HALFWAY)
                checkIfReadyForHandOverSubmit();
            /*if(imageToUploadUri != null) {
                Uri selectedImage = imageToUploadUri;
                getActivity().getContentResolver().notifyChange(selectedImage, null);
                Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());

                //imageview.setImageBitmap(reducedSizeBitmap);
                if (reducedSizeBitmap != null)
                    Images[requestCode].setImageBitmap(reducedSizeBitmap);
                //Images[requestCode].setImageBitmap(imageBitmap);

                if (CurrentTRStatus == STATUS_EMPTY)
                    checkIfReadyForTakeOverSubmit();
                else if (CurrentTRStatus == STATUS_HALFWAY)
                    checkIfReadyForHandOverSubmit();
            }*/

        }
    }
    private void SetReportStatus(int status)
    {

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        FirebaseDatabase.getInstance().getReference("tutorialreport").child(date)
                .child(CurrentRoom).child(CurrentTiming).child("status").setValue("" + status);

    }
    private void checkIfReadyForTakeOverSubmit()
    {
        boolean ready = true;

        for(int i = 0; i < NumImages; i++)
        {
            //if any of the images are the default one, means not ready
            if(Images[i].getDrawable().getConstantState() == getResources().getDrawable(R.mipmap.ic_launcher_round).getConstantState())
            {
                ready = false;
                break;
            }
        }

        //if ready to submit, then enable to button to let people submit
        if(ready)
            TakeOverButton.setEnabled(true);
    }
    private void checkIfReadyForHandOverSubmit()
    {
        boolean ready = true;

        for(int i = NumImages; i < NumImages*2; i++)
        {
            //if any of the images are the default one, means not ready
            if(Images[i].getDrawable().getConstantState() == getResources().getDrawable(R.mipmap.ic_launcher_round).getConstantState())
            {
                ready = false;
                break;
            }
        }

        //if ready to submit, then enable to button to let people submit
        if(ready)
            HandOverButton.setEnabled(true);
    }
    private void SubmitTakeOverRequest()
    {
        //set the status for the current TR and time
        SetReportStatus(STATUS_HALFWAY);

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //update the date and time
        UpdateDateAndTime("takeOverTime", date);

        //update the user who submitted the report
        String name = memberController.currentMember.getName();
        FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).child("takeOverName").setValue(name);


        //prepare variables to help with image uploads
        ByteArrayOutputStream baos;
        byte[] picByteArr;
        String encodedImage;

        //upload all the images
        for(int i = 0; i < NumImages; i++) {

            baos = new ByteArrayOutputStream();
            Bitmap bm=((BitmapDrawable)Images[i].getDrawable()).getBitmap();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            picByteArr = baos.toByteArray();
            encodedImage = Base64.encodeToString(picByteArr, Base64.DEFAULT);

            uploadFile(encodedImage, i, date );
        }

        UpdateOnCurrentTrStatus();
    }
    private void SubmitHandOverRequest()
    {
        //set the status
        SetReportStatus(STATUS_FINISH);


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //update the date and time
        UpdateDateAndTime("handOverTime", date);

        //update the user who submitted the report
        String name = memberController.currentMember.getName();
        FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).child("handOverName").setValue(name);


        ByteArrayOutputStream baos;
        byte[] picByteArr;
        String encodedImage;

        //upload all the images
        for(int i = NumImages; i < NumImages*2; i++) {

            baos = new ByteArrayOutputStream();
            Bitmap bm=((BitmapDrawable)Images[i].getDrawable()).getBitmap();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            picByteArr = baos.toByteArray();
            encodedImage = Base64.encodeToString(picByteArr, Base64.DEFAULT);

            uploadFile(encodedImage, i, date);
        }

        UpdateOnCurrentTrStatus();
    }
    private void uploadFile(String encoded, int ImageNumber, String date) {

        FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).child("image" + ImageNumber).setValue(encoded);

    }
    private void UpdateDateAndTime(String purpose, String date)
    {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
        String sTime = localDateFormat.format(currentDate);

        FirebaseDatabase.getInstance().getReference("tutorialreport").child(date).child(CurrentRoom).child(CurrentTiming).child(purpose).setValue(date + " " + sTime);
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
    public void setPic(ImageView mImageView) {

        //ImageView mImageView = (ImageView) findViewById(R.id.PreviewImage2);
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        String PhotoPath = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "POSTPHOTO.jpg";



        Bitmap bitmap = BitmapFactory.decodeFile(PhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
}
