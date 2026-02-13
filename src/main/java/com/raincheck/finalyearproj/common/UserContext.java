package com.raincheck.finalyearproj.common;

public class UserContext {
    private static final ThreadLocal<String> TL_USER = new ThreadLocal<>();

    public static void setUser(String email) {
        TL_USER.set(email);
    }

    public static String getUser() {
        return TL_USER.get();
    }

    public static void removeUser() {
        TL_USER.remove(); // 必须手动释放，防止内存泄漏
    }
}
