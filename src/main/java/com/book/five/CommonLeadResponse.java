package com.book.five;


import lombok.Data;

/**
 * @author liyunzheng
 * @date 2017/11/26
 */
@Data
public class CommonLeadResponse extends LeadResponse {

     private String backSerialNo;
     private String serialNo;
     private String orderNo;
     private String tranDate;
     private String tranTime;
     private String merchantNo;
     private String topUpChannelId;
     private String thirdUserOpenId;

}
