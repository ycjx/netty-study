package com.ycjx.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/17 下午8:49
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + "客户端读到的数据 ->" + byteBuf.toString(Charset.forName("utf-8")));

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
       ByteBuf buffer = ctx.alloc().buffer();
       byte[] bytes = "哪路托".getBytes(Charset.forName("utf-8"));
       buffer.writeBytes(bytes);
       return buffer;
    }
}
