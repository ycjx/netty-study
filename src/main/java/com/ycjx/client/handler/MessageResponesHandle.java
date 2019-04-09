package com.ycjx.client.handler;

import com.ycjx.bean.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-24 18:33
 */
public class MessageResponesHandle extends SimpleChannelInboundHandler<MessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + msg .getMessage());
    }
}
