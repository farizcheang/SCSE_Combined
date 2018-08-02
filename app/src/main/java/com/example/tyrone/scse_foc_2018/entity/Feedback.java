package com.example.tyrone.scse_foc_2018.entity;

public class Feedback extends Object {

    private String uid, focDate, gameRating, welfareRating, hosRating, others;

    public Feedback() {
        focDate="0";
        uid="uid";
        gameRating="0";
        welfareRating="0";
        hosRating="0";
        others="nil";
    }

    public Feedback(String in_uid, String in_focDate, String in_gameRating, String in_welfareRating, String in_hosRating, String in_others)
    {
        focDate = in_focDate;
        uid = in_uid;
        gameRating = in_gameRating;
        welfareRating = in_welfareRating;
        hosRating = in_hosRating;
        others = in_others;

    }

    public String getFocDate() {
        return focDate;
    }

    public String getGameRating() {
        return gameRating;
    }

    public String getUid() {
        return uid;
    }

    public String getWelfareRating() {
        return welfareRating;
    }

    public String getOthers() {
        return others;
    }

    public String getHosRating() {
        return hosRating;
    }
}
