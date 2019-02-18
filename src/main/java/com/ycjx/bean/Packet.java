package com.ycjx.bean;

import lombok.Data;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午1:22
 */
@Data
public abstract class Packet {

    /**
     * 版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
