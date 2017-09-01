package com.example.ayush.medicare;

public class DoctorsDetails {
    String dname, degree, haddress, hname, rating, yoexp, special;

    public DoctorsDetails(String name, String degree, String hname, String haddress, String rating, String yoexp, String special) {
        dname = name;
        this.degree = degree;
        this.hname = hname;
        this.haddress = haddress;
        this.rating = rating;
        this.yoexp = yoexp;
        this.special = special;
    }

    String getDocName() {
        return dname;
    }

    String getDocdegree() {
        return degree;
    }

    String getDochaddress() {
        return haddress;
    }

    String getDochname() {
        return hname;
    }

    String getDocrating() {
        return rating;
    }

    String getDocyoexp() {
        return yoexp;
    }

    String getDocSepcial() {
        return special;
    }


}
