package com.pitter.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    
    public static String getJwtSecretKey()  {
        Properties configuration = new Properties();
        InputStream inputStream = getPropertiesAsStream("application-oauth.properties");
        try {
            configuration.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration.getProperty("com.pitter.jwtSecretKey");
    }

    public static Long getJwtAccessTokenPeriod() {
        Properties configuration = new Properties();
        InputStream inputStream = getPropertiesAsStream("application-oauth.properties");
        try {
            configuration.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.valueOf(configuration.getProperty("com.pitter.jwtAccessTokenPeriod"));
    }

    public static Long getJwtRefreshTokenPeriod() {
        Properties configuration = new Properties();
        InputStream inputStream = getPropertiesAsStream("application-oauth.properties");
        try {
            configuration.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.valueOf(configuration.getProperty("com.pitter.jwtRefreshTokenPeriod"));
    }

    private static InputStream getPropertiesAsStream(String resourceFileName) {
        return PropertiesUtils.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);
    }
}
