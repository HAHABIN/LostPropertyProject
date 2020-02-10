package com.example.habin.lostpropertyproject.Util;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Create by HABIN on 2020/2/1012:30
 * Email:739115041@qq.com
 */
public class ToastUtils {
    private static Application app;
    private ToastUtils() {
    }

    public static void init(Application app) {
        ToastUtils.app = app;
    }

    public static void show_s(String msg) {
        if (app == null) return;
        show_s(app, msg);
    }

    public static void show_l(String msg) {
        if (app == null) return;
        show_l(app, msg);
    }

    public static void show_s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void show_l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
