package com.adgvit.internals.Model;

public class Card1Item {
    int image1;
    int image2;
    String text1,text2;

    public Card1Item(int image1, int image2, String text1, String text2) {
        this.image1 = image1;
        this.image2 = image2;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
