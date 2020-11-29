package com.example.adginternals;

import android.widget.ImageView;
import android.widget.TextView;

public class card1item {
    int image1;
    int image2;
    String text1,text2,text3,text4;

    public card1item(int image1, int image2, String text1, String text2, String text3, String text4) {
        this.image1 = image1;
        this.image2 = image2;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
    }

    public int getImage1() {
        return image1;
    }

    public int getImage2() {
        return image2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }
}
