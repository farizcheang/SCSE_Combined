package com.example.tyrone.scse_foc_2018.entity;

public class Feedback extends Object {

    private String uid, focDate, venue, service, reception, activities,duration,overall,feedback;

    public Feedback() {
        focDate="0";
        uid="uid";
        venue="0";
        service="0";
        reception="0";
        activities="0";
        duration="0";
        overall="0";
        feedback="nil";
    }

    public Feedback(String in_uid, String in_focDate, String in_venue, String in_service, String in_reception, String in_activities, String in_duration, String in_overall, String in_feedback)
    {
        focDate = in_focDate;
        uid = in_uid;
        venue=in_venue;
        service=in_service;
        reception=in_reception;
        activities=in_activities;
        duration=in_duration;
        overall=in_overall;
        feedback=in_feedback;


    }

    public String getFocDate() {
        return focDate;
    }

    public String getUid() { return uid; }
    public String getVenue() {
        return venue;
    }
    public String getService() {
        return service;
    }
    public String getReception() {
        return reception;
    }
    public String getActivities() {
        return activities;
    }
    public String getDuration() {
        return duration;
    }
    public String getOverall() {
        return overall;
    }
    public String getFeedback() {
        return feedback;
    }
}
