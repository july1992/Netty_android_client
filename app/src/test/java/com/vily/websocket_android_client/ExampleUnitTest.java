package com.vily.websocket_android_client;

import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {


        Map<Integer,FrindUser> map1 = new HashMap<>();
        map1.put(2,new FrindUser(2,"sssss"));
        map1.put(3,new FrindUser(3,"aaaaa"));
        String s = JSON.toJSONString(map1);

        System.out.println("---------s:"+s);

        JSONObject s1 = JSON.parseObject(s);
        System.out.println("---------s1:"+s1);



        HashMap map = JSON.parseObject(s,HashMap.class);
        Object o = map.get(2);


        FrindUser user = JSON.parseObject(o.toString(), FrindUser.class);

        Type type = new TypeToken<Map<Integer,FrindUser>>() {}.getType();
        Map<Integer,FrindUser> o1 = JSON.parseObject(s, type);

        System.out.println("---------:"+o1.get(2).toString());

//        ResultVO<List<UserBean>> resultVO = GsonUtils.fromJson(json, type);
    }



}