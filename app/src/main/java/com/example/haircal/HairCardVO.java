package com.example.haircal;

import java.util.Arrays;

public class HairCardVO {
    private String[] img;
    private String hairShop;
    private String designer;
    private int pirce;
    private String date;
    private String comment;


    public HairCardVO() {
    }

    public HairCardVO(String hairShop, String designer, int pirce, String date, String comment) {
        this.hairShop = hairShop;
        this.designer = designer;
        this.pirce = pirce;
        this.date = date;
        this.comment = comment;
    }

    public HairCardVO(String[] img, String hairShop, String designer, int pirce, String date, String comment) {
        this.img = img;
        this.hairShop = hairShop;
        this.designer = designer;
        this.pirce = pirce;
        this.date = date;
        this.comment = comment;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getHairShop() {
        return hairShop;
    }

    public void setHairShop(String hairShop) {
        this.hairShop = hairShop;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public int getPirce() {
        return pirce;
    }

    public void setPirce(int pirce) {
        this.pirce = pirce;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "HairCardVO{" +
                "img=" + Arrays.toString(img) +
                ", hairShop='" + hairShop + '\'' +
                ", designer='" + designer + '\'' +
                ", pirce=" + pirce +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
