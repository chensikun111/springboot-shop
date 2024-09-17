package org.example.shopvue.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CreateShopid {
    public static String createShopid(List<String> list) {
        Set<String> set = new HashSet<>(list);
        String shopid = RandomFiveDigitString();
        Boolean repeat = true;
        while (repeat) {
            if (set.contains(shopid)) {
                shopid = RandomFiveDigitString();
            }else {
                repeat = false;
            }
        }
        return shopid;
    }

    //生成五位整数作为shopid
    public static String RandomFiveDigitString(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }
}
