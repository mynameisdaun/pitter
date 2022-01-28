package com.pitter.common.utils;

public class StringUtils {

    public static void checkNullOrEmpty(String str, String msg) {
        if(str==null || str.isEmpty() || str.equals(" ")) {
            throw new IllegalArgumentException(msg);
        }
    }

}
