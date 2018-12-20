package com.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2016/11/13.
 */
public class JsonUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();

    private static final ObjectMapper JSON = new ObjectMapper();

    private static final ObjectMapper JSON_NOFORMAT = new ObjectMapper();

    static {
        //Include.NON_NULL 属性为NULL 不序列化
        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //是否缩放排列输出
        JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
        //当bean中不存在时，忽略json中的多余字段
        JSON.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);



        //Include.NON_NULL 属性为NULL 不序列化
//        JSON_NOFORMAT.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //是否缩放排列输出
        JSON_NOFORMAT.configure(SerializationFeature.INDENT_OUTPUT, Boolean.FALSE);
        //当bean中不存在时，忽略json中的多余字段
        JSON_NOFORMAT.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static <T> String toJsonNf(T t) {
        try {
            return JSON_NOFORMAT.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObjectNf(String str, Class<T> clazz) {
        try {
            return (T) JSON_NOFORMAT.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String toJson(T t) {
        try {
            return JSON.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T toObject(String str, Class<T> clazz) {
        try {
            return (T) JSON.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T cast2(Class<T> clazz, Map map){
        return (T)map;
    }

}
