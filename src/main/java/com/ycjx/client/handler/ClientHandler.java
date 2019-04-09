package com.ycjx.client.handler;

import com.ycjx.bean.LoginRequestPacket;
import com.ycjx.bean.LoginResponsePacket;
import com.ycjx.bean.Packet;
import com.ycjx.utils.LoginUtil;
import com.ycjx.utils.PacketEncodeCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午11:06
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登陆");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setUserId("8978");
        loginRequestPacket.setUsername("ycjx");
        loginRequestPacket.setPassword("yay");
        ByteBuf byteBuf = PacketEncodeCodec.INSTANCE.encode(loginRequestPacket);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketEncodeCodec.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println("用户ycjx登陆成功");
            } else {
                System.out.println("用户ycjx登陆失败，失败原因:+" + loginResponsePacket.getReason());
            }
        }
    }
}
