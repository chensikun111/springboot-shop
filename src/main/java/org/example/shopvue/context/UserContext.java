package org.example.shopvue.context;

public class UserContext {
    private static final ThreadLocal<String> tl = new ThreadLocal<>();

    public static String getUserId() {
        return tl.get();
    }
    public static void setUserId(String userId) {
        tl.set(userId);
    }
    public static void clear() {
        tl.remove();
    }
}
