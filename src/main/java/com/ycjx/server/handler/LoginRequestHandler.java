package com.ycjx.server.handler;

import com.ycjx.bean.LoginRequestPacket;
import com.ycjx.bean.LoginResponsePacket;
import com.ycjx.utils.LoginUtil;
import com.ycjx.utils.Session;
import com.ycjx.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-24 18:12
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (vaild(msg)) {
            System.out.println("登陆成功");
            //打上登陆成功标记
            LoginUtil.markAsLogin(ctx.channel());
            //session绑定
            SessionUtil.bindSession(new Session(msg.getUserId(),msg.getUsername()),ctx.channel());

            loginResponsePacket.setSuccess(true);
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("密码错误");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //用户断线后取消绑定
        SessionUtil.unBindSession(ctx.channel());
    }


    private boolean vaild(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
