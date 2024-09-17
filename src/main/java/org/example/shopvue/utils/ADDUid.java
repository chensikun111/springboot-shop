package org.example.shopvue.utils;

public class ADDUid {
    public String addUid(int num) {
        int id;
        id = 10000000+num;
        String Uid = Integer.toString(id);
        return Uid;
    }
}
