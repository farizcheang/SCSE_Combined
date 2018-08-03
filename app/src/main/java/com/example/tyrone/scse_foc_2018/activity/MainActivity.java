package com.example.tyrone.scse_foc_2018.activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.controller.NewsController;
import com.example.tyrone.scse_foc_2018.fragment.AccountFragment;
import com.example.tyrone.scse_foc_2018.fragment.ApproveScoreFragment;
import com.example.tyrone.scse_foc_2018.fragment.CreateAccidentReportFragment;
import com.example.tyrone.scse_foc_2018.fragment.FeedbackFragment;
import com.example.tyrone.scse_foc_2018.fragment.LocationCheckInFragment;
import com.example.tyrone.scse_foc_2018.fragment.NewsFragment;
import com.example.tyrone.scse_foc_2018.fragment.TrReportFragment;
import com.example.tyrone.scse_foc_2018.fragment.UpdateNewsFragment;
import com.example.tyrone.scse_foc_2018.fragment.UpdateScoreFragment;
import com.example.tyrone.scse_foc_2018.fragment.ViewAccidentReportFragment;
import com.example.tyrone.scse_foc_2018.fragment.ViewOGLocationFragment;
import com.example.tyrone.scse_foc_2018.fragment.ViewScoreFragment;

import android.widget.Button;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Tyrone on 6/2/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public DrawerLayout mDrawerLayout;
    public Toolbar toolbar;

    //  Controller
    private FragmentManager fragmentManager;
    private NewsController newsController;
    private MemberController memberController;

    //  Fragment
    Fragment currentFragment;
    private NewsFragment newsFragment;
    private UpdateNewsFragment updateNewsFragment;

    private ViewScoreFragment scoreFragment;
    private UpdateScoreFragment updateScoreFragment;
    private ApproveScoreFragment approveScoreFragment;

    private TrReportFragment trReportFragment;
    private FeedbackFragment feedbackFragment;

    private CreateAccidentReportFragment createAccidentReportFragment;
    private ViewAccidentReportFragment viewAccidentReportFragment;

    private AccountFragment accountFragment;
    private LocationCheckInFragment locationCheckInFragment;
    private ViewOGLocationFragment viewOGlocationFragment;

    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsFragment = new NewsFragment();
        updateNewsFragment = new UpdateNewsFragment();
        scoreFragment = new ViewScoreFragment();
        updateScoreFragment = new UpdateScoreFragment();
        approveScoreFragment = new ApproveScoreFragment();
        trReportFragment = new TrReportFragment();
        createAccidentReportFragment = new CreateAccidentReportFragment();
        viewAccidentReportFragment = new ViewAccidentReportFragment();
        accountFragment = new AccountFragment();
        locationCheckInFragment = new LocationCheckInFragment();
        viewOGlocationFragment = new ViewOGLocationFragment();
        feedbackFragment = new FeedbackFragment();

        memberController = new MemberController();

        memberController.retrieveMemberRecord();

        initToolBar();
        initDrawer();

        //  Check if it is null
        if ( savedInstanceState == null ) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fl_contents,newsFragment).commit();
            currentFragment = newsFragment;
        }

    }

    public void initDrawer() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        String SelectItem = item.toString();
                        switch (SelectItem) {
                            case "Updates" :
                                ft.replace(R.id.fl_contents,newsFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Add News" :
                                ft.replace(R.id.fl_contents,updateNewsFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Score" :
                                ft.replace(R.id.fl_contents,scoreFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Add Score for Prog" :
                                ft.replace(R.id.fl_contents,updateScoreFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Approve Score for Chief Prog" :
                                ft.replace(R.id.fl_contents,approveScoreFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "TR report" :
                                ft.replace(R.id.fl_contents,trReportFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Accident report" :
                                ft.replace(R.id.fl_contents,createAccidentReportFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "View Accident report" :
                                ft.replace(R.id.fl_contents,viewAccidentReportFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Account" :
                                ft.replace(R.id.fl_contents,accountFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Location Check In" :
                                ft.replace(R.id.fl_contents,locationCheckInFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "View OG Location" :
                                ft.replace(R.id.fl_contents,viewOGlocationFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Feedback" :
                                ft.replace(R.id.fl_contents, feedbackFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "Logout"  :
                                //delete shared pref stuff
                                doLogOut();

                                //change the page
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;

                        }
                        Log.i("MenuItem",item.toString());
                        return true;
                    }
                }
        );
    }

    public void initToolBar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.titlebar_news);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //Toast.makeText(LoginActivity.this,"clicking Toolbar");
                        mDrawerLayout.openDrawer(Gravity.START);

                        //this line caused an error
                        //java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String com.example.tyrone.scse_foc_2018.entity.Member.getGroup()' on a null object reference
                        //Log.i("Group Name:",memberController.currentMember.getGroup());
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
    }

    public void doLogOut()
    {
        //delete the shared pref stuff
        SharedPreferences sharedPref = this.getSharedPreferences("LoginInformation", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        //clear everything
        editor.clear();
        editor.commit();

    }
    protected void setupToolbar() {

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });*/
    }

    private void setupMenu() {
        //FragmentManager fm = getFragmentManager();

        // Kept for debugging
//        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
//            @Override
//            public void onDrawerStateChange(int oldState, int newState) {
//                if (newState == ElasticDrawer.STATE_CLOSED) {
//                    Log.i("MainActivity", "Drawer STATE_CLOSED");
//                }
//            }
//
//            @Override
//            public void onDrawerSlide(float openRatio, int offsetPixels) {
//                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
//            }
//        });
    }

    /*@Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }*/

    /*@Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    protected void onPause() {
        super.onPause();
    }*/
}