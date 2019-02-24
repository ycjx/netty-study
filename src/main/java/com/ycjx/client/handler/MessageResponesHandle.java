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
        System.out.println("服务端发送消息：" + msg.getMessage());
    }
}
