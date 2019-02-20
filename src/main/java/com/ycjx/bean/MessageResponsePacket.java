package com.ycjx.bean;

import lombok.Data;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-20 21:51
 */
@Data
public class MessageResponsePacket extends Packet {


    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONES;
    }
}
