package com.ycjx.client;

import com.ycjx.bean.MessageRequestPacket;
import com.ycjx.client.handler.AuthHandler;
import com.ycjx.client.handler.LoginResponseHandle;
import com.ycjx.client.handler.LoginResponseHandle2;
import com.ycjx.client.handler.MessageResponesHandle;
import com.ycjx.utils.LoginUtil;
import com.ycjx.utils.PacketEncodeCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/16 下午5:58
 */
public class Client2 {


    private final static int MAX_RETRY = 1000;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new PacketEncodeCodec())
                                .addLast(new LoginResponseHandle2())
                                .addLast(new AuthHandler())
                                .addLast(new MessageResponesHandle());
                    }
                });
        //建立连接
        connet(workGroup, bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }


    /**
     * 失败重连
     *
     * @param bootstrap
     * @param host
     * @param port
     * @param retry     重连次数
     */
    private static void connet(NioEventLoopGroup workGroup, Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                Channel channel = ((ChannelFuture) future).channel();
                System.out.println("连接成功！");

//                NioSocketChannel channel2 = new NioSocketChannel();
//                workGroup.register(channel2);
//                byte[] bytes = "这是第二个channel".getBytes(Charset.forName("utf-8"));
//
//                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//                buffer.writeBytes(bytes);
//                channel2.writeAndFlush(buffer);
                startConsoleThread(channel);

            } else {
                if (retry == 0) {
                    System.err.println("放弃连接！");
                }
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                bootstrap.config().group().schedule(() -> {
                    connet(workGroup, bootstrap, host, port, retry - 1);
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();


                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setToUserId("8978");
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketEncodeCodec.INSTANCE.encode(packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
