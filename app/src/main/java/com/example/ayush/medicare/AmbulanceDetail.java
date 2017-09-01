package com.example.ayush.medicare;


public class AmbulanceDetail {
    String sName;
    String address;
    String number;

    public AmbulanceDetail(String name, String address, String number) {

        this.sName = name;
        this.address = address;
        this.number = number;
    }


    public String getName() {
        return sName;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }
}

