package com.book.five;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * 调用前置直充Api
 * @author liyunzheng
 * @date 2017/11/26
 */
@Slf4j
public class TopLeadClient implements LeadClient {
    @Getter
    @Setter
    private String hostAddress;

    @Getter
    @Setter
    private String rsaPrivateKey;

    public TopLeadClient(String hostAddress, String rsaPrivateKey){
        this.hostAddress = hostAddress;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String _getFullUrl(String hostAddress,String apiMethod){
        StringBuffer serverUrl = new StringBuffer();
        serverUrl.append(hostAddress);
        serverUrl.append(apiMethod);
        return serverUrl.toString();
    }

    @Override
    public <T extends LeadResponse> T execute(LeadRequest<T> request) throws IllegalAccessException, InstantiationException {
        T res = null;
        Class<T> responseClass = request.getResponseClass();
        res = responseClass.newInstance();
        res.setResultCode("123"+hostAddress);
        return res;

    }
}
