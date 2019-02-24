package com.ycjx.server.handler;

import com.ycjx.bean.LoginRequestPacket;
import com.ycjx.bean.LoginResponsePacket;
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
        if(vaild(msg)){
            System.out.println("登陆成功");
            loginResponsePacket.setSuccess(true);
        }else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("密码错误");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }


    private boolean vaild(LoginRequestPacket loginRequestPacket){
        if(loginRequestPacket.getUserId() == 8978 &&
                "yay".equals(loginRequestPacket.getPassword())){
            return true;
        }
        return false;
    }
}
