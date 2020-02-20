package com.example.habin.lostpropertyproject.Util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;

import com.example.habin.lostpropertyproject.Bean.entity.ArticleType;
import com.example.habin.lostpropertyproject.MyApplication;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouas666 on 2017/12/12.
 */
public class StringUtils {

    /**
     * encodeBase64File:(将图片文件转成base64 字符串).
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64Photo(String path) throws Exception {
        Bitmap photo = UiUtils.CompressBitmap(path);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] buffer = stream.toByteArray();
        stream.close();
        photo.recycle();
        return android.util.Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 验证邮箱的正则表达式
     * @param email
     * @return
     */
    public static boolean checkEmail(String email)
    {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
     * @param mobileNumber
     * @return
     */
    public static boolean checkPhoneNumber(String mobileNumber) {
        boolean flag = false;
        if (mobileNumber.length()==0){
            return false;
        }
        try {
            // Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;

        }
        return flag;
    }

    /**
     * 随机返回颜色Color十六进制字符串
     * @return
     */
    public static String randomColor(){
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length()==1 ? "0" + red : red ;
        //判断绿色代码的位数
        green = green.length()==1 ? "0" + green : green ;
        //判断蓝色代码的位数
        blue = blue.length()==1 ? "0" + blue : blue ;
        //生成十六进制颜色值
        return "#"+red+green+blue;
    }


    /*
     * 将时间戳转换为时间
     *
     * s就是时间戳
     */

    public static String stampToDate(long time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //如果它本来就是long类型的,则不用写这一步
//        long lt = new Long(s);
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }
    /**
     * String str = "2019-03-13 ";
     * 时间字符串转为时间戳
     * */
    public static long  dateToStamp(String str)  {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }

    //类别搜索返回
    public static int typeNameToId(String name){
        //默认11其他
        int id = 11;
        ArrayList<ArticleType> typeList = MyApplication.getTypeList();
        //直接跳出多重循环 如果有多个for
        loop:for (ArticleType type : typeList){
            if (type.getTypeName().equals(name)){
                id =  type.getTypeId();
                break loop;
            }
        }
        return id;
    }
}
