package com.example.tyrone.scse_foc_2018.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.News;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private String title, author, datetime, content;

    ArrayList<News> newsArray = new ArrayList<News>();

    private final Activity context;


    public NewsAdapter(Activity inContext, ArrayList<News> news)
    {
        super(inContext,R.layout.news_listview_detail, news);
        context = inContext;
        newsArray = news;

    }
    @Override
    public boolean isEnabled(int position) {
        return false;
    }
    @Override
    public int getCount() {
        Log.i("getCount size", String.valueOf(newsArray.size()));
        return newsArray.size();
    }

    @Override
    public News getItem(int position) {
        Log.i("getItem position", String.valueOf(position));
        return newsArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i("getItem id", String.valueOf(position));
        return position;
    }


    public void add(News news) {
        Log.i("getAddNews id", String.valueOf(news.getAuthor()));
        newsArray.add(news);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("getView", "test");
        LayoutInflater inflater = context.getLayoutInflater();

        News news = getItem(position);

        if ( view == null ) {
            view = inflater.inflate(R.layout.news_listview_detail, null);
        }
        Log.i("getView index", String.valueOf(position));
        if(position==0) {
            Log.d("getTitle", news.getAuthor());
        }
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) view.findViewById(R.id.authorTextView);
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);

        titleTextView.setText(news.getTitle());
        messageTextView.setText(news.getContent());
        authorTextView.setText(news.getAuthor());
        dateTextView.setText(news.getDate());
        return view;
    }
}
