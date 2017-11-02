package com.example.jimmykudo.tourgideegy;

/**
 * Created by Jimmy Kudo on 10/15/2017.
 */

public class Place {
    private int imgRes;
    private int strRes;
    private int desRes;

    public Place(int strRes, int desRes,int imgRes) {
        this.imgRes = imgRes;
        this.strRes = strRes;
        this.desRes = desRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public int getStrRes() {
        return strRes;
    }

    public int getDesRes() {
        return desRes;
    }
}
