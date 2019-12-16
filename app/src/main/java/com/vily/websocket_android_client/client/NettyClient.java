package com.vily.websocket_android_client.client;

import android.util.Log;


import com.google.gson.Gson;
import com.vily.websocket_android_client.bean.PaketData;
import com.vily.websocket_android_client.bean.Type;
import com.vily.websocket_android_client.listener.NettyConnectListener;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * author : Gecx
 * date : 2019-11-26
 * description : 服务端
 */
public class NettyClient {

    private final String TAG = "NettyClient";
    private Channel socketChannel;


    private static NettyClient instance;
    private ChannelFuture mChannelFuture;

    public static NettyClient getInstance() {
        if (instance == null) {
            synchronized (NettyClient.class) {
                if (instance == null) {
                    instance = new NettyClient();
                }
            }
        }
        return instance;
    }

    private NettyClient() {

    }


    public void connect(String ip, String port) {
        Bootstrap bootstrap = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .option(ChannelOption.TCP_NODELAY, true)//屏蔽Nagle算法试图
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new ChannelHandle());
                        }
                    });
            mChannelFuture = bootstrap.connect(ip, Integer.parseInt(port))
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) {
                            if (future.isSuccess()) {
                                Log.i(TAG, "client connect success");
                                socketChannel = future.channel();
                                if (socketChannel != null) {
                                    Gson gson = new Gson();
                                    socketChannel.writeAndFlush(gson.toJson(new PaketData(1, 2, Type.BIND_SERVER)));
                                }
                            } else {
                                future.channel().close();
                                group.shutdownGracefully();
                                Log.i(TAG, "client connect fail");

                            }
                        }
                    });


            mChannelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
            if(mConnectListener!=null){
                mConnectListener.connect(false);
            }
        }finally {
            group.shutdownGracefully();
        }

    }

    private class ChannelHandle extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) {
            Log.e(TAG, "client channelRead0 : " + msg);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Log.e(TAG, "client exceptionCaught : " + cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            Log.i(TAG, "client channelActive");
            ctx.writeAndFlush(new TextWebSocketFrame("第一次连接"));
            if(mConnectListener!=null){
                mConnectListener.connect(true);
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            Log.e(TAG, "client channelInactive");
            if(mConnectListener!=null){
                mConnectListener.connect(false);
            }
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            Log.e(TAG, "client channelRegistered");

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            Log.e(TAG, "client channelUnregistered");
        }
    }

    public void send() {

        if (socketChannel != null) {
            Log.i(TAG, "send: ------");
            Gson gson = new Gson();

            socketChannel.writeAndFlush(gson.toJson(new PaketData(1, 2, Type.BIND_SERVER)));
        }
    }

    public void close() {
        if (socketChannel != null) {
            socketChannel.close();
            socketChannel = null;
        }
        if(mChannelFuture!=null){
            mChannelFuture=null;
        }

        if(instance!=null){
            instance=null;
        }
    }

    private NettyConnectListener mConnectListener;

    public void setConnectListener(NettyConnectListener connectListener) {
        mConnectListener = connectListener;
    }
}
