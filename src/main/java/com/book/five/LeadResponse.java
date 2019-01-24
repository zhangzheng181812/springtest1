package com.book.five;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 前置响应基本信息
 * @author liyunzheng
 * @date 2017/11/26
 */
@ToString
public class LeadResponse implements Serializable{

    @Getter
    @Setter
    private String resultMsg;


    @Getter
    @Setter
    private String resultCode;

    /**远程调用成功true**/
    public boolean isSuccess(){
        return true;
    }


}
