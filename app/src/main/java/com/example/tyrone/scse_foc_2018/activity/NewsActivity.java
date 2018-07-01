package com.example.tyrone.scse_foc_2018.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.controller.ItemAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    public DrawerLayout mDrawerLayout;
    public Toolbar toolbar;


    String KeyMessage =  "Message No ";
    String KeyCurrentMessageIndex = "key";
    int CurrentMessageIndex = 0;

    ListView myListView;
    String[] messages;

    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initToolBar();
        initDrawer();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        CurrentMessageIndex = sharedPref.getInt(KeyCurrentMessageIndex, 0);

        myListView = (ListView) findViewById(R.id.BroadcastListView);

        itemAdapter = new ItemAdapter(this);

        StringTokenizer tokens;
        if(CurrentMessageIndex > 0) {
            for (int i = 1; i < CurrentMessageIndex+1; i++) {

                Log.i("asd", "reading message index " + CurrentMessageIndex);
                String Message = sharedPref.getString(KeyMessage + i, "default");

                tokens = new StringTokenizer(Message, ":");
                String author = tokens.nextToken();// take out the author
                String date = tokens.nextToken(); // take out the date
                String message = tokens.nextToken(); //take out the message

                itemAdapter.AddBroadcastMessage(message, author, date);
            }
        }
        myListView.setAdapter(itemAdapter);
    }

    public void initDrawer() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
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

    public void BroadcastMessage(View view)
    {
        Log.i("asd", "coming into broadcastmessage");
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //EditText editText = (EditText) findViewById(R.id.BroadcastMessageInput);

        //String message = editText.getText().toString();
        String author = "tyrone";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //itemAdapter.AddBroadcastMessage(message, author, date);

        CurrentMessageIndex++;
        //add the message to the data storage along with the message index
        //editor.putString(KeyMessage + CurrentMessageIndex, author + ":" + date + ":" + message);
        //editor.putInt(KeyCurrentMessageIndex, CurrentMessageIndex);


        Log.i("asd", "putting key is = " + KeyMessage + CurrentMessageIndex);

        editor.commit();
        Refresh(view);
    }

    public void Refresh(View view)
    {
        Log.i("asd", "coming into the refresh");

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        itemAdapter.notifyDataSetChanged();

        String message = "";

        if(CurrentMessageIndex > 0)
            message = sharedPref.getString(KeyMessage + CurrentMessageIndex, "default");


        Log.i("asd", "getting key is = " + KeyMessage + CurrentMessageIndex);

        //TextView preview = (TextView) findViewById(R.id.message1);
        //preview.setText(CurrentMessageIndex);
    }
    public void Reset(View view)
    {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for(int i = 0; i < 10; i++)
            sharedPref.edit().remove(KeyMessage + i).commit();

        CurrentMessageIndex = 0;
        Log.i("clear", "CLEARING EVERYTHING");
        editor.clear();
        Log.i("clear", "value of key BEFORE putting in is " + sharedPref.getInt(KeyCurrentMessageIndex, 0));
        Log.i("clear", "and message index is " + CurrentMessageIndex);
        editor.putInt(KeyCurrentMessageIndex, CurrentMessageIndex);
        editor.commit();
        itemAdapter.clear();

        Log.i("clear", "value of key after clear is " + sharedPref.getInt(KeyCurrentMessageIndex, 0));

    }
}

