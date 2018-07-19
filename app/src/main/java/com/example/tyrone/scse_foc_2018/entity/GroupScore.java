package com.example.tyrone.scse_foc_2018.entity;

public class GroupScore extends Object {
    private String score;
    private String image;

    public GroupScore()
    {
        score = "0";
        image = "";
    }
    public GroupScore(String inScore, String inImage)
    {
        score = inScore;
        image = inImage;
    }
    public String getScore(){return score;};
    public String getImage(){return image;};

}
