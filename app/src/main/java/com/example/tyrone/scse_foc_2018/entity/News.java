package com.example.tyrone.scse_foc_2018.entity;

public class News extends Object{
    private String title, author, date, time, content;

    public News() {
        title="testTitle";
        author="testAuthor";
        date="testDate";
        time="testTime";
        content="testContent";
    }

    public News(String inTitle, String inAuthor, String inDate, String inTime, String inContext)
    {
        title = inTitle;
        author = inAuthor;
        date = inDate;
        time = inTime;
        content = inContext;

    }
    //  Get Values
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    //  Set Values
}
