package org.example.shopvue.utils;

import java.util.UUID;

public class CreateUUID {
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
