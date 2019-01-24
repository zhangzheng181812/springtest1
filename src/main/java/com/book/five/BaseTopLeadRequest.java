package com.book.five;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 前置请求基础类，提供相同的参数构造
 * @author liyunzheng
 * @date 2017/11/26
 */
public abstract class BaseTopLeadRequest<T extends LeadResponse> implements LeadRequest<T> {

    @Getter
    @Setter
    private String serialNo;
    @JSONField(name = "tranDate")
    @Getter
    @Setter
    private String tranDate;
    @JSONField(name = "tranTime")
    @Getter
    @Setter
    private String tranTime;
    @JSONField(name = "merchantNo")
    @Getter
    @Setter
    private String merchantNo;

    @Setter
    @Getter
    private String topUpChannelId;



    @Override
    public Long getTimeStamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String getLeadAppId() {
        return "01";
    }

    @Override
    public boolean isSaveLog() {
        return true;
    }

    @Override
    public boolean isAsyncSaveLog() {
        return false;
    }

    @Override
    public void saveLog(LeadResponse response) {
        if (!isSaveLog()) return;
        if (isAsyncSaveLog()){

        }
    }


}
