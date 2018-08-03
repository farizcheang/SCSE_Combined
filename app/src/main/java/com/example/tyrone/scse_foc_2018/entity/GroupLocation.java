package com.example.tyrone.scse_foc_2018.entity;

public class GroupLocation extends Object {
    private String location, time;

    public GroupLocation() {
        location="testLocation";
        time="testTime";
    }

    public GroupLocation(String inLocation, String inTime, String inAccident, String inDate, String inImage)
    {
        location = inLocation;
        time = inTime;
    }

    //  Get Values
    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

}
