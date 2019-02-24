package com.ycjx.utils;

import com.ycjx.bean.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午10:29
 */
public class PacketEncodeCodec extends ByteToMessageCodec<Packet> {

    private static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketEncodeCodec INSTANCE = new PacketEncodeCodec();


    public ByteBuf encode(Packet packet) {

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        //序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(msg);

        //编码过程
        out.writeInt(MAGIC_NUMBER);
        out.writeByte(msg.getVersion());
        out.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        out.writeByte(msg.getCommand());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //跳过magic
        in.skipBytes(4);
        //跳过版本号
        in.skipBytes(1);
        //序列化算法标识
        byte serializeAlgorithm = in.readByte();
        //指令
        byte command = in.readByte();
        //数据包长度
        int length = in.readInt();

        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            out.add(serializer.deserialize(requestType, bytes));
        }

    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过magic
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        if (serializeAlgorithm == SerializerAlgorithm.json) {
            return new JSONSerializer();
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if (command == Command.LOGIN_REQUEST) {
            return LoginRequestPacket.class;
        }
        if (command == Command.LOGIN_RESPONSE) {
            return LoginResponsePacket.class;
        }
        if (command == Command.MESSAGE_REQUEST) {
            return MessageRequestPacket.class;
        }
        if (command == Command.MESSAGE_RESPONES) {
            return MessageResponsePacket.class;
        }
        return null;
    }
}
