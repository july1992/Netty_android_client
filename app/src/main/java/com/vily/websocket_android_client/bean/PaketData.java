package com.vily.websocket_android_client.bean;

import java.io.Serializable;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-11-22
 *  
 **/
public class PaketData implements Serializable {

    private Byte type;//消息类型

    private int id;

    private String state;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PaketData{" +
                "type=" + type +
                ", id=" + id +
                ", state='" + state + '\'' +
                '}';
    }

}
