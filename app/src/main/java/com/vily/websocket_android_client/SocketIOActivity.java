package com.vily.websocket_android_client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vily.websocket_android_client.client.NettyClient;
import com.vily.websocket_android_client.listener.NettyConnectListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketIOActivity extends AppCompatActivity implements View.OnClickListener {

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
                Socket s = null;
                try {
                    s = new Socket(ip, Integer.parseInt(port));


                    // 获取输出流
                    OutputStream os = s.getOutputStream();
                    os.write("你说啥萨沙沙河撒".getBytes());

                    // 获取输入流
                    InputStream is = s.getInputStream();
                    byte[] bys = new byte[1024];
                    int len = is.read(bys);// 阻塞,所以只有服务器反馈，才能继续向下走
                    String client = new String(bys, 0, len);
                    System.out.println("client:" + client);

                    // 释放资源
                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
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
