package com.vily.websocket_android_client;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonUtils {

    private static final Gson gson = new Gson();

    /**
     * 将Json串反序列化为ArrayList集合
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> readValuesAsArrayList(String json, Type t) {
        try {
            return gson.fromJson(json, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象序列化为Json串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化成对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化成对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type){
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
