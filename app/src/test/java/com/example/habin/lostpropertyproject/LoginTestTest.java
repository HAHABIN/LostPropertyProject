package com.example.habin.lostpropertyproject;

import com.example.habin.lostpropertyproject.Util.JsonUtil;
import com.example.habin.lostpropertyproject.Util.StringUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Create by HABIN on 2019/11/621:14
 * Email:739115041@qq.com
 */

public class LoginTestTest {
    @Test
    public void test() {
        String s = StringUtils.stampToDate("1581783612000");
        System.out.println(s);
        String str = "2019-03-13 13:54:00";
        long l = StringUtils.dateToStamp(s);
        System.out.println(l);
        Calendar instance = Calendar.getInstance();
        System.out.println(instance.toString());
    }
}