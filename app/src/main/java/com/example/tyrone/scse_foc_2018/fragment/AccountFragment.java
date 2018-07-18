package com.example.tyrone.scse_foc_2018.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.activity.LoginActivity;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.entity.Member;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountFragment extends Fragment {

    private ImageView iv_userProfilePic;
    private TextView tv_userName;
    private TextView tv_userMobileNo;
    private TextView tv_userEmail;
    private FloatingActionButton btn_camera;
    private FloatingActionButton btn_editAccount;

    private byte[] profilepicByteArr;
    private Bitmap profilepic;

    final MemberController memberController = new MemberController();

    private Member member;

    private final String TAG = "ACCOUNT_TAG";

    public AccountFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);

        iv_userProfilePic = v.findViewById(R.id.ivMenuUserProfilePhoto);
        tv_userName = v.findViewById(R.id.tv_userName);
        tv_userMobileNo = v.findViewById(R.id.tv_userMobileNo);
        tv_userEmail = v.findViewById(R.id.tv_userEmail);
        btn_camera = v.findViewById(R.id.btn_camera);
        btn_editAccount = v.findViewById(R.id.btn_editAccount);

        registerForContextMenu(btn_camera); //display menu: open camera, open gallery.

        memberController.retrieveMemberRecord(this);

        btn_camera.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getActivity().closeContextMenu();
                return false;
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().openContextMenu(v);
            }
        });

        btn_editAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //directs to Edit Account Fragment
                /*Fragment fragment = new EditAccountFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contents, fragment);
                ft.commit();*/
            }
        });

        return v;
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.camera_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_takephoto:
                Fragment fragment = new CameraviewFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contents, fragment);
                ft.commit();
                return true;
            case R.id.menu_deletephoto:
                member.setAvatar(null);
                if(memberController.updateMemberRecord(member)){
                    //successful update
                    //update imageview to be default img
                    profilepic = BitmapFactory.decodeResource(getResources(), R.drawable.temp_shop);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    profilepic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    profilepicByteArr = baos.toByteArray();
                    final String encodedImage = Base64.encodeToString(profilepicByteArr, Base64.DEFAULT);
                    profilepicByteArr = Base64.decode(encodedImage, Base64.DEFAULT);

                    try {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String filename = "IMG_" + timeStamp + ".jpg";
                        File file = new File(getContext().getCacheDir(), filename);
                        file.createNewFile();

                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(profilepicByteArr);
                        fos.flush();
                        fos.close();

                        //display profile pic as a circle
                        com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                                .cornerRadiusDp(50)
                                .oval(false)
                                .build();

                        Picasso.with(iv_userProfilePic.getContext())
                                .load(file)
                                .fit()
                                .transform(transformation)
                                .into(iv_userProfilePic);

                    } catch (IOException ex) {
                        Log.e(TAG, ex.getMessage());
                    }

                    Log.d(TAG, "updateMemberRecord:success");
                    Toast.makeText(getActivity(), "Deleted photo successfully!",
                            Toast.LENGTH_SHORT).show();

                }else{
                    //update failed
                    Log.d(TAG, "updateMemberRecord:failure");
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.account_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_logout){

            new AlertDialog.Builder(this.getActivity())
                    .setTitle(R.string.logout)
                    .setMessage(R.string.logout_message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //handles logout
                            if(memberController.logout()) {
                                Toast.makeText(getActivity(), "You have logged out successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getActivity(), "Logout failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onGetDataSuccess(DataSnapshot data) {

        member = data.getValue(Member.class);

        if(member != null){
            tv_userName.setText(member.getName());
            tv_userMobileNo.setText(Integer.toString(member.getMobileNo()).substring(0,4) + " " + Integer.toString(member.getMobileNo()).substring(4));
            tv_userEmail.setText(member.getEmail());

            if(member.getAvatar() != null) {
                //get avatar string from member obj and convert into bytearray to be set as bg img in imageview
                profilepicByteArr = Base64.decode(member.getAvatar(), Base64.DEFAULT);
            }else{
                profilepic = BitmapFactory.decodeResource(getResources(), R.drawable.temp_shop);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                profilepic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                profilepicByteArr = baos.toByteArray();
                final String encodedImage = Base64.encodeToString(profilepicByteArr, Base64.DEFAULT);
                profilepicByteArr = Base64.decode(encodedImage, Base64.DEFAULT);
            }
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String filename = "IMG_" + timeStamp + ".jpg";
                File file = new File(getContext().getCacheDir(), filename);
                file.createNewFile();

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(profilepicByteArr);
                fos.flush();
                fos.close();

                //display profile pic as a circle
                com.squareup.picasso.Transformation transformation = new RoundedTransformationBuilder()
                        .cornerRadiusDp(50)
                        .oval(false)
                        .build();

                Picasso.with(iv_userProfilePic.getContext())
                        .load(file)
                        .fit()
                        .transform(transformation)
                        .into(iv_userProfilePic);

            } catch (IOException ex) {
                Log.e(TAG, ex.getMessage());
            }

            Log.d(TAG, "retrieveMemberRecord:success");
        }else{
            Log.d(TAG, "retrieveMemberRecord:failure");
        }
    }

    @Override
    public void onGetDataFailed(DatabaseError databaseError) {
        Log.e("AccountFragment:DBError", databaseError.toString());
    }*/
}
