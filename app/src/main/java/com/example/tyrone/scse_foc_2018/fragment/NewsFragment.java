package com.example.tyrone.scse_foc_2018.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.adapter.NewsAdapter;
import com.example.tyrone.scse_foc_2018.controller.NewsController;
import com.example.tyrone.scse_foc_2018.entity.News;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    ListView listView;
    final NewsController newsController = new NewsController();
    final ArrayList<News> newsArrayList = new ArrayList<News>();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_news,container,false);
        newsController.retrieveNews(this);
        return view;
    }


    public void onGetDataSuccess (DataSnapshot data) {
        for ( DataSnapshot news :  data.getChildren() ) {
            newsArrayList.add(news.getValue(News.class));
        }
        listView.setAdapter(NewsAdapter);

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*View view = inflater.inflate(R.layout.fragment_my_listing, container, false);
        listView = view.findViewById(R.id.frag_list);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //Fragment fragTool = new detailedListing();
                //Fragment fragment = new detailedListingFragment();
                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                // ft.replace(R.id.toolbar,fragTool);
                //ft.replace (R.id.fl_contents, fragment);
                //ft.commit();


            }

        });

        //Log.d(("ADAPTER_TAG"), Integer.toString(adapter.getCount()));
        //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.fragment_ndl, title);
        //listview.setAdapter(adapter2);

        return view;
    }*/


}
