package com.example.tyrone.scse_foc_2018.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.News;

public class NewsAdapter extends ArrayAdapter<News> {

    private String title, author, datetime, content;

    private final Activity context;

    public NewsAdapter(Activity inContext, String inTitle, String inAuthor, String inDateline, String inContent)
    {
        super(inContext, R.layout.news_listview_detail);
        title = inTitle;
        author = inAuthor;
        datetime = inDateline;
        content = inContent;
        context = inContext;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.news_listview_detail, null);
        TextView messageTextView = (TextView) v.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) v.findViewById(R.id.authorTextView);
        TextView dateTextView = (TextView) v.findViewById(R.id.dateTextView);
    }
}
