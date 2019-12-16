package com.vily.websocket_android_client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vily.websocket_android_client.client.JWebSocketClient;
import com.vily.websocket_android_client.client.NettyClient;
import com.vily.websocket_android_client.listener.NettyConnectListener;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 *  * description : 
 *  * Author : Vily
 *  * Date : 2019-12-16
 *  
 **/
public class WebSocketClientActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "NettyClientActivity";

    private EditText mEt_ip;
    private EditText mPort;
    private Button mBtn_connect_server;
    private ImageView mIv_connect_state;

    private JWebSocketClient mJWebSocketClient;
    private Button mBtn_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_websocket);


        initView();
        initData();
        initListener();
    }



    private void initView() {

        mEt_ip = findViewById(R.id.et_ip);
        mPort = findViewById(R.id.et_port);
        mBtn_connect_server = findViewById(R.id.btn_connect_server);
        mIv_connect_state = findViewById(R.id.iv_connect_state);


        mBtn_send = findViewById(R.id.btn_send);
    }


    private void initData() {
        mBtn_connect_server.setOnClickListener(this);

        mBtn_send.setOnClickListener(this);


    }

    private void initListener() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_connect_server :

                String ip = mEt_ip.getText().toString().trim();
                String port = mPort.getText().toString().trim();
                URI uri = URI.create("ws://"+ip+":"+port+"/ws");



                mJWebSocketClient = new JWebSocketClient(uri) {
                    @Override
                    public void onMessage(final String message) {
                        //message就是接收到的消息
                        Log.i(TAG, "onMessage: ----:"+message);

                    }

                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        Log.i(TAG, "onOpen: -----:");
                       mIv_connect_state.setSelected(true);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.i(TAG, "onClose: ----:");
                        mIv_connect_state.setSelected(false);
                    }
                };

                try {
                    mJWebSocketClient.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;


            case R.id.btn_send:

                Log.i(TAG, "onClick: ----发送:"+"wwwww");
                if (mJWebSocketClient != null && mJWebSocketClient.isOpen()) {
                    mJWebSocketClient.send("wwwwww");
                }
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mJWebSocketClient!=null){
            mJWebSocketClient.close();
            mJWebSocketClient=null;
        }

    }
}
