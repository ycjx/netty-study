package com.ycjx.bean;

import lombok.Data;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-20 21:48
 */
@Data
public class MessageRequestPacket extends Packet{


    private String toUserId;


    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
