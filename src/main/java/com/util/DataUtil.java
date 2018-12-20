package com.util;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/9/6.
 */
public class DataUtil {
    /**
     * 封装aeskey,验签,参数加密
     * @param data
     * @param privateKey 己方私钥
     * @param publicKey  对方公钥
     * @return
     * @throws Exception
     */
    public static Map cipherData(Map data, String privateKey, String publicKey) throws Exception {
        Map urlParam = new HashMap();
        //aes加密  base64转码
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(data);
        String aesPassword = CharUtil.createRandomStr(16);
        String paramEncode = Base64Util.encode(AESUtils.encryptAES(jsonObject.toJSONString(), aesPassword));
        //rsa私钥签名  base64转码
        String signEncode = Base64Util.encode(RsaUtil.sign(jsonObject.toJSONString(), privateKey, "utf-8").getBytes());
        //rsa对方公钥加密 base64转码
        String keyPassEncode = Base64Util.encode(RsaUtil.encrypt(publicKey, aesPassword.getBytes()));
        urlParam.put("data", paramEncode);
        urlParam.put("sign", signEncode);
        urlParam.put("keyPass", keyPassEncode);
        return urlParam;
    }

    /**
     * 解密password 解密data
     *
     * @param data       Aes加密后入参
     * @param keypass    rsa加密后password
     * @param privateKey 己方私钥
     * @return
     * @throws Exception
     */
    public static Map unCipherPassAndDate(String data, String keypass, String privateKey) throws Exception {
        Map dataMap = null;
        //解密password
        keypass = new String(RsaUtil.decrypt(privateKey, Base64Util.decode(keypass)));
        //aes解密参数
        String param = new String(AESUtils.decryptAES(data, keypass), "utf-8");
        System.out.println(param);
        dataMap = (Map) JSONObject.parse(param);
        dataMap.put("param", param);
        return dataMap;
    }

    /**
     * 验签
     * @param data
     * @param sign
     * @param publicKey 对方公钥
     * @return
     */
    public static boolean verify(String data, String sign, String publicKey){
        //base64转码
        String signDecode = new String(Base64Util.decode(sign));
        //验证来源
        System.out.println("rsa延签前:"+System.currentTimeMillis());
        boolean verify = RsaUtil.verify(data,signDecode,publicKey,"utf-8");
        System.out.println("rsa延签后:"+System.currentTimeMillis());
        return verify;
    }
}
