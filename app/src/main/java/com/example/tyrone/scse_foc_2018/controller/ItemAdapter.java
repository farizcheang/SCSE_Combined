package com.example.tyrone.scse_foc_2018.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tyrone.scse_foc_2018.R;
import com.example.tyrone.scse_foc_2018.entity.News;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {


    LayoutInflater mInflater;
    ArrayList<News> BroadcastedNews;

    public ItemAdapter(Context c)
    {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        BroadcastedNews = new ArrayList<News>();
    }
    @Override
    public int getCount() {
        return BroadcastedNews.size();
    }

    @Override
    public Object getItem(int i) {
        //return items.get(i);
        return (BroadcastedNews.size() - i - 1);
    }
    public void AddBroadcastMessage(String message, String author, String date)
    {
        News news = new News("title", author, date, message);
        BroadcastedNews.add(0,news);
    }
    public void clear()
    {
        BroadcastedNews.clear();
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View View, ViewGroup viewGroup) {

        View v = mInflater.inflate(R.layout.news_listview_detail, null);
        TextView messageTextView = (TextView) v.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) v.findViewById(R.id.authorTextView);
        TextView dateTextView = (TextView) v.findViewById(R.id.dateTextView);

        String message = BroadcastedNews.get(i).getContent();
        String author = BroadcastedNews.get(i).getAuthor();
        String date = BroadcastedNews.get(i).getDatetime();

        messageTextView.setText(message);
        authorTextView.setText(author);
        dateTextView.setText(date);

        return v;
    }
}