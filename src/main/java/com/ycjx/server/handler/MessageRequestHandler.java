package com.ycjx.server.handler;

import com.ycjx.bean.MessageRequestPacket;
import com.ycjx.bean.MessageResponsePacket;
import com.ycjx.utils.Session;
import com.ycjx.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-24 18:15
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {

        Session session = SessionUtil.getSession(ctx.channel());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(msg.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());
        if(toUserChannel!= null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else {
            messageResponsePacket.setFromUserId("服务器");
            messageResponsePacket.setFromUserName("服务器");
            messageResponsePacket.setMessage("[" + msg.getToUserId() + "] 不在线，发送失败!");
            ctx.channel().writeAndFlush(messageResponsePacket);
            System.err.println("[" + msg.getToUserId() + "] 不在线，发送失败!");
        }

        System.out.println("服务端接收消息："+msg.getMessage());

    }
}
