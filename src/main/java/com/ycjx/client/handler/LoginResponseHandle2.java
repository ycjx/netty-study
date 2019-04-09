package com.ycjx.client.handler;

import com.ycjx.bean.LoginRequestPacket;
import com.ycjx.bean.LoginResponsePacket;
import com.ycjx.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-24 18:30
 */
public class LoginResponseHandle2 extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登陆");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setUserId("5400");
        loginRequestPacket.setUsername("yccc");
        loginRequestPacket.setPassword("yay");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println("login is success");
        } else {
            System.out.println("登陆失败：" + msg.getReason());
        }
    }




}
