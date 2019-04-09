package com.ycjx.bean;

import com.ycjx.utils.Session;
import io.netty.util.AttributeKey;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-20 22:06
 */
public class Attributes {

    public static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");


    public static AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
