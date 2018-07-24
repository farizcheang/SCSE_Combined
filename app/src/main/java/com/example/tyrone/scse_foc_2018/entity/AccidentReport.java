package com.example.tyrone.scse_foc_2018.entity;

public class AccidentReport extends Object {
    private String student, description, accident, date, image;

    public AccidentReport() {
        student="testStudent";
        description="testDescription";
        accident="testAccident";
        date="testDate";
        image = "";
    }

    public AccidentReport(String inStudent, String inDescription, String inAccident, String inDate, String inImage)
    {
        student = inStudent;
        description = inDescription;
        accident = inAccident;
        date = inDate;
        image = inImage;
    }

    //  Get Values
    public String getStudent() {
        return student;
    }

    public String getDescription() {
        return description;
    }

    public String getAccident() {
        return accident;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

}
