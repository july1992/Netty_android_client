package com.vily.websocket_android_client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import java.net.URISyntaxException;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;

public class SocketIO2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NettyClientActivity";

    private EditText mEt_ip;
    private EditText mPort;
    private Button mBtn_connect_server;
    private ImageView mIv_connect_state;


    private Button mBtn_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        switch (view.getId()) {
            case R.id.btn_connect_server:


                final String ip = mEt_ip.getText().toString().trim();
                final String port = mPort.getText().toString().trim();


                // 创建客户端Socket对象
                String url = "http://192.168.90.206:3000";

                try {

                    final Socket socket =  IO.socket(url);

                    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                        @Override
                        public void call(Object... args) {
                            Log.i(TAG, "call: ---------开始连接");
                            socket.emit("test", "vily-vily-vily");
                            socket.disconnect();
                        }

                    }).on("client_test", new Emitter.Listener() {

                        @Override
                        public void call(Object... args) {
                            Log.i(TAG, "call: ------收到服务器消息:"+args);
                        }

                    }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                        @Override
                        public void call(Object... args) {
                            Log.i(TAG, "call: -------连接失败");
                        }

                    });
                    socket.connect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;


            case R.id.btn_send:


                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
