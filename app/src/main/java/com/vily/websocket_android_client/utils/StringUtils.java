package com.vily.websocket_android_client.utils;

import android.text.TextUtils;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-11-22
 *  
 **/
public class StringUtils {

    public static int toInt(String content) {

        int i = -1;
        if (TextUtils.isEmpty(content)) {
            return i;
        }

        try {
            i = Integer.parseInt(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }
}
