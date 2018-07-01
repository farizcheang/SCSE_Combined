package com.example.tyrone.scse_foc_2018.entity;

public class News {
    private String title, author, datetime, content;

    public News(String inTitle, String inAuthor, String inDateline, String inContext)
    {
        title = inTitle;
        author = inAuthor;
        datetime = inDateline;
        content = inContext;

    }
    //  Get Values
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getContent() {
        return content;
    }

    //  Set Values
}
