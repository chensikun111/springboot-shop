package org.example.shopvue.utils;

public class AddOrderId {

    public static String AddOrderId(int num) {
        Long orderId;
        orderId = 1000000000L+num;
        String id = String.valueOf(orderId);
        return id;
    }
}
