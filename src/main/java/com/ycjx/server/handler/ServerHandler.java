package com.ycjx.server.handler;

import com.ycjx.bean.LoginRequestPacket;
import com.ycjx.bean.LoginResponsePacket;
import com.ycjx.bean.MessageRequestPacket;
import com.ycjx.bean.Packet;
import com.ycjx.utils.PacketEncodeDecode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午11:07
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        ByteBuf byteBuf2 = byteBuf.copy();

        Packet packet = PacketEncodeDecode.INSTANCE.decode(byteBuf);
        ByteBuf responseBuf;
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if(vaild(loginRequestPacket)){
                System.out.println("登陆成功");
                loginResponsePacket.setSuccess(true);
            }else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("密码错误");
            }
            responseBuf = PacketEncodeDecode.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseBuf);
        }else if(packet instanceof MessageRequestPacket){

            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;

            System.out.println(messageRequestPacket.getMessage());
        }
    }

    private boolean vaild(LoginRequestPacket loginRequestPacket){
        if(loginRequestPacket.getUserId() == 8978 &&
                "yay".equals(loginRequestPacket.getPassword())){
            return true;
        }
        return false;
    }
}
