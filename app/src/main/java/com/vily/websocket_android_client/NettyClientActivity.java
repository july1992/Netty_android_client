package com.vily.websocket_android_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.vily.websocket_android_client.client.NettyClient;
import com.vily.websocket_android_client.listener.NettyConnectListener;

public class NettyClientActivity extends AppCompatActivity implements View.OnClickListener {

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


        NettyClient.getInstance().setConnectListener(new NettyConnectListener() {
            @Override
            public void connect(final boolean connect) {
                Log.i(TAG, "connect: --------:"+connect);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIv_connect_state.setSelected(connect);
                    }
                });
            }
        });
    }

    private void initListener() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_connect_server :



                final String ip = mEt_ip.getText().toString().trim();
                final String port = mPort.getText().toString().trim();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NettyClient.getInstance().connect(ip,port);
                    }
                }).start();


                break;


            case R.id.btn_send:
                NettyClient.getInstance().send();

                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        NettyClient.getInstance().close();
    }
}
