package com.pitter.common.utils;

import org.junit.Test;

import java.util.Date;

import static com.pitter.common.utils.DateUtils.*;
import static com.pitter.common.utils.DateUtils.now;
import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void date() throws Exception {
        //given
        System.out.println(oneMonthBefore());
    }

    @Test
    public void utkl() throws Exception {
        //given
        System.out.println(new Date());
        System.out.println(now());
        //when
        //then
        //
        assertEquals(new Date(), now());
        }
}