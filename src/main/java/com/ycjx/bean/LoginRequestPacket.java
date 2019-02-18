package com.ycjx.bean;

import lombok.Data;


/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午1:35
 */
@Data
public class LoginRequestPacket extends Packet  {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
