package com.example.tyrone.scse_foc_2018.entity;

public class GroupLocation extends Object {
    private String location, time, date;

    public GroupLocation() {
        location="testLocation";
        time="testTime";
        date = "testDate";
    }

    public GroupLocation(String inLocation, String inTime, String inDate)
    {
        location = inLocation;
        time = inTime;
        date = inDate;
    }

    //  Get Values
    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {return date;}
}
