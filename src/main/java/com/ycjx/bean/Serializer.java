package com.ycjx.bean;

/**
 * @author:yuxj
 * @descriptio 序列化接口
 * @create:2019/2/18 下午1:39
 */
public interface Serializer {

    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转为二进制
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转为java对象
     *
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> tClass, byte[] bytes);

}
