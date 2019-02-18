package com.ycjx.bean;

import com.alibaba.fastjson.JSON;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019/2/18 下午9:54
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.json;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> tClass, byte[] bytes) {
        return JSON.parseObject(bytes,tClass);
    }
}
