package com.example.ayush.medicare;

public class EhrDetails {
    String key, value;

    public EhrDetails(String bgroup, String diabetic) {
        this.key = bgroup;
        this.value = diabetic;

    }

    public String mgetKey() {
        return key;
    }

    public String mgetValue() {
        return value;
    }
}
