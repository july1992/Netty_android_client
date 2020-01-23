package com.vily.websocket_android_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-12-16
 *  
 **/
public class HomeActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
    }

    public void toNetty(View view) {

        Intent intent=new Intent(HomeActivtiy.this,NettyClientActivity.class);
        startActivity(intent);
    }

    public void toWebSocket(View view) {

        Intent intent=new Intent(HomeActivtiy.this,WebSocketClientActivity.class);
        startActivity(intent);
    }

    public void tosocketio(View view) {
        Intent intent=new Intent(HomeActivtiy.this,SocketIO2Activity.class);
        startActivity(intent);
    }
}
