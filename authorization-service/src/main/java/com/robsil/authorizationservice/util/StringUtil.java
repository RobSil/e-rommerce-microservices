package com.robsil.authorizationservice.util;

public class StringUtil {

    private StringUtil() {}

    public static boolean isNullOrEmpty(String string) {
        if (string == null) {
            return true;
        }

        return string.isEmpty();
    }

}
