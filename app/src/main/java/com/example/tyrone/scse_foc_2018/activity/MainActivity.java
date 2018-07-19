package com.example.tyrone.scse_foc_2018.activity;
import android.app.ProgressDialog;
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
import com.example.tyrone.scse_foc_2018.fragment.NewsFragment;
import com.example.tyrone.scse_foc_2018.fragment.UpdateNewsFragment;
import com.example.tyrone.scse_foc_2018.fragment.UpdateScoreFragment;
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
    private NewsFragment newsFragment;
    private UpdateNewsFragment updateNewsFragment;

    private ViewScoreFragment scoreFragment;
    private UpdateScoreFragment updateScoreFragment;

    private AccountFragment accountFragment;

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
        accountFragment = new AccountFragment();

        initToolBar();
        initDrawer();

        //  Check if it is null
        if ( savedInstanceState == null ) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fl_contents,newsFragment).commit();
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
                            case "Account" :
                                ft.replace(R.id.fl_contents,accountFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;


                                /*
                                <string name="Updates">Updates</string>
    <string name="Add_Updates">Add Updates</string>
    <string name="View_Score">Analyse</string>
    <string name="Add_Score">New Listing</string>
    <string name="Account">Account</string>
    <string name="Chat">Chats</string>
    <string name="TrReport">Tr Report</string>
    <string name="Logout">Logout</string>
                                 */
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
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
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