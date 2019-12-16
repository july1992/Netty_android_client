package com.vily.websocket_android_client.utils;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-11-13
 *  
 **/

public class ChannelMapUtils {

    private static Map<String, Channel> manager = new ConcurrentHashMap<>();

    public static void put(String senderNo, Channel channel) {
        manager.put(senderNo, channel);
    }

    public static Channel get(String senderNo) {
        return manager.get(senderNo);
    }

    public static String getSenderNo(Channel channel){

        for (String key : manager.keySet()) {
            if(channel.equals(manager.get(key))){
                return key;
            }
        }

        return null;
    }

    public static void stop(String senderNo){
        Channel channel = get(senderNo);
        channel.close();
    }

    public static void remove(Channel channel){
        manager.values().remove(channel);
    }

}
