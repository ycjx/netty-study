package com.ycjx.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/16 下午5:58
 */
public class Client {


    private final static int MAX_RETRY = 1000;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {

                    }
                });
        //建立连接
        connet(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }


    /**
     * 失败重连
     *
     * @param bootstrap
     * @param host
     * @param port
     * @param retry     重连次数
     */
    private static void connet(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else {
                if (retry == 0) {
                    System.err.println("放弃连接！");
                }
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                bootstrap.config().group().schedule(() -> {
                    connet(bootstrap, host, port, retry - 1);
                }, delay, TimeUnit.SECONDS);
            }
        });
    }
}
