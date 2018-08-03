package com.example.tyrone.scse_foc_2018.entity;

public class ScoreUpdateRequest extends Object {
    private String score;
    private String description;
    private String requester;
    private String group;

    public ScoreUpdateRequest()
    {
        score = "0";
        description = "";
        requester = "";
        group = "";
    }
    public ScoreUpdateRequest(String inDescription, String inRequester, String inScore, String inGroup)
    {
        score = inScore;
        description = inDescription;
        requester = inRequester;
        group = inGroup;
    }
    public String getScore(){return score;};
    public String getDescription(){return description;};
    public String getRequester(){return requester;};
    public String getGroup(){return group;};
}
