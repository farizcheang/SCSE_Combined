package com.example.tyrone.scse_foc_2018.entity;

public class TutorialReport extends Object {
    private String status, beforeimage, afterimage;

    public TutorialReport() {
        status = "0";
        beforeimage = "";
        afterimage = "";

    }

    //  Get Values
    public String getStatus() {
        return status;
    }

    public String getBeforeimage() {
        return beforeimage;
    }

    public String getAfterimage() {
        return afterimage;
    }


}
