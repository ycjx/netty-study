package com.ycjx.utils;


import com.ycjx.bean.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-02-20 22:07
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> isLogin = channel.attr(Attributes.LOGIN);
        return isLogin.get() != null;
    }
}
