package com.example.tyrone.scse_foc_2018.entity;

import android.widget.ImageView;

public class TutorialReport extends Object {
    private String status;
    private String takeOverName;
    private String takeOverTime;
    private String handOverName;
    private String handOverTime;

    private String[] EncodedImages;

    private String image0;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;
    private String image10;
    private String image11;
    private String image12;
    private String image13;


    public TutorialReport() {
        status = "0";
        takeOverName = "";
        takeOverTime = "";
        handOverName = "";
        handOverTime = "";

        image0 = "";
        image1 = "";
        image2 = "";
        image3 = "";
        image4 = "";
        image5 = "";
        image6 = "";
        image7 = "";
        image8 = "";
        image9 = "";
        image10 = "";
        image11 = "";
        image12 = "";
        image13 = "";

        EncodedImages = new String[14];
        for(int i = 0; i < 14; i++)
            EncodedImages[i] = "";

        EncodedImages[0] = image0;
        EncodedImages[1] = image1;
        EncodedImages[2] = image2;
        EncodedImages[3] = image3;
        EncodedImages[4] = image4;
        EncodedImages[5] = image5;
        EncodedImages[6] = image6;
        EncodedImages[7] = image7;
        EncodedImages[8] = image8;
        EncodedImages[9] = image9;
        EncodedImages[10] = image10;
        EncodedImages[11] = image11;
        EncodedImages[12] = image12;
        EncodedImages[13] = image13;

    }

    //  Get Values
    public String getStatus() {
        return status;
    }

    public String getTakeOverName() {
        return takeOverName;
    }

    public String getTakeOverTime() {
        return takeOverTime;
    }

    public String getHandOverName() {
        return handOverName;
    }

    public String getHandOverTime() {
        return handOverTime;
    }

    public String getImage(int i) {return EncodedImages[i];}

    public void setEncodedImages()
    {
        EncodedImages[0] = image0;
        EncodedImages[1] = image1;
        EncodedImages[2] = image2;
        EncodedImages[3] = image3;
        EncodedImages[4] = image4;
        EncodedImages[5] = image5;
        EncodedImages[6] = image6;
        EncodedImages[7] = image7;
        EncodedImages[8] = image8;
        EncodedImages[9] = image9;
        EncodedImages[10] = image10;
        EncodedImages[11] = image11;
        EncodedImages[12] = image12;
        EncodedImages[13] = image13;
    }
    public String getImage0() {return image0;}
    public String getImage1() {return image1;}
    public String getImage2() {return image2;}
    public String getImage3() {return image3;}
    public String getImage4() {return image4;}
    public String getImage5() {return image5;}
    public String getImage6() {return image6;}
    public String getImage7() {return image7;}
    public String getImage8() {return image8;}
    public String getImage9() {return image9;}
    public String getImage10() {return image10;}
    public String getImage11() {return image11;}
    public String getImage12() {return image12;}
    public String getImage13() {return image13;}


}
