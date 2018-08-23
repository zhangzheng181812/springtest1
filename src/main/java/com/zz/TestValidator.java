package com.zz;

import com.entity.Demo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.ValidateUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/8/23.
 */
@Validated
@Controller
@RequestMapping("/testValidator")
public class TestValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    //处理异常的两种方式  1 全局异常处理 bindException       2 参数组添加BindingResult参数
    @RequestMapping("/test")
    public void test(@Validated Demo demo,BindingResult result) throws JsonProcessingException {
        if(result.hasErrors()){

            List<ObjectError> list = result.getAllErrors();

            for(ObjectError error:list){

                System.out.println(error.getCode()+"---"+error.getArguments()+"---"+error.getDefaultMessage());

            }

        }
        System.out.println(objectMapper.writeValueAsString(demo));
    }

    /**
     * 直接在参数钱加限制，需要在类上添加@Validated主页，并处理异常  ConstraintViolationException
     * @param password
     */
    @RequestMapping("/test2")
    @ResponseBody
    public void test(@Length(min=6,message="密码长度不能小于6位") String password)  {

    }

    @RequestMapping("/test3")
    @ResponseBody
    public void test2() throws IOException {
        HashMap hashMap = new HashMap();
        hashMap.put("id","1");
        hashMap.put("name","2");
        hashMap.put("password","3");
        String s = objectMapper.writeValueAsString(hashMap);
        Demo demo = objectMapper.readValue(s, Demo.class);
        boolean validate = ValidateUtil.validate(demo);
        String s1 = ValidateUtil.validateWithErrorMsg(demo);
        System.out.println(s1);
    }
}
