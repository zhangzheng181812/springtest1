package com.book.five;


/**
 * @author liyunzheng
 * @date 2017/11/26
 */
public interface LeadClient {

    /**
     * 执行调用前置Api请求
     * @param request
     * @param <T>
     * @return
     */
    <T extends LeadResponse> T execute(LeadRequest<T> request) throws IllegalAccessException, InstantiationException;


}
