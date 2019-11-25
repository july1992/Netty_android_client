package com.vily.websocket_android_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.vily.websocket_android_client.bean.PaketData;
import com.vily.websocket_android_client.bean.Type;
import com.vily.websocket_android_client.client.JWebSocketClient;
import com.vily.websocket_android_client.utils.StringUtils;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText mEt_ip;
    private EditText mPort;
    private Button mBtn_connect_server;
    private ImageView mIv_connect_state;
    private EditText mEt_leapId;
    private Button mBtn_bind_server;
    private Button mBtn_bind_mesh;
    private EditText mEt_send_content;
    private TextView mTv_commond;
    private JWebSocketClient mJWebSocketClient;

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
        mEt_leapId = findViewById(R.id.et_leapId);
        mBtn_bind_server = findViewById(R.id.btn_bind_server);
        mBtn_bind_mesh = findViewById(R.id.btn_bind_mesh);
        mEt_send_content = findViewById(R.id.et_send_content);
        mTv_commond = findViewById(R.id.tv_commond);
    }


    private void initData() {
        mBtn_connect_server.setOnClickListener(this);
        mBtn_bind_server.setOnClickListener(this);
        mBtn_bind_mesh.setOnClickListener(this);
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

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTv_commond.setText("收到来自服务端的消息："+message);
                            }
                        });

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

            case R.id.btn_bind_server :
                String leapId = mEt_leapId.getText().toString().trim();
                PaketData paketData=new PaketData(StringUtils.toInt(leapId),-1, Type.BIND_SERVER);
                Log.i(TAG, "onClick: ----发送:"+paketData);
                if (mJWebSocketClient != null && mJWebSocketClient.isOpen()) {
                    mJWebSocketClient.send(JSON.toJSONString(paketData));
                }
                break;

            case R.id.btn_bind_mesh :
                break;

        }
    }
}
