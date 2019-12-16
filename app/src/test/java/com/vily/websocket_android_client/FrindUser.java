package com.vily.websocket_android_client;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-11-26
 *  
 **/
public class FrindUser {

    private int leapId;
    private String name;

    public FrindUser() {
    }

    public FrindUser(int leapId, String name) {
        this.leapId = leapId;
        this.name = name;
    }

    public int getLeapId() {
        return leapId;
    }

    public void setLeapId(int leapId) {
        this.leapId = leapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FrindUser{" +
                "leapId=" + leapId +
                ", name='" + name + '\'' +
                '}';
    }
}
