package com.example.tyrone.scse_foc_2018.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.adapter.NewsAdapter;
import com.example.tyrone.scse_foc_2018.controller.MemberController;
import com.example.tyrone.scse_foc_2018.controller.NewsController;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewOGNewsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    MemberController memberController;
    ListView listView;

    public final NewsController newsController = new NewsController();
    private ArrayList<News> newsArrayList = new ArrayList<News>();
    NewsAdapter newsAdapter;


    //the part that refrehses the page every second
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* do what you need to do */
            RetrieveNews();
            /* and here comes the "trick" */
            handler.postDelayed(this, 1000);
        }
    };
    public ViewOGNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsArrayList.clear();
        newsController.retrieveNews(this);
        memberController = new MemberController();
        memberController.retrieveMemberRecord();

        handler.postDelayed(runnable, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_view_ognews, container, false);
        View view = inflater.inflate(R.layout.activity_news,container,false);
        listView = (ListView) view.findViewById(R.id.frag_list);

        newsAdapter = new NewsAdapter(getActivity(),newsArrayList);
        //Log.i("onGetDataSuccessNull", String.valueOf(this.listView.getAdapter().getCount()));

        listView.setAdapter(newsAdapter);

        Log.i("onGetView", String.valueOf(this.newsArrayList.size()));
        return view;
    }
    public void onGetDataSuccess (DataSnapshot data) {

        newsArrayList.clear();
        for ( DataSnapshot news :  data.getChildren() ) {
            //News news = new News();

            newsArrayList.add(0, news.getValue(News.class));

        }

        //Log.i("onGetDataSuccessChange", String.valueOf(this.listView.getAdapter().getCount()));
        newsAdapter.notifyDataSetChanged();


    }
    void RetrieveNews()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if ( user != null ) {
            database = FirebaseDatabase.getInstance().getReference("OGnews").child(memberController.currentMember.getGroup());
            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    onGetDataSuccess(dataSnapshot);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
