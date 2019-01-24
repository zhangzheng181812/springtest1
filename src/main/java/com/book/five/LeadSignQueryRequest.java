package com.book.five;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liyunzheng
 * @date 2017/11/27
 */
public class LeadSignQueryRequest extends BaseTopLeadRequest<LeadSignQueryResponse> {

    @Getter
    @Setter
    private String thirdUserOpenId;
    @Getter
    @Setter
    private String uCI;
    @Getter
    @Setter
    private String digitCardNo;



    public Map<String, Object> getBizContent() {
        HashMap<String, Object> params = new HashMap<>();
        return params;
    }

    @Override
    public String getApiMethod() {
        return null;
    }

    @Override
    public Class<LeadSignQueryResponse> getResponseClass() {
        return LeadSignQueryResponse.class;
    }


}
