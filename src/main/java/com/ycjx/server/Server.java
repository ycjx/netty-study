package com.ycjx.server;

import com.ycjx.server.handler.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/16 下午4:12
 */
public class Server {

    public static void main(String[] args) {
        //负责监听端口
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理读取数据的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) {
                        System.out.println("服务端启动中");
                    }
                })
                .attr(AttributeKey.newInstance("ServerName"), "YCJX")
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
                    }
                })
                .childAttr(AttributeKey.newInstance("oo"), "oo")
                .option(ChannelOption.SO_BACKLOG, 1024)//临时存放已完成三次握手的请求的队列的最大长度
                .childOption(ChannelOption.SO_KEEPALIVE, true)//开启TCP底层心跳机制
                .childOption(ChannelOption.TCP_NODELAY, true);//开启Nagle算法
        serverBootstrap.bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口绑定成功");
            } else {
                System.err.println("端口绑定失败");
            }
        });

    }

}
