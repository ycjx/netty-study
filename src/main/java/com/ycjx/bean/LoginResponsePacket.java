package com.ycjx.bean;

import lombok.Data;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午11:18
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
