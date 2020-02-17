package com.example.habin.lostpropertyproject.Util;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.habin.lostpropertyproject.Bean.Local.City.Province;
import com.example.habin.lostpropertyproject.Common.Constants;
import com.example.habin.lostpropertyproject.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * json转换类
 */
public class JsonUtil {
    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    static {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.enableComplexMapKeySerialization();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gsonBuilder.create();
    }

    /**
     * 获取gson对象
     **/
    public static Gson getGson() {
        return gson;
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static String toJson(Object[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(char[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(String[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(short[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(int[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(long[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(float[] arr) {
        return gson.toJson(arr);
    }

    public static String toJson(double[] arr) {
        return gson.toJson(arr);
    }

    public static Object fromJson(String jsonString, Class<?> _class) {
        return gson.fromJson(jsonString, _class);
    }

    /**
     * 将Json格式的字符串转换成指定对象组成的List返回
     * <br>例如：List<"String"> list = json2List("……", new TypeToken<"List<"String">">(){});
     * <br>     List<"Map<"Integer, Object">"> maplist = json2List("……", new TypeToken<"List<"Map<"Integer, Object">">">(){});
     *
     * @param <T>        泛型标识
     * @param jsonString JSON数据格式字符串
     * @param typeToken  目标类型器，标识需要转换成的目标List对象
     * @return
     */
    public static <T> List<T> fromJson(String jsonString, TypeToken<List<T>> typeToken) {
        Type type = typeToken.getType();
        return gson.fromJson(jsonString, type);
    }

    public static void main(String[] args) {
        Map<String, String> maps = new LinkedHashMap<String, String>();
        ;
        maps.put("qq", "<html> fdfdfd\"</html>");
        String str = JsonUtil.toJson(maps);
        System.out.println(str);
        maps.clear();
        maps = (Map<String, String>) JsonUtil.fromJson(str, Map.class);
        System.out.println(maps.get("qq"));


    }

    public static List<JSONObject> jsonArrayToList(JSONArray arr) {
        if (arr == null || arr.length() <= 0) return null;
        try {
            Field valuesField = JSONArray.class.getDeclaredField("values");
            valuesField.setAccessible(true);
            return (List<JSONObject>) valuesField.get(arr);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            try {
                t = gson.fromJson(gsonString, cls);
            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtils.show(UiUtils.getContext(), "数据解析出错");
            }
        }
        return t;
    }

    /**
     * 将JSONObject转成泛型bean
     *
     * @param jsonObject
     * @param cls
     * @return
     */
    public static <T> T JSONObjectToBean(JSONObject jsonObject, Class<T> cls) {
        T t = null;
        if (gson != null) {
            try {
                t = gson.fromJson(jsonObject.toString(), cls);

            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtils.show(UiUtils.getContext(), "数据解析出错");
            }
        }
        return t;
    }

    /**
     * 将对象转成泛型bean
     *
     * @param object
     * @param cls
     * @return
     */
    public static <T> T BeanToBean(Object object, Class<T> cls) {
        T t = null;
        if (gson != null) {
            try {
                t = gson.fromJson(gson.toJson(object), cls);
            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtils.show(UiUtils.getContext(), "数据解析出错");
            }
        }
        return t;
    }

    /**
     * 获取省级数据列表
     * */
    public static ArrayList<Province> JsontoProvince(Context context) {
        //获取assets 目录下的Json文件
        String jsonData = getJson(context, "provice.json");

        ArrayList<Province> provinces = null;
        try {
            //获取省级列表
            provinces = parseData(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinces;
    }

    /**
     * 将json数据转化为省级列表
     * */
    public static ArrayList<Province> parseData(String result) {//Gson 解析
        //动态数组
        ArrayList<Province> detail = new ArrayList<>();
        try {
            //转化为数组json
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                //将json转为实体
                Province entity = gson.fromJson(data.optJSONObject(i).toString(), Province.class);
                //添加到List
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 获取assets 目录下的Json文件
     * */
    public static String getJson(Context context, String fileName) {
        //StringBuilder可变字符串 线程不安全
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //资源管理框架 获取Assets目录下的文件 因为系统在编译的时候不会编译assets下的资源文件，所以需要AssetManager管理
            AssetManager assetManager = context.getAssets();
            //InputStreamReader类是从字节流到字符流的桥接器
            InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(fileName));
            /* BufferedReader类从字符输入流中读取文本并缓冲字符，以便有效地读取字符，数组和行
                读取文本
            * */
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String line;
            //循环获取 逐行读取
            while ((line = bf.readLine()) != null) {
                //拼接字符
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
