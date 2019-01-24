package com.book.five;

import java.io.Serializable;
import java.util.Map;

/**
 * 构建调用前置需要的基本信息
 * @author liyunzheng
 * @date 2017/11/26
 */
public interface LeadRequest<T extends LeadResponse> extends Serializable{


    /**
     * 获取前置请求时间戳
     * @return
     */
    Long getTimeStamp();

    /**
     * 获取appId
     * @return
     */
    String getLeadAppId();

    /**
     * 请求业务参数
     */
    Map<String,Object> getBizContent();

    /**
     * 获取调用api，交由具体的实现者实现
     * @return
     */
    String getApiMethod();

    /**
     * 获取具体响应实现类的定义。
     */
    Class<T> getResponseClass();

    /**
     * 是否保存日志
     */
    boolean isSaveLog();

    /**
     * 是否异步保存日志
     */
    boolean isAsyncSaveLog();

    /**
     * 保存请求日志
     */
    void saveLog(LeadResponse response);


}
