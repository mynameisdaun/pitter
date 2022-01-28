package com.pitter.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date now() {
        return new Date();
    }

    public static Date oneMonthBefore() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        return calendar.getTime();
    }
}
