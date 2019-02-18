package com.ycjx.utils;

import com.ycjx.bean.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Map;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午10:29
 */
public class PacketEncodeDecode {

    private static final int MAGIC_NUMBER = 0x12345678;


    public ByteBuf encode(Packet packet){

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

    public Packet decode(ByteBuf byteBuf){
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
        if(serializeAlgorithm == SerializerAlgorithm.json){
            return new JSONSerializer();
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if(command == Command.LOGIN_REQUEST){
            return Packet.class;
        }
        return null;
    }
}
