package com.example.tyrone.scse_foc_2018.entity;

public class AccidentReport extends Object {
    private String student, description, accident, date;

    public AccidentReport() {
        student="testStudent";
        description="testDescription";
        accident="testAccident";
        date="testDate";
    }

    public AccidentReport(String inStudent, String inDescription, String inAccident, String inDate)
    {
        student = inStudent;
        description = inDescription;
        accident = inAccident;
        date = inDate;
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

}
