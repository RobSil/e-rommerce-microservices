package com.robsil.merchantservice.util;

public class TokenGenerator {

    private TokenGenerator() {}

    public static String generate() {
        return StringUtil.randomAlphanumeric(64);
    }

}
