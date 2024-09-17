package org.example.shopvue.utils;

import org.example.shopvue.model.Power;

public class IsManger {
    public static boolean isManger(Power power) {
        if(power == null){
            return false;
        }
        if(power.getRole().equals("manager")){
            return true;
        }else{
            return false;
        }
    }
}
